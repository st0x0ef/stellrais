package com.st0x0ef.stellaris.common.utils.capabilities.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public abstract class SingleFluidStorage implements UniversalFluidStorage {

    protected FluidStack stack;
    protected long capacity;
    protected long maxFill;
    protected long maxDrain;

    public SingleFluidStorage(long capacity, long maxFill, long maxDrain) {
        this.stack = FluidStack.empty();
        this.capacity = capacity;
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
    }
    public SingleFluidStorage(long capacity) {
        this(capacity, capacity, capacity);
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank != 0)
            Stellaris.LOG.error("Tank {} does not exist. SingleFluidStorage only has 1 tank", tank);
        return stack.copy();
    }

    @Override
    public long getTankCapacity(int tank) {
        if (tank != 0)
            Stellaris.LOG.error("Tank {} does not exist. SingleFluidStorage only has 1 tank", tank);
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        if (tank != 0)
            Stellaris.LOG.error("Tank {} does not exist. SingleFluidStorage only has 1 tank", tank);
        return true;
    }

    public long getFluidValueInTank() {
        return stack.getAmount();
    }

    public void setFluidInTank(FluidStack stack) {
        this.stack = stack;
        this.stack.setAmount(Math.clamp(stack.getAmount(), 0, getTankCapacity(0)));
    }

    @Override
    public FluidStack drain(long maxAmount, boolean simulate) {
        long removedAmount = Math.min(Math.min(maxDrain, maxAmount), stack.getAmount());
        stack.shrink(removedAmount);
        onChange();
        return FluidStack.create(stack.getFluid(), removedAmount);
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {

        if (!isFluidValid(0, stack)) return FluidStack.empty();
        if (getFluidInTank(0).isEmpty()) return FluidStack.empty();
        if (getFluidInTank(0).getFluid()!=stack.getFluid()) return FluidStack.empty();

        long drained = Math.min(stack.getAmount(), Math.min(this.maxDrain, stack.getAmount()));
        if (!simulate) {
            this.stack.shrink(drained);
            onChange();
        }

        return FluidStack.create(stack, drained);
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {

        if (!isFluidValid(0, stack)) return 0L;
        if (!(this.stack.getFluid()==stack.getFluid() || this.stack.isEmpty())) return 0L;
        if (this.stack.getAmount()>=getTankCapacity(0)) return 0L;

        long filled = Math.clamp(getTankCapacity(0) - getFluidValueInTank(), 0L, Math.min(this.maxFill, stack.getAmount()));
        if (!simulate) {
            this.stack = FluidStack.create(getFluidInTank(0), getFluidValueInTank() + filled);
            onChange();
        }

        return filled;
    }

    public void save(CompoundTag compoundTag, HolderLookup.Provider provider, String name) {
        if (isEmpty()) return;
        compoundTag.put(name+"-singleFluid", FluidStackHooks.write(provider, getFluidInTank(0), new CompoundTag()));
    }

    public void load(CompoundTag compoundTag, HolderLookup.Provider provider, String name) {
        if (!compoundTag.contains(name+"-singleFluid")) return;
        FluidStack.read(provider, compoundTag.get(name+"-singleFluid")).ifPresent(this::setFluidInTank);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public boolean canGrow() {
        return stack.getAmount() < this.capacity;
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        return List.of(stack).iterator();
    }

    abstract protected void onChange();

}
