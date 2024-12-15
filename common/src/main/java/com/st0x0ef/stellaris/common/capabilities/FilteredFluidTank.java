package com.st0x0ef.stellaris.common.capabilities;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.level.material.Fluid;

public abstract class FilteredFluidTank extends OnChangeFluidTank {

    public final Fluid fluid;

    public FilteredFluidTank(Fluid fluid, long maxAmount) {
        super(maxAmount);
        this.fluid = fluid;
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return super.isValid(stack) && stack.getFluid()==fluid;
    }
}
