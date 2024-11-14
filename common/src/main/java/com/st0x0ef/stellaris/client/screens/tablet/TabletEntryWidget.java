package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import me.shedaniel.clothconfig2.api.scroll.ScrollingContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TabletEntryWidget extends AbstractScrollWidget {

    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "icon/scroller");


    private final TabletEntry entry;

    public TabletEntryWidget(int x, int y, int width, int height, Component message, TabletEntry entry) {
        super(x, y, width, height, message);
        this.entry = entry;
    }

    @Override
    protected int getInnerHeight() {
        return 40;
    }

    @Override
    protected double scrollRate() {
        return 9;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();

        this.entry.infos().forEach((info -> {
            StringWidget title = new StringWidget(Component.translatable(info.title()), Minecraft.getInstance().font);
            StringWidget description = new StringWidget(Component.translatable(info.description()), Minecraft.getInstance().font);
            title.render(guiGraphics, mouseX, mouseY, partialTick);
            description.render(guiGraphics, mouseX, mouseY, partialTick);
        }));
        guiGraphics.pose().popPose();

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }


    @Override
    public void renderScrollBar(GuiGraphics guiGraphics) {
        int i = this.getScrollBarHeight();
        int j = this.getX() + this.width;

        int k = Math.max(this.getY(), (int) this.scrollAmount() * (this.height - i) / this.getMaxScrollAmount() + this.getY());
        RenderSystem.enableBlend();
        guiGraphics.blitSprite(SCROLLER_SPRITE, j, k, 8, i);
        RenderSystem.disableBlend();
    }
}
