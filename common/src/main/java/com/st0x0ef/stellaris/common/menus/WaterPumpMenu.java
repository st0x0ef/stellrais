package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.capabilities.fluid.FluidTank;
import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterPumpBlockEntity;
import com.st0x0ef.stellaris.common.network.packets.SyncWidgetsTanksPacket;
import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class WaterPumpMenu extends BaseContainer {

    private final ContainerLevelAccess access;
    private final WaterPumpBlockEntity blockEntity;

    public WaterPumpMenu(int containerId, Inventory inventory, ContainerLevelAccess access, WaterPumpBlockEntity blockEntity) {
        super(MenuTypesRegistry.WATER_PUMP_MENU.get(), containerId, 0, inventory, 0);
        this.access = access;
        this.blockEntity = blockEntity;
    }

    public static WaterPumpMenu create(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        WaterPumpBlockEntity blockEntity = (WaterPumpBlockEntity) inventory.player.level().getBlockEntity(pos);
        return new WaterPumpMenu(containerId, inventory, ContainerLevelAccess.create(inventory.player.level(), pos), blockEntity);
    }

    @Override
    public boolean stillValid(Player player) {
        if (!player.isLocalPlayer()) {
            syncWidgets((ServerPlayer) player);
        }

        return access.evaluate((level, pos) ->
                        level.getBlockState(pos).is(BlocksRegistry.WATER_PUMP.get()) && player.canInteractWithBlock(pos, 4),
                true
        );
    }

    public WaterPumpBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public void syncWidgets(ServerPlayer player) {
        if (!player.level().isClientSide()) {
            FluidTank tank = blockEntity.getWaterTank();

            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(new long[] {tank.getFluidValue()},
                    new ResourceLocation[] {tank.getFluidStack().getFluid().arch$registryName()}));

            NetworkManager.sendToPlayer(player, new SyncWidgetsTanksPacket(
                    new long[] {blockEntity.getEnergy(null).getEnergy(), 0}));
        }
    }
}