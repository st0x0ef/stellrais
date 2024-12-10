package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.menus.slot.ResultSlot;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LanderMenu extends AbstractContainerMenu {
    private final Container inventory;

    public LanderMenu(int syncId, Inventory inventory, FriendlyByteBuf buffer) {
        this(syncId, inventory, new SimpleContainer(15));
    }

    public LanderMenu(int syncId, Inventory playerInventory, Container container)
    {
        super(MenuTypesRegistry.LANDER_MENU.get(), syncId);

        checkContainerSize(container, 15);
        this.inventory = (container);

        addSlots(inventory);

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasItem()) {
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


    private void addSlots(Container inventory) {
        //FUEL SLOTS
        this.addSlot(new Slot(inventory, 0, 19, 58));
        this.addSlot(new Slot(inventory, 1, 48, 58));

        //INVENTORY SLOTS
        this.addSlot(new Slot(inventory, 2, 83, 19));
        this.addSlot(new Slot(inventory, 3, 83, 37));

        this.addSlot(new Slot(inventory, 4, 101, 19));
        this.addSlot(new Slot(inventory, 5, 101, 37));

        this.addSlot(new Slot(inventory, 6, 119, 19));
        this.addSlot(new Slot(inventory, 7, 119, 37));

        this.addSlot(new Slot(inventory, 8, 137, 19));
        this.addSlot(new Slot(inventory, 9, 137, 37));

        //UPGRADE SLOTS
        this.addSlot(new Slot(inventory, 10, 73, 63));
        this.addSlot(new Slot(inventory, 11, 98, 63));
        this.addSlot(new Slot(inventory, 12, 123, 63));
        this.addSlot(new Slot(inventory, 13, 148, 63));

        //ROCKET SLOT
        this.addSlot(new ResultSlot(inventory, 14, 34, 27));
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, (84 + i * 18) + 8));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 150));
        }
    }
}