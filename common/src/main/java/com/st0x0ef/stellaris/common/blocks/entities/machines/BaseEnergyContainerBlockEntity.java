package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import com.st0x0ef.stellaris.common.blocks.entities.ImplementedInventory;
import com.st0x0ef.stellaris.common.utils.capabilities.energy.OnChangeEnergyStorage;
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

public abstract class BaseEnergyContainerBlockEntity extends BaseContainerBlockEntity implements EnergyProvider.BLOCK, ImplementedInventory, TickingBlockEntity {

    public static final String ENERGY_TAG = "stellaris.energy";

    protected @NotNull OnChangeEnergyStorage energy;
    private NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);

    public BaseEnergyContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int initialMaxCapacity, int initialMaxInsert, int initialMaxExtract) {
        super(type, pos, state);
        this.energy = new OnChangeEnergyStorage(initialMaxCapacity, initialMaxInsert, initialMaxExtract) {
            @Override
            protected void onChange() {
                setChanged();
                //TODO networking
            }
        };
    }

    public BaseEnergyContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int initialMaxCapacity) {
        this(type, pos, state, initialMaxCapacity, initialMaxCapacity, initialMaxCapacity);
    }

    public BaseEnergyContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, 15000);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
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
        ContainerHelper.loadAllItems(tag, items, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt(ENERGY_TAG, getEnergy(null).getEnergy());
        ContainerHelper.saveAllItems(tag, items, provider);
    }

    @Override
    public @NotNull BaseEnergyStorage getEnergy(@Nullable Direction direction) {
        return energy;
    }
}