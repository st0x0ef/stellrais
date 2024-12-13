package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.st0x0ef.stellaris.common.blocks.entities.machines.BaseEnergyContainerBlockEntity.ENERGY_TAG;

public abstract class BaseEnergyBlockEntity extends BlockEntity implements EnergyProvider.BLOCK, TickingBlockEntity {

    protected @NotNull BaseEnergyStorage energyContainer;

    public BaseEnergyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxCapacity) {
        super(type, pos, state);
        this.energyContainer = new BaseEnergyStorage(maxCapacity, maxCapacity, maxCapacity);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        getEnergy(null).setEnergyStored(tag.getInt(ENERGY_TAG));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putLong(ENERGY_TAG, getEnergy(null).getEnergy());
    }

    @Override
    public @NotNull BaseEnergyStorage getEnergy(@Nullable Direction direction) {
        return energyContainer;
    }
}