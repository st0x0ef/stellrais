package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterSeparatorBlockEntity;
import com.st0x0ef.stellaris.common.menus.slot.FluidContainerSlot;
import com.st0x0ef.stellaris.common.menus.slot.ResultSlot;
import com.st0x0ef.stellaris.common.menus.slot.SpecificFluidContainerSlot;
import com.st0x0ef.stellaris.common.network.packets.SyncWidgetsTanksPacket;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;

public class WaterSeparatorMenu extends BaseContainer {

    private final Container container;
    private final WaterSeparatorBlockEntity blockEntity;

    public static WaterSeparatorMenu create(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        WaterSeparatorBlockEntity blockEntity = (WaterSeparatorBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos());
        return new WaterSeparatorMenu(containerId, inventory, new SimpleContainer(4), blockEntity);
    }

    public WaterSeparatorMenu(int containerId, Inventory inventory, Container container, WaterSeparatorBlockEntity blockEntity) {
        super(MenuTypesRegistry.WATER_SEPARATOR_MENU.get(), containerId, 4, inventory, 58);
        this.container = container;
        this.blockEntity = blockEntity;

        addSlot(new ResultSlot(container, 0, 104, 114)); // Water tank output
        addSlot(new SpecificFluidContainerSlot(container, Fluids.WATER, 1, 56, 114, false)); // Water tank input
        addSlot(new SpecificFluidContainerSlot(container, FluidRegistry.FLOWING_HYDROGEN.get(), 2, 20, 114, true)); // Hydrogen tank output
        addSlot(new FluidContainerSlot(container, 3, 140, 114, true, true)); // Oxygen tank output
    }

    @Override
    public boolean stillValid(Player player) {
        if (!player.isLocalPlayer()) {
            this.syncWidgets((ServerPlayer) player);
        }

        return container.stillValid(player);
    }

    public WaterSeparatorBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public void syncWidgets(ServerPlayer player) {
        if (!player.level().isClientSide()) {
            FluidTank resultTank1 = blockEntity.getResultTanks().getFirst();
            FluidTank resultTank2 = blockEntity.getResultTanks().get(1);


            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(new long[] {resultTank1.getFluidValue(), resultTank2.getFluidValue()},
                    new ResourceLocation[] {resultTank1.getFluidStack().getFluid().arch$registryName(), resultTank2.getFluidStack().getFluid().arch$registryName()}
            ));

            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(
                    new long[] {blockEntity.ingredientTank.getFluidValueInTank(blockEntity.ingredientTank.getTanks())},
                    new ResourceLocation[] {blockEntity.ingredientTank.getFluidInTank(blockEntity.ingredientTank.getTanks()).getFluid().arch$registryName()}
            ));

            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(
                    new long[] {blockEntity.getEnergy(null).getEnergy(), 0, 0}
            ));
        }
    }
}
