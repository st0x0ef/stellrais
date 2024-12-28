package com.st0x0ef.stellaris.common.items;

import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CoffeeItem extends Item {
    private static final int DRINK_DURATION = 40;

    public CoffeeItem(Properties properties) {
        super(properties);
    }

    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(stack, level, livingEntity);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty()) {
            return new ItemStack(ItemsRegistry.COFFEE_CUP);
        } else {
            if (livingEntity instanceof Player player) {
                if (!player.hasInfiniteMaterials()) {
                    ItemStack itemStack = new ItemStack(ItemsRegistry.COFFEE_CUP);
                    if (!player.getInventory().add(itemStack)) {
                        player.drop(itemStack, false);
                    }
                }
            }
            return stack;
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    public Holder.Reference<SoundEvent> getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public Holder.Reference<SoundEvent> getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public int getDefaultMaxStackSize() {
        return 64;
    }
}
