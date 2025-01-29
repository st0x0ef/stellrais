package com.st0x0ef.stellaris.mixin.client;

import com.st0x0ef.stellaris.common.effects.SandStormEffect;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.*;

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

}