package com.st0x0ef.stellaris.client.renderers.entities.vehicle;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.st0x0ef.stellaris.common.entities.vehicles.IVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class VehicleRenderer<T extends IVehicleEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
    protected final M model;
    protected final List<RenderLayer<T, M>> layers = Lists.newArrayList();

    public VehicleRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    public M getModel() {
        return this.model;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null);
        this.model.riding = shouldSit;
        float f = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        float f1 = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        float f2 = f1 - f;
        if (shouldSit && entity.getVehicle() instanceof LivingEntity livingentity) {
            f = Mth.rotLerp(partialTick, livingentity.yBodyRotO, livingentity.yBodyRot);
            f2 = f1 - f;
            float f3 = Mth.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());

        float f7 = this.getBob(entity, partialTick);
        this.setupRotations(entity, poseStack, f7, f, partialTick);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0D, -1.501F, 0.0D);
        float f8 = 0.0F;
        float f5 = 0.0F;


        this.model.prepareMobModel(entity, f5, f8, partialTick);
        this.model.setupAnim(entity, f5, f8, f7, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isBodyVisible(entity);
        boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
        RenderType rendertype = this.getRenderType(entity, flag, flag1, flag2);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = buffer.getBuffer(rendertype);
            int i = getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTick));
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, i, -1);
        }

        if (!entity.isSpectator()) {
            for(RenderLayer<T, M> renderlayer : this.layers) {
                renderlayer.render(poseStack, buffer, packedLight, entity, f5, f8, partialTick, f7, f2, f6);
            }
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }


    @Nullable
    protected RenderType getRenderType(T entity, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        ResourceLocation resourcelocation = this.getTextureLocation(entity);
        if (p_115324_) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (p_115323_) {
            return this.model.renderType(resourcelocation);
        } else {
            return p_115325_ ? RenderType.outline(resourcelocation) : null;
        }
    }

    protected boolean isBodyVisible(T entity) {
        return !entity.isInvisible();
    }

    protected boolean isShaking(T entity) {
        return false;
    }

    public static int getOverlayCoords(Entity entity, float p_115340_) {
        return OverlayTexture.pack(OverlayTexture.u(p_115340_), OverlayTexture.v(false));
    }

    protected float getWhiteOverlayProgress(T p_115334_, float p_115335_) {
        return 0.0F;
    }

    protected void setupRotations(T entity, PoseStack poseStack, float p_115319_, float p_115320_, float p_115321_) {
        if (this.isShaking(entity)) {
            if (!Minecraft.getInstance().isPaused()) {
                double shakeDirection1 = (p_115321_ * (entity.level().random.nextBoolean() ? 1 : -1)) / 50;
                double shakeDirection2 = (p_115321_ * (entity.level().random.nextBoolean() ? 1 : -1)) / 50;
                double shakeDirection3 = (p_115321_ * (entity.level().random.nextBoolean() ? 1 : -1)) / 50;
                poseStack.translate(shakeDirection1, shakeDirection2, shakeDirection3);
            }
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - p_115320_));
    }

    protected float getBob(T p_115305_, float p_115306_) {
        return (float)p_115305_.tickCount + p_115306_;
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return livingEntity != null && camera.isVisible(livingEntity.getBoundingBoxForCulling());
    }
}
