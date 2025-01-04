package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.providers.EnergyProvider;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import com.st0x0ef.stellaris.common.network.packets.SyncEnergyPacket;
import com.st0x0ef.stellaris.common.utils.capabilities.energy.EnergyStorage;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
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

    protected @NotNull EnergyStorage energyContainer;

    public BaseEnergyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxCapacity) {
        super(type, pos, state);
        this.energyContainer = new EnergyStorage(maxCapacity) {
            @Override
            protected void onChange() {
                setChanged();
            }
        };
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
    public @NotNull EnergyStorage getEnergy(@Nullable Direction direction) {
        return energyContainer;
    }
}