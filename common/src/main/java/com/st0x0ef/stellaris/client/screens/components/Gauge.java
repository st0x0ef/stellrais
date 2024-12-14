package com.st0x0ef.stellaris.client.screens.components;

import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Deprecated(forRemoval = true)
@Environment(EnvType.CLIENT)
public class Gauge extends AbstractWidget {
    protected ResourceLocation texture;
    protected int max_value;
    protected int value;
    public final ResourceLocation overlay_texture;
    private int yOffSet = 0;



    public Gauge(int x, int y, int width, int height, Component message, ResourceLocation texture, @Nullable ResourceLocation overlay_texture, int value, int max_value) {
        super(x, y, width, height, message);
        this.texture = texture;
        this.max_value = max_value;
        this.value = value;
        this.overlay_texture = overlay_texture;
    }

    public Gauge(int x, int y, int width, int height, Component message, ResourceLocation texture, @Nullable ResourceLocation overlay_texture, int value, int max_value, int yOffSet) {
        this(x, y, width, height, message, texture, overlay_texture, value, max_value);
        this.yOffSet = yOffSet;
    }

    public void update(int value) {
        this.value = value;
    }

    public void updateMaxValue(int value) {
        this.max_value = value;
    }

    public void updateFluidTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (value >= max_value) {
            value = max_value;
            graphics.blit(RenderType::guiTextured, texture, getX(), getY(), width, height - 1, width, height - 1, width, height - 1);
        } else if (value <= 0) {
            graphics.blit(RenderType::guiTextured, texture, getX(), getY(), width, height, width, 0, width, 45);
        } else {
            int widgetY = getY() + 45 - (45 * value / max_value) + yOffSet;
            graphics.blit(RenderType::guiTextured, texture, getX(), widgetY, (float) width, (float) height, width, (int) (45 / ((float) max_value / value)), width, 45);
        }

        if (overlay_texture != null) {
            ScreenHelper.drawTexture(getX(), getY(), width, height, overlay_texture, false);
        }
    }

    public void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY, Font font) {
        this.renderTooltips(graphics, mouseX, mouseY, font, new ArrayList<>());
    }

    public void renderTooltips(GuiGraphics graphics, int mouseX, int mouseY, Font font, List<Component> components) {
        String GaugeComponent = getMessage().getString() + " : " + value + " / " + max_value;
        Component capacity;

        if (value >= max_value) {
            capacity = Utils.getMessageComponent(GaugeComponent, "Lime");
        } else if (value <= 0) {
            capacity = Utils.getMessageComponent(GaugeComponent, "Red");
        } else {
            capacity = Utils.getMessageComponent(GaugeComponent, "Orange");
        }


        components.addFirst(capacity);
        if (mouseX >= this.getX() && mouseX <= this.getX() + width && mouseY >= this.getY() && mouseY <= this.getY() + this.height) {
            graphics.renderComponentTooltip(font, components, mouseX, mouseY);
        }
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Deprecated(forRemoval = true)
    public static class SidewayGauge extends Gauge {

        public SidewayGauge(int x, int y, int width, int height, @Nullable Component message, ResourceLocation texture, @Nullable ResourceLocation overlay_texture, int value, int max_value) {
            super(x, y, width, height, message, texture, overlay_texture, value, max_value);
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            int pourcent = width * value/max_value;
            graphics.blit(RenderType::guiTextured, texture, getX(), getY(), width, height, pourcent, height, width, height);

            if (overlay_texture != null) {
                ScreenHelper.drawTexture(getX(), getY(), width, height, overlay_texture, false);
            }
        }
    }
}
