package com.st0x0ef.stellaris.platform.fabric;

import com.st0x0ef.stellaris.platform.ClientUtilsPlatform;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.Item;

public class ClientUtilsPlatformImpl {

    public static void registerArmor(ModelLayerLocation layer, ClientUtilsPlatform.ArmorFactory<HumanoidRenderState> factory, Item... items) {
        ArmorRenderer.register((poseStack, buffer, stack, entity, slot, packedLight, original) -> {
            ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(layer);
            HumanoidModel<?> model = factory.create(root, slot, stack, original);
            /*if (stack.getItem() instanceof ArmorItem armorItem) {
                ArmorMaterial armorMaterial = armorItem.getMaterial().value();
                int i = stack.is(ItemTags.DYEABLE) ? ARGB.opaque(DyedItemColor.getOrDefault(stack, ARGB.color(250, 250, 250))) : -1;
                for (EquipmentModel layer1 : armorMaterial.layers()) {
                    int j = layer1.dyeable() ? i : -1;
                    model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(layer1.texture(false))),
                            packedLight, OverlayTexture.NO_OVERLAY, j);
                }
            }*/ // TODO : rewrite this
        }, items);
    }

}
