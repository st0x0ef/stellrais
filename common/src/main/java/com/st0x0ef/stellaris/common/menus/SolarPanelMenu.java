package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.blocks.entities.machines.SolarPanelEntity;
import com.st0x0ef.stellaris.common.network.packets.SyncWidgetsTanksPacket;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class SolarPanelMenu extends BaseContainer {

    private final Container inventory;
    private final SolarPanelEntity blockEntity;

    public static SolarPanelMenu create(int syncId, Inventory inventory, FriendlyByteBuf data) {
        SolarPanelEntity entity = (SolarPanelEntity) inventory.player.level().getBlockEntity(data.readBlockPos());
        return new SolarPanelMenu(syncId, inventory, new SimpleContainer(1), entity);
    }

    public SolarPanelMenu(int syncId, Inventory playerInventory, Container container, SolarPanelEntity entity) {
        super(MenuTypesRegistry.SOLAR_PANEL_MENU.get(), syncId, 1, playerInventory, 22);

        checkContainerSize(container, 1);
        this.inventory = container;
        this.blockEntity = entity;

        addSlot(new Slot(inventory, 0, 38, 44));

    }

    public SolarPanelEntity getBlockEntity() {
        return blockEntity;
    }


    @Override
    public boolean stillValid(Player player) {
        if (!player.isLocalPlayer()) {
            this.syncBattery((ServerPlayer) player);
        }

        return inventory.stillValid(player);
    }

    public void syncBattery(ServerPlayer player) {
        if (!player.level().isClientSide()) {
            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(new long[] {blockEntity.getEnergy(null).getEnergy()}));
        }
    }
}
