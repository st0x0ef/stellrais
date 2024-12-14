package com.st0x0ef.stellaris.common.blocks.machines;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMachineBlock extends BaseTickingEntityBlock {

    public static final Property<Direction> FACING = BlockStateProperties.FACING;

    public BaseMachineBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BaseContainerBlockEntity) {
                MenuRegistry.openExtendedMenu((ServerPlayer) player, getMenuProvider(state, level, pos));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    protected ExtendedMenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BaseContainerBlockEntity containerBlockEntity) {
            return new MenuProvider(pos, containerBlockEntity);
        }
        return null;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BaseContainerBlockEntity containerBlockEntity) {
                Containers.dropContents(level, pos, containerBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
                state.updateNeighbourShapes(level, pos, UPDATE_NEIGHBORS);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction opposite = context.getHorizontalDirection().getOpposite();
        return defaultBlockState().setValue(FACING, opposite);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    private record MenuProvider(BlockPos pos,
                                BaseContainerBlockEntity containerBlockEntity) implements ExtendedMenuProvider {

        @Override
        public void saveExtraData(FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
        }

        @Override
        public Component getDisplayName() {
            return containerBlockEntity.getDisplayName();
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
            return containerBlockEntity.createMenu(syncId, inventory, player);
        }
    }
}
