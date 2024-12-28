package com.st0x0ef.stellaris.common.utils;

import com.st0x0ef.stellaris.common.data_components.OxygenComponent;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import net.minecraft.world.item.ItemStack;

public class OxygenUtils {
    public static boolean addOxygen(ItemStack stack, long amount) {
        if (stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).oxygen() + amount > stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).capacity()) return false;
        setOxygen(stack, stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).oxygen() + amount);
        return true;
    }

    public static boolean removeOxygen(ItemStack stack, long amount) {
        if (stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).oxygen() < amount) return false;
        setOxygen(stack, stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).oxygen() - amount);
        return true;
    }

    public static void setOxygen(ItemStack stack, long amount) {
        stack.set(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(), new OxygenComponent(amount, stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).capacity()));
    }

    public static long getOxygen(ItemStack stack) {
        if (stack.has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get())) {
            return stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).oxygen();
        }

        return 0L;
    }

    public static long getOxygenCapacity(ItemStack stack) {
        if (stack.has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get())) {
            return stack.get(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()).capacity();
        }

        return 0L;
    }
}
