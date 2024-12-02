package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
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
        //We don't want to render the border
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

            guiGraphics.drawCenteredString(getFont(), info.title(), getWidth(),
                    getY() + finalHeight.get() + 1 + (i * 20) , Utils.getColorHexCode("white"));

            int descriptionHeight = renderDescription(info.description(), getX() + 5, getY() + finalHeight.get() + 20 + (i * 20), getWidth() - 5, guiGraphics);
            finalHeight.addAndGet(descriptionHeight);

            int finalI = i;
            info.image().ifPresent((image) -> {
                int height = getY() + 40 + finalHeight.get() + (finalI * 20);
                guiGraphics.blitSprite(image.location(), getWidth() - image.width()/2, height, image.width(), image.height());
                finalHeight.addAndGet(height - 40);
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

    public int renderDescription(String description, int x, int y, int maxWidth, GuiGraphics guiGraphics) {
        List<String> lines = createLines(description, maxWidth);
        for(int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(getFont(), lines.get(i), x, y + (i * 10), Utils.getColorHexCode("white"));
        }
        return lines.size() * getFont().lineHeight;
    }

    public List<String> createLines(String message, int maxWidth) {
        String[] words = message.split("\\s+");
        List<String> lines = new ArrayList<>();

        String currentLine = "";
        for(String word : words) {
            if(Minecraft.getInstance().font.width(currentLine + word) < maxWidth) {
                currentLine += " " + word;
            } else {
                lines.add(currentLine);
                currentLine = word;
            }
        }
        return lines;
    }

    public Font getFont() {
        return Minecraft.getInstance().font;
    }
}
