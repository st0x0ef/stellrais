package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.systems.SystemsMain;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.energy.EnergyProvider;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseEnergyBlockEntity extends BlockEntity implements EnergyProvider.BlockEntity, TickingBlockEntity {

    private final SimpleValueStorage energy = new SimpleValueStorage(this, SystemsMain.VALUE_CONTENT, 1000);


    public BaseEnergyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract int getMaxCapacity();


    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public ValueStorage getEnergy(@Nullable Direction direction) {
        return energy;
    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
    }

    public void distributeEnergy(@Nullable Direction[] directions) {

        if(directions == null) directions = Direction.values();

        for (Direction direction : directions) {
            ValueStorage foundEnergy = EnergyApi.BLOCK.find(level, getBlockPos().above(), Direction.DOWN);
            if (foundEnergy != null) {
                TransferUtil.moveValue(foundEnergy, energy, 50, false);
            }
        }
    }
}