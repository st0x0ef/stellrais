package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import com.st0x0ef.stellaris.common.menus.MilkyWayMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MilkyWayScreen extends AbstractContainerScreen<MilkyWayMenu> {
    public static final ResourceLocation MILKY_WAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/environment/milky_way.png");
    public static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/planet_selection.png");

    public static float rotationAngle = 0;

    public static boolean isPausePressed = false;
    public Component milkywayTranslatable = Component.translatable("text.stellaris.milkywayscreen.milkyway");

    public MilkyWayScreen(MilkyWayMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 1200;
        this.imageHeight = 1600;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderMilkyWay(graphics, partialTicks);
    }

    @Override
    protected void init() {
        super.init();
        isPausePressed = false;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_X){
            isPausePressed = !isPausePressed;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void renderMilkyWay(GuiGraphics guiGraphics, float partialTicks) {
        Font font = Minecraft.getInstance().font;

        int milkywayWidth = 192;
        int milkywayHeight = 192;
        int milkywayX = (width - milkywayWidth) / 2;
        int milkywayY = (height - milkywayHeight) / 2;

        if (!isPausePressed) {
            rotationAngle -= partialTicks * 0.005f;
        }

        ScreenHelper.drawTexturewithRotation(guiGraphics, MILKY_WAY_TEXTURE, milkywayX, milkywayY, 0, 0, milkywayWidth, milkywayHeight, milkywayWidth, milkywayHeight, rotationAngle);

        int nameWidth = font.width(milkywayTranslatable);
        guiGraphics.drawString(font, milkywayTranslatable.getString(), (width - nameWidth) / 2, 10, 0xFFFFFF);
    }
}

