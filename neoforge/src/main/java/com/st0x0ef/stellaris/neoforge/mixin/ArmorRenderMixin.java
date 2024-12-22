package com.st0x0ef.stellaris.neoforge.mixin;

import com.st0x0ef.stellaris.common.items.CustomArmorItem;
import com.st0x0ef.stellaris.platform.neoforge.ClientUtilsPlatformImpl;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.Item;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
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

            @SuppressWarnings("unchecked")
            private static <T extends LivingEntity> void uncheckedCopyTo(HumanoidModel<T> from, HumanoidModel<?> to) {
                from.copyPropertiesTo((HumanoidModel<T>) to);
            }

            @SuppressWarnings("unchecked")
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
                if (renderer == null) {
                    renderer = ClientUtilsPlatformImpl.ARMOR_RENDERERS.get(stack.getItem());
                }
                if (renderer == null) return original;

                ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(renderer.layer());

                return renderer.factory().create(root, slot, stack, original);
            }

            @Override
            public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel<?> replacement = getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
                if (replacement != original) {
                    uncheckedCopyTo(original, replacement);
                    return replacement;
                } else {
                    return original;
                }
            }
        });
    }


}
