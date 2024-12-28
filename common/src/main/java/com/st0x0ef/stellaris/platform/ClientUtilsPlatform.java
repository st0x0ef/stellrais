package com.st0x0ef.stellaris.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ClientUtilsPlatform {
    public interface ArmorFactory<T extends HumanoidRenderState> {
        HumanoidModel<T> create(ModelPart root, EquipmentSlot slot, ItemStack stack, Model parentModel);
    }
    @ExpectPlatform
    public static void registerArmor(ModelLayerLocation layer, ArmorFactory<?> factory, Item... items) {
        throw new AssertionError();
    }
}
