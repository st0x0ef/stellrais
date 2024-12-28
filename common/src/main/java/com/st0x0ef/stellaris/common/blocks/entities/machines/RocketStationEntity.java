package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.blocks.entities.ImplementedInventory;
import com.st0x0ef.stellaris.common.data.recipes.RocketStationRecipe;
import com.st0x0ef.stellaris.common.data.recipes.input.RocketStationInput;
import com.st0x0ef.stellaris.common.menus.RocketStationMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RocketStationEntity extends BaseContainerBlockEntity implements ImplementedInventory, RecipeInput {

    private NonNullList<ItemStack> items = NonNullList.withSize(15, ItemStack.EMPTY);
    private final RecipeManager.CachedCheck<RocketStationInput, RocketStationRecipe> quickCheck = RecipeManager.createCheck(RecipesRegistry.ROCKET_STATION_TYPE.get());

    public RocketStationEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.ROCKET_STATION.get(), blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.rocket_station");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new RocketStationMenu(i, inventory, this);
    }

    @Override
    public void setChanged() {
        if (this.level != null) {
            this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            super.setChanged();
        }
    }

    @Override
    public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        ContainerHelper.saveAllItems(compoundTag, this.items, provider);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return false;
    }

    public void tick(Level level) {
        if (level.isClientSide()) {
            return;
        }

        ItemStack outputStack = getItem(14);
        if (outputStack.isEmpty() || outputStack.getCount() < outputStack.getMaxStackSize()) {
            Optional<RecipeHolder<RocketStationRecipe>> recipeHolder = quickCheck.getRecipeFor(new RocketStationInput(getLevel().getBlockEntity(getBlockPos()), getItems()), (ServerLevel) level);
            if (recipeHolder.isPresent()) {
                RocketStationRecipe recipe = recipeHolder.get().value();
                ItemStack resultStack = recipe.output();
                if (outputStack.isEmpty() || (ItemStack.isSameItemSameComponents(outputStack, resultStack)
                        && outputStack.getCount() + resultStack.getCount() <= outputStack.getMaxStackSize())) {

                    if (outputStack.isEmpty()) {
                        setItem(14, resultStack.copy());
                    }
                    else if (ItemStack.isSameItemSameComponents(outputStack, resultStack)) {
                        outputStack.grow(1);
                    }
                    else return;

                    for (int i = 0; i < 14; i++) {
                        ItemStack stack = getItem(i);
                        stack.shrink(1);

                        if (stack.isEmpty()) {
                            setItem(i, ItemStack.EMPTY);
                        }
                    }
                    setChanged();
                }
            }
        }
    }

    @Override
    public int size() {
        return getContainerSize();
    }

    @Override
    public int getContainerSize() {
        return 15;
    }
}