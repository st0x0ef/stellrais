package com.st0x0ef.stellaris.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CanItem extends Item {

    private final int maxNutrition;

    public CanItem(Properties properties, int maxNutrition) {
        super(properties);
        this.maxNutrition = maxNutrition;
    }

    public static void setFoodProperties(ItemStack stack, FoodProperties foodProperties) {
        stack.set(DataComponents.FOOD, foodProperties);
    }

    public static FoodProperties getFoodProperties(ItemStack stack) {
        return stack.get(DataComponents.FOOD);
    }

    public static int getNutrition(ItemStack stack) {
        FoodProperties properties = getFoodProperties(stack);
        return properties != null ? properties.nutrition() : 0;
    }

    public static float getSaturation(ItemStack stack) {
        FoodProperties properties = getFoodProperties(stack);
        return properties != null ? properties.saturation() : 0;
    }

    public static boolean addFoodToCan(ItemStack canStack, ItemStack foodStack) {
        int canNutrition = getNutrition(canStack) + getNutrition(foodStack);
        if (canNutrition <= ((CanItem) canStack.getItem()).getMaxNutrition()) {
            setFoodProperties(canStack, new FoodProperties(canNutrition, Math.round((getSaturation(canStack) + getSaturation(foodStack)) * 10F) / 10F, false));


            return true;
        }
        return false;
    }

    public int getMaxNutrition() {
        return maxNutrition;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        FoodProperties properties = getFoodProperties(stack);

        if (properties == null || (properties.nutrition() <= 0 && properties.saturation() <= 0)) {
            tooltip.add(Component.translatable("tooltip.item.stellaris.can.empty").withStyle(ChatFormatting.GRAY));
            return;
        }

        if (properties.nutrition() > 0) {
            tooltip.add(Component.translatable("tooltip.item.stellaris.can.nutrition", properties.nutrition(), getMaxNutrition()).withStyle(ChatFormatting.GRAY));
        }

        if (properties.saturation() > 0) {
            tooltip.add(Component.translatable("tooltip.item.stellaris.can.saturation", properties.saturation()).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);
        if (entity instanceof Player player && !player.hasInfiniteMaterials()) {
            ItemStack emptyCanStack = new ItemStack(stack.getItem());
            if (stack.isEmpty()) {
                return emptyCanStack;
            }

            if (!player.getInventory().add(emptyCanStack)) {
                player.drop(emptyCanStack, false);
            }
        }
        return stack;
    }
}
