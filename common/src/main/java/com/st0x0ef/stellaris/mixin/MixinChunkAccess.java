package com.st0x0ef.stellaris.mixin;

import com.st0x0ef.stellaris.common.oil.ChunkOilLevelGetter;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ChunkAccess.class)
public class MixinChunkAccess implements ChunkOilLevelGetter {

    private int oilLevel = 0;

    @Inject(method = "<init>", at = @At(value = "TAIL"), cancellable = false)
    private void generateRandomOilLevel(ChunkPos chunkPos, UpgradeData upgradeData, LevelHeightAccessor levelHeightAccessor, Registry biomeRegistry, long inhabitedTime, LevelChunkSection[] sections, BlendingData blendingData, CallbackInfo ci) {
        Random random = new Random();
        oilLevel = random.nextInt(0, 10000);
    }

    @Override
    public int stellaris$getChunkOilLevel() {
        return oilLevel;
    }
}
