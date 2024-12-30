package com.st0x0ef.stellaris.neoforge.mixin;

import com.st0x0ef.stellaris.common.items.CustomArmorItem;
import com.st0x0ef.stellaris.platform.neoforge.ClientUtilsPlatformImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(CustomArmorItem.class)
public abstract class ArmorRenderMixin extends Item {

    public ArmorRenderMixin(Properties properties) {
        super(properties);
    }

    @Unique
    public void stellaris$initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ClientUtilsPlatformImpl.ArmorRenderer renderer;

            private static <T extends HumanoidRenderState> void uncheckedCopyTo(HumanoidModel<T> from, HumanoidModel<?> to) {
                from.copyPropertiesTo((HumanoidModel<T>) to);
            }

            @Override
            public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentModel.LayerType layerType, Model original) {
                if (renderer == null) {
                    renderer = ClientUtilsPlatformImpl.ARMOR_RENDERERS.get(itemStack.getItem());
                }
                if (renderer == null) return original;

                ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(renderer.layer());

                return renderer.factory().create(root, itemStack.getEquipmentSlot(), itemStack, original);
            }

            @Override
            public Model getGenericArmorModel(ItemStack itemStack, EquipmentModel.LayerType layerType, Model original) {
                HumanoidModel<?> replacement = (HumanoidModel<?>) getHumanoidArmorModel(itemStack, layerType, original);
                if (replacement != original) {
                    uncheckedCopyTo((HumanoidModel<? extends HumanoidRenderState>) original, replacement);
                    return replacement;
                } else {
                    return original;
                }
            }
        });
    }


}
