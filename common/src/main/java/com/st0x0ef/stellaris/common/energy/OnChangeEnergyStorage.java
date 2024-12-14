package com.st0x0ef.stellaris.common.energy;

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
        onChange();
        super.setEnergyStored(energy);
    }

    @Override
    public int insert(int amount, boolean simulate) {
        if (!simulate) onChange();
        return super.insert(amount, simulate);
    }

    @Override
    public int extract(int amount, boolean simulate) {
        if (!simulate) onChange();
        return super.extract(amount, simulate);
    }

    protected abstract void onChange();
}
