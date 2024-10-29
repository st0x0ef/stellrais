package com.st0x0ef.stellaris.client.renderers.entities.vehicle.rover;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class RoverItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/vehicle/rover.png");
    private RoverModel<?> model;

    public RoverItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    public RoverItemRenderer get() {
        return this;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack matrixStackIn, MultiBufferSource buffer, int combinedLight, int packedOverlay) {
        matrixStackIn.pushPose();

        matrixStackIn.translate(0.5D, 1.5D, 0.5D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);

        Minecraft mc = Minecraft.getInstance();

        if (this.model == null) {
            this.model = new RoverModel<>(mc.getEntityModels().bakeLayer(RoverModel.LAYER_LOCATION));
        }

        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(TEXTURE));

        this.model.renderToBuffer(matrixStackIn, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, -1);

        matrixStackIn.popPose();
    }
}