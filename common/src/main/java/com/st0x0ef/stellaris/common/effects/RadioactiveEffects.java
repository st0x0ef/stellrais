package com.st0x0ef.stellaris.common.effects;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.flag.FeatureElement;
import org.jetbrains.annotations.NotNull;

public interface RadioactiveEffects extends FeatureElement {


    boolean applyEffectTick(LivingEntity livingEntity, int amplifier);

    boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier);

    @NotNull MobEffect withSoundOnAdded(SoundEvent event);

    void onEffectAdded(LivingEntity entity, int amplifier);
}
