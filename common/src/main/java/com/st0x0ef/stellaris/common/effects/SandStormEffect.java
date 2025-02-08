package com.st0x0ef.stellaris.common.effects;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.registry.EffectsRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class SandStormEffect extends MobEffect {

    public static ResourceLocation SANDSTORM_OVERLAY =  ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/sandstorm_overlay.png");

    public SandStormEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public int getBlendDurationTicks() {
        return 22;
    }

    @Environment(EnvType.CLIENT)
    public static class SandStormFogFunction implements FogRenderer.MobEffectFogFunction {

        public SandStormFogFunction() {
        }

        public Holder<MobEffect> getMobEffect() {
            return EffectsRegistry.SANDSTORM;
        }

        @Override
        public float getModifiedVoidDarkness(LivingEntity entity, MobEffectInstance effectInstance, float f, float partialTick) {
            return f;
        }

        public void setupFog(FogRenderer.FogData fogData, LivingEntity entity, MobEffectInstance effectInstance, float farPlaneDistance, float f) {
            float g = effectInstance.isInfiniteDuration() ? 20.0F : Mth.lerp(Math.min(1.0F, (float)effectInstance.getDuration() / 40.0F), farPlaneDistance, 20.0F);
            if (fogData.mode == FogRenderer.FogMode.FOG_SKY) {
                fogData.start = 4F;
                fogData.end = g * 0.8F;
            } else {
                fogData.start = g * 0.25F;
                fogData.end = g;
            }

        }
    }


}