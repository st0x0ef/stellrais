package com.st0x0ef.stellaris.client.screens.helper;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Optional;

public class ScreenHelper {

    public static class renderTextureWithColor {

        public static void blit(PoseStack stack, int p_93202_, int p_93203_, int p_93204_, int p_93205_, int p_93206_, TextureAtlasSprite p_93207_, Vec3 color) {
            innerBlit(stack.last().pose(), p_93202_, p_93202_ + p_93205_, p_93203_, p_93203_ + p_93206_, p_93204_, p_93207_.getU0(), p_93207_.getU1(), p_93207_.getV0(), p_93207_.getV1(), color);
        }

        public void blit(PoseStack stack, int p_93230_, int p_93231_, int blitOffset, int p_93232_, int p_93233_, int p_93234_, int p_93235_, Vec3 color) {
            blit(stack, p_93230_, p_93231_, blitOffset, (float) p_93232_, (float) p_93233_, p_93234_, p_93235_, 256, 256, color);
        }

        public static void blit(PoseStack stack, int p_93145_, int p_93146_, int p_93147_, float p_93148_, float p_93149_, int p_93150_, int p_93151_, int p_93152_, int p_93153_, Vec3 color) {
            innerBlit(stack, p_93145_, p_93145_ + p_93150_, p_93146_, p_93146_ + p_93151_, p_93147_, p_93150_, p_93151_, p_93148_, p_93149_, p_93152_, p_93153_, color);
        }

        public static void blit(PoseStack stack, int p_93162_, int p_93163_, int p_93164_, int p_93165_, float p_93166_, float p_93167_, int p_93168_, int p_93169_, int p_93170_, int p_93171_, Vec3 color) {
            innerBlit(stack, p_93162_, p_93162_ + p_93164_, p_93163_, p_93163_ + p_93165_, 0, p_93168_, p_93169_, p_93166_, p_93167_, p_93170_, p_93171_, color);
        }

        public static void blit(PoseStack stack, int p_93135_, int p_93136_, float p_93137_, float p_93138_, int p_93139_, int p_93140_, int p_93141_, int p_93142_, Vec3 color) {
            blit(stack, p_93135_, p_93136_, p_93139_, p_93140_, p_93137_, p_93138_, p_93139_, p_93140_, p_93141_, p_93142_, color);
        }

        private static void innerBlit(PoseStack stack, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_, Vec3 color) {
            innerBlit(stack.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float) p_93198_, (p_93196_ + (float) p_93194_) / (float) p_93198_, (p_93197_ + 0.0F) / (float) p_93199_, (p_93197_ + (float) p_93195_) / (float) p_93199_, color);
        }

        private static void innerBlit(Matrix4f matrix4f, int x, int y, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_, Vec3 color) {


            int r = (int) color.x();
            int g = (int) color.y();
            int b = (int) color.z();

            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.addVertex(matrix4f, (float) x, (float) p_93117_, (float) p_93118_).setColor(r, g, b, 255).setUv(p_93119_, p_93122_);
            bufferBuilder.addVertex(matrix4f, (float) y, (float) p_93117_, (float) p_93118_).setColor(r, g, b, 255).setUv(p_93120_, p_93122_);
            bufferBuilder.addVertex(matrix4f, (float) y, (float) p_93116_, (float) p_93118_).setColor(r, g, b, 255).setUv(p_93120_, p_93121_);
            bufferBuilder.addVertex(matrix4f, (float) x, (float) p_93116_, (float) p_93118_).setColor(r, g, b, 255).setUv(p_93119_, p_93121_);
            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        }
    }

    public static void drawVertical(GuiGraphics graphics, int leftPos, int topPos, int width, int height, double min, double max, ResourceLocation resourceLocation, boolean blend) {
        double ratio = min / max;
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;

        if (blend) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, resourceLocation);
        renderWithFloat.blit(graphics.pose(), leftPos, topPos + remainHeight, 0, remainHeight, width, ratioHeight, width, height);

        if (blend) {
            RenderSystem.disableBlend();
        }
    }


    public static class PlanetScreenHelper {
        public static final Component CATALOG_TEXT = tl("catalog");
        public static final Component BACK_TEXT = tl("back");

        public static final Component SOLAR_SYSTEM_TEXT = tl("solar_system");

        public static final Component PLANET_TEXT = tl("planet");
        public static final Component MOON_TEXT = tl("moon");

        public static final Component ORBIT_TEXT = tl("orbit");

        public static final Component NO_GRAVITY_TEXT = tl("no_gravity");

        public static final Component SPACE_STATION_TEXT = tl("space_station");

        public static final Component CATEGORY_TEXT = tl("category");
        public static final Component PROVIDED_TEXT = tl("provided");
        public static final Component TYPE_TEXT = tl("type");
        public static final Component GRAVITY_TEXT = tl("gravity");
        public static final Component OXYGEN_TEXT = tl("oxygen");
        public static final Component TEMPERATURE_TEXT = tl("temperature");
        public static final Component OXYGEN_TRUE_TEXT = tl("oxygen.true");
        public static final Component OXYGEN_FALSE_TEXT = tl("oxygen.false");
        public static final Component ITEM_REQUIREMENT_TEXT = tl("item_requirement");

        private static Component tl(String key) {
            return Component.translatable("stellaris.gui." + key);
        }


    }

    //Hate this
    public static class renderWithFloat {

        public static void blit(PoseStack p_93201_, float p_93202_, float p_93203_, float p_93204_, float p_93205_, float p_93206_, TextureAtlasSprite p_93207_) {
            innerBlit(p_93201_.last().pose(), p_93202_, p_93202_ + p_93205_, p_93203_, p_93203_ + p_93206_, p_93204_, p_93207_.getU0(), p_93207_.getU1(), p_93207_.getV0(), p_93207_.getV1());
        }

        public void blit(PoseStack p_93229_, float p_93230_, float p_93231_, float blitOffset, float p_93232_, float p_93233_, float p_93234_, float p_93235_) {
            blit(p_93229_, p_93230_, p_93231_, blitOffset, p_93232_, p_93233_, p_93234_, p_93235_, 256, 256);
        }

        public static void blit(PoseStack p_93144_, float p_93145_, float p_93146_, float p_93147_, float p_93148_, float p_93149_, float p_93150_, float p_93151_, float p_93152_, float p_93153_) {
            innerBlit(p_93144_, p_93145_, p_93145_ + p_93150_, p_93146_, p_93146_ + p_93151_, p_93147_, p_93150_, p_93151_, p_93148_, p_93149_, p_93152_, p_93153_);
        }

        public static void blit(PoseStack p_93161_, float p_93162_, float p_93163_, float p_93164_, float p_93165_, float p_93166_, float p_93167_, float p_93168_, float p_93169_, float p_93170_, float p_93171_) {
            innerBlit(p_93161_, p_93162_, p_93162_ + p_93164_, p_93163_, p_93163_ + p_93165_, 0, p_93168_, p_93169_, p_93166_, p_93167_, p_93170_, p_93171_);
        }

        public static void blit(PoseStack p_93134_, float p_93135_, float p_93136_, float p_93137_, float p_93138_, float p_93139_, float p_93140_, float p_93141_, float p_93142_) {
            blit(p_93134_, p_93135_, p_93136_, p_93139_, p_93140_, p_93137_, p_93138_, p_93139_, p_93140_, p_93141_, p_93142_);
        }

        private static void innerBlit(PoseStack p_93188_, float p_93189_, float p_93190_, float p_93191_, float p_93192_, float p_93193_, float p_93194_, float p_93195_, float p_93196_, float p_93197_, float p_93198_, float p_93199_) {
            innerBlit(p_93188_.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / p_93198_, (p_93196_ + p_93194_) / p_93198_, (p_93197_ + 0.0F) / p_93199_, (p_93197_ + p_93195_) / p_93199_);
        }

        private static void innerBlit(Matrix4f p_93113_, float p_93114_, float p_93115_, float p_93116_, float p_93117_, float p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferBuilder.addVertex(p_93113_, p_93114_, p_93117_, p_93118_).setUv(p_93119_, p_93122_);
            bufferBuilder.addVertex(p_93113_, p_93115_, p_93117_, p_93118_).setUv(p_93120_, p_93122_);
            bufferBuilder.addVertex(p_93113_, p_93115_, p_93116_, p_93118_).setUv(p_93120_, p_93121_);
            bufferBuilder.addVertex(p_93113_, p_93114_, p_93116_, p_93118_).setUv(p_93119_, p_93121_);
            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        }
    }

    public static void drawTexture(int leftPos, int topPos, int width, int height, ResourceLocation texture, boolean blend) {
        if (blend) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource()).blit(texture, leftPos, topPos, 0, 0, width, height, width, height);

        if (blend) {
            RenderSystem.disableBlend();
        }
    }

    public static void drawTexturewithRotation(GuiGraphics graphics, ResourceLocation resourceLocation, int x, int y, int uOffset, int vOffset, int width, int height, int textureWidth, int textureHeight, float rotationAngle) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();

        poseStack.translate(x + width / 2.0f, y + height / 2.0f, 0);
        poseStack.mulPose(new Matrix4f().rotation(rotationAngle, 0.0f, 0.0f, 1.0f));
        poseStack.translate(-(x + width / 2.0f), -(y + height / 2.0f), 0);

        RenderSystem.setShaderTexture(0, resourceLocation);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        graphics.blit(resourceLocation, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);

        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    public static @Nullable Entity createEntity(Level level, ResourceLocation location) {

        Optional<EntityType<?>> maybeType = BuiltInRegistries.ENTITY_TYPE.getOptional(location);
        if (maybeType.isEmpty()) {
            return EntityType.PIG.create(level);
        }
        EntityType<?> type = maybeType.get();

        return type.create(level);
    }

    public static void renderEntityInInventory(GuiGraphics guiGraphics, float x, float y, float scale, Vector3f translate, Quaternionf pose, @Nullable Quaternionf cameraOrientation, Entity entity) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((double)x, (double)y, (double)50.0F);
        guiGraphics.pose().scale(scale, scale, -scale);
        guiGraphics.pose().translate(translate.x, translate.y, translate.z);
        guiGraphics.pose().mulPose(pose);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (cameraOrientation != null) {
            entityRenderDispatcher.overrideCameraOrientation(cameraOrientation.conjugate(new Quaternionf()).rotateY((float)Math.PI));
        }

        entityRenderDispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880));
        guiGraphics.flush();
        entityRenderDispatcher.setRenderShadow(true);
        guiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }


    public static void renderItemWithCustomSize(GuiGraphics graphics, Minecraft minecraft, ItemStack stack, int x, int y, float size) {
        if (!stack.isEmpty()) {
            BakedModel bakedModel = minecraft.getItemRenderer().getModel(stack, null, null, 0);
            graphics.pose.pushPose();
            graphics.pose.translate((x + size / 2), (y + size / 2), (float)(150));

            try {
                graphics.pose.scale(size, -size, size);
                boolean bl = !bakedModel.usesBlockLight();
                if (bl) {
                    Lighting.setupForFlatItems();
                }

                minecraft.getItemRenderer().render(stack, ItemDisplayContext.GUI, false, graphics.pose, graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedModel);
                graphics.flush();
                if (bl) {
                    Lighting.setupFor3DItems();
                }
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.forThrowable(throwable, "Rendering item");
                CrashReportCategory crashReportCategory = crashReport.addCategory("Item being rendered");
                crashReportCategory.setDetail("Item Type", () -> String.valueOf(stack.getItem()));
                crashReportCategory.setDetail("Item Components", () -> String.valueOf(stack.getComponents()));
                crashReportCategory.setDetail("Item Foil", () -> String.valueOf(stack.hasFoil()));
                throw new ReportedException(crashReport);
            }

            graphics.pose.popPose();
        }
    }


}