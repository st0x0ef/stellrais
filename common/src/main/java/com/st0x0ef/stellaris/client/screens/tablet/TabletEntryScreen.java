package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.TexturedButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TabletEntryScreen extends Screen {

    private int leftPos;
    private int topPos;
    private int imageHeight;
    private int imageWidth;
    private final TabletMainScreen screen;
    public TabletEntry entry;

    protected TabletEntryScreen(Component title, TabletMainScreen screen, int leftPos, int topPos, TabletEntry entry) {
        super(title);
        this.screen = screen;
        this.leftPos = leftPos;
        this.topPos = topPos;
        this.entry = entry;
        this.imageHeight = 162;
        this.imageWidth = 250;

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, this.topPos + 30, 16777215);

    }



    @Override
    protected void init() {
        /** Back Button **/
        TexturedButton homeButton = new TexturedButton(this.leftPos + 15, this.topPos + 20, 20, 20, (button -> {
            this.minecraft.setScreen(screen);
        }))
                .tex(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/tablet/main_page.png"), ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/tablet/main_page_hover.png"));
        this.addRenderableWidget(homeButton);

        TabletEntryWidget widget = new TabletEntryWidget(this.leftPos + 15, this.topPos + 50, 215, 100, Component.literal(""), this.entry, this.width);
        this.addRenderableWidget(widget);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TabletMainScreen.MENU_BACKGROUND);
        guiGraphics.blit(TabletMainScreen.BACKGROUND, this.leftPos , this.topPos , 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

    }
}
