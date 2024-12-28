package com.st0x0ef.stellaris.common.armors;

import com.st0x0ef.stellaris.common.items.CustomArmorItem;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import com.st0x0ef.stellaris.common.utils.FuelUtils;

import java.util.List;

public abstract class AbstractSpaceArmor extends CustomArmorItem {
    public AbstractSpaceArmor(ArmorMaterial material, ArmorType type, Properties properties) {
        super(material, type, properties);
    }

    public static class AbstractSpaceChestplate extends AbstractSpaceArmor {
        public AbstractSpaceChestplate(ArmorMaterial material, ArmorType type, Properties properties) {
            super(material, type, properties);
        }

        public void onArmorTick(ItemStack stack, ServerLevel level, Player player) {

        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

            tooltipComponents.add(Component.translatable("jetsuit.stellaris.oxygen", OxygenUtils.getOxygen(stack)));
        }
    }

    public static class Chestplate extends AbstractSpaceChestplate {
        public Chestplate(ArmorMaterial material, ArmorType type, Properties properties) {
            super(material, type, properties);
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

            tooltipComponents.add(Component.translatable("jetsuit.stellaris.fuel", FuelUtils.getFuel(stack)));
        }

        public static long getFuel(ItemStack stack) {
            return stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).amount();
        }
    }
}
