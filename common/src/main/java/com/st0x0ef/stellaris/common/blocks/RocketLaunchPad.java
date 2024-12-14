package com.st0x0ef.stellaris.common.blocks;

import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class RocketLaunchPad extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty STAGE = BlockStateProperties.LIT;

    public static final VoxelShape SHAPE_HIGH = Shapes.box(0, 0, 0, 1, 0.25, 1);
    public static final VoxelShape SHAPE_NORMAL = Shapes.box(0, 0, 0, 1, 0.187, 1);

    public RocketLaunchPad(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(STAGE, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER);
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, STAGE);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return !world.getBlockState(pos.below()).is(state.getBlock());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(STAGE)) {
            return SHAPE_HIGH;
        } else {
            return SHAPE_NORMAL;
        }
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
        p_60567_.scheduleTick(p_60568_, this, 1);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        int y = pos.getY();

        /** POS FOR 3x3 */
        int x = pos.getX() - 1;
        int z = pos.getZ() - 1;

        /** POS FOR 5x5 */
        int x2 = pos.getX() - 2;
        int z2 = pos.getZ() - 2;

        /** LISTS */
        List<Boolean> flag1 = new ArrayList<>();
        List<Boolean> flag2 = new ArrayList<>();

        /** CHECK IF LAUNCH PAD 3x3 */
        for (int i1 = x; i1 < x + 3; i1++) {
            for (int f2 = z; f2 < z + 3; f2++) {
                BlockPos pos2 = new BlockPos(i1, y, f2);

                flag1.add(level.getBlockState(pos2).is(BlocksRegistry.ROCKET_LAUNCH_PAD.get()));
            }
        }

        /** CHECK IF LAUNCH PAD 5x5 (STAGE == FALSE) */
        for (int i1 = x2; i1 < x2 + 5; i1++) {
            for (int f2 = z2; f2 < z2 + 5; f2++) {
                BlockPos pos2 = new BlockPos(i1, y, f2);

                if (level.getBlockState(pos2).is(BlocksRegistry.ROCKET_LAUNCH_PAD.get()) && !pos2.equals(pos)) {
                    flag2.add(level.getBlockState(pos2).getValue(STAGE));
                }
            }
        }

        /** VARIABLE SETTER */
        if (!flag1.contains(false)) {
            if (!state.getValue(STAGE) && !flag2.contains(true)) {
                level.setBlock(pos, state.setValue(STAGE, true), 2);
            }
        } else {
            if (state.getValue(STAGE)) {
                level.setBlock(pos, state.setValue(STAGE, false), 2);
            }
        }

        level.scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }
}
