package com.st0x0ef.stellaris.client.renderers.entities.vehicle.lander;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.vehicles.LanderEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class LanderModel extends EntityModel<EntityRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "lander"), "main");
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart lander;

    public LanderModel(ModelPart root) {
        super(root);
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
        this.lander = root.getChild("lander");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r1 = leg1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = leg1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 31).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 11).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, -0.7854F, 0.0F));

        PartDefinition cube_r3 = leg1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 17).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, -0.7854F, 0.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r4 = leg2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 11).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, -2.3562F, 0.0F));

        PartDefinition cube_r5 = leg2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 17).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, -2.3562F, 0.0F));

        PartDefinition cube_r6 = leg2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r7 = leg2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r8 = leg3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 31).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 11).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 2.3562F, 0.0F));

        PartDefinition cube_r9 = leg3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 17).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 2.3562F, 0.0F));

        PartDefinition cube_r10 = leg3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r11 = leg3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r12 = leg4.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 31).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 11).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.7854F, 0.0F));

        PartDefinition cube_r13 = leg4.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 17).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.7854F, 0.0F));

        PartDefinition cube_r14 = leg4.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r15 = leg4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition lander = partdefinition.addOrReplaceChild("lander", CubeListBuilder.create().texOffs(88, 0).addBox(-5.0F, -19.0F, -8.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-8.0F, -35.0F, -13.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(78, 46).addBox(8.0F, -30.5F, -8.5F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(1.0F, -33.0F, 14.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-4.0F, -33.0F, 14.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(88, 18).addBox(-6.0F, -23.0F, -14.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(88, 18).addBox(-6.0F, -31.0F, -16.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(64, 56).addBox(10.0F, -28.5F, -6.5F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-5.0F, -13.9F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-7.0F, -10.9F, -7.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-12.0F, -16.0F, -12.0F, 24.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(82, 26).addBox(-8.995F, -36.0F, -12.0F, 18.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r16 = lander.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(82, 26).addBox(-9.0F, -9.0F, -1.0F, 18.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -33.0F, -5.0F, 1.5708F, 0.0F, -3.1416F));

        PartDefinition cube_r17 = lander.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(64, 56).addBox(9.5F, -10.5F, 0.5F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -18.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r18 = lander.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(64, 68).addBox(-6.0F, -38.0F, 5.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(64, 68).addBox(-6.0F, -38.0F, 0.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r19 = lander.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(42, 76).mirror().addBox(-12.5F, -25.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 80).mirror().addBox(-12.5F, -10.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 80).addBox(9.5F, -10.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(42, 76).addBox(9.5F, -25.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 96).addBox(-11.5F, -24.5F, -6.5F, 24.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.5F, 19.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r20 = lander.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(56, 74).addBox(-4.5F, -15.5F, -12.5F, 10.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -17.5F, 15.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r21 = lander.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(52, 98).addBox(-9.5F, -21.5F, -6.5F, 20.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.5F, 23.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r22 = lander.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(102, 46).addBox(-4.5F, -13.0F, 0.0F, 9.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -15.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r23 = lander.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(48, 44).addBox(-5.0F, -16.5F, -6.5F, 11.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.5F, -3.5F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r24 = lander.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 76).addBox(-6.5F, -19.5F, -8.5F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -17.5F, 0.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r25 = lander.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(78, 46).addBox(-1.5F, -8.5F, -5.5F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.5F, -22.0F, -4.0F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.leg1.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.leg2.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.leg3.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.leg4.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.lander.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}