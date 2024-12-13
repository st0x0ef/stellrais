package com.st0x0ef.stellaris.client.renderers.entities.martianraptor;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.mobs.MartianRaptor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MartianRaptorRenderer extends MobRenderer<MartianRaptor, LivingEntityRenderState, MartianRaptorModel> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/entity/martian_raptor.png");

    public MartianRaptorRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MartianRaptorModel(renderManagerIn.bakeLayer(MartianRaptorModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState p_114482_) {
        return TEXTURE;
    }
}