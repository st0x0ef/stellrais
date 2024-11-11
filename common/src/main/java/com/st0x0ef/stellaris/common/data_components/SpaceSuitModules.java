package com.st0x0ef.stellaris.common.data_components;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.st0x0ef.stellaris.common.items.module.SpaceSuitModule;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record SpaceSuitModules(List<ItemStack> modules) implements Serializable {

    public static SpaceSuitModules empty() {
        return new SpaceSuitModules(List.of());
    }

    public static final Codec<SpaceSuitModules> CODEC = ItemStack.CODEC.listOf().xmap(SpaceSuitModules::new, modules -> modules.modules);
    public static final StreamCodec<RegistryFriendlyByteBuf, SpaceSuitModules> STREAM_CODEC = ItemStack.STREAM_CODEC
            .apply(ByteBufCodecs.list())
            .map(SpaceSuitModules::new, modules -> modules.modules);


    public ItemStack getItemUnsafe(int index) {
        return (ItemStack)this.modules.get(index);
    }

    public Stream<ItemStack> itemCopyStream() {
        return this.modules.stream().map(ItemStack::copy);
    }

    public Iterable<ItemStack> items() {
        return this.modules;
    }

    public Iterable<ItemStack> itemsCopy() {
        return Lists.<ItemStack, ItemStack>transform(this.modules, ItemStack::copy);
    }

    public static ItemStack getIfContains(ItemStack stack, Item module) {
        ItemStack moduleToReturn = ItemStack.EMPTY;
        SpaceSuitModules spaceSuitModules = stack.getOrDefault(DataComponentsRegistry.SPACE_SUIT_MODULES.get(), empty());
        if (spaceSuitModules.items() == null) return moduleToReturn;
        for (ItemStack moduleStack: spaceSuitModules.items()) {
            if (moduleStack.is(module)) {
                moduleToReturn=moduleStack;
                break;
            }
        }
        return moduleToReturn;
    }

    public static boolean containsAllInModules(ItemStack stack, List<Item> modules) {
        boolean containsAll = true;
        for (Item item : modules) {
            if (item instanceof SpaceSuitModule validModule) {
                if(!containsInModules(stack, validModule)) containsAll = false; break;
            }
        }
        return containsAll;
    }

    public static boolean containsInModules(ItemStack stack, ItemStack module) {
        return containsInModules(stack, getModuleStatic(module));
    }

    public static boolean containsInModules(ItemStack stack, SpaceSuitModule module) {
        if (stack.isEmpty()) return false;
        SpaceSuitModules spaceSuitModules = stack.get(DataComponentsRegistry.SPACE_SUIT_MODULES.get());
        if (spaceSuitModules==null) return false;
        boolean boolToReturn = false;
        for (SpaceSuitModule module1: spaceSuitModules.getModules()) {
            if (module1==module) {
                boolToReturn = true;
                break;
            }
        }

        return boolToReturn;
    }

    public List<SpaceSuitModule> getModules() {
        return Lists.<ItemStack, SpaceSuitModule>transform(this.modules, this::getModule);
    }

    private SpaceSuitModule getModule(ItemStack itemStack) {
        return getModuleStatic(itemStack);
    }

    private static SpaceSuitModule getModuleStatic(ItemStack itemStack) {
        if (itemStack.getItem() instanceof SpaceSuitModule spaceSuitModule) return spaceSuitModule;
        return null; //failsafe, shouldn't happen unless tampered with or incorrect checks for upgrade station
    }

    public static class Mutable {
        private final List<ItemStack> modules;

        public Mutable(SpaceSuitModules contents) {
            this.modules = new ArrayList<>(contents.modules);
        }

        public Mutable insert(ItemStack stack) {
            if (!stack.isEmpty() && stack.getItem().canFitInsideContainerItems()) {
                    this.modules.add(stack);
            }
            return this;
        }

        public SpaceSuitModules toImmutable() {
            return new SpaceSuitModules(List.copyOf(this.modules));
        }
    }
}
