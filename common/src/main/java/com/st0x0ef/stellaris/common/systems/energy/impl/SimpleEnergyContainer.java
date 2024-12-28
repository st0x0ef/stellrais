package com.st0x0ef.stellaris.common.systems.energy.impl;

import com.st0x0ef.stellaris.common.systems.SystemsMain;
import com.st0x0ef.stellaris.common.systems.energy.base.EnergySnapshot;
import com.st0x0ef.stellaris.platform.systems.energy.EnergyContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

/**
 * A simple implementation for energy storage.
 * This class should be wrapped by a {@link WrappedBlockEnergyContainer} or a {@link WrappedItemEnergyContainer} to provide the necessary functionality.
 */
public class SimpleEnergyContainer implements EnergyContainer {
    private final long capacity;
    private final long maxInsert;
    private final long maxExtract;
    private long energy;

    public SimpleEnergyContainer(long maxCapacity) {
        this(maxCapacity, 1024, 1024);
    }

    public SimpleEnergyContainer(long maxCapacity, long maxTransfer) {
        this(maxCapacity, maxTransfer, maxTransfer);
    }

    public SimpleEnergyContainer(long maxCapacity, long maxExtract, long maxInsert) {
        this.capacity = maxCapacity;
        this.maxExtract = maxExtract;
        this.maxInsert = maxInsert;
    }

    @Override
    public long insertEnergy(long maxAmount, boolean simulate) {
        long inserted = Mth.clamp(maxAmount, 0, Math.min(maxInsert(), getMaxCapacity() - getStoredEnergy()));
        if (simulate) return inserted;
        this.setEnergy(this.energy + inserted);
        return inserted;
    }

    @Override
    public long extractEnergy(long maxAmount, boolean simulate) {
        long extracted = Mth.clamp(maxAmount, 0, Math.min(maxExtract(), getStoredEnergy()));
        if (simulate) return extracted;
        this.setEnergy(this.energy - extracted);
        return extracted;
    }

    @Override
    public long internalInsert(long maxAmount, boolean simulate) {
        long inserted = Mth.clamp(maxAmount, 0, getMaxCapacity() - getStoredEnergy());
        if (simulate) return inserted;
        this.setEnergy(this.energy + inserted);
        return inserted;
    }

    @Override
    public long internalExtract(long maxAmount, boolean simulate) {
        long extracted = Mth.clamp(maxAmount, 0, getStoredEnergy());
        if (simulate) return extracted;
        this.setEnergy(this.energy - extracted);
        return extracted;
    }

    @Override
    public void setEnergy(long energy) {
        this.energy = energy;
    }

    @Override
    public long getStoredEnergy() {
        return energy;
    }

    @Override
    public long getMaxCapacity() {
        return capacity;
    }

    @Override
    public long maxInsert() {
        return maxInsert;
    }

    @Override
    public long maxExtract() {
        return maxExtract;
    }

    @Override
    public CompoundTag serialize(CompoundTag root) {
        CompoundTag tag = root.getCompound(SystemsMain.SYSTEMS_DATA);
        tag.putLong("Energy", this.energy);
        root.put(SystemsMain.SYSTEMS_DATA, tag);
        return root;
    }

    @Override
    public void deserialize(CompoundTag root) {
        CompoundTag tag = root.getCompound(SystemsMain.SYSTEMS_DATA);
        this.energy = tag.getLong("Energy");
    }

    @Override
    public boolean allowsInsertion() {
        return true;
    }

    @Override
    public boolean allowsExtraction() {
        return true;
    }

    @Override
    public EnergySnapshot createSnapshot() {
        return new SimpleEnergySnapshot(this);
    }

    @Override
    public void clearContent() {
        this.energy = 0;
    }
}
