package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import com.fej1fun.potentials.fluid.BaseFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class FluidStorage extends BaseFluidStorage {

    public FluidStorage(int tanks, long capacity, long maxFill, long maxDrain) {
        super(tanks, capacity, maxFill, maxDrain);
    }
    public FluidStorage(int tanks, long capacity) {
        super(tanks, capacity);
    }

    @Override
    public FluidStack drain(long maxAmount, boolean simulate) {
        AtomicReference<FluidStack> toReturn = new AtomicReference<>(FluidStack.empty());
        fluidStacks.stream().filter(stack -> !stack.isEmpty()).max(Comparator.comparing(FluidStack::getAmount)).ifPresent(stack -> {
            int i;
            for (i = 0; i < getTanks(); i++) {
                if (stack.isFluidStackEqual(getFluidInTank(i))) break;
            }
            long removedAmount = Math.min(maxAmount, stack.getAmount());
            toReturn.set(FluidStack.create(stack.getFluid(), removedAmount));
            stack.shrink(removedAmount);
            onChange(i);
        });
        return toReturn.get();
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        long drained = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (getFluidInTank(i).isEmpty()) continue;
            if (getFluidInTank(i).getFluid()!=stack.getFluid()) continue;
            drained = Math.min(getFluidValueInTank(i), Math.min(this.maxDrain, stack.getAmount()));
            if (!simulate) {
                setFluidInTank(i, FluidStack.create(getFluidInTank(i), getFluidValueInTank(i) - drained));
                onChange(i);
            }
            break;
        }
        return FluidStack.create(stack, drained);
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
