package com.st0x0ef.stellaris.neoforge.systems.item;

import com.st0x0ef.stellaris.common.systems.generic.base.ItemContainerLookup;
import com.st0x0ef.stellaris.neoforge.systems.generic.NeoForgeItemContainerLookup;
import com.st0x0ef.stellaris.platform.systems.item.base.ItemContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ForgeItemApiItemLookup implements ItemContainerLookup<ItemContainer, Void> {
    public static final ForgeItemApiItemLookup INSTANCE = new ForgeItemApiItemLookup();
    private final NeoForgeItemContainerLookup<IItemHandler, Void> lookup = new NeoForgeItemContainerLookup<>(Capabilities.ItemHandler.ITEM);

    @Override
    public ItemContainer find(ItemStack stack, @Nullable Void context) {
        return PlatformItemContainer.of(lookup.find(stack, context));
    }

    @SafeVarargs
    @Override
    public final void registerItems(ItemGetter<ItemContainer, Void> getter, Supplier<Item>... containers) {
        lookup.registerItems((stack, context) -> ForgeItemContainer.of(getter.getContainer(stack, context)), containers);
    }
}
