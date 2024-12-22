package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.menus.WaitMenu;
import com.st0x0ef.stellaris.common.registry.EntityData;
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
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class WaitScreen extends AbstractContainerScreen<WaitMenu> {
    public static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/planet_selection.png");

    public final String playerChoosing;
    public int timeOnTheScreen = 0;


    public WaitScreen(WaitMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 1200;
        this.imageHeight = 1600;
        playerChoosing = menu.getFirstPlayerName();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderText(graphics, partialTicks);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void containerTick() {
        timeOnTheScreen++;
        super.containerTick();
    }

    private void renderText(GuiGraphics guiGraphics, float partialTicks) {
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawCenteredString(font, Component.translatable("text.stellaris.waitscreen", playerChoosing), this.width / 2, this.height/2, 15067135);


        if(timeOnTheScreen > 2500 && timeOnTheScreen < 3500) {
            guiGraphics.drawCenteredString(font, Component.literal("He is taking very long..."), this.width / 2, this.height/2 + 20, 15067135);

        } else if (timeOnTheScreen > 3500){
            guiGraphics.drawCenteredString(font, Component.literal("Is bro sleeping ?"), this.width / 2, this.height/2 + 20, 15067135);

        }
    }

    @Override
    public void onClose() {
        if(this.getPlayer().getEntityData().get(EntityData.DATA_PLANET_MENU_OPEN)) {
            return;
        }
        super.onClose();
    }

    public Player getPlayer() {
        return menu.getPlayer();
    }
}

