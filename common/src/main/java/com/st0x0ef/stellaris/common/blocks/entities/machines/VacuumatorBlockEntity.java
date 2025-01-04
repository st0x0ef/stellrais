package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.blocks.entities.ImplementedInventory;
import com.st0x0ef.stellaris.common.items.CanItem;
import com.st0x0ef.stellaris.common.menus.VacuumatorMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VacuumatorBlockEntity extends BaseContainerBlockEntity implements ImplementedInventory, TickingBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);

    public VacuumatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.VACUUMATOR_ENTITY.get(), blockPos, blockState);
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new VacuumatorMenu(i, inventory, this);
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
    protected @NotNull Component getDefaultName() {
        return Component.translatable("block.stellaris.vacuumator");
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction direction) {
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

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    public void tick() {
        if (canCraft()) {
            craft();
            setChanged();
        }
    }

    public boolean canCraft() {
        if (getItem(0).getItem() instanceof CanItem) {
            return isFood(getItem(1)) && getItem(2).is(Items.GLASS_BOTTLE) && getItem(3).isEmpty() && getItem(4).isEmpty();
        }

        return false;
    }

    public void craft() {
        ItemStack canStack = getItem(0);

        ItemStack resultStack = new ItemStack(canStack.getItem());
        CanItem.setFoodProperties(resultStack, CanItem.getFoodProperties(canStack));


        if (CanItem.addFoodToCan(resultStack, getItem(1))) {
            for (int i = 0; i < 3; i++) {
                if(getItem(i).getCount() >= 1) {
                    removeItem(i, 1);
                }
            }

            setItem(3, resultStack);
            setItem(4, PotionContents.createItemStack(Items.POTION, Potions.WATER));

        }


    }

    public static boolean isFood(ItemStack food) {
        return food.has(DataComponents.FOOD);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }
}