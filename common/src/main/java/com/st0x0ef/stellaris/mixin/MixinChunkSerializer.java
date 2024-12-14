package com.st0x0ef.stellaris.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.st0x0ef.stellaris.common.oil.OilUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SerializableChunkData.class)
public class MixinChunkSerializer {
    @Inject(at = @At("HEAD"), method = "parse")
    private void readDataLevel(LevelHeightAccessor levelHeightAccessor, RegistryAccess registries, CompoundTag tag, CallbackInfoReturnable<SerializableChunkData> cir) {
        if (tag.contains("oilLevel")) {
            instance.stellaris$setChunkOilLevel(tag.getInt("oilLevel"));
        } else {
            instance.stellaris$setChunkOilLevel(OilUtils.getRandomOilLevel());
        }
    }


    @WrapOperation(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtUtils;addCurrentDataVersion(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;"))
    private CompoundTag writeOilLevel(CompoundTag tag, Operation<CompoundTag> original) {
        CompoundTag compound = original.call(tag);
        compound.putInt("oilLevel", chunk.stellaris$getChunkOilLevel());

        return compound;
    }
}