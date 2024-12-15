package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.BaseFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.material.Fluid;

public class FluidTank extends BaseFluidTank {

    private final Fluid fluid;

    public FluidTank(long maxAmount, Fluid Fluid) {
        super(maxAmount, maxAmount, maxAmount);
        this.setFluidStack(FluidStack.create(Fluid, 0));
        this.fluid = Fluid;
    }


    public void drainFluid(long amount) {
        this.addFluid(-amount);
    }

    public void addFluid(long amount) {
        setFluidStack(FluidStack.create(this.fluid, getFluidValue() + amount));
    }

    public void setFluidStack(Fluid stack, long amount) {
        this.setFluidStack(FluidStack.create(stack, amount));
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
