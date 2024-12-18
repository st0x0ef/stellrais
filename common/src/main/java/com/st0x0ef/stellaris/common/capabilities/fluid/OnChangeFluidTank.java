package com.st0x0ef.stellaris.common.capabilities.fluid;

import dev.architectury.fluid.FluidStack;

public abstract class OnChangeFluidTank extends FluidTank {

    public OnChangeFluidTank(long maxAmount) {
        super(maxAmount);
    }

    @Override
    public void setFluidStack(FluidStack stack) {
        super.setFluidStack(stack);
        onChange();
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        long filled = super.fillFluid(stack, simulate);
        if (!simulate) onChange();
        return filled;
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        long drained = super.drainFluid(stack, simulate);
        if (!simulate) onChange();
        return drained;
    }

    public abstract void onChange();

}
