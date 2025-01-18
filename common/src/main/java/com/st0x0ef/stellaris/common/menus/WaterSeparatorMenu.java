package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterSeparatorBlockEntity;
import com.st0x0ef.stellaris.common.menus.slot.FluidContainerSlot;
import com.st0x0ef.stellaris.common.menus.slot.ResultSlot;
import com.st0x0ef.stellaris.common.menus.slot.SpecificFluidContainerSlot;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import net.minecraft.network.FriendlyByteBuf;
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
        return container.stillValid(player);
    }

    public WaterSeparatorBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
