package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.small;

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

public class SmallRocketModel extends EntityModel<EntityRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "small_rocket"), "main");
    private final ModelPart rocket;

    public SmallRocketModel(ModelPart root) {
        super(root);
        this.rocket = root.getChild("rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition top = rocket.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 98).addBox(-10.0F, -47.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(-10.0F, -47.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 98).addBox(-10.0F, -47.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(10.0F, -47.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(120, 39).addBox(-1.0F, -85.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 18).addBox(-2.0F, -89.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 22).addBox(-2.0F, -6.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 22).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(72, 26).addBox(-3.0F, -1.5F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 57).addBox(0.0F, 4.5F, -12.0F, 0.0F, 17.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 65).mirror().addBox(-12.0F, 4.5F, 0.0F, 8.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 65).addBox(4.0F, 4.5F, 0.0F, 8.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -68.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.48F, -0.7854F, 0.0F));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.829F, 0.7854F, 0.0F));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0F, -65.4F, -13.0779F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r5 = top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.48F, 0.7854F, 0.0F));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, 2.3126F, 0.7854F, 3.1416F));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(32, 57).addBox(0.0F, 24.0F, -12.0F, 0.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -88.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r8 = top.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.48F, 2.3562F, 0.0F));

        PartDefinition cube_r9 = top.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0F, -65.4F, -13.0779F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r10 = top.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, 2.3126F, -0.7854F, 3.1416F));

        PartDefinition cube_r11 = top.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0F, -65.4F, -13.0779F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r12 = top.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.48F, -2.3562F, 0.0F));

        PartDefinition cube_r13 = top.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.829F, -0.7854F, 0.0F));

        PartDefinition cube_r14 = top.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0F, -65.4F, -13.0779F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r15 = top.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));

        PartDefinition cube_r16 = top.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 0.0F, 0.3491F, 3.1416F, 0.0F));

        PartDefinition cube_r17 = top.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

        PartDefinition cube_r18 = top.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition body = rocket.addOrReplaceChild("body", CubeListBuilder.create().texOffs(62, 46).addBox(4.0F, -32.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 89).addBox(-10.0F, -9.0F, -10.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 69).addBox(-10.0F, -9.0F, -10.0F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 89).addBox(-10.0F, -9.0F, 10.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(9.0F, -10.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 69).addBox(10.0F, -9.0F, -10.0F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(10.0F, -32.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(10.0F, -26.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 98).addBox(-10.0F, -32.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 98).addBox(-10.0F, -26.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).addBox(9.0F, -46.0F, -9.0F, 0.0F, 47.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-9.0F, -46.0F, 9.0F, 18.0F, 47.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -46.0F, -9.0F, 18.0F, 47.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(62, 53).addBox(-9.0F, -46.0F, -9.0F, 2.0F, 47.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 101).addBox(-10.0F, -26.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 101).addBox(-10.0F, -32.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).mirror().addBox(-9.0F, -46.0F, -9.0F, 0.0F, 47.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 78).addBox(-10.0F, -26.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(-10.0F, -32.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(36, 47).addBox(-6.0F, -34.0F, -10.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 60).addBox(-4.0F, -24.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 46).addBox(-4.0F, -32.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 60).addBox(-4.0F, -32.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 47).addBox(-9.0F, 1.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 47).addBox(-9.0F, -46.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(62, 53).addBox(-1.0F, -23.5F, -1.0F, 2.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -22.5F, 8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(62, 53).addBox(-1.0F, -23.5F, -1.0F, 2.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -22.5F, 8.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition body_r3 = body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(62, 53).addBox(-1.0F, -23.5F, -1.0F, 2.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -22.5F, -8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(88, 0).addBox(-3.0F, -7.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 12.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r20 = body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(88, 0).addBox(-3.0F, -7.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -3.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r21 = body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(88, 0).addBox(-3.0F, -7.0F, -4.0F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 65).addBox(-8.0F, 4.0F, 8.0F, 16.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(-16, 72).addBox(-8.0F, 4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(-16, 103).addBox(-8.0F, 8.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 49).addBox(-8.0F, 4.0F, -8.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 65).addBox(-8.0F, 4.0F, -8.0F, 16.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 49).addBox(8.0F, 4.0F, -8.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 69).addBox(-6.0F, 1.0F, -6.0F, 12.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 57).addBox(-6.0F, 1.0F, -6.0F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 57).addBox(6.0F, 1.0F, -6.0F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 69).addBox(-6.0F, 1.0F, 6.0F, 12.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fins = rocket.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition cube_r22 = fins.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, 11.0F, 22.8284F, 4.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r23 = fins.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(72, 0).addBox(-0.5858F, 11.0F, 21.4142F, 4.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r24 = fins.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, 11.0F, 20.0F, 4.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r25 = fins.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(72, 0).addBox(-3.4142F, 11.0F, 21.4142F, 4.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.0F, -1.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r26 = fins.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0F, 4.25F, 13.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 9.0F, -1.0F, 1.1345F, -2.3562F, 0.0F));

        PartDefinition cube_r27 = fins.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0F, 12.9801F, 1.507F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -18.0F, 1.0F, -2.0944F, -0.7854F, 3.1416F));

        PartDefinition cube_r28 = fins.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(110, 46).addBox(0.0F, 1.0F, 13.0F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, -1.0F, 1.1345F, -2.3562F, 0.0F));

        PartDefinition cube_r29 = fins.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(116, 0).addBox(0.4142F, 11.6984F, 0.9094F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -18.0F, 1.0F, -2.0944F, 0.7854F, 3.1416F));

        PartDefinition cube_r30 = fins.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0F, 4.25F, 13.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 9.0F, -1.0F, 1.1345F, 2.3562F, 0.0F));

        PartDefinition cube_r31 = fins.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(110, 46).addBox(0.0F, 1.0F, 13.0F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, -1.0F, 1.1345F, 2.3562F, 0.0F));

        PartDefinition cube_r32 = fins.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(116, 0).addBox(-2.4142F, 11.6984F, 0.9094F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -18.0F, 1.0F, 1.0472F, -0.7854F, 0.0F));

        PartDefinition cube_r33 = fins.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(110, 46).addBox(0.0F, 1.0F, 13.0F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, 1.0F, 1.1345F, -0.7854F, 0.0F));

        PartDefinition cube_r34 = fins.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0F, 4.25F, 13.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 9.0F, 1.0F, 1.1345F, -0.7854F, 0.0F));

        PartDefinition cube_r35 = fins.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0F, 10.4167F, 0.3117F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -18.0F, 1.0F, 1.0472F, 0.7854F, 0.0F));

        PartDefinition cube_r36 = fins.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(110, 46).addBox(0.0F, 1.0F, 13.0F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 1.0F, 1.1345F, 0.7854F, 0.0F));

        PartDefinition cube_r37 = fins.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0F, 4.25F, 13.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 9.0F, 1.0F, 1.1345F, 0.7854F, 0.0F));

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