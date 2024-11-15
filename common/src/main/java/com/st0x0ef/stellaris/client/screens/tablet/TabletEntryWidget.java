package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.concurrent.atomic.AtomicInteger;

public class TabletEntryWidget extends AbstractScrollWidget {

    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "icon/scroller");

    private AtomicInteger finalHeight = new AtomicInteger(0);
    private final TabletEntry entry;

    public TabletEntryWidget(int x, int y, int width, int height, Component message, TabletEntry entry) {
        super(x, y, width, height, message);
        this.entry = entry;
    }

    @Override
    protected int getInnerHeight() {
        return finalHeight.get() * 2;
    }

    @Override
    protected void renderBorder(GuiGraphics guiGraphics, int x, int y, int width, int height) {

    }


    @Override
    protected double scrollRate() {
        return 9;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        finalHeight.set(0);
        for (int i = 0; i < this.entry.infos().size(); i++) {
            TabletEntry.Info info = this.entry.infos().get(i);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, info.title(), getWidth(),
                    getY() + finalHeight.get() + 1 + (i * 20) , Utils.getColorHexCode("white"));

            guiGraphics.drawCenteredString(Minecraft.getInstance().font, info.title(), getWidth(),
                    getY() + 20 + finalHeight.get() + (i * 20), Utils.getColorHexCode("white"));

            finalHeight.addAndGet(20);

            int finalI = i;
            info.image().ifPresent((image) -> {
                int height = getY() + 40 + finalHeight.get() + (finalI * 20);
                guiGraphics.blitSprite(image.location(), getWidth() - image.width()/2, height, image.width(), image.height());
                finalHeight.addAndGet(height);
            });

        }

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
