package com.st0x0ef.stellaris.client.renderers.entities.mogler;

import com.st0x0ef.stellaris.Stellaris;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class MoglerModel extends EntityModel<MoglerRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "mogler"), "main");
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public MoglerModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.0F, -13.0F, 16.0F, 9.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(85, 30).addBox(-9.0F, -20.0F, 12.0F, 18.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 41).addBox(-10.0F, -18.0F, 5.0F, 20.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(58, 52).addBox(-11.0F, -18.0F, 2.0F, 22.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 0).addBox(-12.0F, -22.0F, -4.0F, 24.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 66).addBox(-10.0F, -26.0F, -3.0F, 20.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 22).addBox(-7.5F, -8.5F, -1.75F, 15.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(62, 75).addBox(-9.0F, -8.5F, -1.75F, 18.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition jaw2 = head.addOrReplaceChild("jaw2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r8 = jaw2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(96, 99).addBox(5.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition jaw1 = head.addOrReplaceChild("jaw1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r9 = jaw1.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 110).addBox(-10.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(64, 99).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 14.5F, -6.5F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(32, 90).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 14.5F, -6.5F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 88).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 14.5F, 12.5F));

        PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 14.5F, 12.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(MoglerRenderState state) {
        this.head.yRot = state.yRot * ((float)Math.PI / 180F);
        float f = 1.0F - Mth.abs(10 - 2 * state.ageInTicks) / 10.0F;
        this.head.xRot = Mth.lerp(f, 0.0F, -1.14906584F);

        this.leg1.xRot = Mth.cos(state.ageInTicks) * 1.2F * 2; // TODO : adjust the animation (*2 params)
        this.leg2.xRot = Mth.cos(state.ageInTicks + (float)Math.PI) * 1.2F * 2; // TODO : adjust the animation (*2 params)
        this.leg3.xRot = this.leg1.xRot;
        this.leg4.xRot = this.leg2.xRot;
    }
}