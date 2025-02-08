package com.st0x0ef.stellaris.common.menus.slot;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

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

        UniversalFluidStorage fluidStorage = Capabilities.Fluid.ITEM.getCapability(stack);
        if(fluidStorage == null) return false;

        if (emptyOnly) {
            for (FluidStack fluidStack : fluidStorage) {
                if (fluidStack.isEmpty()) return true;
            }
            return false;
        }

        for (FluidStack fluidStack : fluidStorage) {
            if (fluidStack.getFluid() == fluid) return true;
        }
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
