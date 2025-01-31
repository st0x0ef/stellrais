package com.st0x0ef.stellaris.common.utils.capabilities.energy;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.st0x0ef.stellaris.Stellaris;
import net.minecraft.nbt.CompoundTag;

public abstract class EnergyStorage extends BaseEnergyStorage {

    public EnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }
    public EnergyStorage(int capacity) {
        super(capacity);
    }

    @Override
    public int insert(int amount, boolean simulate) {
        int inserted = super.insert(amount, simulate);
        if (!simulate) onChange();
        return inserted;
    }

    @Override
    public int extract(int amount, boolean simulate) {
        int extracted = super.extract(amount, simulate);
        if (!simulate) onChange();
        return extracted;
    }

    public void save(CompoundTag tag, String name) {
        tag.putInt("energy-"+name, energy);
    }

    public void load(CompoundTag tag, String name) {
        this.energy = tag.getInt("energy-"+name);
    }

    protected abstract void onChange();
}
