package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.normal;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.VehicleRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.RocketModel;
import com.st0x0ef.stellaris.common.entities.vehicles.IVehicleEntity;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import com.st0x0ef.stellaris.common.vehicle_upgrade.ModelUpgrade;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class NormalRocketRenderer extends VehicleRenderer<RocketEntity, EntityRenderState, NormalRocketModel> {
    public ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/vehicle/rocket_skin/normal/standard.png");

    public NormalRocketRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new NormalRocketModel(renderManagerIn.bakeLayer(NormalRocketModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(IVehicleEntity entity) {
        RocketEntity rocket = (RocketEntity) entity;
        rocket.MODEL_UPGRADE = new ModelUpgrade(RocketModel.NORMAL);
        return rocket.getFullSkinTexture();
    }

    @Override
    protected boolean isShaking(RocketEntity rocket) {
        return rocket.getEntityData().get(RocketEntity.ROCKET_START);
    }

}