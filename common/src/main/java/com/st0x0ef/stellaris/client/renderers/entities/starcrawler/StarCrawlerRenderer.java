package com.st0x0ef.stellaris.client.renderers.entities.starcrawler;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.mobs.StarCrawler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class StarCrawlerRenderer extends MobRenderer<StarCrawler, LivingEntityRenderState, StarCrawlerModel> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/entity/star_crawler.png");

    public StarCrawlerRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new StarCrawlerModel(renderManagerIn.bakeLayer(StarCrawlerModel.LAYER_LOCATION)), 0f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderer) {
        return TEXTURE;
    }


}