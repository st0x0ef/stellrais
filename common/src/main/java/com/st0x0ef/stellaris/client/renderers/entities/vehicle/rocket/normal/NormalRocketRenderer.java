package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.normal;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.VehicleRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.RocketModel;
import com.st0x0ef.stellaris.common.entities.RocketEntity;
import com.st0x0ef.stellaris.common.rocket_upgrade.ModelUpgrade;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class NormalRocketRenderer extends VehicleRenderer<RocketEntity, NormalRocketModel<RocketEntity>> {
    public ResourceLocation TEXTURE = new ResourceLocation(Stellaris.MODID, "textures/vehicle/rocket_skin/normal/standard.png");

    public NormalRocketRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new NormalRocketModel<>(renderManagerIn.bakeLayer(NormalRocketModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketEntity entity) {
        entity.MODEL_UPGRADE = new ModelUpgrade(RocketModel.NORMAL);
        ResourceLocation location = new ResourceLocation(entity.getFullSkinTexture());
        return location;
    }

    @Override
    protected boolean isShaking(RocketEntity rocket) {
        return rocket.ROCKET_START;
    }

}