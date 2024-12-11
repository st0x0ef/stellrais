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
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class JetSuitModel extends HumanoidModel<LivingEntity> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "jetsuit"), "main");
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/models/armor/jetsuit_layer_1.png");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart left_boot;
	private final ModelPart right_boot;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
    private final HumanoidModel<LivingEntity> parentModel;
	private final EquipmentSlot slot;

	public JetSuitModel(ModelPart root, EquipmentSlot slot, ItemStack stack, @Nullable HumanoidModel<LivingEntity> parentModel) {
        super(root, RenderType::entityTranslucent);
        this.parentModel = parentModel;

        this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.right_arm = root.getChild("right_arm");
		this.left_arm = root.getChild("left_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.left_boot = left_leg.getChild("left_boot");
		this.right_boot = right_leg.getChild("right_boot");

		this.slot = slot;
		this.setVisible();

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

		PartDefinition visor = partdefinition.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
		.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F))
		.texOffs(14, 59).addBox(3.0F, -13.0F, 1.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0175F, 0.0873F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(0, 55).addBox(-2.5F, 1.0F, 2.75F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(32, 31).addBox(-2.0F, -5.0F, 0.75F, 0.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 2.0F, 0.0F, -0.3491F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("Body_r2", CubeListBuilder.create().texOffs(32, 31).addBox(2.0F, -5.0F, 0.75F, 0.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 2.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition armr = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
		.texOffs(48, 8).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition arml = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
		.texOffs(48, 0).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(5.0F, 2.0F, 0.0F));


		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(33, 19).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(33, 19).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offset(2.1F, 12.0F, 0.0F));

		PartDefinition Right_boot = RightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.27F)), PartPose.offset(-0.1F, 0.0F, 0.0F));
		PartDefinition Left_boot = LeftLeg.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.27F)), PartPose.offset(-0.1F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
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
				this.rightArm.visible = true;
				this.leftArm.visible = true;
			}
			case LEGS -> {
				this.rightLeg.visible = true;
				this.leftLeg.visible = true;
			}

		}
	}
}