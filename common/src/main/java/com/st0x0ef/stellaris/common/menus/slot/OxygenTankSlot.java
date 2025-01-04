package com.st0x0ef.stellaris.common.menus.slot;

import com.st0x0ef.stellaris.common.items.OxygenTankItem;
import com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import dev.architectury.hooks.fluid.FluidBucketHooks;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

public class OxygenTankSlot extends Slot {

    public OxygenTankSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem() instanceof OxygenTankItem || itemStack.getItem() instanceof AbstractSpaceArmor.AbstractSpaceChestplate
                || itemStack.getItem() instanceof BucketItem item
                && (FluidBucketHooks.getFluid(item).isSame(FluidRegistry.FLOWING_OXYGEN.get()));
    }

    public int getMaxStackSize() {
        return 1;
    }
}
