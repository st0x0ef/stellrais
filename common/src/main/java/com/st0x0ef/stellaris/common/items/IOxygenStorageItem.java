package com.st0x0ef.stellaris.common.items;

import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FilteredFluidStorage;

public interface IOxygenStorageItem {
    FilteredFluidStorage oxygenTank = new FilteredFluidStorage(1, 3000, 3000, 3000, (n,fluidStack) -> fluidStack.getFluid().isSame(FluidRegistry.FLOWING_OXYGEN.get())) {
        @Override
        protected void onChange(int i) {}
    };

    default FilteredFluidStorage getOxygenTank() {
        return oxygenTank;
    }
}
