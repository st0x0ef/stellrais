package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.menus.SolarPanelMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SolarPanelEntity extends BaseGeneratorBlockEntity {

    public SolarPanelEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.SOLAR_PANEL.get(), blockPos, blockState, 1, 128000);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new SolarPanelMenu(containerId, inventory, this, this);
    }

    @Override
    public boolean canGenerate() {
        BlockPos blockPos = this.getBlockPos().offset(0, 1, 0);
        return level.isDay() && level.canSeeSky(blockPos);
    }

    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.solar_panel");
    }

    @Override
    public int getContainerSize() {
        return 1;
    }
}