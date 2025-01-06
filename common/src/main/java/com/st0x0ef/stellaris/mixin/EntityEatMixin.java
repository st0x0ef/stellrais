package com.st0x0ef.stellaris.mixin;

import com.st0x0ef.stellaris.common.registry.TagRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class EntityEatMixin extends LivingEntity {
    @Shadow public abstract void playSound(SoundEvent soundEvent, float f, float g);

    @Unique
    public abstract boolean stellaris$canEat(TagKey<Item> canAlwaysEat);

    protected EntityEatMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }


    @Inject(at = @At(value = "HEAD"), method = "canEat", cancellable = true)
    private void cancelEat(boolean canAlwaysEat, CallbackInfoReturnable<Boolean> cir) {
        if(level() instanceof ServerLevel serverLevel && !PlanetUtil.hasOxygenAt(serverLevel, getOnPos()) && !stellaris$canEat(TagRegistry.SPACE_FOOD)) {
            cir.setReturnValue(canAlwaysEat);
        }
    }
}
