package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.tiny;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class TinyRocketModel extends EntityModel<EntityRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "tiny_rocket"), "main");
    private final ModelPart rocket;

    public TinyRocketModel(ModelPart root) {
        super(root);
        this.rocket = root.getChild("rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition top = rocket.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 48).addBox(10.0F, -52.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-10.0F, -52.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-10.0F, -52.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-10.0F, -52.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(104, 67).addBox(-3.0F, -75.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(88, 69).addBox(-2.0F, -77.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(80, 69).addBox(-1.0F, -89.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 69).addBox(-2.0F, -90.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, -2.3562F, 0.0F));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, 2.3562F, 0.0F));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, 0.7854F, 0.0F));

        PartDefinition cube_r5 = top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));

        PartDefinition cube_r8 = top.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, 3.1416F, 0.0F));

        PartDefinition body = rocket.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 44).addBox(-6.0F, -42.0F, -10.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 58).addBox(-4.0F, -32.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(63, 43).addBox(4.0F, -40.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 58).addBox(-4.0F, -40.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(63, 43).addBox(-4.0F, -40.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -51.0F, -9.0F, 18.0F, 44.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 77).addBox(-9.0F, -51.0F, -9.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).addBox(-9.0F, -51.0F, -9.0F, 0.0F, 44.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-9.0F, -51.0F, 9.0F, 18.0F, 44.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).addBox(9.0F, -51.0F, -9.0F, 0.0F, 44.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).addBox(-10.0F, -15.0F, -10.0F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(10.0F, -15.0F, -10.0F, 0.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).addBox(-10.0F, -15.0F, 10.0F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-10.0F, -15.0F, -10.0F, 0.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 44).addBox(-9.0F, -7.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 44).addBox(-9.0F, -50.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(-13.0F, -17.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 71).addBox(-12.0F, -20.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 71).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -29.0F, 8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -29.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -29.0F, -8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(94, 15).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, -1).addBox(-8.0F, -4.0F, -8.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(94, 15).addBox(-8.0F, -4.0F, 8.0F, 16.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, -1).addBox(8.0F, -4.0F, -8.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(78, 22).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(80, 81).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(94, 19).addBox(-6.0F, -7.0F, 6.0F, 12.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, 19).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, 7).addBox(6.0F, -7.0F, -6.0F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(94, 7).addBox(-6.0F, -7.0F, -6.0F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fins = rocket.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r13 = fins.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, -1.0F, 1.1345F, 2.3562F, 0.0F));

        PartDefinition cube_r14 = fins.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r15 = fins.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r16 = fins.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, -1.0F, 1.1345F, -2.3562F, 0.0F));

        PartDefinition cube_r17 = fins.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r18 = fins.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 1.0F, 1.1345F, -0.7854F, 0.0F));

        PartDefinition cube_r19 = fins.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 1.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r20 = fins.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 1.0F, 1.1345F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(EntityRenderState state) {
        //this.rocket.yRot = netHeadYaw / (180F / (float) Math.PI);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        rocket.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}