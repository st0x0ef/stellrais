package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.TexturedButton;
import com.st0x0ef.stellaris.common.menus.TabletMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TabletMainScreen extends AbstractContainerScreen<TabletMenu> {

    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/tablet/tablet_background.png");
    public static ArrayList<TabletEntry> ENTRIES = new ArrayList<>();
    public ArrayList<TexturedButton> BUTTONS = new ArrayList<>();

    public TabletMainScreen(TabletMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, Component.literal("Tablet"));
        this.imageHeight = 162;
        this.imageWidth = 250;
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        guiGraphics.blit(BACKGROUND, this.leftPos , this.topPos , 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void init() {
        super.init();

        AtomicInteger row = new AtomicInteger(0);
        AtomicInteger column = new AtomicInteger(0);

        ENTRIES.forEach((entry) -> {

            TexturedButton button = new TexturedButton(this.leftPos + 68 + (column.get() * 30), this.topPos + 60 + (row.get() * 30), 20, 20, Component.translatable(entry.id()), (button1) -> {
                this.minecraft.setScreen(new TabletEntryScreen(Component.translatable(entry.id()), this, this.leftPos, this.topPos, entry));
            }).tex(entry.icon(), entry.hoverIcon()).tooltip(Tooltip.create(Component.translatable(entry.id())));

            if(column.get() == 3) {
                column.set(0);
                row.getAndIncrement();
            } else {
                column.getAndIncrement();
            }
            BUTTONS.add(button);
            this.addRenderableWidget(button);
        });

    }

}
