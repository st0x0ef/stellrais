package com.st0x0ef.stellaris.client.renderers.armors;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JetSuitModel extends HumanoidModel<LivingEntity> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "jetsuit"), "main");
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/models/armor/jetsuit4.png");
	private final HumanoidModel<LivingEntity> parentModel;

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart waist;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart left_shoe;
	private final ModelPart right_shoe;
	private final ModelPart antenna_r1;
	private final ModelPart lamp_r1;



	private final EquipmentSlot slot;


	public JetSuitModel(ModelPart root, EquipmentSlot slot, ItemStack stack, @Nullable HumanoidModel<LivingEntity> parentModel) {
		super(root, RenderType::entityTranslucent);
		this.parentModel = parentModel;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.waist = root.getChild("waist");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.left_shoe = left_leg.getChild("left_shoe");
		this.right_shoe = right_leg.getChild("right_shoe");
		this.antenna_r1 = head.getChild("antenna_r1");
		this.lamp_r1 = head.getChild("lamp_r1");


		this.slot = slot;
		this.setVisible();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Armure = partdefinition.addOrReplaceChild("Armure", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F))
				.texOffs(16, 34).addBox(4.7F, -5.8F, 1.2F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition lamp_r1 = head.addOrReplaceChild("lamp_r1", CubeListBuilder.create().texOffs(48, 48).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.7F, -0.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition antenna_r1 = head.addOrReplaceChild("antenna_r1", CubeListBuilder.create().texOffs(50, 16).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2F, -5.8F, 4.2F, -0.7854F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
				.texOffs(0, 16).addBox(-4.0F, 1.0F, 2.0F, 8.0F, 13.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(26, 32).addBox(-3.0F, 2.0F, 2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 34).addBox(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
				.texOffs(48, 32).addBox(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F))
				.texOffs(0, 50).addBox(9.0F, 11.1F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0001F)), PartPose.offset(-7.0F, -10.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false)
				.texOffs(48, 32).mirror().addBox(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false)
				.texOffs(0, 50).mirror().addBox(-13.0F, 11.1F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0001F)).mirror(false), PartPose.offset(3.0F, -10.0F, 0.0F));

		PartDefinition waist = partdefinition.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, 0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 45).mirror().addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(0, 50).addBox(2.0F, 12.8F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0001F)), PartPose.offset(-4.0F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 45).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(0, 50).mirror().addBox(-6.0F, 12.8F, -2.1F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0001F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_shoe = partdefinition.addOrReplaceChild("left_shoe", CubeListBuilder.create().texOffs(32, 45).mirror().addBox(1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 0.0F));

		PartDefinition right_shoe = partdefinition.addOrReplaceChild("right_shoe", CubeListBuilder.create().texOffs(32, 45).addBox(-5.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		if (Platform.isNeoForge()) {
			MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
			vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE));
		}

		parentModel.copyPropertiesTo(this);

		super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	protected  Iterable<ModelPart> headParts() {
		return ImmutableList.of(head);
	}

	@Override
	protected  Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(body, rightArm, leftArm, rightLeg, leftLeg, head, left_shoe, right_shoe);
	}

	private void setVisible() {
		this.setAllVisible(false);
		switch (this.slot) {
			case HEAD -> {
				this.head.visible = true;
				this.antenna_r1.visible = true;
				this.lamp_r1.visible = true;
			}
			case CHEST -> {
				this.body.visible = true;
				this.rightArm.visible = true;
				this.leftArm.visible = true;
			}
			case LEGS -> {
				this.rightLeg.visible = true;
				this.leftLeg.visible = true;
			}
			case FEET ->  {
				this.waist.visible = true;

			}

		}
	}

}