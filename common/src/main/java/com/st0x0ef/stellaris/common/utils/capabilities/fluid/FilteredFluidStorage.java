package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import dev.architectury.fluid.FluidStack;

import java.util.function.Predicate;

public abstract class FilteredFluidStorage extends FluidStorage {
    private Predicate<FluidStack> isValid;

    public FilteredFluidStorage(int tanks, long capacity, long maxFill, long maxDrain, Predicate<FluidStack> isValid) {
        super(tanks, capacity, maxFill, maxDrain);
        this.isValid = isValid;
    }
    public FilteredFluidStorage(int tanks, long capacity, Predicate<FluidStack> isValid) {
        super(tanks, capacity);
        this.isValid = isValid;
    }

    public void setPredicate(Predicate<FluidStack> isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return isValid.test(stack);
    }
}
