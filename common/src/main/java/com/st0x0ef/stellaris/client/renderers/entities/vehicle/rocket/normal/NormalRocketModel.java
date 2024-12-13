package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.normal;

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

public class NormalRocketModel extends EntityModel<EntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "normal_rocket"), "main");
    private final ModelPart rocket;

    public NormalRocketModel(ModelPart root) {
        super(root);
        this.rocket = root.getChild("rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 0.0F));
        PartDefinition body = rocket.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, -18).addBox(9.0F, -51.0F, -9.0F, 0.0F, 52.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -51.0F, -9.0F, 18.0F, 52.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(80, 74).addBox(-9.0F, -51.0F, -9.0F, 2.0F, 52.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-9.0F, -51.0F, 9.0F, 18.0F, 52.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).addBox(-9.0F, -51.0F, -9.0F, 0.0F, 52.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 99).addBox(-9.0F, 1.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 99).addBox(-9.0F, -51.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-10.0F, -11.0F, -10.0F, 20.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(10.0F, -11.0F, -10.0F, 0.0F, 11.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-10.0F, -11.0F, 10.0F, 20.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-10.0F, -11.0F, -10.0F, 0.0F, 11.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 65).addBox(-10.0F, -26.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 65).addBox(-10.0F, -32.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-10.0F, -26.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-10.0F, -32.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 83).addBox(-10.0F, -44.0F, -10.0F, 20.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 63).addBox(-10.0F, -44.0F, -10.0F, 0.0F, 7.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(40, 52).addBox(-6.0F, -34.0F, -10.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(10.0F, -32.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(10.0F, -26.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 63).addBox(10.0F, -44.0F, -10.0F, 0.0F, 7.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 83).addBox(-10.0F, -44.0F, 10.0F, 20.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-10.0F, -32.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-10.0F, -26.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(39, 65).addBox(-4.0F, -24.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 51).addBox(-4.0F, -32.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(39, 65).addBox(-4.0F, -32.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 51).addBox(4.0F, -32.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(96, 9).addBox(-15.0F, -7.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(96, 9).addBox(-15.0F, -7.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(80, 74).addBox(-1.0F, -26.0F, -1.0F, 2.0F, 52.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -25.0F, 8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(80, 74).addBox(-1.0F, -26.0F, -1.0F, 2.0F, 52.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -25.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition body_r3 = body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(80, 74).addBox(-1.0F, -26.0F, -1.0F, 2.0F, 52.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -25.0F, -8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bottom = rocket.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 117).addBox(-7.0F, -12.0F, -7.0F, 14.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 103).addBox(-7.0F, -12.0F, -7.0F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 117).addBox(-7.0F, -12.0F, 7.0F, 14.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 103).addBox(7.0F, -12.0F, -7.0F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(18, 92).addBox(-9.0F, -9.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 120).addBox(-9.0F, -9.0F, -9.0F, 18.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(9.0F, -9.0F, -9.0F, 0.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(-9.0F, -9.0F, -9.0F, 0.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 120).addBox(-9.0F, -9.0F, 9.0F, 18.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(18, 110).addBox(-9.0F, -5.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition top = rocket.addOrReplaceChild("top", CubeListBuilder.create().texOffs(72, 18).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 87).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(10.0F, 41.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-10.0F, 41.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-10.0F, 41.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-10.0F, 41.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(52, 67).addBox(-5.0F, 26.0F, 6.0F, 10.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(52, 57).addBox(-6.0F, 26.0F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(52, 67).addBox(-5.0F, 26.0F, -6.0F, 10.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(52, 57).addBox(6.0F, 26.0F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -102.5F, 0.0F));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 67).addBox(0.0F, -10.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 26.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 67).addBox(0.0F, -10.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 26.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition cube_r5 = top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(16, 77).addBox(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 26.0F, -6.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 77).addBox(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 26.0F, 6.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition body_r4 = top.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(16, 67).addBox(-9.0F, -10.0F, 0.25F, 18.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.5F, -9.3F, -0.3491F, 0.0F, 0.0F));

        PartDefinition body_r5 = top.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(16, 49).addBox(0.0F, -10.0F, -9.0F, 0.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 42.5F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition body_r6 = top.addOrReplaceChild("body_r6", CubeListBuilder.create().texOffs(16, 67).addBox(-9.0F, -10.0F, 0.0F, 18.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.5F, 9.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition body_r7 = top.addOrReplaceChild("body_r7", CubeListBuilder.create().texOffs(16, 49).addBox(0.0F, -10.0F, -9.0F, 0.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 42.5F, -0.3F, 0.0F, 0.0F, -0.3491F));

        PartDefinition body_r8 = top.addOrReplaceChild("body_r8", CubeListBuilder.create().texOffs(98, 31).addBox(-1.0F, -16.0F, -2.0607F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4571F, 26.5F, 6.4571F, 0.4363F, -0.7854F, 0.0F));

        PartDefinition body_r9 = top.addOrReplaceChild("body_r9", CubeListBuilder.create().texOffs(76, 54).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 27.0F, 5.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition body_r10 = top.addOrReplaceChild("body_r10", CubeListBuilder.create().texOffs(98, 31).addBox(-1.0F, -16.0F, 0.0607F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4571F, 26.5F, -6.4571F, -0.4363F, 0.7854F, 0.0F));

        PartDefinition body_r11 = top.addOrReplaceChild("body_r11", CubeListBuilder.create().texOffs(76, 54).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 27.0F, -5.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition body_r12 = top.addOrReplaceChild("body_r12", CubeListBuilder.create().texOffs(98, 31).addBox(-2.0607F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4571F, 26.5F, -6.4571F, -0.3999F, 0.6956F, -0.583F));

        PartDefinition body_r13 = top.addOrReplaceChild("body_r13", CubeListBuilder.create().texOffs(76, 54).addBox(0.0F, -0.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 27.0F, -5.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition body_r14 = top.addOrReplaceChild("body_r14", CubeListBuilder.create().texOffs(98, 31).addBox(-2.0607F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4571F, 26.5F, 6.4571F, 0.3999F, -0.6956F, -0.583F));

        PartDefinition body_r15 = top.addOrReplaceChild("body_r15", CubeListBuilder.create().texOffs(76, 54).addBox(0.0F, -0.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 27.0F, 5.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition body_r16 = top.addOrReplaceChild("body_r16", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 42.5F, -9.0F, -0.4326F, 0.678F, -0.6346F));

        PartDefinition body_r17 = top.addOrReplaceChild("body_r17", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 42.5F, -9.0F, -0.48F, 0.7854F, 0.0F));

        PartDefinition body_r18 = top.addOrReplaceChild("body_r18", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 42.5F, 9.0F, 0.4326F, 0.678F, 0.6346F));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(80, 17).addBox(-2.0F, -6.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(80, 0).addBox(-3.0F, -1.5F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(80, 17).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition body_r19 = top.addOrReplaceChild("body_r19", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 42.5F, 9.0F, 0.48F, 0.7854F, 0.0F));

        PartDefinition fins = rocket.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));

        PartDefinition cube_r8 = fins.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 67).addBox(-2.0F, 5.0F, 23.8284F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r9 = fins.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 67).addBox(-0.5858F, 5.0F, 22.4142F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r10 = fins.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 67).addBox(-2.0F, 5.0F, 21.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r11 = fins.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 67).addBox(-3.4142F, 5.0F, 22.4142F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r12 = fins.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(72, 21).addBox(0.0F, 4.9167F, -0.6883F, 0.0F, 20.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -10.0F, 1.0F, 1.0472F, 0.7854F, 0.0F));

        PartDefinition cube_r13 = fins.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(72, 21).addBox(1.4142F, 6.1984F, -0.0906F, 0.0F, 20.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -10.0F, 1.0F, -2.0944F, 0.7854F, 3.1416F));

        PartDefinition cube_r14 = fins.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(72, 21).addBox(0.0F, 7.4801F, 0.507F, 0.0F, 20.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -10.0F, 1.0F, -2.0944F, -0.7854F, 3.1416F));

        PartDefinition cube_r15 = fins.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 21).addBox(-1.4142F, 6.1984F, -0.0906F, 0.0F, 20.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -10.0F, 1.0F, 1.0472F, -0.7854F, 0.0F));

        PartDefinition cube_r16 = fins.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 12.9801F, 1.507F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 1.0F, -2.0944F, -0.7854F, 3.1416F));

        PartDefinition cube_r17 = fins.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 0).addBox(-2.4142F, 11.6984F, 0.9094F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 1.0F, 1.0472F, -0.7854F, 0.0F));

        PartDefinition cube_r18 = fins.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 10.4167F, 0.3117F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 1.0F, 1.0472F, 0.7854F, 0.0F));

        PartDefinition cube_r19 = fins.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 0).addBox(0.4142F, 11.6984F, 0.9094F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 1.0F, -2.0944F, 0.7854F, 3.1416F));

        PartDefinition cube_r20 = fins.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 10.4167F, 0.3117F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -15.0F, 1.0F, 1.0472F, 0.7854F, 0.0F));

        PartDefinition cube_r21 = fins.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(72, 0).addBox(-2.4142F, 11.6984F, 0.9094F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -15.0F, 1.0F, 1.0472F, -0.7854F, 0.0F));

        PartDefinition cube_r22 = fins.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 12.9801F, 1.507F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -15.0F, 1.0F, -2.0944F, -0.7854F, 3.1416F));

        PartDefinition cube_r23 = fins.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(72, 0).addBox(0.4142F, 11.6984F, 0.9094F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -15.0F, 1.0F, -2.0944F, 0.7854F, 3.1416F));

        PartDefinition cube_r24 = fins.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 12.9801F, 1.507F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -23.0F, 1.0F, -2.0944F, -0.7854F, 3.1416F));

        PartDefinition cube_r25 = fins.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(72, 0).addBox(0.4142F, 11.6984F, 0.9094F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -23.0F, 1.0F, -2.0944F, 0.7854F, 3.1416F));

        PartDefinition cube_r26 = fins.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(72, 0).addBox(-2.4142F, 11.6984F, 0.9094F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -23.0F, 1.0F, 1.0472F, -0.7854F, 0.0F));

        PartDefinition cube_r27 = fins.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 10.4167F, 0.3117F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -23.0F, 1.0F, 1.0472F, 0.7854F, 0.0F));

        PartDefinition boosters = rocket.addOrReplaceChild("boosters", CubeListBuilder.create().texOffs(104, 90).addBox(-19.0F, -39.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(96, 49).mirror().addBox(-20.0F, -33.0F, -4.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 79).mirror().addBox(-20.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(72, 64).mirror().addBox(-19.0F, -7.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(110, 32).addBox(-12.0F, -17.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(110, 32).addBox(-12.0F, -27.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(104, 90).addBox(11.0F, -39.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(96, 49).addBox(10.0F, -33.0F, -4.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(72, 64).addBox(11.0F, -7.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 79).addBox(10.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(110, 32).addBox(8.0F, -27.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(110, 32).addBox(8.0F, -17.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0F, 0.0F));

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