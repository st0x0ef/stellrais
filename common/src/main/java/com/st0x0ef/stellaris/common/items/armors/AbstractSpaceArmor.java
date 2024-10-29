package com.st0x0ef.stellaris.common.items.armors;

import com.st0x0ef.stellaris.common.items.CustomArmorItem;
import com.st0x0ef.stellaris.common.utils.FuelUtils;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class AbstractSpaceArmor extends CustomArmorItem {
    public AbstractSpaceArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public static class AbstractSpaceChestplate extends AbstractSpaceArmor {
        public AbstractSpaceChestplate(Holder<ArmorMaterial> material, Type type, Properties properties) {
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
        public Chestplate(Holder<ArmorMaterial> material, Type type, Properties properties) {
            super(material, type, properties);
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

            tooltipComponents.add(Component.translatable("jetsuit.stellaris.fuel", getFuel(stack)));
        }

        public static void addFuel(ItemStack stack, int amount) {
            FuelUtils.addFuel(stack, amount);
        }

        public static long getFuel(ItemStack stack) {
            return FuelUtils.getFuel(stack);
        }
    }
}
