package com.st0x0ef.stellaris.mixin;

import com.st0x0ef.stellaris.common.registry.TagRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class EntityEatMixin extends LivingEntity {
    @Shadow public abstract void playSound(SoundEvent soundEvent, float f, float g);

    protected EntityEatMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }


    @Inject(at = @At(value = "HEAD"), method = "canEat")
    private void cancelEat(boolean canAlwaysEat, CallbackInfoReturnable<Boolean> cir) {
        if(PlanetUtil.isPlanet(level.dimension().location())) {
            if(!PlanetUtil.hasOxygen(level) && !food.is(TagRegistry.SPACE_FOOD)) {
                cir.setReturnValue(false);
            }
        }
    }
}
