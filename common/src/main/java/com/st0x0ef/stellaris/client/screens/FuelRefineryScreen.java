package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.GaugeWidget;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidTank;
import com.st0x0ef.stellaris.common.blocks.entities.machines.FuelRefineryBlockEntity;
import com.st0x0ef.stellaris.common.menus.FuelRefineryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FuelRefineryScreen extends AbstractContainerScreen<FuelRefineryMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/fuel_refinery.png");

    private final FuelRefineryBlockEntity blockEntity = getMenu().getBlockEntity();
    private GaugeWidget ingredientTankGauge;
    private GaugeWidget resultTankGauge;
    private GaugeWidget energyGauge;

    public FuelRefineryScreen(FuelRefineryMenu menu, Inventory playerInventory, Component title) {
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

        FluidTank ingredientTank = blockEntity.getIngredientTank();
        ingredientTankGauge = new GaugeWidget(leftPos + 41, topPos + 36, 12, 46, Component.translatable("stellaris.screen.oil"),
                GUISprites.OIL_OVERLAY, GUISprites.LIQUID_TANK_OVERLAY, ingredientTank.getMaxAmount() -1, GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(ingredientTankGauge);

        FluidTank resultTank = blockEntity.getResultTank();
        resultTankGauge = new GaugeWidget(leftPos + 98, topPos + 36, 12, 46, Component.translatable("stellaris.screen.fuel"),
                GUISprites.FUEL_OVERLAY, GUISprites.LIQUID_TANK_OVERLAY, resultTank.getMaxAmount() -1, GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(resultTankGauge);

        energyGauge = new GaugeWidget(leftPos + 147, topPos + 32, 13, 46, Component.translatable("stellaris.screen.energyContainer"),
                GUISprites.ENERGY_FULL, GUISprites.BATTERY_OVERLAY, blockEntity.getEnergy(null).getMaxEnergy(), GaugeWidget.Direction4.DOWN_UP);
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

        ingredientTankGauge.updateAmount(blockEntity.getIngredientTank().getFluidValue());
        resultTankGauge.updateAmount(blockEntity.getResultTank().getFluidValue());
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
        ingredientTankGauge.renderTooltip(guiGraphics, x, y, font);
        resultTankGauge.renderTooltip(guiGraphics, x, y, font);
        energyGauge.renderTooltip(guiGraphics, x, y, font);
    }
}
