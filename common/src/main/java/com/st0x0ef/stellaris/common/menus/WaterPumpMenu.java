package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterPumpBlockEntity;
import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
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
        return access.evaluate((level, pos) ->
                        level.getBlockState(pos).is(BlocksRegistry.WATER_PUMP.get()) && player.canInteractWithBlock(pos, 4),
                true
        );
    }

    public WaterPumpBlockEntity getBlockEntity() {
        return blockEntity;
    }
}