package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.blocks.entities.machines.CoalGeneratorEntity;
import com.st0x0ef.stellaris.common.menus.slot.CoalGeneratorSlot;
import com.st0x0ef.stellaris.common.network.packets.SyncWidgetsTanksPacket;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CoalGeneratorMenu extends AbstractContainerMenu {
    private final Container inventory;
    private final CoalGeneratorEntity entity;
    private final ContainerData data;



    public static CoalGeneratorMenu create(int syncId, Inventory inventory, FriendlyByteBuf data) {
        CoalGeneratorEntity entity = (CoalGeneratorEntity) inventory.player.level().getBlockEntity(data.readBlockPos());

        return new CoalGeneratorMenu(syncId, inventory, new SimpleContainer(1), entity, new SimpleContainerData(2));
    }

    public CoalGeneratorMenu(int syncId, Inventory playerInventory, Container container, CoalGeneratorEntity entity, ContainerData containerData)
    {
        super(MenuTypesRegistry.COAL_GENERATOR_MENU.get(), syncId);

        checkContainerSize(container, 1);
        this.inventory = container;
        this.entity = entity;
        this.data = containerData;

        this.addSlot(new CoalGeneratorSlot(inventory, 0, 46, 66));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addDataSlots(containerData);
    }

    public CoalGeneratorEntity getBlockEntity() {
        return entity;
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
//        if(!player.isLocalPlayer()) {
//            this.syncBattery((ServerPlayer) player);
//        }

        return this.inventory.stillValid(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, (84 + i * 18) + 58));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 200));
        }
    }

    public float getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return Mth.clamp((float)this.data.get(0) / (float)i, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    public void syncBattery(ServerPlayer player) {
        if (!player.level().isClientSide()) {

            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(
                    new long[]{this.getBlockEntity().getEnergy(null).getEnergy()}
            ));

        }
    }
}
