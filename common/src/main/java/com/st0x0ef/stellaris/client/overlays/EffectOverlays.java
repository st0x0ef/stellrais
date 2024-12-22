package com.st0x0ef.stellaris.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.common.effects.SandStormEffect;
import com.st0x0ef.stellaris.common.registry.EffectsRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectOverlays {

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player.hasEffect(EffectsRegistry.SANDSTORM)) {

            MobEffectInstance instance = minecraft.player.getEffect(EffectsRegistry.SANDSTORM);
            float alpha = instance.getBlendFactor(minecraft.player, deltaTracker.getGameTimeDeltaPartialTick(true));

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
            guiGraphics.blit(SandStormEffect.SANDSTORM_OVERLAY, 0, 0, -90, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight());
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        }

    }
}
