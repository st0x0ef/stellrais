package com.st0x0ef.stellaris.neoforge.systems.item;

import com.st0x0ef.stellaris.common.systems.generic.base.BlockContainerLookup;
import com.st0x0ef.stellaris.neoforge.systems.generic.NeoForgeBlockContainerLookup;
import com.st0x0ef.stellaris.platform.systems.item.base.ItemContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ForgeItemApiBlockLookup implements BlockContainerLookup<ItemContainer, @Nullable Direction> {
    public static final ForgeItemApiBlockLookup INSTANCE = new ForgeItemApiBlockLookup();

    private final NeoForgeBlockContainerLookup<IItemHandler, Direction> lookup = new NeoForgeBlockContainerLookup<>(Capabilities.ItemHandler.BLOCK);

    @Override
    public @Nullable ItemContainer find(Level level, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        return PlatformItemContainer.of(lookup.find(level, pos, state, entity, direction));
    }

    @SafeVarargs
    @Override
    public final void registerBlocks(BlockGetter<ItemContainer, @Nullable Direction> getter, Supplier<Block>... containers) {
        lookup.registerBlocks((level, pos, state, entity, direction) -> ForgeItemContainer.of(getter.getContainer(level, pos, state, entity, direction)), containers);
    }

    @SafeVarargs
    @Override
    public final void registerBlockEntities(BlockGetter<ItemContainer, @Nullable Direction> getter, Supplier<BlockEntityType<?>>... containers) {
        lookup.registerBlockEntities((level, pos, state, entity, direction) -> ForgeItemContainer.of(getter.getContainer(level, pos, state, entity, direction)), containers);
    }
}
