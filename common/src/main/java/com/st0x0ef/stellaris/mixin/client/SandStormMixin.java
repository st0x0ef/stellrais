package com.st0x0ef.stellaris.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.registry.EffectsRegistry;
import com.st0x0ef.stellaris.common.registry.SoundRegistry;
import com.st0x0ef.stellaris.common.registry.TagRegistry;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class SandStormMixin {

    @Final
    @Shadow
    @Mutable
    private Minecraft minecraft;

    @Inject(
            method = "renderSnowAndRain",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    public void renderSandStorm(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci, @Local BlockPos.MutableBlockPos mutableBlockPos) {
         if(this.minecraft.level != null) {
             Level level = this.minecraft.level;
             if (level.getBiome(mutableBlockPos).is(TagRegistry.SANDSTORM_BIOMES_TAG)) {
                 RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/environment/sandstorm.png"));

                 if(Utils.entityHasBlockAbove(this.minecraft.player, null, null)) {
                     this.minecraft.player.addEffect(new MobEffectInstance(EffectsRegistry.SANDSTORM, 30));

                 }
             }
         }
    }

    @WrapOperation(
            method = "tickRain",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V")
    )
    private void bypassExpensiveCalculationIfNecessary(ClientLevel instance, BlockPos blockPos, SoundEvent soundEvent, SoundSource soundSource, float rainLevel, float v, boolean rainSoundTime, Operation<Void> original) {
        if(this.minecraft.level != null) {
            Level level = this.minecraft.level;
            if (level.getBiome(blockPos).is(TagRegistry.SANDSTORM_BIOMES_TAG)) {
                this.minecraft.level.playLocalSound(blockPos, SoundRegistry.WIND_SOUND.get(), SoundSource.WEATHER, 0.1F, 0.5F, false);

            } else {
                original.call(instance, blockPos, soundEvent, soundSource, rainLevel, v, rainSoundTime);
            }
        }

    }

}
