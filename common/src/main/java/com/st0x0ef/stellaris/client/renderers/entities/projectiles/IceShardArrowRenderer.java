package com.st0x0ef.stellaris.client.renderers.entities.projectiles;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.IceShardArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class IceShardArrowRenderer extends ArrowRenderer<IceShardArrowEntity, ArrowRenderState> implements IceShardArrowRenderers {
    private static final ResourceLocation LAYER_LOCATION = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"textures/entity/ice_shard_arrow.png");

    public IceShardArrowRenderer(EntityRendererProvider.Context p_174165_) {
        super(p_174165_);
    }

    @Override
    protected ResourceLocation getTextureLocation(ArrowRenderState renderState) {
        return null ;
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }



    @Override
    public ResourceLocation getTextureLocation(IceShardArrowEntity entity) {
        return LAYER_LOCATION;
    }

    @Override
    public boolean shouldRender(IceShardArrowEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return livingEntity != null && camera.isVisible(livingEntity.getBoundingBox());
    }
}
