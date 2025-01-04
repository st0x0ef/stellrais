package com.st0x0ef.stellaris.common.utils.capabilities.energy;

import com.fej1fun.potentials.energy.BaseEnergyStorage;

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

    protected abstract void onChange();
}
