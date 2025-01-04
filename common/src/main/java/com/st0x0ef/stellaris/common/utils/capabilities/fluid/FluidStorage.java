package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import com.fej1fun.potentials.fluid.BaseFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class FluidStorage extends BaseFluidStorage {

    public FluidStorage(int tanks, long capacity, long maxFill, long maxDrain) {
        super(tanks, capacity, maxFill, maxDrain);
    }
    public FluidStorage(int tanks, long capacity) {
        super(tanks, capacity);
    }

    @Override
    public FluidStack drain(long maxAmount, boolean simulate) {
        FluidStack toReturn = super.drain(maxAmount, simulate);
        if (simulate) onChange();
        return toReturn;
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        FluidStack toReturn = super.drain(stack, simulate);
        if (simulate) onChange();
        return toReturn;
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        long filled = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (!(fluidStacks.get(i).getFluid()==stack.getFluid() || fluidStacks.get(i).isEmpty())) continue;
            if (fluidStacks.get(i).getAmount()>=getTankCapacity(i)) continue;
            filled = Math.clamp(getTankCapacity(i) - getFluidValueInTank(i), 0L, Math.min(this.maxFill, stack.getAmount()));
            if (!simulate) {
                setFluidInTank(i, FluidStack.create(getFluidInTank(i), getFluidValueInTank(i) + filled));
                onChange(i);
            }
            break;
        }
        return filled;
    }

    public void save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        for (int i = 0; i < getTanks(); i++)
            if(!getFluidInTank(i).isEmpty()) {
                Tag tag = new CompoundTag();
                tag = FluidStackHooks.write(provider, getFluidInTank(i), tag);
                compoundTag.put("fluid"+i, tag);
            }
    }

    public void load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        for (AtomicInteger i = new AtomicInteger(); i.get() < getTanks();)
            FluidStack.read(provider, compoundTag.get("fluid"+i)).ifPresent(stack -> setFluidInTank(i.getAndIncrement(), stack));
    }

    public boolean isEmpty() {
        for (FluidStack stack : this)
            if (!stack.isEmpty()) return false;

        return true;
    }

    protected abstract void onChange(int tank);
}
