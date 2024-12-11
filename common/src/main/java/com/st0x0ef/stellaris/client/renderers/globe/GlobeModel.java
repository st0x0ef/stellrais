package com.st0x0ef.stellaris.client.renderers.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.blocks.entities.GlobeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class GlobeModel<T extends GlobeBlockEntity> extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "globe"), "main");
    public final ModelPart globe;

    public GlobeModel(ModelPart root) {
        super(root, RenderType::entityCutout);
        this.globe = root.getChild("globe");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition globe = partdefinition.addOrReplaceChild("globe", CubeListBuilder.create().texOffs(0, 16).addBox(-7.0F, -16.0F, 1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 28).addBox(-4.0F, -1.0F, -2.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 35).addBox(-3.0F, -5.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-2.0F, -4.0F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, -1.0F));

        PartDefinition planet = globe.addOrReplaceChild("planet", CubeListBuilder.create(), PartPose.offset(-1.0F, -10.0F, 1.0F));

        PartDefinition cube_r1 = planet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        globe.render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    public void setupAnim(GlobeBlockEntity entity, float partialTicks) {
        this.globe.getChild("planet").yRot = Mth.lerp(partialTicks, entity.getYaw0(), entity.getYaw());
    }
}
