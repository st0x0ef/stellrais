package com.st0x0ef.stellaris.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.st0x0ef.stellaris.client.renderers.armors.JetSuitModel;
import com.st0x0ef.stellaris.client.renderers.armors.SpaceSuitModel;
import com.st0x0ef.stellaris.common.entities.vehicles.LanderEntity;
import com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
import com.st0x0ef.stellaris.common.items.armors.SpaceSuit;
import com.st0x0ef.stellaris.common.utils.Utils;
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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class RenderPlayerMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    @Shadow
    protected abstract void setModelProperties(AbstractClientPlayer clientPlayer);

    public RenderPlayerMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void renderPlayerHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, ModelPart renderedArm, ModelPart rendererArmwear, CallbackInfo ci) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);

        if(stack.getItem() instanceof JetSuit.Suit || (stack.getItem() instanceof AbstractSpaceArmor)) {
            ci.cancel();

            PlayerModel<AbstractClientPlayer> playerModel = getModel();
            setModelProperties(player);
            playerModel.attackTime = 0.0F;
            playerModel.crouching = false;
            playerModel.swimAmount = 0.0F;
            playerModel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            renderedArm.xRot = 0.0F;


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
            boolean isRightHand = (renderedArm == model.rightArm);

            if (isRightHand) {
                model.rightArm.copyFrom(renderedArm);
                model.rightArm.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY);
            } else {
                model.leftArm.copyFrom(renderedArm);
                model.leftArm.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void renderPlayer(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (entity.getVehicle() instanceof LanderEntity) {
            ci.cancel();
        }
        if (Utils.isLivingInSpaceSuit(entity)) {
            if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuitItem) {
                ItemStack spaceSuitStack = entity.getItemBySlot(EquipmentSlot.CHEST);
                spaceSuitItem.getModules(spaceSuitStack).forEach(module -> {
                    module.renderModel(poseStack, buffer, entity, entityYaw, partialTicks, packedLight);
                });
            }
        }
    }

}