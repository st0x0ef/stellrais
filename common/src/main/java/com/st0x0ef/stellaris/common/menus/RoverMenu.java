package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.entities.vehicles.RoverEntity;
import com.st0x0ef.stellaris.common.menus.slot.ResultSlot;
import com.st0x0ef.stellaris.common.menus.slot.upgrade.*;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class RoverMenu extends AbstractContainerMenu implements IVehicleMenu
{

    private final Container inventory;
    private final RoverEntity rover;

    public RoverMenu(int syncId, Inventory inventory, FriendlyByteBuf buffer) {
        this(syncId, inventory, new SimpleContainer(13), buffer.readVarInt());
    }

    public RoverMenu(int syncId, Inventory playerInventory, Container container, int entityId) {
        super(MenuTypesRegistry.ROVER_MENU.get(), syncId);

        this.rover = (RoverEntity) playerInventory.player.level().getEntity(entityId);
        checkContainerSize(container, 13);
        this.inventory = container;

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
        if (!player.isLocalPlayer()) {
            this.getRover().syncRocketData((ServerPlayer) player);
        }

        return this.inventory.stillValid(player);
    }


    private void addSlots(Container inventory) {
        //FUEL SLOTS
        this.addSlot(new VehicleFuelSlot(inventory, 0, 20, 27));
        this.addSlot(new ResultSlot(inventory, 1, 20, 57));

        //UPGRADE SLOTS
        this.addSlot(new MotorUpgradeSlot(inventory, 2, 88, 66, this.rover));
        this.addSlot(new SpeedUpgradeSlot(inventory,3,114,66));
        this.addSlot(new TankUpgradeSlot(inventory, 4, 140, 66));

        //INVENTORY SLOTS
        this.addSlot(new Slot(inventory, 5, 86, 20));
        this.addSlot(new Slot(inventory, 6, 86, 38));

        this.addSlot(new Slot(inventory, 7, 104, 20));
        this.addSlot(new Slot(inventory, 8, 104, 38));

        this.addSlot(new Slot(inventory, 9, 122, 20));
        this.addSlot(new Slot(inventory, 10, 122, 38));

        this.addSlot(new Slot(inventory, 11, 140, 20));
        this.addSlot(new Slot(inventory, 12, 140, 38));
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, (84 + i * 18) + 11));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 153));
        }
    }

    public RoverEntity getRover() {
        return rover;
    }

    @Override
    public int getFuel() {
        return getRover().getFuel();
    }
}
