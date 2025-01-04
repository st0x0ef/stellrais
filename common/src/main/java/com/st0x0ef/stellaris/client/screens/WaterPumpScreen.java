package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.GaugeWidget;
import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterPumpBlockEntity;
import com.st0x0ef.stellaris.common.menus.WaterPumpMenu;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidTank;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WaterPumpScreen extends AbstractContainerScreen<WaterPumpMenu> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/water_pump.png");

    private final WaterPumpBlockEntity blockEntity = getMenu().getBlockEntity();
    private GaugeWidget waterTankGauge;
    private GaugeWidget energyGauge;

    public WaterPumpScreen(WaterPumpMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void init() {
        super.init();

        if (blockEntity == null) {
            return;
        }

        FluidTank waterTank = blockEntity.getWaterTank();
        waterTankGauge = new GaugeWidget(leftPos + 25, topPos + 20, 12, 42, Component.translatable("stellaris.screen.water"), GUISprites.WATER_OVERLAY, GUISprites.LIQUID_TANK_OVERLAY, waterTank.getMaxAmount() - 1, GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(waterTankGauge);

        energyGauge = new GaugeWidget(leftPos + 150, topPos + 20, 13, 46, Component.translatable("stellaris.screen.energyContainer"), GUISprites.ENERGY_FULL, GUISprites.BATTERY_OVERLAY, blockEntity.getEnergy(null).getMaxEnergy(), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(energyGauge);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);

        if (blockEntity == null) {
            return;
        }

        waterTankGauge.updateAmount((int) blockEntity.getWaterTank().getFluidValue());
        energyGauge.updateAmount((int) blockEntity.getEnergy(null).getEnergy());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);

        if (waterTankGauge != null) {
            waterTankGauge.renderTooltip(guiGraphics, x, y, font);
        }

        if (energyGauge != null) {
            energyGauge.renderTooltip(guiGraphics, x, y, font);
        }
    }
}