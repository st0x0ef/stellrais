package com.st0x0ef.stellaris.common.capabilities.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import java.util.Collections;
import java.util.Iterator;

public class FluidTank implements UniversalFluidStorage {

    private FluidStack fluidStack = FluidStack.empty();
    private final long maxAmount;

    public FluidTank(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void save(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.save(compoundTag, provider);
    }

    public void load(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.load(compoundTag, provider);
    }

    public void save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        if (!this.getFluidStack().isEmpty()) {
            CompoundTag fluidTag = new CompoundTag();
            FluidStackHooks.write(provider, this.getFluidStack(), fluidTag);
            compoundTag.put("FluidTankData", fluidTag);
        }
    }
    
    public void load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        if (compoundTag.contains("FluidTankData")) {
            CompoundTag fluidTag = compoundTag.getCompound("FluidTankData");
            FluidStack.read(provider, fluidTag).ifPresent(this::setFluidStack);
        } else {
            this.setFluidStack(FluidStack.empty());
        }
    }

    public boolean canGrow() {
        long toReceive = Math.clamp(this.getMaxAmount() - getFluidValue(), 0, Math.min(this.getMaxAmount(), getFluidStack().getAmount()));

        return this.getFluidValue() < this.getMaxAmount();
    }

    @Override
    public int getTanks() {
        // Assuming a single tank for this implementation
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        // Assuming a single tank, return the fluid stack for that tank
        if (tank == 0) {
            return this.getFluidStack();
        }
        throw new IllegalArgumentException("Invalid tank index: " + tank);
    }

    @Override
    public long getTankCapacity(int tank) {
        // Assuming a single tank, return the maximum capacity for that tank
        if (tank == 0) {
            return this.getMaxAmount();
        }
        throw new IllegalArgumentException("Invalid tank index: " + tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        // Assuming a single tank, define logic for valid fluid
        if (tank == 0) {
            // Example logic: check if the fluid is not empty
            return !stack.isEmpty();
        }
        throw new IllegalArgumentException("Invalid tank index: " + tank);
    }

    @Override
    public long fill(FluidStack resource, boolean simulate) {
        // Example implementation: add fluid to the tank
        if (resource.isEmpty()) {
            return 0;
        }

        long space = this.getMaxAmount() - this.getFluidValue();
        long toFill = Math.min(space, resource.getAmount());

        if (!simulate) {
            // Add the fluid to the tank
            this.setFluidStack(FluidStack.create(resource.getFluid(), this.getFluidValue() + toFill));
        }

        return toFill;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean simulate) {
        // Example implementation: remove fluid from the tank
        if (resource.isEmpty() || !resource.isFluidEqual(this.getFluidStack())) {
            return FluidStack.empty();
        }

        long drainedAmount = Math.min(this.getFluidValue(), resource.getAmount());

        if (!simulate) {
            // Remove the fluid from the tank
            this.setFluidStack(FluidStack.create(resource.getFluid(), this.getFluidValue() - drainedAmount));
        }

        return FluidStack.create(resource.getFluid(), drainedAmount);
    }

    @Override
    public FluidStack drain(long maxDrain, boolean simulate) {
        // Example implementation: remove a specified amount of fluid from the tank
        if (maxDrain <= 0 || this.getFluidStack().isEmpty()) {
            return FluidStack.empty();
        }

        long drainedAmount = Math.min(this.getFluidValue(), maxDrain);

        if (!simulate) {
            // Remove the fluid from the tank
            this.setFluidStack(FluidStack.create(this.getFluidStack().getFluid(), this.getFluidValue() - drainedAmount));
        }

        return FluidStack.create(this.getFluidStack().getFluid(), drainedAmount);
    }

    @Override
    public Iterator<FluidStack> iterator() {
        // Return an iterator over a single element (the fluid stack)
        return Collections.singletonList(this.getFluidStack()).iterator();
    }

    public FluidStack getFluidStack() {
        // Return the current fluid stack in the tank
        return this.fluidStack;
    }

    public void setFluidStack(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public long getFluidValue() {
        // Return the amount of fluid in the tank
        return this.fluidStack.getAmount();
    }

    public long getMaxAmount() {
        // Return the maximum capacity of the tank
        return this.maxAmount;
    }
}
