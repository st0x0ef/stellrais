package com.st0x0ef.stellaris.common.items;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.blocks.entities.machines.OxygenDistributorBlockEntity;
import com.st0x0ef.stellaris.common.data_components.CappedLongComponent;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class OxygenTankItem extends Item {
    public OxygenTankItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (stack.has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get())) {
            tooltip.add(Component.translatable("tooltip.item.stellaris.oxygen_tank", OxygenUtils.getOxygen(stack), OxygenUtils.getOxygenCapacity(stack)).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if(level.isClientSide) return super.use(level, player, usedHand);

        if (player.isShiftKeyDown()) {
            if (player.getItemBySlot(EquipmentSlot.CHEST).has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get())) {
                ItemStack armor = player.getItemBySlot(EquipmentSlot.CHEST);
                Stellaris.LOG.info("ee");

                ItemStack tank = player.getItemInHand(usedHand);
                CappedLongComponent oxygenComponent = tank.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get());

                if (oxygenComponent.amount() == 0) {
                    return super.use(level, player, usedHand);
                }

                if (OxygenUtils.getOxygenCapacity(armor) - OxygenUtils.getOxygen(armor) > oxygenComponent.amount()) {
                    OxygenUtils.addOxygen(armor, oxygenComponent.amount());
                    OxygenUtils.setOxygen(tank, 0);
                } else if (OxygenUtils.getOxygenCapacity(armor) - OxygenUtils.getOxygen(armor) <= oxygenComponent.amount()) {
                    OxygenUtils.addOxygen(armor, OxygenUtils.getOxygenCapacity(armor) - OxygenUtils.getOxygen(armor));
                    OxygenUtils.addOxygen(tank, -(OxygenUtils.getOxygenCapacity(armor) + OxygenUtils.getOxygen(armor)));
                }

            }
        }

        return super.use(level, player, usedHand);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity block = context.getLevel().getBlockEntity(context.getClickedPos());
        if (block instanceof OxygenDistributorBlockEntity entity) {
            ItemStack stack = context.getItemInHand();
            if (stack.has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get())) {
                entity.addOyxgen(OxygenUtils.getOxygen(stack));
                OxygenUtils.setOxygen(stack, 0);
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        long storedOxygen = OxygenUtils.getOxygen(stack);
        return (int) Mth.clamp((13 + storedOxygen * 13) / OxygenUtils.getOxygenCapacity(stack), 0, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xA7E6ED;
    }
}
