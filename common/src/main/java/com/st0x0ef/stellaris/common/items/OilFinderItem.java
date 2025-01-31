package com.st0x0ef.stellaris.common.items;

import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class OilFinderItem extends Item {
    public OilFinderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide()) {
            return InteractionResultHolder.fail(player.getItemInHand(usedHand));
        }

        int oilLevel = level.getChunk(player.getOnPos()).stellaris$getChunkOilLevel();

        MutableComponent component = Component.literal("Found Oil " + level.getChunk(player.getOnPos()).stellaris$getChunkOilLevel() + "mb");
        if (oilLevel > 40000) {
            component.withColor(Utils.getColorHexCode("green"));
        } else if (oilLevel > 0) {
            component.withColor(Utils.getColorHexCode("orange"));
        } else {
            component = Component.literal("No oil found");
            component.withColor(Utils.getColorHexCode("red"));
        }

        player.getItemInHand(usedHand).hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
        player.displayClientMessage(component, true);

        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.item.stellaris.oil_finder").withStyle(ChatFormatting.GRAY));
    }
}