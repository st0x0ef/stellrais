package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.GaugeWidget;
import com.st0x0ef.stellaris.common.capabilities.FluidTank;
import com.st0x0ef.stellaris.common.blocks.entities.machines.PumpjackBlockEntity;
import com.st0x0ef.stellaris.common.menus.PumpjackMenu;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PumpjackScreen extends AbstractContainerScreen<PumpjackMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/pumpjack.png");

    private final PumpjackBlockEntity blockEntity = getMenu().getBlockEntity();
    private GaugeWidget resultTankGauge;
    private GaugeWidget energyGauge;

    public PumpjackScreen(PumpjackMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 177;
        imageHeight = 192;
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void init() {
        super.init();

        if (blockEntity == null) {
            return;
        }

        FluidTank resultTank = blockEntity.getResultTank();
        resultTankGauge = new GaugeWidget(leftPos + 79, topPos + 32, 12, 46, Component.translatable("stellaris.screen.oil"), GUISprites.OIL_OVERLAY, GUISprites.LIQUID_TANK_OVERLAY, resultTank.getMaxCapacity() -1, GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(resultTankGauge);

        energyGauge = new GaugeWidget(leftPos + 147, topPos + 31, 13, 46, Component.translatable("stellaris.screen.energy"), GUISprites.ENERGY_FULL, GUISprites.BATTERY_OVERLAY, blockEntity.getEnergy(null).getMaxEnergy(), GaugeWidget.Direction4.DOWN_UP);
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

        guiGraphics.drawString(this.font, "Oil Level", leftPos + 20, topPos + 33, Utils.getColorHexCode("gray"));

        if(blockEntity.chunkOilLevel() > 5000) {
            guiGraphics.drawCenteredString(this.font, ""+blockEntity.chunkOilLevel(), leftPos + 40, topPos + 44, Utils.getColorHexCode("green"));

        } else if(blockEntity.chunkOilLevel <= 5000 && blockEntity.chunkOilLevel() > 2500) {
            guiGraphics.drawCenteredString(this.font, ""+blockEntity.chunkOilLevel(), leftPos + 40, topPos + 44, Utils.getColorHexCode("orange"));

        } else {
            guiGraphics.drawCenteredString(this.font, ""+blockEntity.chunkOilLevel(), leftPos + 40, topPos + 44, Utils.getColorHexCode("red"));

        }



        resultTankGauge.updateAmount(blockEntity.getResultTank().getAmount());
        energyGauge.updateAmount(blockEntity.getEnergy(null).getEnergy());

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
        resultTankGauge.renderTooltip(guiGraphics, x, y, font);
        energyGauge.renderTooltip(guiGraphics, x, y, font);
    }
}