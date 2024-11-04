package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.blocks.entities.ImplementedInventory;
import com.st0x0ef.stellaris.common.systems.SystemsMain;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.energy.EnergyProvider;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseEnergyContainerBlockEntity extends BaseContainerBlockEntity implements EnergyProvider.BlockEntity, ImplementedInventory, TickingBlockEntity {

    private final SimpleFluidStorage fluids = new SimpleFluidStorage(this, SystemsMain.FLUID_CONTENTS, 1, FluidAmounts.BUCKET);

    public final SimpleValueStorage energy = new SimpleValueStorage(this, SystemsMain.VALUE_CONTENT, 1000);
    private NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);

    public BaseEnergyContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    protected int getMaxCapacity() {
        return 15000;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public ValueStorage getEnergy(@Nullable Direction direction) {
        return energy;
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
        ContainerHelper.loadAllItems(tag, items, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ContainerHelper.saveAllItems(tag, items, provider);
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