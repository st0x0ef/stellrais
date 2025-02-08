package com.st0x0ef.stellaris.common.menus.slot;

import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class VehicleFuelSlot extends Slot {
    public VehicleFuelSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ItemsRegistry.FUEL_BUCKET.get()) || stack.is(ItemsRegistry.HYDROGEN_BUCKET.get()) || stack.has(DataComponentsRegistry.RADIOACTIVE.get());
    }
}
