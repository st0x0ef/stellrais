package com.st0x0ef.stellaris.common.blocks.machines;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.mojang.serialization.MapCodec;
import com.st0x0ef.stellaris.common.blocks.entities.machines.CableBlockEntity;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RenderShape;
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
import org.jetbrains.annotations.NotNull;

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
        Level level = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        BlockState state = this.defaultBlockState();
        PROPERTY_BY_DIRECTION.forEach((direction, booleanProperty) ->
                state.setValue(booleanProperty, isConnectable(level, blockPos.relative(direction), direction.getOpposite())));
        return state;
    }

    private boolean isConnectable(Level level, BlockPos pos, Direction direction) {
        return Capabilities.Energy.BLOCK.getCapability(level, pos, direction)!=null;
    }

//    @Override
//    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
//        BlockEntity blockEntity = levelAccessor.getBlockEntity(blockPos);
//        BlockEntity blockEntityTo = levelAccessor.getBlockEntity(blockPos.relative(direction));
//
//        if (isConnectable(blockEntity, blockEntityTo, blockState2, direction)) {
//            return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction), true);
//        }
//        else {
//            return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction), false);
//        }
//    }


    @Override
    protected @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        BlockEntity entity = levelAccessor.getBlockEntity(pos);
        if (entity!=null) {
            Level level = entity.getLevel();
            if (level!=null)
                if (isConnectable(level, neighborPos, direction.getOpposite()))
                    return state.setValue(PROPERTY_BY_DIRECTION.get(direction), true);
        }
        return state.setValue(PROPERTY_BY_DIRECTION.get(direction), false);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
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
