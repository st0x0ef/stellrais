package com.st0x0ef.stellaris.common.utils;

import com.fej1fun.potentials.fluid.ItemFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;

public class OxygenUtils {
    public static boolean addOxygen(ItemStack stack, long amount) {
        if (stack.getItem() instanceof FluidProvider.ITEM item && item.getFluidTank(stack) instanceof ItemFluidStorage storage) {
            if (storage.getFluidInTank(0).isEmpty()) {
                storage.fill(FluidStack.create(FluidRegistry.FLOWING_OXYGEN.get(), 0), false);
                return true;
            }
            if (storage.getFluidValueInTank(0) + amount > storage.getTankCapacity(0)) return false;

            storage.fill(storage.getFluidInTank(0).copyWithAmount(storage.getFluidValueInTank(0) + amount), false);
            return true;
        }
        return false;
    }

    public static boolean removeOxygen(ItemStack stack, long amount) {
        if (stack.getItem() instanceof FluidProvider.ITEM item && item.getFluidTank(stack) instanceof ItemFluidStorage storage) {
            if (storage.getFluidInTank(0).isEmpty()) {
                return false;
            }
            if (storage.getFluidValueInTank(0) - amount < 0) return false;

            storage.drain(storage.getFluidInTank(0).copyWithAmount(amount), false);
            return true;
        }
        return false;
    }

    public static void removeAllOxyygen(ItemStack stack) {
        if (stack.getItem() instanceof FluidProvider.ITEM item && item.getFluidTank(stack) instanceof ItemFluidStorage storage) {
            storage.drain(storage.getFluidInTank(0).copyWithAmount(0), false);
        }
    }

    public static long getOxygen(ItemStack stack) {
        if (stack.getItem() instanceof FluidProvider.ITEM item && item.getFluidTank(stack) instanceof ItemFluidStorage storage) {
            return storage.getFluidValueInTank(0);
        }
        return 0L;
    }

    public static long getOxygenCapacity(ItemStack stack) {
        if (stack.getItem() instanceof FluidProvider.ITEM item && item.getFluidTank(stack) instanceof ItemFluidStorage storage) {
            return storage.getTankCapacity(0);
        }

        return 0L;
    }
}
