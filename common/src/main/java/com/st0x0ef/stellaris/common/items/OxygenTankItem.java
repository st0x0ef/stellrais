package com.st0x0ef.stellaris.common.items;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.fej1fun.potentials.fluid.ItemFluidStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.blocks.entities.machines.OxygenDistributorBlockEntity;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
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
        UniversalFluidStorage storage = Capabilities.Fluid.ITEM.getCapability(stack);
        if (storage != null) {
            tooltip.add(Component.translatable("tooltip.item.stellaris.oxygen_tank", storage.getFluidInTank(0).getAmount(), storage.getTankCapacity(0)).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(level.isClientSide) return super.use(level, player, usedHand);

        if (player.isShiftKeyDown()) {
            ItemStack tank = player.getItemInHand(usedHand);
            ItemStack armor = player.getItemBySlot(EquipmentSlot.CHEST);

            UniversalFluidStorage oxygenTankStorage = Capabilities.Fluid.ITEM.getCapability(tank);
            UniversalFluidStorage chestplateStorage = Capabilities.Fluid.ITEM.getCapability(armor);

            if (oxygenTankStorage == null || chestplateStorage == null) return super.use(level, player, usedHand);


            if (oxygenTankStorage.getFluidInTank(0).isEmpty()) {
                return super.use(level, player, usedHand);
            }

            if (chestplateStorage.getTankCapacity(0) - chestplateStorage.getFluidInTank(0).getAmount() >= oxygenTankStorage.getFluidInTank(0).getAmount()) {
                chestplateStorage.fill(oxygenTankStorage.getFluidInTank(0).copy(), false);
                oxygenTankStorage.drain(oxygenTankStorage.getFluidInTank(0).copy(), false);
            } else {
                long amount = chestplateStorage.getTankCapacity(0) - chestplateStorage.getFluidInTank(0).getAmount();
                chestplateStorage.fill(oxygenTankStorage.getFluidInTank(0).copyWithAmount(amount), false);
                oxygenTankStorage.drain(oxygenTankStorage.getFluidInTank(0).copyWithAmount(amount), false);
            }
        }

        return super.use(level, player, usedHand);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity block = context.getLevel().getBlockEntity(context.getClickedPos());
        if (block instanceof OxygenDistributorBlockEntity entity) {
            ItemStack stack = context.getItemInHand();
            UniversalFluidStorage storage = Capabilities.Fluid.ITEM.getCapability(stack);
            if (storage != null) {
                long amount = entity.addOxygen(storage.getFluidInTank(0).getAmount());
                storage.drain(storage.getFluidInTank(0).copyWithAmount(amount), false);
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
        UniversalFluidStorage storage = Capabilities.Fluid.ITEM.getCapability(stack);
        if (storage != null) {
            return (int) Mth.clamp(((storage.getFluidInTank(0).getAmount() + 1) * 13) / storage.getTankCapacity(0), 0, 13);
        }
        return 0;
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
