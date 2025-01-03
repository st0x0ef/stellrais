package com.st0x0ef.stellaris.common.capabilities.energy;

import com.fej1fun.potentials.energy.BaseEnergyStorage;

public abstract class OnChangeEnergyStorage extends BaseEnergyStorage {

    public OnChangeEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }
    public OnChangeEnergyStorage(int capacity) {
        super(capacity);
    }

    @Override
    public void setEnergyStored(int energy) {
        super.setEnergyStored(energy);
        onChange();
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
