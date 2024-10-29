package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.GaugeWidget;
import com.st0x0ef.stellaris.common.entities.vehicles.RoverEntity;
import com.st0x0ef.stellaris.common.menus.RoverMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(value = EnvType.CLIENT)
public class RoverScreen  extends AbstractContainerScreen<RoverMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/rover_gui.png");

    private final RoverEntity rover = getMenu().getRover();
    private GaugeWidget fuelGauge;

    public RoverScreen(RoverMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        imageWidth = 177;
        imageHeight = 177;
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void init() {
        super.init();

        if (rover == null) {
            return;
        }

        fuelGauge = new GaugeWidget(leftPos + 51, topPos + 27, 12, 46, Component.translatable("stellaris.screen.fuel"), rover.getRoverComponent().getMotorUpgrade().getFluidTexture(), GUISprites.FLUID_TANK_OVERLAY, rover.getRoverComponent().getTankCapacity(), GaugeWidget.Direction4.DOWN_UP);
        addRenderableWidget(fuelGauge);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        renderTooltip(graphics, mouseX, mouseY);

        if (rover == null) {
            return;
        }

        fuelGauge.updateAmount(rover.getFuel());
        fuelGauge.updateSprite(rover.getRoverComponent().getFuelTexture());

        fuelGauge.renderTooltip(graphics, mouseX, mouseY, font);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }
}
