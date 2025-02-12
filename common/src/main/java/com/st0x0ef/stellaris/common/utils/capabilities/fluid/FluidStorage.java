package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import com.fej1fun.potentials.fluid.BaseFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public abstract class FluidStorage extends BaseFluidStorage {

    
    public FluidStorage(int tanks, long capacity, long maxFill, long maxDrain) {
        super(tanks, capacity, maxFill, maxDrain);
    }
    public FluidStorage(int tanks, long capacity) {
        super(tanks, capacity);
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        long filled = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (!(fluidStacks.get(i).getFluid()==stack.getFluid() || fluidStacks.get(i).isEmpty())) continue;
            if (fluidStacks.get(i).getAmount()>=capacity) continue;
            filled = Math.clamp(this.capacity - getFluidValueInTank(i), 0L, Math.min(this.maxFill, stack.getAmount()));
            if (!simulate) {
                fluidStacks.set(i, stack.copyWithAmount(getFluidValueInTank(i) + filled));
                onChange(i);
            }
            break;
        }
        return filled;
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

    // Really hate this method
    // So don't use it pls (it's going to be way slower)
    @Override
    public FluidStack drain(long maxAmount, boolean simulate) {
        AtomicReference<FluidStack> toReturn = new AtomicReference<>(FluidStack.empty());
        fluidStacks.stream().filter(stack -> !stack.isEmpty()).max(Comparator.comparing(FluidStack::getAmount)).ifPresent(stack -> {
            long removedAmount = Math.min(this.maxDrain, Math.min(maxAmount, stack.getAmount()));
            toReturn.set(FluidStack.create(stack.getFluid(), removedAmount));
            if (!simulate) {
                stack.shrink(removedAmount);
                for (int i = 0; i < getTanks(); i++) {
                    if (fluidStacks.get(i).equals(stack)) {
                        onChange(i);
                        break;
                    }
                }
            }
        });
        return toReturn.get();
    }

    @Override
    public long fillWithoutLimits(FluidStack stack, boolean simulate) {
        long filled = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (!(fluidStacks.get(i).getFluid()==stack.getFluid() || fluidStacks.get(i).isEmpty())) continue;
            if (fluidStacks.get(i).getAmount()>=capacity) continue;
            filled = Math.clamp(this.capacity - getFluidValueInTank(i), 0L, stack.getAmount());
            if (!simulate) {
                fluidStacks.set(i, stack.copyWithAmount(getFluidValueInTank(i) + filled));
                onChange(i);
            }
            break;
        }
        return filled;
    }

    @Override
    public FluidStack drainWithoutLimits(FluidStack stack, boolean simulate) {
        long drained = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (getFluidInTank(i).isEmpty()) continue;
            if (getFluidInTank(i).getFluid()!=stack.getFluid()) continue;
            drained = Math.min(getFluidValueInTank(i), stack.getAmount());
            if (!simulate) {
                fluidStacks.get(i).shrink(drained);
                onChange(i);
            }

            break;
        }
        return FluidStack.create(stack, drained);
    }

    public void save(CompoundTag compoundTag, HolderLookup.Provider provider, String name) {
        for (int i = 0; i < getTanks(); i++) {

            if (!getFluidInTank(i).isEmpty()) {
                compoundTag.put(name+"-fluid-"+i, FluidStackHooks.write(provider, getFluidInTank(i), new CompoundTag()));
            }
        }


    }

    public void load(CompoundTag compoundTag, HolderLookup.Provider provider, String name) {
        for (int i = 0; i < getTanks(); i++) {

            if (compoundTag.contains(name+"-fluid-"+i)) {
                setFluidInTank(i, FluidStackHooks.read(provider, compoundTag.get(name+"-fluid-"+i)).orElse(FluidStack.empty()));
            }
        }
    }

    public boolean isEmpty() {
        for (FluidStack stack : this)
            if (!stack.isEmpty()) return false;

        return true;
    }

    public boolean canGrow() {
        return this.getFluidValueInTank(this.getTanks()) < this.getTankCapacity(this.getTanks());
    }

    protected abstract void onChange(int tank);
}
