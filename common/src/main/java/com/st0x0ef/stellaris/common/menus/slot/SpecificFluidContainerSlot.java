package com.st0x0ef.stellaris.common.menus.slot;

import com.st0x0ef.stellaris.common.items.OxygenTankItem;
import com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor;
import dev.architectury.hooks.fluid.FluidBucketHooks;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class SpecificFluidContainerSlot extends Slot {

    private final boolean emptyOnly;
    private final Fluid fluid;

    public SpecificFluidContainerSlot(Container container, Fluid fluid, int slot, int x, int y, boolean emptyOnly) {
        super(container, slot, x, y);
        this.emptyOnly = emptyOnly;
        this.fluid = fluid;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() instanceof BucketItem item) {
            if (emptyOnly) {
                return FluidBucketHooks.getFluid(item).isSame(Fluids.EMPTY);
            } else {
                return FluidBucketHooks.getFluid(item).isSame(fluid);
            }
        } // TODO : check for fluid tank from other mods when potentials will be fully integrated

        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
