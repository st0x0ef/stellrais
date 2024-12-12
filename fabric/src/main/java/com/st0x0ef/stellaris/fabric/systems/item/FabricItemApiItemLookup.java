package com.st0x0ef.stellaris.fabric.systems.item;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.fabric.systems.generic.FabricItemContainerLookup;
import com.st0x0ef.stellaris.platform.systems.item.base.ItemContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricItemApiItemLookup extends FabricItemContainerLookup<ItemContainer, Void> {
    public static final FabricItemApiItemLookup INSTANCE = new FabricItemApiItemLookup();

    public FabricItemApiItemLookup() {
        super(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "item"), ItemContainer.class, Void.class);
    }

    @SafeVarargs
    @Override
    public final void registerItems(ItemGetter<ItemContainer, Void> getter, Supplier<Item>... containers) {
        super.registerItems((stack, context) -> UpdatingItemContainer.of(getter.getContainer(stack, context)), containers);
    }
}
