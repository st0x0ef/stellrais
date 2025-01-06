package com.st0x0ef.stellaris.client.renderers.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.items.GlobeItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class GlobeItemRenderer implements SpecialModelRenderer<ItemDisplayContext> {

    private ResourceLocation texture;
    private GlobeModel<?> model;

    public GlobeItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {

    }

    
    public GlobeItemRenderer setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    @Override
    public void render(@Nullable ItemDisplayContext patterns, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
        if (patterns instanceof GlobeItem globeItem) {
            this.texture = globeItem.getTexture();
        } else {
            this.texture = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/block/globes/earth_globe.png");
        }
        poseStack.pushPose();

        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;

        if (this.model == null) {
            this.model = new GlobeModel<>(mc.getEntityModels().bakeLayer(GlobeModel.LAYER_LOCATION));
        }

        VertexConsumer vertexBuilder = bufferSource.getBuffer(RenderType.entityCutoutNoCullZOffset(texture));

        /** Animation */
        if (level != null) {
            if (!mc.isPaused() && mc.getFps()>0) {
                model.globe.getChild("planet").yRot = (float) (level.getGameTime() + (1 / mc.getFps())) / -20;
            }
        }

        this.model.renderToBuffer(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, -1);

        poseStack.popPose();
    }

    @Override
    public @Nullable ItemDisplayContext extractArgument(ItemStack stack) {
        return null;
    }
}