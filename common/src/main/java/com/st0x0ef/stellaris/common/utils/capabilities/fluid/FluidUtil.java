package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.fej1fun.potentials.fluid.BaseFluidStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;

public class FluidUtil {
    public static FluidStack moveFluidToItem(int tank, UniversalFluidStorage from, ItemStack stackTo, long amount) {
        UniversalFluidStorage to = Capabilities.Fluid.ITEM.getCapability(stackTo);
        if (to==null) return FluidStack.empty();
        return moveFluid(from, to, FluidStack.create(from.getFluidInTank(tank), amount));
    }

    public static FluidStack moveFluid(UniversalFluidStorage from, UniversalFluidStorage to, FluidStack stack) {
        FluidStack inserted = FluidStack.create(stack ,to.fill(from.drain(stack, true), true));

        if (inserted.isEmpty()) return FluidStack.empty();

        from.drain(inserted, false);
        to.fill(inserted, false);
        return inserted;
    }

    /// ignores max fill/drain limits
    public static FluidStack moveFluidWithSet(BaseFluidStorage from, BaseFluidStorage to, FluidStack stack) {
        FluidStack inserted = FluidStack.create(stack ,to.fill(from.drain(stack, true), true));

        if (inserted.isEmpty()) return FluidStack.empty();

        from.drainWithoutLimits(inserted, false);
        to.fillWithoutLimits(inserted, false);
        return inserted;
    }
}
