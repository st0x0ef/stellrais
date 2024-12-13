package com.st0x0ef.stellaris.client.renderers.entities.cheeseboss;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.mobs.cheese_boss.CheeseBoss;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CheeseBossRenderer extends MobRenderer<CheeseBoss, LivingEntityRenderState, CheeseBossModel> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/entity/cheese_boss.png");

    public CheeseBossRenderer(EntityRendererProvider.Context context) {
        super(context, new CheeseBossModel(context.bakeLayer(CheeseBossModel.LAYER_LOCATION)), 1f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState entity) {return TEXTURE;}

}
