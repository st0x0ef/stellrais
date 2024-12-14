package com.st0x0ef.stellaris.mixin.client;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.effects.SandStormEffect;
import com.st0x0ef.stellaris.common.registry.EffectsRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

import static net.minecraft.client.renderer.FogRenderer.MOB_EFFECT_FOG;

@Mixin(FogRenderer.class)
public class FogRenderMixin {

    /**
     * @author TATHAN
     * @reason Minecraft don't allow us to manually add our own fog functions, so we have to overwrite the method to add our own.
     */
    @Overwrite
    private static FogRenderer.MobEffectFogFunction getPriorityFogFunction(Entity entity, float partialTick) {
        ArrayList<FogRenderer.MobEffectFogFunction> list = new ArrayList<>(MOB_EFFECT_FOG);
        list.add(new SandStormEffect.SandStormFogFunction());

        if (entity instanceof LivingEntity livingEntity) {
            return (FogRenderer.MobEffectFogFunction)list.stream().filter((mobEffectFogFunction) -> mobEffectFogFunction.isEnabled(livingEntity, partialTick)).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    @Mixin(Gui.class)
    public static class RenderSandStormOverlay {

        @Final
        @Shadow
        @Mutable
        private Minecraft minecraft;


        @Inject(method = "renderCameraOverlays", at = @At("HEAD"))
        private void renderSandStormOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
            Gui gui = (Gui)(Object)this;

            if (this.minecraft.player.hasEffect(EffectsRegistry.SANDSTORM)) {

                MobEffectInstance instance = this.minecraft.player.getEffect(EffectsRegistry.SANDSTORM);

                gui.renderTextureOverlay(guiGraphics, SandStormEffect.SANDSTORM_OVERLAY, instance.getBlendFactor(this.minecraft.player, deltaTracker.getGameTimeDeltaPartialTick(true)));
            }
        }
    }
}
