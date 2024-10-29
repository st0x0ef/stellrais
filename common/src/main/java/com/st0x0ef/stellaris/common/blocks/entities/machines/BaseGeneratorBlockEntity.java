package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.systems.energy.EnergyApi;
import com.st0x0ef.stellaris.common.systems.energy.impl.ExtractOnlyEnergyContainer;
import com.st0x0ef.stellaris.common.systems.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class BaseGeneratorBlockEntity extends BaseEnergyContainerBlockEntity {

    private WrappedBlockEnergyContainer energyContainer;

    protected int energyGeneratedPT;
    private final int maxCapacity;

    public BaseGeneratorBlockEntity(BlockEntityType<?> entityType, BlockPos blockPos, BlockState blockState, int energyGeneratedPT, int maxCapacity) {
        super(entityType, blockPos, blockState);
        this.energyGeneratedPT = energyGeneratedPT;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public final WrappedBlockEnergyContainer getEnergyStorage(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(entity, new ExtractOnlyEnergyContainer(maxCapacity, Integer.MAX_VALUE)) : energyContainer;
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
        if (canGenerate()) {
            WrappedBlockEnergyContainer container = getWrappedEnergyContainer();
            if (container.getStoredEnergy() < container.getMaxCapacity()) {
                container.setEnergy(container.getStoredEnergy() + energyGeneratedPT);
            }
            else if (container.getStoredEnergy() > container.getMaxCapacity()) {
                container.setEnergy(container.getMaxCapacity());
            }
        }
        EnergyApi.distributeEnergyNearby(this, 100);
    }
}
