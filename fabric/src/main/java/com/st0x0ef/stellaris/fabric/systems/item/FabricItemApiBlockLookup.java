package com.st0x0ef.stellaris.fabric.systems.item;

import com.st0x0ef.stellaris.common.systems.generic.base.BlockContainerLookup;
import com.st0x0ef.stellaris.platform.systems.item.base.ItemContainer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FabricItemApiBlockLookup implements BlockContainerLookup<ItemContainer, Direction> {
    public static final FabricItemApiBlockLookup INSTANCE = new FabricItemApiBlockLookup();

    @Override
    public @Nullable ItemContainer find(Level level, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        return PlatformItemContainer.of(level, pos, state, entity, direction);
    }

    @SafeVarargs
    @Override
    public final void registerBlocks(BlockGetter<ItemContainer, Direction> getter, Supplier<Block>... containers) {
        for (Supplier<Block> container : containers) {
            ItemStorage.SIDED.registerForBlocks((level, pos, state, entity, dir) -> FabricItemContainer.of(getter.getContainer(level, pos, state, entity, dir)), container.get());
        }
    }

    @SafeVarargs
    @Override
    public final void registerBlockEntities(BlockGetter<ItemContainer, Direction> getter, Supplier<BlockEntityType<?>>... containers) {
        for (Supplier<BlockEntityType<?>> container : containers) {
            ItemStorage.SIDED.registerForBlockEntity((entity, direction) -> FabricItemContainer.of(getter.getContainer(entity.getLevel(), entity.getBlockPos(), entity.getBlockState(), entity, direction)), container.get());
        }
    }
}
