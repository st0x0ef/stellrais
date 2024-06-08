package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.blocks.entities.machines.SolarPanelEntity;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import com.st0x0ef.stellaris.platform.systems.energy.EnergyContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SolarPanelMenu extends AbstractContainerMenu {

    private final Container inventory;
    private final SolarPanelEntity entity;

    public static SolarPanelMenu create(int syncId, Inventory inventory, FriendlyByteBuf data) {
        SolarPanelEntity entity = (SolarPanelEntity) inventory.player.level().getBlockEntity(data.readBlockPos());

        return new SolarPanelMenu(syncId, inventory, new SimpleContainer(), entity);
    }

    public SolarPanelMenu(int syncId, Inventory playerInventory, Container container, SolarPanelEntity entity)
    {
        super(MenuTypesRegistry.SOLAR_PANEL_MENU.get(), syncId);

        //checkContainerSize(container, 1);
        this.inventory = (container);
        this.entity = entity;

        //TODO fix the slot i didnt have time
        //this.addSlot(new Slot(inventory, 0, 41, 63));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }

    public SolarPanelEntity getBlockEntity() {
        return entity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, (84 + i * 18) + 62));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 204));
        }
    }

    public EnergyContainer getEnergyContainer() {
        return this.entity.getWrappedEnergyContainer().container();
    }
}
