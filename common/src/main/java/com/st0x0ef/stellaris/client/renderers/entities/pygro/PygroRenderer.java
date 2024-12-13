package com.st0x0ef.stellaris.client.renderers.entities.pygro;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.mobs.pygro.Pygro;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

@Environment(EnvType.CLIENT)
public class PygroRenderer extends HumanoidMobRenderer<Pygro, PygroRenderState, PygroModel> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/entity/pygro.png");

    public PygroRenderer(EntityRendererProvider.Context context) {
        super(context, new PygroModel(context.bakeLayer(PygroModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public PygroRenderState createRenderState() {
        return new PygroRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(PygroRenderState entity) {
        return TEXTURE;
    }


    private static PygroModel createModel(EntityModelSet p_174350_, ModelLayerLocation p_174351_) {
        return new PygroModel(p_174350_.bakeLayer(p_174351_));
    }

    protected boolean isShaking(PygroRenderState state) {
        return super.isShaking(state) || state != null && state.isConverting;
    }
}