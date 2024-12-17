package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.blocks.entities.machines.FuelRefineryBlockEntity;
import com.st0x0ef.stellaris.common.menus.slot.FluidContainerSlot;
import com.st0x0ef.stellaris.common.menus.slot.ResultSlot;
import com.st0x0ef.stellaris.common.network.packets.SyncWidgetsTanksPacket;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class FuelRefineryMenu extends BaseContainer {

    private final Container container;
    private final FuelRefineryBlockEntity blockEntity;

    public static FuelRefineryMenu create(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        FuelRefineryBlockEntity blockEntity = (FuelRefineryBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos());
        return new FuelRefineryMenu(containerId, inventory, new SimpleContainer(4), blockEntity);
    }

    public FuelRefineryMenu(int containerId, Inventory inventory, Container container, FuelRefineryBlockEntity blockEntity) {
        super(MenuTypesRegistry.FUEL_REFINERY.get(), containerId, 4, inventory, 26);
        this.container = container;
        this.blockEntity = blockEntity;

        // Ingredient tank
        addSlot(new FluidContainerSlot(container, 0, 10, 36, false, false));
        addSlot(new ResultSlot(container, 1, 10, 66));

        // Result tank
        addSlot(new FluidContainerSlot(container, 2, 125, 36, true, true));
        addSlot(new ResultSlot(container, 3, 125, 66));
    }

    @Override
    public boolean stillValid(Player player) {
        if (!player.isLocalPlayer()) {
            syncWidgets((ServerPlayer) player);
        }
        return container.stillValid(player);
    }

    public FuelRefineryBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public void syncWidgets(ServerPlayer player) {
        if (!player.level().isClientSide()) {

            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(
                    new long[] {blockEntity.getIngredientTank().getFluidValue(), blockEntity.getResultTank().getFluidValue()},
                    new ResourceLocation[] {blockEntity.getIngredientTank().getFluidStack().getFluid().arch$registryName(), blockEntity.getResultTank().getFluidStack().getFluid().arch$registryName()}
            ));

            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(
                    new long[] {blockEntity.getEnergy(null).getEnergy()}
            ));

        }
    }
}
