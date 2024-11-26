package com.st0x0ef.stellaris.mixin;

import com.st0x0ef.stellaris.common.data_components.SpaceSuitModules;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    LivingEntity stellaris$livingEntity = (LivingEntity) ((Object) this);

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void tick(CallbackInfo ci){
        ResourceLocation stellaris$dimension = stellaris$livingEntity.level().dimension().location();

        boolean stellaris$gravityNormalizer = SpaceSuitModules.containsInModules(stellaris$livingEntity.getItemBySlot(EquipmentSlot.CHEST), ItemsRegistry.MODULE_GRAVITY_NORMALIZER.get().getDefaultInstance());
        if (!stellaris$dimension.equals(ResourceLocation.withDefaultNamespace("overworld")) && PlanetUtil.isPlanet(stellaris$dimension) && !stellaris$gravityNormalizer) {
            double stellaris$gravity = Utils.MPS2ToMCG(PlanetUtil.getPlanet(stellaris$dimension).gravity());
            stellaris$livingEntity.getAttribute(Attributes.GRAVITY).setBaseValue(stellaris$gravity);
            stellaris$livingEntity.getAttribute(Attributes.SAFE_FALL_DISTANCE).setBaseValue(3.0/(stellaris$gravity/0.08));
            stellaris$livingEntity.getAttribute(Attributes.FALL_DAMAGE_MULTIPLIER).setBaseValue(stellaris$gravity/0.08);
        } else {
            stellaris$livingEntity.getAttribute(Attributes.GRAVITY).setBaseValue(0.08);
            stellaris$livingEntity.getAttribute(Attributes.SAFE_FALL_DISTANCE).setBaseValue(3.0);
            stellaris$livingEntity.getAttribute(Attributes.FALL_DAMAGE_MULTIPLIER).setBaseValue(1.0);
        }
    }
}
