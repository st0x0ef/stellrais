package com.st0x0ef.stellaris.common.items.module;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;

public class GravityNormalizerModule extends Item implements SpaceSuitModule {

    public GravityNormalizerModule(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public MutableComponent displayName() {
        return Component.translatable("spacesuit.stellaris.gravity_normalizer");
    }
}
