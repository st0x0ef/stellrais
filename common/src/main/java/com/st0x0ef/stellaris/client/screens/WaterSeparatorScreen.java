package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.GaugeWidget;
import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterSeparatorBlockEntity;
import com.st0x0ef.stellaris.common.menus.WaterSeparatorMenu;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidStorage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class WaterSeparatorScreen extends AbstractContainerScreen<WaterSeparatorMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/water_separator.png");

    private final WaterSeparatorBlockEntity blockEntity = getMenu().getBlockEntity();
    private GaugeWidget ingredientTankGauge;
    private GaugeWidget resultTank1Gauge;
    private GaugeWidget resultTank2Gauge;
    private GaugeWidget energyGauge;

    public WaterSeparatorScreen(WaterSeparatorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 177;
        imageHeight = 224;
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void init() {
        super.init();

        if (blockEntity == null) {
            return;
        }

        FluidStorage ingredientTank = blockEntity.getIngredientTank();
        ingredientTankGauge = new GaugeWidget(leftPos + 50, topPos + 58, 76, 40, Component.translatable("stellaris.screen.water"), GUISprites.WATER_OVERLAY, GUISprites.WATER_SEPARATOR_OVERLAY, ingredientTank.getTankCapacity(ingredientTank.getTanks()), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(ingredientTankGauge);

        FluidStorage resultTank1 = blockEntity.getResultTanks().getFirst();
        resultTank1Gauge = new GaugeWidget(leftPos + 22, topPos + 52, 12, 46, Component.translatable("stellaris.screen.hydrogen"), GUISprites.HYDROGEN_OVERLAY, GUISprites.LIQUID_TANK_OVERLAY, resultTank1.getTankCapacity(resultTank1.getTanks()), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(resultTank1Gauge);

        FluidStorage resultTank2 = blockEntity.getResultTanks().get(1);
        resultTank2Gauge = new GaugeWidget(leftPos + 142, topPos + 52, 12, 46, Component.translatable("stellaris.screen.oxygen"), GUISprites.OXYGEN_OVERLAY, GUISprites.LIQUID_TANK_OVERLAY, resultTank2.getTankCapacity(resultTank2.getTanks()), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(resultTank2Gauge);

        energyGauge = new GaugeWidget(leftPos + 64, topPos + 24, 47, 13, Component.translatable("stellaris.screen.energyContainer"), GUISprites.SIDEWAYS_ENERGY_FULL, GUISprites.SIDEWAYS_ENERGY_OVERLAY, blockEntity.getEnergy(null).getMaxEnergy(), GaugeWidget.Direction4.LEFT_RIGHT);
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

        ingredientTankGauge.updateAmount((int)blockEntity.getIngredientTank().getFluidValueInTank(blockEntity.getIngredientTank().getTanks()));
        resultTank1Gauge.updateAmount((int)blockEntity.getResultTanks().getFirst().getFluidValue());
        resultTank2Gauge.updateAmount((int)blockEntity.getResultTanks().getLast().getFluidValue());
        energyGauge.updateAmount((int)blockEntity.getEnergy(null).getEnergy());
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
        ingredientTankGauge.renderTooltip(guiGraphics, x, y, this.font);
        resultTank1Gauge.renderTooltip(guiGraphics, x, y, this.font);
        resultTank2Gauge.renderTooltip(guiGraphics, x, y, this.font);
        energyGauge.renderTooltip(guiGraphics, x, y, this.font);
    }
}
