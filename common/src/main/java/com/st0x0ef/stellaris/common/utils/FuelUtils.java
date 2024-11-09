package com.st0x0ef.stellaris.common.utils;

import com.st0x0ef.stellaris.common.data_components.CappedLongComponent;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import net.minecraft.world.item.ItemStack;

public class FuelUtils {

    public static boolean addFuel(ItemStack stack, long amount) {
        if (stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).amount() + amount > stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).capacity()) return false;
        setFuel(stack, stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).amount() + amount);
        return true;
    }

    public static boolean removeFuel(ItemStack stack, long amount) {
        if (stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).amount() < amount) return false;
        setFuel(stack, stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).amount() - amount);
        return true;
    }

    public static void setFuel(ItemStack stack, long amount) {
        long newAmount = Math.clamp(amount, 0, getFuelCapacity(stack));
        stack.set(DataComponentsRegistry.STORED_FUEL_COMPONENT.get(), new CappedLongComponent(newAmount, stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).capacity()));
    }

    public static long getFuel(ItemStack stack) {
        if (stack.has(DataComponentsRegistry.STORED_FUEL_COMPONENT.get())) {
            return stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).amount();
        }

        return 0L;
    }

    public static long getFuelCapacity(ItemStack stack) {
        if (stack.has(DataComponentsRegistry.STORED_FUEL_COMPONENT.get())) {
            return stack.get(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()).capacity();
        }

        return 0L;
    }
}
