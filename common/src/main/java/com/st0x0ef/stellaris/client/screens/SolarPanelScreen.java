package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.GaugeWidget;
import com.st0x0ef.stellaris.common.blocks.entities.machines.SolarPanelEntity;
import com.st0x0ef.stellaris.common.menus.SolarPanelMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelMenu> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/solar_panel.png");

    private final SolarPanelEntity blockEntity = getMenu().getBlockEntity();
    private GaugeWidget energyGauge;

    public SolarPanelScreen(SolarPanelMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        imageWidth = 177;
        imageHeight = 188;
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void init() {
        super.init();

        if (blockEntity == null) {
            return;
        }

        energyGauge = new GaugeWidget(leftPos + 109, topPos + 29, 11, 46, Component.translatable("stellaris.screen.energyContainer"), GUISprites.ENERGY_FULL, GUISprites.BATTERY_OVERLAY, blockEntity.getEnergy(null).getMaxEnergy(), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(energyGauge);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        renderTooltip(graphics, mouseX, mouseY);

        if (blockEntity == null) {
            return;
        }

        energyGauge.updateAmount(blockEntity.getEnergy(null).getEnergy());
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        energyGauge.renderTooltips(guiGraphics, x, y, font, list -> list.add(Component.translatable("gauge_text.stellaris.max_generation", getMenu().getBlockEntity().getEnergyGeneratedPT())));
    }
}
