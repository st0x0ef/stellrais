package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.blocks.machines.CoalGeneratorBlock;
import com.st0x0ef.stellaris.common.menus.CoalGeneratorMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.TagRegistry;
import com.st0x0ef.stellaris.platform.systems.energy.EnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity.getFuel;

public class CoalGeneratorEntity extends BaseGeneratorBlockEntity {

    private int litTime;
    private int litDuration;
    public final ContainerData dataAccess = new ContainerData() {

        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return litTime;
                }
                case 1 -> {
                    return litDuration;
                }
                default -> {
                    return 0;
                }
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> litTime = value;
                case 1 -> litDuration = value;
            }
        }

        public int getCount() {
            return 2;
        }
    };

    public CoalGeneratorEntity(BlockPos blockPos, BlockState blockState) {
        this(BlockEntityRegistry.COAL_GENERATOR.get(), blockPos, blockState, 3, 30000);
    }

    public CoalGeneratorEntity(BlockEntityType<?> entityType, BlockPos blockPos, BlockState blockState, int energyGeneratedPT, int maxCapacity) {
        super(entityType, blockPos, blockState, energyGeneratedPT, maxCapacity);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new CoalGeneratorMenu(containerId, inventory, this, this, dataAccess);
    }

    public void tick() {
        boolean wasLit = isLit();
        boolean shouldUpdate = false;

        if (canGenerate()) {
            --litTime;
        }

        ItemStack stack = getItems().getFirst();
        if (!isLit() && !stack.isEmpty()) {
            litTime = getBurnDuration(stack);
            litDuration = litTime;
            if (isLit()) {
                shouldUpdate = true;
                Item item = stack.getItem();
                stack.shrink(1);
                if (stack.isEmpty()) {
                    Item item2 = item.getCraftingRemainingItem();
                    getItems().set(0, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                }
            }
        }

        if (wasLit != isLit()) {
            shouldUpdate = true;
            BlockState state = getBlockState().setValue(CoalGeneratorBlock.LIT, isLit());
            level.setBlock(getBlockPos(), state, 3);
        }

        if (shouldUpdate) {
            setChanged(level, getBlockPos(), getBlockState());
        }

        if (isLit()) {

            if (energy.getStoredAmount() < energy.getCapacity()) {
                energy.insert(getEnergyGeneratedPT(), false);
            }
            else if (energy.getStoredAmount() > energy.getCapacity()) {
                energy.set(energy.getCapacity());
            }
        }

        this.distributeEnergy(null);
    }

    protected int getBurnDuration(ItemStack fuelStack) {
        if (fuelStack.isEmpty() || !fuelStack.is(TagRegistry.COAL_GENERATOR_FUEL_TAG)) {
            return 0;
        }

        return getFuel().getOrDefault(fuelStack.getItem(), 0);
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    @Override
    public boolean canGenerate() {
        boolean isMaxEnergy = energy.getStoredAmount()==energy.getCapacity();
        return isLit() && !isMaxEnergy;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        litTime = tag.getShort("BurnTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putShort("BurnTime", (short) this.litTime);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.coal_generator");
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    protected int getMaxCapacity() {
        return 128000;
    }
}
