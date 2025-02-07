package com.st0x0ef.stellaris.common.menus.slot;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FluidContainerSlot extends Slot {

    private final boolean emptyOnly;
    private final boolean allowTanks;

    public FluidContainerSlot(Container container, int slot, int x, int y, boolean emptyOnly, boolean allowTanks) {
        super(container, slot, x, y);
        this.emptyOnly = emptyOnly;
        this.allowTanks = allowTanks;
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
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
