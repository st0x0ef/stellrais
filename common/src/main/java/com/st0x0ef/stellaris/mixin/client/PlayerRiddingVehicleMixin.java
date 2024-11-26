package com.st0x0ef.stellaris.mixin.client;

import com.st0x0ef.stellaris.common.entities.vehicles.IVehicleEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class PlayerRiddingVehicleMixin {

    @Inject(at = @At(value = "HEAD"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    private void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo c) {
        if(entity.getVehicle() instanceof IVehicleEntity vehicle) {
            ((HumanoidModel<LivingEntity>) (Object) this).riding = vehicle.setPassengersRiding();
        }
    }
}
