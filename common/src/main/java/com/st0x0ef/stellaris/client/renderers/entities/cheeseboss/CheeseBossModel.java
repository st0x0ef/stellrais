package com.st0x0ef.stellaris.client.renderers.entities.cheeseboss;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class CheeseBossModel extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "cheese_boss"), "main");
	private final ModelPart CheeseBoss;
	private final ModelPart Head;

	public CheeseBossModel(ModelPart root) {
        super(root);
        this.CheeseBoss = root.getChild("CheeseBoss");
		this.Head = CheeseBoss.getChild("Body").getChild("Head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition CheeseBoss = partdefinition.addOrReplaceChild("CheeseBoss", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Body = CheeseBoss.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -23.0F, -4.0F, 18.0F, 23.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.0F, 0.0F));

		PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(70, 63).addBox(-4.0F, -38.0F, -4.0F, 13.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 27.0F, -17.0F, -0.288F, -0.103F, -0.3341F));

		PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(112, 51).addBox(-15.0F, -38.0F, -6.0F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.0F, 27.0F, -17.0F, -0.2451F, 0.1841F, 0.6318F));

		PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(28, 79).mirror().addBox(-5.75F, -5.0F, -5.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -9.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(36, 63).addBox(-5.0F, -9.0F, -4.0F, 10.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 0.0F));

		PartDefinition cube_r4 = Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 51).mirror().addBox(0.0F, -45.0F, -6.0F, 0.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.0F, 26.0F, 5.0F, 0.1309F, 0.0F, 0.3491F));

		PartDefinition cube_r5 = Head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 51).addBox(0.0F, -45.0F, -6.0F, 0.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 26.0F, 5.0F, 0.1309F, 0.0F, -0.3491F));

		PartDefinition cube_r6 = Head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(54, 79).addBox(-3.0F, -32.0F, -1.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(2.0F, 19.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r7 = Head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(54, 79).mirror().addBox(0.0F, -32.0F, -1.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition ShoulderL = Body.addOrReplaceChild("ShoulderL", CubeListBuilder.create(), PartPose.offset(9.0F, -19.0F, 3.0F));

		PartDefinition cube_r8 = ShoulderL.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(100, 34).mirror().addBox(-1.0F, -44.0F, -12.0F, 0.0F, 17.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 26.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition cube_r9 = ShoulderL.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(50, 0).mirror().addBox(-6.0F, -6.0F, -5.0F, 15.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, -3.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition ArmL = ShoulderL.addOrReplaceChild("ArmL", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-4.0F, -3.0F, -4.0F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.0F, 3.0F, -3.0F));

		PartDefinition LowerArmL = ArmL.addOrReplaceChild("LowerArmL", CubeListBuilder.create().texOffs(0, 79).mirror().addBox(-3.0F, 7.0F, -4.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(0, 43).mirror().addBox(-3.0F, 0.0F, -4.0F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition cube_r10 = LowerArmL.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(64, 34).mirror().addBox(-4.5F, -4.0F, -5.5F, 8.0F, 8.0F, 10.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition ShoulderR = Body.addOrReplaceChild("ShoulderR", CubeListBuilder.create(), PartPose.offset(-9.0F, -19.0F, 0.0F));

		PartDefinition cube_r11 = ShoulderR.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(50, 0).addBox(-9.0F, -6.0F, -5.0F, 15.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition cube_r12 = ShoulderR.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(100, 34).addBox(1.0F, -44.0F, -12.0F, 0.0F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 26.0F, 3.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition ArmR = ShoulderR.addOrReplaceChild("ArmR", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -3.0F, -4.0F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 3.0F, 0.0F));

		PartDefinition LowerArmR = ArmR.addOrReplaceChild("LowerArmR", CubeListBuilder.create().texOffs(0, 79).addBox(-4.0F, 7.0F, -4.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.5F))
		.texOffs(0, 43).addBox(-4.0F, 0.0F, -4.0F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition cube_r13 = LowerArmR.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(64, 34).addBox(-3.5F, -4.0F, -5.5F, 8.0F, 8.0F, 10.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition LegL = CheeseBoss.addOrReplaceChild("LegL", CubeListBuilder.create().texOffs(98, 0).mirror().addBox(-7.9118F, 0.9583F, -4.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, -29.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition cube_r14 = LegL.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(28, 34).mirror().addBox(-4.5F, -4.0F, -5.5F, 8.0F, 8.0F, 10.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 11.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r15 = LegL.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(110, 63).addBox(-6.0F, 2.0F, -5.0F, 8.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition LowerLegL = LegL.addOrReplaceChild("LowerLegL", CubeListBuilder.create(), PartPose.offset(-4.5F, 12.5F, 0.0F));

		PartDefinition cube_r16 = LowerLegL.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(98, 10).mirror().addBox(1.5F, -17.0F, -4.0F, 7.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 63).mirror().addBox(0.5F, -9.0F, -5.0F, 9.0F, 6.0F, 9.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(-5.5F, 15.5F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition LegR = CheeseBoss.addOrReplaceChild("LegR", CubeListBuilder.create().texOffs(98, 0).mirror().addBox(1.0F, 1.0F, -4.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, -29.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition cube_r17 = LegR.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(110, 63).mirror().addBox(-2.0F, 2.0F, -5.0F, 8.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition cube_r18 = LegR.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(28, 34).addBox(-3.5F, -4.0F, -5.5F, 8.0F, 8.0F, 10.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(4.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition LowerLegR = LegR.addOrReplaceChild("LowerLegR", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, 12.5F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition cube_r19 = LowerLegR.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 63).addBox(-9.5F, -9.0F, -5.0F, 9.0F, 6.0F, 9.0F, new CubeDeformation(-0.5F))
		.texOffs(98, 10).addBox(-8.5F, -17.0F, -4.0F, 7.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 15.4564F, 0.001F, 0.0F, 0.0F, -0.0436F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(state.yRot, state.xRot);

		//this.animateWalk(CheeseBossAnim.walking, state.limbSwing, limbSwingAmount,4.1f,2.5f);
		//this.animate(entity.idleAnimationState, CheeseBossAnim.idle, state.ageInTicks, 1f);
		//this.animate(entity.punchAnimationState, CheeseBossAnim.punching, state.ageInTicks, 1f);
		//this.animate(entity.spitAnimationState, CheeseBossAnim.cheese_shooting, state.ageInTicks, 1f);

	}

	private void applyHeadRotation(float netHeadYaw, float headPitch) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		headPitch = Mth.clamp(headPitch, -25.0F, 45.0F);
		this.Head.yRot = (netHeadYaw * 0.017453292F);
		this.Head.xRot = (headPitch * 0.017453292F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		CheeseBoss.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}