package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.common.effects.RadioactiveEffect;
import com.st0x0ef.stellaris.common.effects.SandStormEffect;
import com.st0x0ef.stellaris.platform.EffectRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class EffectsRegistry {
    public static Holder<MobEffect> RADIOACTIVE;
    public static Holder<MobEffect> SANDSTORM;

    public static void register() {
        RADIOACTIVE = EffectRegister.registerEffect("radioactive", () -> new RadioactiveEffect(MobEffectCategory.HARMFUL, 8889187));
        SANDSTORM = EffectRegister.registerEffect("sandstorm", () -> new SandStormEffect(MobEffectCategory.HARMFUL, 8889187));
    }
}