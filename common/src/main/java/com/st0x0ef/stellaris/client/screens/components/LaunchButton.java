package com.st0x0ef.stellaris.client.screens.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class LaunchButton extends Button {
    private ResourceLocation buttonTexture;
    private ResourceLocation hoverButtonTexture;

    private int xTexStart;
    private int yTexStart;

    private int yDiffText;

    private int textureWidth;
    private int textureHeight;

    public LaunchButton(int xIn, int yIn, int widthIn, int heightIn, OnPress onPressIn) {
        this(xIn, yIn, widthIn, heightIn, Component.empty(), onPressIn, DEFAULT_NARRATION);
    }

    public LaunchButton(int xIn, int yIn, int widthIn, int heightIn, Component title, OnPress onPressIn) {
        this(xIn, yIn, widthIn, heightIn, title, onPressIn, DEFAULT_NARRATION);
    }

    public LaunchButton(int xIn, int yIn, int widthIn, int heightIn, Component title, OnPress onPressIn,
                        CreateNarration onTooltipIn) {
        super(xIn, yIn, widthIn, heightIn, title, onPressIn, onTooltipIn);
        this.textureWidth = widthIn;
        this.textureHeight = heightIn;
        this.yDiffText = 0;
        this.xTexStart = 0;
        this.yTexStart = 0;
    }

    public void setButtonTexture(ResourceLocation buttonTexture, ResourceLocation hoverButtonTexture) {
        this.buttonTexture = buttonTexture;
        this.hoverButtonTexture = hoverButtonTexture;
    }

    @SuppressWarnings("unchecked")
    private <T extends LaunchButton> T cast() {
        return (T) this;
    }

    public <T extends LaunchButton> T tex(ResourceLocation buttonTexture, ResourceLocation hoverTexture) {
        this.buttonTexture = buttonTexture;
        this.hoverButtonTexture = hoverTexture;
        return cast();
    }

    public <T extends LaunchButton> T size(int texWidth, int texHeight) {
        this.textureWidth = texWidth;
        this.textureHeight = texHeight;
        return cast();
    }

    public <T extends LaunchButton> T setUVs(int xTexStart, int yTexStart) {
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        return cast();
    }

    public void setYShift(int y) {
        this.yDiffText = y;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();

        int i = this.yTexStart;
        if (this.isHoveredOrFocused()) {
            i += this.yDiffText;
        }

        /** TEXTURE MANAGER */
        ResourceLocation texture = this.getTypeTexture(this.isHovered, this.buttonTexture, this.hoverButtonTexture);

        /** TEXTURE RENDERER */
        RenderSystem.setShaderTexture(0, texture);
        ScreenHelper.renderTextureWithColor.blit(graphics.pose(), this.getX(), this.getY(), (float) this.xTexStart, (float) i,
                this.width, this.height, this.textureWidth, this.textureHeight, this.getTypeColor());

        /** FONT RENDERER */
        Font fontRenderer = minecraft.font;
//        int j = getFGColor();
//
//        graphics.drawCenteredString(fontRenderer, this.getMessage(), this.getX() + this.width / 2,
//                this.getY() + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);

        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    /** TYPE TEXTURE MANAGER */
    private ResourceLocation getTypeTexture(boolean hover, ResourceLocation buttonTexture,
                                            ResourceLocation hoverButtonTexture) {
        if (hover) {
            return hoverButtonTexture;
        } else {
            return buttonTexture;
        }
    }

    protected Vec3 getTypeColor() {
        return ColorTypes.DEFAULT.getColor();
    }

    public enum ColorTypes {
        DEFAULT(new Vec3(255, 255, 255)), WHITE(new Vec3(240, 240, 240)), ORANGE(new Vec3(235, 136, 68)),
        MAGENTA(new Vec3(195, 84, 205)), LIGHT_BLUE(new Vec3(102, 137, 211)), YELLOW(new Vec3(222, 207, 42)),
        LIME(new Vec3(65, 205, 52)), PINK(new Vec3(216, 129, 152)), GRAY(new Vec3(67, 67, 67)),
        LIGHT_GRAY(new Vec3(171, 171, 171)), CYAN(new Vec3(40, 118, 151)), PURPLE(new Vec3(123, 47, 190)),
        BLUE(new Vec3(37, 49, 146)), BROWN(new Vec3(81, 48, 26)), GREEN(new Vec3(53, 163, 79)),
        RED(new Vec3(179, 49, 44)), BLACK(new Vec3(30, 27, 27));

        private final Vec3 color;

        ColorTypes(Vec3 color) {
            this.color = color;
        }

        public Vec3 getColor() {
            return this.color;
        }
    }


}
