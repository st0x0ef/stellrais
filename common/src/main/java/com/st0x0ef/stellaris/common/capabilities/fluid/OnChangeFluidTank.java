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
    public long fill(FluidStack stack, boolean simulate) {
        long filled = super.fill(stack, simulate);
        if (!simulate) onChange();
        return filled;
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        FluidStack drained = super.drain(stack, simulate);
        if (!simulate) onChange();
        return drained;
    }

    @Override
    public FluidStack drain(long maxDrain, boolean simulate) {
        FluidStack drained = super.drain(maxDrain, simulate);
        if (!simulate) onChange();
        return drained;
    }

    public abstract void onChange();

}
