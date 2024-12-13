package com.st0x0ef.stellaris.client.renderers.entities.alien;


import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.mobs.alien.Alien;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class AlienRenderer extends MobRenderer<Alien, LivingEntityRenderState, AlienModel> {

    /** TEXTURES */
    public static final ResourceLocation ALIEN = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"textures/entity/alien/alien.png");

    public static final CustomHeadLayer.Transforms CUSTOM_HEAD_TRANSFORMS = new CustomHeadLayer.Transforms(-0.1171875F, -0.07421875F, 1.0F);

    public AlienRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AlienModel(renderManagerIn.bakeLayer(AlienModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer(this, renderManagerIn.getModelSet(), CUSTOM_HEAD_TRANSFORMS, renderManagerIn.getItemRenderer()));
        this.addLayer(new AlienProfessionLayer(this, renderManagerIn.getResourceManager()));
        this.addLayer(new CrossedArmsItemLayer(this, renderManagerIn.getItemRenderer()));
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return ALIEN;
    }
}