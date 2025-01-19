package com.st0x0ef.stellaris.client.screens.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import com.st0x0ef.stellaris.client.screens.tablet.TabletEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class TabletButton extends Button {
    private ResourceLocation buttonTexture;
    private ResourceLocation hoverButtonTexture;

    private int xTexStart;
    private int yTexStart;

    private int yDiffText;

    private int textureWidth;
    private int textureHeight;
    private TabletEntry.Info info;

    public TabletButton(int xIn, int yIn, int widthIn, int heightIn, OnPress onPressIn, TabletEntry.Info info) {
        this(xIn, yIn, widthIn, heightIn, Component.empty(), onPressIn, DEFAULT_NARRATION, info);
    }

    public TabletButton(int xIn, int yIn, int widthIn, int heightIn, Component title, OnPress onPressIn, TabletEntry.Info info) {
        this(xIn, yIn, widthIn, heightIn, title, onPressIn, DEFAULT_NARRATION, info);
    }

    public TabletButton(int xIn, int yIn, int widthIn, int heightIn, Component title, OnPress onPressIn,
                        CreateNarration onTooltipIn, TabletEntry.Info info) {
        super(xIn, yIn, widthIn, heightIn, title, onPressIn, onTooltipIn);
        this.textureWidth = widthIn;
        this.textureHeight = heightIn;
        this.yDiffText = 0;
        this.xTexStart = 0;
        this.yTexStart = 0;
        this.buttonTexture = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/button.png");
        this.hoverButtonTexture = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/button.png");
        this.info = info;
    }

    public <T extends TabletButton> T  tooltip(@Nullable Tooltip tooltip) {
        this.setTooltip(tooltip);
        return cast();
    }
    @SuppressWarnings("unchecked")
    private <T extends TabletButton> T cast() {
        return (T) this;
    }

    public <T extends TabletButton> T tex(ResourceLocation buttonTexture, ResourceLocation hovorTexture) {
        this.buttonTexture = buttonTexture;
        this.hoverButtonTexture = hovorTexture;
        return cast();
    }

    public <T extends TabletButton> T size(int texWidth, int texHeight) {
        this.textureWidth = texWidth;
        this.textureHeight = texHeight;
        return cast();
    }

    public <T extends TabletButton> T setUVs(int xTexStart, int yTexStart) {
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
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
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
        switch (info.type()) {
            case "item":
                info.item().ifPresent((item) -> {
                    ScreenHelper.renderItemWithCustomSize(graphics, minecraft, item.stack(), this.getX(), this.getY(), this.width);
                });
                break;
            case "entity":
                info.entity().ifPresent((entity) -> {
                    Entity entity1 = ScreenHelper.createEntity(Minecraft.getInstance().level, entity.entity());
                    ScreenHelper.renderEntityInInventory(graphics, this.getX(), this.getY(), 7, new Vector3f(1.5f, 2.5f, 0), new Quaternionf(-1, 0, 0, 0), null, entity1);
                });


        }


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
        return TexturedButton.ColorTypes.DEFAULT.getColor();
    }

}
