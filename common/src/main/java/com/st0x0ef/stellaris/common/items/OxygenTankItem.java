package com.st0x0ef.stellaris.common.items;

import com.fej1fun.potentials.fluid.ItemFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.blocks.entities.machines.OxygenDistributorBlockEntity;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OxygenTankItem extends Item implements FluidProvider.ITEM {
    private final int capacity;
    public OxygenTankItem(Item.Properties properties, int capacity) {
        super(properties);
        this.capacity = capacity;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.item.stellaris.oxygen_tank", OxygenUtils.getOxygen(stack), OxygenUtils.getOxygenCapacity(stack)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(level.isClientSide) return super.use(level, player, usedHand);

        if (player.isShiftKeyDown()) {
            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof FluidProvider.ITEM chestplate && chestplate.getFluidTank(player.getItemBySlot(EquipmentSlot.CHEST)) instanceof ItemFluidStorage storage) {
                ItemStack armor = player.getItemBySlot(EquipmentSlot.CHEST);

                ItemStack tank = player.getItemInHand(usedHand);

                if (storage.getFluidValueInTank(0) == 0) {
                    return super.use(level, player, usedHand);
                }

                if (OxygenUtils.getOxygenCapacity(armor) - OxygenUtils.getOxygen(armor) > storage.getFluidValueInTank(0)) {
                    OxygenUtils.addOxygen(armor, storage.getFluidValueInTank(0));
                    OxygenUtils.removeAllOxyygen(tank);
                } else if (OxygenUtils.getOxygenCapacity(armor) - OxygenUtils.getOxygen(armor) <= storage.getFluidValueInTank(0)) {
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
            long amount = entity.addOxygen(OxygenUtils.getOxygen(stack));
            OxygenUtils.removeOxygen(stack, amount);
            return InteractionResult.SUCCESS;

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

    @Override
    public ItemFluidStorage getFluidTank(@NotNull ItemStack stack) {
        return new ItemFluidStorage(DataComponentsRegistry.FLUID_LIST.get(), stack, 1, capacity);
    }
}
