package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import com.fej1fun.potentials.fluid.BaseFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

@Deprecated
public class FluidTank extends BaseFluidTank {

    public FluidTank(long maxAmount) {
        super(maxAmount, maxAmount, maxAmount);
    }

    public void save(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.save(compoundTag, provider);
    }

    public void load(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.load(compoundTag, provider);
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

    public boolean canGrow() {
        long toReceive = Math.clamp(this.getMaxAmount() - getFluidValue(), 0, Math.min(this.getMaxAmount(), getFluidStack().getAmount()));
        return this.getFluidValue() < this.getMaxAmount();
    }
}
