package com.st0x0ef.stellaris.common.blocks.machines;

import com.mojang.serialization.MapCodec;
import com.st0x0ef.stellaris.common.blocks.entities.machines.CableBlockEntity;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.TagRegistry;
import com.st0x0ef.stellaris.common.systems.energy.base.EnergyBlock;
import com.st0x0ef.stellaris.platform.systems.energy.CableUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class CableBlock extends BaseTickingEntityBlock {
    private static final Direction[] DIRECTIONS = Direction.values();
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty DOWN = PipeBlock.DOWN;
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
    protected final VoxelShape[] shapeByIndex;

    public CableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((this.stateDefinition.any())
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false));
        this.shapeByIndex = this.makeShapes(0.125f);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return BlockEntityRegistry.CABLE_ENTITY.get();
    }

    @Override
    public boolean hasTicker(Level level) {
        return !level.isClientSide;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CableBlock::new);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Level blockGetter = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        return this.defaultBlockState()
                .setValue(DOWN, isConnectable(blockEntity, blockGetter.getBlockEntity(blockPos.below()), blockGetter.getBlockState(blockPos.below()), Direction.DOWN))
                .setValue(UP, isConnectable(blockEntity, blockGetter.getBlockEntity(blockPos.above()), blockGetter.getBlockState(blockPos.above()), Direction.UP))
                .setValue(NORTH, isConnectable(blockEntity, blockGetter.getBlockEntity(blockPos.north()), blockGetter.getBlockState(blockPos.north()), Direction.NORTH))
                .setValue(EAST, isConnectable(blockEntity, blockGetter.getBlockEntity(blockPos.east()), blockGetter.getBlockState(blockPos.east()), Direction.EAST))
                .setValue(SOUTH, isConnectable(blockEntity, blockGetter.getBlockEntity(blockPos.south()), blockGetter.getBlockState(blockPos.south()), Direction.SOUTH))
                .setValue(WEST, isConnectable(blockEntity, blockGetter.getBlockEntity(blockPos.west()), blockGetter.getBlockState(blockPos.west()), Direction.WEST));
    }

    private boolean isConnectable(BlockEntity blockEntity,BlockEntity blockEntityTo, BlockState blockStateTo, Direction direction) {
        if (!blockStateTo.is(this) && !blockStateTo.is(TagRegistry.ENERGY_BLOCK_TAG) && !(blockEntityTo instanceof EnergyBlock<?>)) {
            CableUtil.isEnergyContainer(blockEntityTo, direction);
        }
        return true;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        BlockEntity blockEntityTo = level.getBlockEntity(pos.relative(direction));

        if (isConnectable(blockEntity, blockEntityTo, neighborState, direction)) {
            return state.setValue(PROPERTY_BY_DIRECTION.get(direction), true);
        }
        else {
            return state.setValue(PROPERTY_BY_DIRECTION.get(direction), false);
        }
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.getBlock() != blockState2.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof CableBlockEntity) {
                blockState.updateNeighbourShapes(level,blockPos,UPDATE_NEIGHBORS);
            }
            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapeByIndex[this.getAABBIndex(state)];
    }

    private VoxelShape[] makeShapes(float apothem) {
        float f = 0.5F - apothem;
        float g = 0.5F + apothem;
        VoxelShape voxelShape = Block.box(f * 16.0F, f * 16.0F, f * 16.0F, g * 16.0F, g * 16.0F, g * 16.0F);
        VoxelShape[] voxelShapes = new VoxelShape[DIRECTIONS.length];

        for(int i = 0; i < DIRECTIONS.length; ++i) {
            Direction direction = DIRECTIONS[i];
            voxelShapes[i] = Shapes.box(0.5 + Math.min(-apothem, (double)direction.getStepX() * 0.5), 0.5 + Math.min(-apothem, (double)direction.getStepY() * 0.5), 0.5 + Math.min(-apothem, (double)direction.getStepZ() * 0.5), 0.5 + Math.max(apothem, (double)direction.getStepX() * 0.5), 0.5 + Math.max(apothem, (double)direction.getStepY() * 0.5), 0.5 + Math.max(apothem, (double)direction.getStepZ() * 0.5));
        }

        VoxelShape[] voxelShapes2 = new VoxelShape[64];

        for(int j = 0; j < 64; ++j) {
            VoxelShape voxelShape2 = voxelShape;

            for(int k = 0; k < DIRECTIONS.length; ++k) {
                if ((j & 1 << k) != 0) {
                    voxelShape2 = Shapes.or(voxelShape2, voxelShapes[k]);
                }
            }

            voxelShapes2[j] = voxelShape2;
        }

        return voxelShapes2;
    }

    protected int getAABBIndex(BlockState state) {
        int i = 0;

        for(int j = 0; j < DIRECTIONS.length; ++j) {
            if ((Boolean) state.getValue((Property<?>)PROPERTY_BY_DIRECTION.get(DIRECTIONS[j]))) {
                i |= 1 << j;
            }
        }

        return i;
    }
}
