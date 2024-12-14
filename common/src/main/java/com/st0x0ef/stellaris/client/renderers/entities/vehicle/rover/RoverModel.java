package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rover;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;


public class RoverModel extends EntityModel<EntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "rover"), "main");

    private final ModelPart rover;
    private final ModelPart antenna;

    public RoverModel(ModelPart root) {
        super(root);
        this.rover = root.getChild("Frame");
        this.antenna = root.getChild("Antenna");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Frame = partdefinition.addOrReplaceChild("Frame", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -11.0F, -26.0F, 30.0F, 2.0F, 48.0F, new CubeDeformation(0.0F))
                .texOffs(108, 0).addBox(9.0F, -9.0F, -23.0F, 4.0F, 2.0F, 42.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-11.0F, -11.0F, -35.0F, 22.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(51, 106).addBox(14.0F, -12.0F, -25.0F, 2.0F, 2.0F, 47.0F, new CubeDeformation(0.0F))
                .texOffs(132, 98).addBox(-16.0F, -12.0F, 22.0F, 32.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(132, 92).addBox(-16.0F, -12.0F, -28.0F, 32.0F, 3.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(0, 104).addBox(-16.0F, -12.0F, -25.0F, 2.0F, 2.0F, 47.0F, new CubeDeformation(0.0F))
                .texOffs(102, 106).addBox(-13.0F, -9.0F, -23.0F, 4.0F, 2.0F, 42.0F, new CubeDeformation(0.0F))
                .texOffs(132, 80).addBox(-11.0F, -13.0F, -14.0F, 22.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(66, 50).addBox(-11.0F, -19.0F, -24.0F, 22.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 104).addBox(3.0F, -21.0F, -17.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 45).addBox(6.0F, -20.0F, -18.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 65).addBox(-12.0F, -15.0F, -15.0F, 24.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-14.0F, -22.0F, 2.0F, 9.0F, 11.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(40, 80).addBox(6.0F, -21.0F, 24.0F, 4.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(74, 78).addBox(4.0F, -28.0F, 24.0F, 8.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-12.0F, -28.0F, 24.0F, 8.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 24).addBox(-10.0F, -21.0F, 24.0F, 4.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 2.0F));

        PartDefinition cube_r1 = Frame.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 50).addBox(-4.0F, 0.0F, -26.0F, 7.0F, 2.0F, 52.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(19.0F, -10.0F, -2.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r2 = Frame.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 50).addBox(-6.0F, -11.0F, -7.0F, 13.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -13.0F, 9.0F, 0.0F, 0.2182F, 0.0F));

        PartDefinition cube_r3 = Frame.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(132, 50).addBox(-12.0F, -1.0F, 0.0F, 24.0F, 3.0F, 12.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -14.0F, -5.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r4 = Frame.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 50).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -16.0F, -20.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition cube_r5 = Frame.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(74, 62).addBox(-10.0F, -17.0F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(74, 66).addBox(-10.0F, -17.0F, 18.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -2.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r6 = Frame.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(74, 70).addBox(-5.0503F, -2.1508F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -4.0F, -21.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r7 = Frame.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(68, 87).addBox(-2.0F, -2.0F, 2.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 80).addBox(-16.0F, -2.0F, 2.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -8.0F, -35.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r8 = Frame.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(86, 93).addBox(-0.55F, 0.0F, -0.85F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(11.0F, -11.0F, -34.0F, 0.0F, -0.5672F, 0.0F));

        PartDefinition cube_r9 = Frame.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(66, 52).addBox(-3.0F, 0.0F, -26.0F, 7.0F, 2.0F, 52.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-19.0F, -10.0F, -2.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r10 = Frame.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(66, 95).addBox(-7.45F, 0.0F, -0.85F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-11.0F, -11.0F, -34.0F, 0.0F, 0.5672F, 0.0F));

        PartDefinition cube_r11 = Frame.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(74, 74).addBox(-1.0F, -17.0F, 18.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 76).addBox(-1.0F, -17.0F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r12 = Frame.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(26, 76).addBox(-5.9497F, -2.1508F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -4.0F, -21.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition Wheels = Frame.addOrReplaceChild("Wheels", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -2.0F));

        PartDefinition Wheel1 = Wheels.addOrReplaceChild("Wheel1", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(96, 72).addBox(-1.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(42, 36).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 0).addBox(-2.0F, -2.0F, 3.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(108, 0).addBox(-2.0F, 3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(122, 0).addBox(-2.0F, -2.0F, -5.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F)), PartPose.offset(19.0F, -5.0F, -19.0F));

        PartDefinition cube_r13 = Wheel1.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r14 = Wheel1.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r15 = Wheel1.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F))
                .texOffs(36, 38).addBox(0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 118).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r16 = Wheel1.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Wheel2 = Wheels.addOrReplaceChild("Wheel2", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(88, 81).addBox(-1.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(31, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 0).addBox(-2.0F, -2.0F, 3.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(108, 0).addBox(-2.0F, 3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(122, 0).addBox(-2.0F, -2.0F, -5.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F)), PartPose.offset(19.0F, -5.0F, 0.0F));

        PartDefinition cube_r17 = Wheel2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r18 = Wheel2.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r19 = Wheel2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F))
                .texOffs(31, 4).addBox(0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(61, 118).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r20 = Wheel2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Wheel3 = Wheels.addOrReplaceChild("Wheel3", CubeListBuilder.create().texOffs(108, 0).addBox(-3.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(0, 89).addBox(-2.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(42, 22).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 0).addBox(-3.0F, -2.0F, 3.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(108, 0).addBox(-3.0F, 3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(122, 0).addBox(-3.0F, -2.0F, -5.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F)), PartPose.offset(20.0F, -5.0F, 19.0F));

        PartDefinition cube_r21 = Wheel3.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r22 = Wheel3.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r23 = Wheel3.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F))
                .texOffs(32, 38).addBox(0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(71, 118).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r24 = Wheel3.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(108, 0).addBox(-2.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Wheel4 = Wheels.addOrReplaceChild("Wheel4", CubeListBuilder.create().texOffs(108, 0).addBox(0.0F, 3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(122, 0).addBox(0.0F, -2.0F, -5.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(122, 0).addBox(0.0F, -2.0F, 3.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(108, 0).addBox(0.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(28, 88).addBox(1.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(6, 8).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, -5.0F, -19.0F));

        PartDefinition cube_r25 = Wheel4.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(4, 8).addBox(-1.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(85, 116).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r26 = Wheel4.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r27 = Wheel4.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r28 = Wheel4.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Wheel5 = Wheels.addOrReplaceChild("Wheel5", CubeListBuilder.create().texOffs(31, 0).addBox(1.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(6, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 0).addBox(0.0F, -2.0F, 3.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(122, 0).addBox(0.0F, -2.0F, -5.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(108, 0).addBox(0.0F, 3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(108, 0).addBox(0.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F)), PartPose.offset(-20.0F, -5.0F, 0.0F));

        PartDefinition cube_r29 = Wheel5.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r30 = Wheel5.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r31 = Wheel5.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F))
                .texOffs(0, 0).addBox(-1.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(39, 50).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r32 = Wheel5.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Wheel6 = Wheels.addOrReplaceChild("Wheel6", CubeListBuilder.create().texOffs(108, 0).addBox(1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F))
                .texOffs(14, 88).addBox(2.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 0).addBox(1.0F, -2.0F, 3.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(122, 0).addBox(1.0F, -2.0F, -5.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.12F))
                .texOffs(108, 0).addBox(1.0F, 3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.12F)), PartPose.offset(-21.0F, -5.0F, 19.0F));

        PartDefinition cube_r33 = Wheel6.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r34 = Wheel6.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r35 = Wheel6.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F))
                .texOffs(0, 2).addBox(-1.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(85, 108).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r36 = Wheel6.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -5.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.121F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Antenna = partdefinition.addOrReplaceChild("Antenna", CubeListBuilder.create().texOffs(0, 0).addBox(8.0F, -29.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(39, 0).addBox(14.0F, -28.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(0.0F, -27.5F, -1.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(8.0F, -35.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(100, 62).addBox(4.0F, -28.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 62).addBox(-1.0F, -28.0F, -1.0F, 2.0F, 29.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 9.0F, 20.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }



    @Override
    public void setupAnim(EntityRenderState state) {
        /*this.rover.yRot = netHeadYaw / (180F / (float) Math.PI);

        float wheelRotation = (float) entity.getDeltaMovement().x / 5f;
        if (entity.getDirection() == Direction.NORTH ||entity.getDirection() == Direction.SOUTH) {
            wheelRotation = (float) entity.getDeltaMovement().z / 5f;
        }



        if (entity.isForward()) {
            this.rover.getChild("Wheels").getChild("Wheel1").xRot += wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel2").xRot += wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel3").xRot += wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel4").xRot += wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel5").xRot += wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel6").xRot += wheelRotation;

        } else if (entity.isBackward()) {
            this.rover.getChild("Wheels").getChild("Wheel1").xRot -= -wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel2").xRot -= -wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel3").xRot -= -wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel4").xRot -= -wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel5").xRot -= -wheelRotation;
            this.rover.getChild("Wheels").getChild("Wheel6").xRot -= -wheelRotation;

        } else {
            this.rover.getChild("Wheels").getChild("Wheel1").xRot = 0;
            this.rover.getChild("Wheels").getChild("Wheel2").xRot = 0;
            this.rover.getChild("Wheels").getChild("Wheel3").xRot = 0;
            this.rover.getChild("Wheels").getChild("Wheel4").xRot = 0;
            this.rover.getChild("Wheels").getChild("Wheel5").xRot = 0;
            this.rover.getChild("Wheels").getChild("Wheel6").xRot = 0;

        }

        if (entity.getXRot() > 0) {
            this.rover.getChild("Wheels").getChild("Wheel1").xRot += entity.getXRot() / 4;
            this.rover.getChild("Wheels").getChild("Wheel2").xRot += entity.getXRot() / 4;
            this.rover.getChild("Wheels").getChild("Wheel3").xRot += entity.getXRot() / 4;
            this.rover.getChild("Wheels").getChild("Wheel4").xRot += entity.getXRot() / 4;
        }*/

        this.antenna.yRot = state.ageInTicks / 20;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.rover.render(poseStack, buffer, packedLight, packedOverlay);
        this.antenna.render(poseStack, buffer, packedLight, packedOverlay);
    }
}