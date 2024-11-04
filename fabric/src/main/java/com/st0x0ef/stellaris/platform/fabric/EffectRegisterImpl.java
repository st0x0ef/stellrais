package com.st0x0ef.stellaris.platform.fabric;

import com.st0x0ef.stellaris.Stellaris;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class EffectRegisterImpl {
    public static Holder<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, name), effect.get());
    }
}
