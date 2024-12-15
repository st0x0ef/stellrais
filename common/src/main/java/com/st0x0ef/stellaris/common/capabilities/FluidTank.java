package com.st0x0ef.stellaris.common.capabilities;

import com.fej1fun.potentials.fluid.BaseFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class FluidTank extends BaseFluidTank {

    public FluidTank(long maxAmount) {
        super(maxAmount, maxAmount, maxAmount);
    }

    public void save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        if(!this.getFluidStack().isEmpty()) {
            Tag tag = new CompoundTag();
            tag = FluidStackHooks.write(provider, this.getFluidStack(), tag);
            compoundTag.put("fluid", tag);
        }
    }

    public void load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        FluidStack.read(provider, compoundTag).ifPresent(this::setFluidStack);
    }
}
