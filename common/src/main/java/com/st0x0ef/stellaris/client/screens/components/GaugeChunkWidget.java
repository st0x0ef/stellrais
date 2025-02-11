package com.st0x0ef.stellaris.client.screens.components;

import com.st0x0ef.stellaris.common.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class GaugeChunkWidget extends AbstractWidget {

    protected long capacity;
    protected long amount = 0L;
    protected ResourceLocation sprite;
    protected ResourceLocation overlay_sprite;
    protected final Direction4 DIRECTION;
    protected final int imageWidth;
    protected final int imageHeight;

    public GaugeChunkWidget(int x, int y, int imageWidth, int imageHeight, int width, int height, Component message, ResourceLocation sprite, @Nullable ResourceLocation overlay_sprite, long capacity, Direction4 direction) {
        super(x, y, width, height, message);
        this.sprite = sprite;
        this.overlay_sprite = overlay_sprite;
        this.capacity = capacity;
        this.DIRECTION = direction;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        switch (DIRECTION) {
            case DOWN_UP -> {
                int i = Mth.ceil(getProgress(amount, capacity) * (getHeight() - 1));
                for (int j = 0; j < width/imageWidth; j++) {
                    guiGraphics.blitSprite(sprite, imageWidth, getHeight(), 0, getHeight() - i, getX() + imageWidth * j, getY() + getHeight() - i, imageWidth, i);
                }

            }
            case UP_DOWN -> {
                int i = Mth.ceil(getProgress(amount, capacity) * (getHeight() - 1));
                for (int j = 0; j < width/imageWidth; j++) {
                    guiGraphics.blitSprite(sprite, imageWidth, getHeight(), 0, 0, getX() + imageWidth * j, getY(), imageWidth, i);
                }
            }
            case LEFT_RIGHT -> {
                int i = Mth.ceil(getProgress(amount, capacity) * (getWidth() - 1));
                for (int j = 0; j < height/imageHeight; j++) {
                    guiGraphics.blitSprite(sprite, getWidth(), imageHeight, 0, 0, getX(), getY() + imageHeight * j, i, imageHeight);
                }
            }
            case RIGHT_LEFT -> {
                int i = Mth.ceil(getProgress(amount, capacity) * (getWidth() - 1));
                for (int j = 0; j < height/imageHeight; j++) {
                    guiGraphics.blitSprite(sprite, getWidth(), imageHeight, getWidth() - i, 0, getX() + getWidth() - i, getY() + imageHeight * j, i, imageHeight);
                }
            }
        }
        if (this.overlay_sprite != null) {
            guiGraphics.blitSprite(overlay_sprite, getX(), getY(), width, height);
        }
    }

    public void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY, Font font) {
        this.renderTooltips(graphics, mouseX, mouseY, font, list -> {});
    }

    public void renderTooltips(GuiGraphics graphics, int mouseX, int mouseY, Font font, Consumer<List<Component>> components) {
        String GaugeComponent = getMessage().getString() + " : " + amount + " / " + this.capacity;
        Component capacity;

        if (amount >= this.capacity) {
            capacity = Utils.getMessageComponent(GaugeComponent, "Lime");
        } else if (amount <= 0) {
            capacity = Utils.getMessageComponent(GaugeComponent, "Red");
        } else {
            capacity = Utils.getMessageComponent(GaugeComponent, "Orange");
        }

        List<Component> components1 = new ArrayList<>();
        components.accept(components1);
        components1.addFirst(capacity);
        if (mouseX >= this.getX() && mouseX <= this.getX() + width && mouseY >= this.getY() && mouseY <= this.getY() + this.height) {
            graphics.renderComponentTooltip(font, components1, mouseX, mouseY);
        }
    }

    public void updateAmount(long value) {
        this.amount = Math.clamp(value, 0, capacity);
    }

    public void updateCapacity(long capacity) {
        this.capacity = capacity;
        this.amount = Math.min(this.amount, capacity);
    }

    public void updateSprite(ResourceLocation sprite) {
        this.sprite = sprite;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

    private double getProgress(Long amount, Long capacity) {
        return Mth.clamp((double)amount / (double)capacity, 0.0D, 1.0D);
    }

    public enum Direction4 {
        DOWN_UP,
        UP_DOWN,
        LEFT_RIGHT,
        RIGHT_LEFT
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

}
