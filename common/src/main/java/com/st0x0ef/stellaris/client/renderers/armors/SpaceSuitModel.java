package com.st0x0ef.stellaris.client.renderers.armors;

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
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SpaceSuitModel extends HumanoidModel<HumanoidRenderState> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "spacesuit"), "main");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/models/armor/spacesuit_white.png");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart waist;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
    private final HumanoidModel<HumanoidRenderState> parentModel;
	private final EquipmentSlot slot;
	private final ModelPart bb_main;

	public SpaceSuitModel(ModelPart root, EquipmentSlot slot, ItemStack stack, @Nullable HumanoidModel<HumanoidRenderState> parentModel) {
        super(root, RenderType::entityTranslucent);
        this.parentModel = parentModel;

		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.waist = root.getChild("waist");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.bb_main = root.getChild("bb_main");

		this.slot = slot;
		this.setVisible();

	}



	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Armure = partdefinition.addOrReplaceChild("Armure", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

		PartDefinition visor = partdefinition.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F))
				.texOffs(0, 0).addBox(4.7F, -5.8F, 1.2F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(44, 11).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.7F, -0.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2F, -5.8F, 4.2F, -0.7854F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
				.texOffs(24, 3).addBox(-2.7F, 9.0F, -3.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(0.8F, 5.5F, -3.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(20, 16).addBox(-2.7F, 5.5F, -3.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(14, 48).addBox(1.0F, 7.0F, 2.7F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(46, 46).addBox(-4.0F, 1.0F, 2.7F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 0).addBox(-4.0F, 6.0F, 2.7F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-3.0F, 2.0F, 2.7F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition waist = partdefinition.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(0, 16).addBox(-8.0F, 0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(46, 30).addBox(-1.5F, 3.0F, -3.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(2.1F, 12.0F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 58).addBox(-3.9F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
				.texOffs(0, 58).mirror().addBox(-0.1F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

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

	private void setVisible() {
		this.setAllVisible(false);
		switch (this.slot) {
			case HEAD -> this.head.visible = true;
			case CHEST -> {
				this.body.visible = true;
				this.leftArm.visible = true;
				this.rightArm.visible = true;
			}
			case LEGS -> {
				this.leftLeg.visible = true;
				this.rightLeg.visible = true;
			}
			case FEET -> this.waist.visible = true;

		}
	}

}