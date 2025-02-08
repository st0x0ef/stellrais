package com.st0x0ef.stellaris.common.items;

import com.st0x0ef.stellaris.common.blocks.entities.machines.OxygenDistributorBlockEntity;
import com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FilteredFluidStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class OxygenTankItem extends Item implements IOxygenStorageItem  {
    public OxygenTankItem(Properties properties) {
        super(properties);
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.item.stellaris.oxygen_tank", oxygenTank.getFluidValueInTank(0), oxygenTank.getTankCapacity(0)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(level.isClientSide) return super.use(level, player, usedHand);

        if (player.isShiftKeyDown()) {
            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AbstractSpaceArmor.Chestplate chestplate) {

                ItemStack tank = player.getItemInHand(usedHand);

                if (oxygenTank.getFluidValueInTank(0) == 0) {
                    return super.use(level, player, usedHand);
                }

                long spaceLeft = chestplate.oxygenTank.getTankCapacity(0) - chestplate.oxygenTank.getFluidValueInTank(0);
                if (spaceLeft > oxygenTank.getFluidValueInTank(0)) {
                    chestplate.oxygenTank.fill(oxygenTank.getFluidInTank(0).copy(), false);
                    chestplate.oxygenTank.drain(oxygenTank.getFluidInTank(0).copy(), false);
                } else if (spaceLeft <= oxygenTank.getFluidValueInTank(0)) {
                    chestplate.oxygenTank.fill(oxygenTank.getFluidInTank(0).copyWithAmount(spaceLeft), false);
                    chestplate.oxygenTank.drain(oxygenTank.getFluidInTank(0).copyWithAmount(spaceLeft), false);
                }

            }
        }

        return super.use(level, player, usedHand);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity block = context.getLevel().getBlockEntity(context.getClickedPos());
        if (block instanceof OxygenDistributorBlockEntity entity) {
            if (oxygenTank.getFluidValueInTank(0) > 0) {
                entity.addOxygen(oxygenTank.getFluidValueInTank(0));
                oxygenTank.drain(oxygenTank.getFluidInTank(0).copy(), false);
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
        long storedOxygen =  oxygenTank.getFluidValueInTank(0);
        return (int) Mth.clamp((13 + storedOxygen * 13) / oxygenTank.getTankCapacity(0), 0, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xA7E6ED;
    }
}
