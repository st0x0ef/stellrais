package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.big;

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

public class BigRocketModel extends EntityModel<EntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "big_rocket"), "main");
    private final ModelPart rocket;

    public BigRocketModel(ModelPart root) {
        super(root);
        this.rocket = root.getChild("rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create().texOffs(88, 80).addBox(10.0F, -28.0F, -10.0F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 100).addBox(-10.0F, -28.0F, 10.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 80).addBox(-10.0F, -28.0F, -10.0F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 100).addBox(-10.0F, -28.0F, -10.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 115).addBox(-10.0F, -38.0F, -10.0F, 20.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 95).addBox(10.0F, -38.0F, -10.0F, 0.0F, 7.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 115).addBox(-10.0F, -38.0F, 10.0F, 20.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 95).addBox(-10.0F, -38.0F, -10.0F, 0.0F, 7.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 108).addBox(-10.0F, -63.0F, -10.0F, 20.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 88).addBox(10.0F, -63.0F, -10.0F, 0.0F, 7.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 108).addBox(-10.0F, -63.0F, 10.0F, 20.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 88).addBox(-10.0F, -63.0F, -10.0F, 0.0F, 7.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition side_booster_right = rocket.addOrReplaceChild("side_booster_right", CubeListBuilder.create().texOffs(104, 16).addBox(-18.0F, -56.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(96, 28).mirror().addBox(-19.0F, -50.0F, -4.0F, 8.0F, 35.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(16, 115).mirror().addBox(-19.0F, -13.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(72, 30).mirror().addBox(-18.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 4.0F, 0.0F));

        PartDefinition side_booster_left = rocket.addOrReplaceChild("side_booster_left", CubeListBuilder.create().texOffs(104, 16).addBox(11.0F, -56.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(96, 28).addBox(10.0F, -50.0F, -4.0F, 8.0F, 35.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(72, 30).addBox(11.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 115).addBox(10.0F, -13.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 4.0F, 0.0F));

        PartDefinition cube_r1 = side_booster_left.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(102, 124).addBox(0.844F, -11.3692F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(102, 124).addBox(-7.6412F, -19.8544F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 85).addBox(-5.3778F, -18.591F, 0.0F, 23.0F, 15.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(102, 124).addBox(8.6222F, -3.591F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -31.75F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r2 = side_booster_left.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(102, 124).addBox(-11.844F, -11.3692F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(102, 124).addBox(-3.3588F, -19.8544F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 85).mirror().addBox(-17.6222F, -18.591F, 0.0F, 23.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(102, 124).addBox(-19.6222F, -3.591F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -31.75F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition main = rocket.addOrReplaceChild("main", CubeListBuilder.create().texOffs(36, -18).addBox(-9.0F, -74.0F, -9.0F, 0.0F, 59.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).addBox(9.0F, -74.0F, -9.0F, 0.0F, 59.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-9.0F, -74.0F, 9.0F, 18.0F, 59.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -74.0F, -9.0F, 18.0F, 59.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(73, 67).addBox(-9.0F, -74.0F, -9.0F, 2.0F, 59.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(77, 69).addBox(-9.0F, -74.0F, -1.0F, 2.0F, 59.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(77, 69).addBox(7.0F, -74.0F, 1.0F, 2.0F, 59.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(77, 69).addBox(7.0F, -74.0F, -1.0F, 2.0F, 59.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(77, 69).addBox(-9.0F, -74.0F, 1.0F, 2.0F, 59.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 59).addBox(-9.0F, -15.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 59).addBox(-9.0F, -73.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(35, 72).addBox(-4.0F, -52.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 72).addBox(-4.0F, -44.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 58).addBox(-4.0F, -52.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 59).addBox(-6.0F, -54.0F, -10.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 58).addBox(4.0F, -52.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(40, 101).addBox(-15.0F, -7.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r4 = main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(40, 101).addBox(-15.0F, -7.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition main_r1 = main.addOrReplaceChild("main_r1", CubeListBuilder.create().texOffs(73, 67).addBox(-1.0F, -29.5F, -1.0F, 2.0F, 59.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -44.5F, 8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition main_r2 = main.addOrReplaceChild("main_r2", CubeListBuilder.create().texOffs(73, 67).addBox(-1.0F, -29.5F, -1.0F, 2.0F, 59.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -44.5F, 8.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition main_r3 = main.addOrReplaceChild("main_r3", CubeListBuilder.create().texOffs(73, 67).addBox(-1.0F, -29.5F, -1.0F, 2.0F, 59.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -44.5F, -8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition main_r4 = main.addOrReplaceChild("main_r4", CubeListBuilder.create().texOffs(77, 69).addBox(-1.0F, -29.5F, -14.0F, 2.0F, 59.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(77, 69).addBox(-1.0F, -29.5F, 0.0F, 2.0F, 59.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -44.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition fins = main.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(-2.0F, -8.0F, -2.0F));

        PartDefinition pyramid = main.addOrReplaceChild("pyramid", CubeListBuilder.create().texOffs(73, 29).addBox(-6.0F, -77.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(73, 29).addBox(6.0F, -77.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(88, 102).addBox(10.0F, -58.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 102).addBox(-10.0F, -58.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 122).addBox(-10.0F, -58.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 122).addBox(-10.0F, -58.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition cube_r5 = pyramid.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        PartDefinition cube_r6 = pyramid.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        PartDefinition cube_r7 = pyramid.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        PartDefinition cube_r8 = pyramid.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        PartDefinition cube_r9 = pyramid.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        PartDefinition cube_r10 = pyramid.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        PartDefinition cube_r11 = pyramid.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.3647F, -77.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r12 = pyramid.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.3647F, -77.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r13 = pyramid.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(72, 0).addBox(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9353F, -77.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r14 = pyramid.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(73, 29).addBox(-6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(73, 29).addBox(6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -73.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r15 = pyramid.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 0).addBox(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9353F, -77.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r16 = pyramid.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        PartDefinition cube_r17 = pyramid.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition cube_r18 = pyramid.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        PartDefinition cube_r19 = pyramid.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        PartDefinition cube_r20 = pyramid.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        PartDefinition cube_r21 = pyramid.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        PartDefinition cube_r22 = pyramid.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        PartDefinition cube_r23 = pyramid.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        PartDefinition cube_r24 = pyramid.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        PartDefinition cube_r25 = pyramid.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition booster = main.addOrReplaceChild("booster", CubeListBuilder.create().texOffs(0, 114).addBox(-6.0F, -15.0F, -6.0F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(6.0F, -15.0F, -6.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(-6.0F, -15.0F, -6.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 114).addBox(-6.0F, -15.0F, 6.0F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 91).addBox(7.0F, -9.0F, -7.0F, 0.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 105).addBox(-7.0F, -9.0F, 7.0F, 14.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 91).addBox(-7.0F, -9.0F, -7.0F, 0.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(-14, 77).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(-14, 91).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 105).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tip = main.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(80, 0).addBox(-4.0F, -110.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(80, 16).addBox(-3.0F, -118.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(104, 0).addBox(-2.0F, -120.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 120).addBox(-2.0F, -135.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(104, 0).addBox(-2.0F, -123.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(120, 0).addBox(-1.0F, -131.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(EntityRenderState state) {
        this.rocket.yRot = netHeadYaw / (180F / (float) Math.PI);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        rocket.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}