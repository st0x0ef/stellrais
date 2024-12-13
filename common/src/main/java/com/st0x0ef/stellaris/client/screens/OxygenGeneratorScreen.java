package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.GaugeWidget;
import com.st0x0ef.stellaris.common.blocks.entities.machines.FluidTank;
import com.st0x0ef.stellaris.common.blocks.entities.machines.OxygenDistributorBlockEntity;
import com.st0x0ef.stellaris.common.menus.OxygenGeneratorMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class OxygenGeneratorScreen extends AbstractContainerScreen<OxygenGeneratorMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/oxygen_distributor.png");

    private final OxygenDistributorBlockEntity blockEntity = getMenu().getBlockEntity();
    private GaugeWidget energyGauge;
    private GaugeWidget oxygenGauge;

    public OxygenGeneratorScreen(OxygenGeneratorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 177;
        imageHeight = 174;
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void init() {
        super.init();

        if (blockEntity == null) return;

        energyGauge = new GaugeWidget(leftPos + 147, topPos + 27, 13, 46, Component.translatable("stellaris.screen.energy"), GUISprites.ENERGY_FULL, GUISprites.BATTERY_OVERLAY, blockEntity.getEnergy(null).getMaxEnergy(), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(energyGauge);

        FluidTank oxygenTank = blockEntity.oxygenTank;
        oxygenGauge = new GaugeWidget(leftPos + 80 , topPos + 59 , 16, 14, Component.translatable("stellaris.screen.oxygen"), GUISprites.NO_OVERLAY, GUISprites.NO_OVERLAY, oxygenTank.getMaxCapacity(), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(oxygenGauge);

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
        oxygenGauge.updateAmount(blockEntity.oxygenTank.getAmount());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        energyGauge.renderTooltip(guiGraphics, x, y, this.font);
        oxygenGauge.renderTooltip(guiGraphics, x, y, this.font);
    }
}
