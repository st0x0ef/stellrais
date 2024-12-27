package com.st0x0ef.stellaris.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.st0x0ef.stellaris.client.renderers.armors.JetSuitModel;
import com.st0x0ef.stellaris.client.renderers.armors.SpaceSuitModel;
import com.st0x0ef.stellaris.common.armors.AbstractSpaceArmor;
import com.st0x0ef.stellaris.common.armors.JetSuit;
import com.st0x0ef.stellaris.common.entities.vehicles.LanderEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class RenderPlayerMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerRenderState, PlayerModel> {
    @Unique
    protected abstract void stellaris$setModelProperties(AbstractClientPlayer clientPlayer);

    public RenderPlayerMixin(EntityRendererProvider.Context context, PlayerModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void renderPlayerHand(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ResourceLocation skinTexture, ModelPart arm, boolean isSleeveVisible, CallbackInfo ci) {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if(stack.getItem() instanceof JetSuit.Suit || (stack.getItem() instanceof AbstractSpaceArmor)) {
            ci.cancel();

            PlayerModel playerModel = getModel();
            stellaris$setModelProperties(player);

            PlayerRenderState renderState = new PlayerRenderState();
            renderState.attackTime = 0.0F;
            renderState.isCrouching = false;
            renderState.swimAmount = 0.0F;

            playerModel.setupAnim(renderState);

            arm.xRot = 0.0F;

            ModelLayerLocation layer;
            ResourceLocation  texture = null;
            HumanoidModel<?> model = null;
            ModelPart rootPart;

            if(stack.getItem() instanceof JetSuit.Suit) {
                layer = JetSuitModel.LAYER_LOCATION;
                texture = JetSuitModel.TEXTURE;
                rootPart = Minecraft.getInstance().getEntityModels().bakeLayer(layer);
                model = new JetSuitModel(rootPart, EquipmentSlot.CHEST, stack, null);
            } else if (stack.getItem() instanceof AbstractSpaceArmor){
                layer = SpaceSuitModel.LAYER_LOCATION;
                rootPart = Minecraft.getInstance().getEntityModels().bakeLayer(layer);
                texture = SpaceSuitModel.TEXTURE;
                model = new SpaceSuitModel(rootPart, EquipmentSlot.CHEST, stack, null);

            }

            boolean isRightHand = arm == model.rightArm;

            if (isRightHand) {
                model.rightArm.copyFrom(arm);
                model.rightArm.render(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY);
            } else {
                model.leftArm.copyFrom(arm);
                model.leftArm.render(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}