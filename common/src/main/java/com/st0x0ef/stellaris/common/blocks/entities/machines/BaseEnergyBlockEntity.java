package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.providers.EnergyProvider;
import com.st0x0ef.stellaris.common.network.packets.SyncEnergyPacket;
import com.st0x0ef.stellaris.common.utils.capabilities.energy.EnergyStorage;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseEnergyBlockEntity extends BlockEntity implements EnergyProvider.BLOCK, TickingBlockEntity {

    protected @NotNull EnergyStorage energyContainer;

    public BaseEnergyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxCapacity, int maxInput, int maxOutput) {
        super(type, pos, state);
        this.energyContainer = new EnergyStorage(maxCapacity , maxInput, maxOutput) {
            @Override
            protected void onChange() {
                setChanged();
                if (level != null && level.getServer() != null) {
                    NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(),
                            new SyncEnergyPacket(energyContainer.getEnergy(), getBlockPos(), null));
                }
            }
        };
    }

    public BaseEnergyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxCapacity) {
        this(type, pos, state, maxCapacity, maxCapacity, maxCapacity);
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
        energyContainer.save(tag, "base");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        energyContainer.load(tag, "base");
    }

    @Override
    public @Nullable EnergyStorage getEnergy(@Nullable Direction direction) {
        return energyContainer;
    }
}