package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.utils.capabilities.energy.EnergyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseGeneratorBlockEntity extends BaseEnergyContainerBlockEntity {

    protected int energyGeneratedPT;
    protected final int maxCapacity;

    public BaseGeneratorBlockEntity(BlockEntityType<?> entityType, BlockPos blockPos, BlockState blockState, int energyGeneratedPT, int maxCapacity) {
        super(entityType, blockPos, blockState, maxCapacity, energyGeneratedPT, maxCapacity);
        this.energyGeneratedPT = energyGeneratedPT;
        this.maxCapacity = maxCapacity;
    }

    public int getEnergyGeneratedPT() {
        return energyGeneratedPT;
    }

    @Override
    public void setChanged() {
        if (this.level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 1);
            super.setChanged();
        }
    }

    public abstract boolean canGenerate();

    @Override
    public void tick() {
        if (canGenerate())
            energyContainer.insert(energyGeneratedPT, false);
        EnergyUtil.distributeEnergyNearby(level, worldPosition, maxCapacity);
    }
}
