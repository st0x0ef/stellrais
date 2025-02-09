package com.st0x0ef.stellaris.common.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.systems.energy.impl.WrappedBlockEnergyContainer;
import dev.architectury.fluid.FluidStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import org.joml.Matrix4f;

public class GuiHelper
{

    public static final ResourceLocation FLUID_TANK_OVERLAY = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/util/fluid_tank_overlay.png");
    public static final ResourceLocation OXYGEN_CONTENT_PATH = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/util/oxygen.png");
    public static final int OXYGEN_TANK_WIDTH = 12;
    public static final int OXYGEN_TANK_HEIGHT = 46;

    public static final ResourceLocation HYDROGEN_CONTENT_PATH = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/util/hydrogen.png");
    public static final int HYDROGEN_TANK_WIDTH = 12;
    public static final int HYDROGEN_TANK_HEIGHT = 46;
    public static final ResourceLocation ENERGY_PATH = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/util/energy_full.png");
    public static final int ENERGY_WIDTH = 13;
    public static final int ENERGY_HEIGHT = 46;
    public static final int FUEL_WIDTH = 48;
    public static final int FUEL_HEIGHT = 48;
    public static final ResourceLocation FLUID_TANK_PATH = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/util/fluid_tank_overlay.png");
    public static final int FLUID_TANK_WIDTH = 12;
    public static final int FLUID_TANK_HEIGHT = 46;
    public static final ResourceLocation ARROW_PATH = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/util/arrow_full.png");
    public static final int ARROW_WIDTH = 24;
    public static final int ARROW_HEIGHT = 17;
    public static final ResourceLocation HAMMER_PATH = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,
            "textures/gui/util/hammer_full.png");
    public static final int HAMMER_WIDTH = 16;
    public static final int HAMMER_HEIGHT = 16;

    public static boolean isHover(Rectangle2d bounds, double x, double y) {
        int left = bounds.getX();
        int right = left + bounds.getWidth();
        int top = bounds.getY();
        int bottom = top + bounds.getHeight();
        return left <= x && x < right && top <= y && y < bottom;
    }

    public static void drawArrow(GuiGraphics graphics, int left, int top, double ratio) {
        GuiHelper.drawHorizontal(graphics, left, top, ARROW_WIDTH, ARROW_HEIGHT, ARROW_PATH, ratio);
    }

    public static void drawHammer(GuiGraphics graphics, int left, int top, double ratio) {
        GuiHelper.drawHorizontal(graphics, left, top, HAMMER_WIDTH, HAMMER_HEIGHT, HAMMER_PATH, ratio);
    }

    public static Rectangle2d getArrowBounds(int left, int top) {
        return new Rectangle2d(left, top, ARROW_WIDTH, ARROW_HEIGHT);
    }

//    public static void drawFire(GuiGraphics graphics, int left, int top, double ratio) {
//        drawVertical(graphics, left, top, FIRE_WIDTH, FIRE_HEIGHT, FIRE_PATH, ratio);
//    }
//
//    public static Rectangle2d getFireBounds(int left, int top) {
//        return new Rectangle2d(left, top, FIRE_WIDTH, FIRE_HEIGHT);
//    }
//
//    public static void drawOxygenTank(GuiGraphics graphics, int left, int top, OxygenStorage oxygenStorage) {
//        drawOxygenTank(graphics, left, top, oxygenStorage.getOxygenStoredRatio());
//    }
//
//    public static void drawOxygenTank(GuiGraphics graphics, int left, int top, double ratio) {
//        int maxHeight = FLUID_TANK_HEIGHT;
//        int scaledHeight = (int) Math.ceil(maxHeight * ratio);
//        int offset = maxHeight - scaledHeight;
//
//        RenderSystem.setShaderTexture(0, OXYGEN_CONTENT_PATH);
//        drawTiledSprite(graphics, left, top + offset, OXYGEN_TANK_WIDTH, scaledHeight, 16, 16, 0.0F, 1.0F, 0.0F,
//                1.0F);
//        drawFluidTankOverlay(graphics, left, top);
//    }
//
//    public static void drawHydrogenTank(GuiGraphics graphics, int left, int top, HydrogenStorage hydrogenStorage) {
//        drawHydrogenTank(graphics, left, top, hydrogenStorage.getHydrogenStoredRatio());
//    }
//
//    public static void drawHydrogenTank(GuiGraphics graphics, int left, int top, double ratio) {
//        int maxHeight = FLUID_TANK_HEIGHT;
//        int scaledHeight = (int) Math.ceil(maxHeight * ratio);
//        int offset = maxHeight - scaledHeight;
//
//        RenderSystem.setShaderTexture(0, HYDROGEN_CONTENT_PATH);
//        drawTiledSprite(graphics, left, top + offset, HYDROGEN_TANK_WIDTH, scaledHeight, 16, 16, 0.0F, 1.0F, 0.0F, 1.0F);
//        drawFluidTankOverlay(graphics, left, top);
//    }
//
//    public static Rectangle2d getOxygenTankBounds(int left, int top) {
//        return new Rectangle2d(left, top, OXYGEN_TANK_WIDTH, OXYGEN_TANK_HEIGHT);
//    }
//
//    public static Rectangle2d getHydrogenTankBounds(int left, int top) {
//        return new Rectangle2d(left, top, HYDROGEN_TANK_WIDTH, HYDROGEN_TANK_HEIGHT);
//    }

    public static void drawEnergy(GuiGraphics graphics, int left, int top, WrappedBlockEnergyContainer energyStorage) {
        drawEnergy(graphics, left, top,(double) energyStorage.getStoredEnergy() / (double) energyStorage.getMaxCapacity());
    }

    public static void drawEnergy(GuiGraphics graphics, int left, int top, double ratio) {
        drawVertical(graphics, left, top, ENERGY_WIDTH, ENERGY_HEIGHT, ENERGY_PATH, ratio);
    }

    public static void drawFuel(GuiGraphics matrixStack, int left, int top, double ratio) {
        ResourceLocation full = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/rocket_fuel_bar_full.png");
        drawVertical(matrixStack, left, top, FUEL_WIDTH, FUEL_HEIGHT, full, ratio);
    }

    public static Rectangle2d getEnergyBounds(int left, int top) {
        return new Rectangle2d(left, top, ENERGY_WIDTH, ENERGY_HEIGHT);
    }

    public static Rectangle2d getBounds(int left, int top, int width, int height) {
        return new Rectangle2d(left, top, width, height);
    }

//    public static void drawFluidTank(GuiGraphics graphics, int left, int top, IFluidTank tank) {
//        drawFluidTank(graphics, left, top, tank.getFluid(), tank.getCapacity());
//    }

//    public static void drawFluidTank(GuiGraphics graphics, int left, int top, FluidStack stack, int capacity) {
//        if (stack != null && !stack.isEmpty() && capacity > 0) {
//            int maxHeight = FLUID_TANK_HEIGHT;
//            int scaledHeight = (int) Math.ceil(maxHeight * ((double) stack.getAmount() / (double) capacity));
//            int offset = maxHeight - scaledHeight;
//            drawFluid(graphics, left, top + offset, FLUID_TANK_WIDTH, scaledHeight, stack);
//        }
//
//        drawFluidTankOverlay(graphics, left, top);
//    }

//    public static void drawFluidTankOverlay(GuiGraphics graphics, int left, int top) {
//        drawVertical(graphics, left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT, FLUID_TANK_PATH, 1.0D);
//    }

//    public static void drawFluid(GuiGraphics graphics, int left, int top, int width, int height, FluidStack stack) {
//        Fluid fluid = FluidUtils.getFluid(stack);
//
//        if (fluid == Fluids.EMPTY
//                || !(fluid.getFluidType().getRenderPropertiesInternal() instanceof IClientFluidTypeExtensions props)) {
//            return;
//        }
//
//        TextureAtlasSprite fluidStillSprite = getStillFluidSprite(stack);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        setGLColorFromInt(props.getTintColor(stack));
//        drawTiledSprite(graphics, left, top, width, height, fluidStillSprite, 16, 16);
//        RenderSystem.disableBlend();
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//    }

//    public static void drawRocketFluidTank(GuiGraphics graphics, int left, int top, FluidStack stack, int capacity) {
//        if (stack != null && !stack.isEmpty() && capacity > 0) {
//            int maxHeight = 46;
//            int scaledHeight = (int) Math.ceil(maxHeight * ((double) stack.getAmount() / (double) capacity));
//            int offset = maxHeight - scaledHeight;
//            GuiHelper.drawFluid(graphics, left, top + offset, 46, scaledHeight, stack);
//        }
//
//    }
//
//    public static void drawFluidHorizontal(GuiGraphics graphics, int left, int top, int width, int height,
//                                           FluidStack stack, int capacity) {
//        Fluid fluid = FluidUtils.getFluid(stack);
//
//        if (fluid == Fluids.EMPTY) {
//            return;
//        }
//
//        double ratio = (double) stack.getAmount() / (double) capacity;
//        drawFluid(graphics, left, top, (int) Math.ceil(width * ratio), height, stack);
//    }

//    public static void drawFluidVertical(GuiGraphics graphics, int left, int top, int width, int height,
//                                         FluidStack stack, int capacity) {
//        Fluid fluid = FluidUtils.getFluid(stack);
//
//        if (fluid == Fluids.EMPTY) {
//            return;
//        }
//
//        double ratio = (double) stack.getAmount() / (double) capacity;
//        int scaledHeight = (int) Math.ceil(height * ratio);
//        int offset = height - scaledHeight;
//        drawFluid(graphics, left, top + offset, scaledHeight, height, stack);
//    }

    public static void drawTiledSprite(GuiGraphics graphics, int left, int top, int width, int height,
                                       TextureAtlasSprite sprite, int tileWidth, int tileHeight) {
        float uMin = sprite.getU0();
        float uMax = sprite.getU1();
        float vMin = sprite.getV0();
        float vMax = sprite.getV1();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        drawTiledSprite(graphics, left, top, width, height, tileWidth, tileHeight, uMin, uMax, vMin, vMax);
    }

    public static void drawTiledSprite(GuiGraphics graphics, int left, int top, int width, int height, int tileWidth,
                                       int tileHeight, float uMin, float uMax, float vMin, float vMax) {
        Matrix4f matrix = graphics.pose().last().pose();

        int xTileCount = width / tileWidth;
        int xRemainder = width - (xTileCount * tileWidth);
        int yTileCount = height / tileWidth;
        int yRemainder = height - (yTileCount * tileWidth);

        int yStart = top + height;

        for (int xTile = 0; xTile <= xTileCount; xTile++) {
            for (int yTile = 0; yTile <= yTileCount; yTile++) {
                int tiledWidth = (xTile == xTileCount) ? xRemainder : tileWidth;
                int tiledHeight = (yTile == yTileCount) ? yRemainder : tileWidth;
                int x = left + (xTile * tileWidth);
                int y = yStart - ((yTile + 1) * tileHeight);

                if (tiledWidth > 0 && tiledHeight > 0) {
                    int maskRight = tileWidth - tiledWidth;
                    int maskTop = tileHeight - tiledHeight;

                    drawTextureWithMasking(matrix, x, y, tileWidth, tileHeight, maskTop, maskRight, 0, uMin, uMax, vMin,
                            vMax);
                }
            }
        }
    }

    public static void drawTextureWithMasking(Matrix4f matrix, float left, float top, float tileWidth, float tileHeight,
                                              int maskTop, int maskRight, float zLevel, float uMin, float uMax, float vMin, float vMax) {
        uMax = uMax - (maskRight / tileWidth * (uMax - uMin));
        vMax = vMax - (maskTop / tileHeight * (vMax - vMin));

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(matrix, left, top + tileHeight, zLevel).setUv(uMin, vMax);
        bufferBuilder.addVertex(matrix, left + tileWidth - maskRight, top + tileHeight, zLevel).setUv(uMax, vMax);
        bufferBuilder.addVertex(matrix, left + tileWidth - maskRight, top + maskTop, zLevel).setUv(uMax, vMin);
        bufferBuilder.addVertex(matrix, left, top + maskTop, zLevel).setUv(uMin, vMin);
        bufferBuilder.buildOrThrow();
    }

    public static void setGLColorFromInt(int color) {
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        float alpha = ((color >> 24) & 0xFF) / 255F;

        RenderSystem.setShaderColor(red, green, blue, alpha);

    }

    private static final ResourceLocation MISSING_TEXTURE_LOCATION = ResourceLocation.parse("missingno");

    public static TextureAtlasSprite getStillFluidSprite(FluidStack stack) {
        Fluid fluid = stack.getFluid();
//        if (fluid.defaultFluidState().getProperties() instanceof IClientFluidTypeExtensions props) {
//             fluidStill = props.getStillTexture();
//        }
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(MISSING_TEXTURE_LOCATION);
    }

    public static Rectangle2d getFluidTankBounds(int left, int top) {
        return new Rectangle2d(left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
    }

    public static Rectangle2d getRocketFluidTankBounds(int left, int top) {
        return new Rectangle2d(left, top, 48, 48);
    }

    public static void drawVertical(GuiGraphics graphics, int left, int top, int width, int height,
                                    ResourceLocation resource, double ratio) {
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;
        RenderSystem.setShaderTexture(0, resource);
        graphics.blit(resource, left, top + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
    }

    public static void drawVerticalReverse(GuiGraphics graphics, int left, int top, int width, int height,
                                           ResourceLocation resource, double ratio) {
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;
        RenderSystem.setShaderTexture(0, resource);
        graphics.blit(resource, left, top, 0, 0, width, remainHeight, width, height);
    }

    public static void drawHorizontal(GuiGraphics graphics, int left, int top, int width, int height,
                                      ResourceLocation resource, double ratio) {
        int ratioWidth = (int) Math.ceil(width * ratio);

        RenderSystem.setShaderTexture(0, resource);
        graphics.blit(resource, left, top, 0, 0, ratioWidth, height, width, height);
    }

    public static void drawHorizontalReverse(GuiGraphics graphics, int left, int top, int width, int height,
                                             ResourceLocation resource, double ratio) {
        int ratioWidth = (int) Math.ceil(width * ratio);
        int remainWidth = width - ratioWidth;
        RenderSystem.setShaderTexture(0, resource);
        graphics.blit(resource, left + ratioWidth, top, ratioWidth, 0, remainWidth, height, width, height);
    }

    public static void innerBlit(Matrix4f matrix, float x1, float x2, float y1, float y2, int blitOffset, float minU,
                                 float maxU, float minV, float maxV) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(matrix, x1, y2, (float) blitOffset).setUv(minU, maxV);
        bufferBuilder.addVertex(matrix, x2, y2, (float) blitOffset).setUv(maxU, maxV);
        bufferBuilder.addVertex(matrix, x2, y1, (float) blitOffset).setUv(maxU, minV);
        bufferBuilder.addVertex(matrix, x1, y1, (float) blitOffset).setUv(minU, minV);
        bufferBuilder.buildOrThrow();
    }

    public static void blit(PoseStack matrixStack, float x, float y, float width, float height, float uOffset,
                            float vOffset, float uWidth, float vHeight, int textureWidth, int textureHeight) {
        innerBlit(matrixStack, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth,
                textureHeight);
    }

    private static void innerBlit(PoseStack matrixStack, float x1, float x2, float y1, float y2, int blitOffset,
                                  float uWidth, float vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        innerBlit(matrixStack.last().pose(), x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float) textureWidth,
                (uOffset + uWidth) / (float) textureWidth, (vOffset + 0.0F) / (float) textureHeight,
                (vOffset + vHeight) / (float) textureHeight);
    }

    public static void blit(PoseStack matrixStack, float x, float y, float uOffset, float vOffset, float width,
                            float height, int textureWidth, int textureHeight) {
        blit(matrixStack, x, y, width, height, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    private GuiHelper() {

    }
}
