package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import dev.architectury.fluid.FluidStack;

import java.util.function.BiPredicate;

public abstract class FilteredFluidStorage extends FluidStorage {
    private BiPredicate<Integer, FluidStack> isValid;

    public FilteredFluidStorage(int tanks, long capacity, long maxFill, long maxDrain, BiPredicate<Integer, FluidStack> isValid) {
        super(tanks, capacity, maxFill, maxDrain);
        this.isValid = isValid;
    }
    public FilteredFluidStorage(int tanks, long capacity, BiPredicate<Integer, FluidStack> isValid) {
        super(tanks, capacity);
        this.isValid = isValid;
    }

    public void setPredicate(BiPredicate<Integer, FluidStack> isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return isValid.test(tank, stack);
    }
}
