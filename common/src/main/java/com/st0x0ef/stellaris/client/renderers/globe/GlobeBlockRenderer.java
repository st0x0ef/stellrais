package com.st0x0ef.stellaris.client.renderers.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.st0x0ef.stellaris.common.blocks.GlobeBlock;
import com.st0x0ef.stellaris.common.blocks.entities.GlobeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class GlobeBlockRenderer<T extends GlobeBlockEntity> implements BlockEntityRenderer<GlobeBlockEntity> {
    private GlobeModel<?> model;

    public GlobeBlockRenderer(BlockEntityRendererProvider.Context Context) {}

    @Override
    public void render(GlobeBlockEntity tileEntity, float particleTicks, PoseStack matrixStackIn, MultiBufferSource buffer, int combinedLight, int overlay) {
        BlockState state = tileEntity.getLevel().getBlockState(tileEntity.getBlockPos());

        if (!(state.getBlock() instanceof GlobeBlock)) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        BlockState blockstate = tileEntity.getBlockState();
        Direction direction = blockstate.getValue(GlobeBlock.FACING);

        matrixStackIn.pushPose();

        matrixStackIn.translate(0.5D, 1.5D, 0.5D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));

        if (this.model == null) {
            this.model = new GlobeModel<>(mc.getEntityModels().bakeLayer(GlobeModel.LAYER_LOCATION));
        }


        /** Animation */
        this.model.setupAnim(tileEntity, particleTicks);

        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(((GlobeBlock) state.getBlock()).texture));

        this.model.renderToBuffer(matrixStackIn, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, -1);
        mc.renderBuffers().bufferSource().endBatch();

        matrixStackIn.popPose();
    }
}