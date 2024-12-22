package com.st0x0ef.stellaris.mixin;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.entities.CustomLightningBolt;
import com.st0x0ef.stellaris.common.registry.EntityRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerLevel.class)
public class StormyPlanetMixin {

    @Inject(at = @At(value = "HEAD"), method = "tickChunk")
    public void spawnMoreLightningBolt(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        PlanetUtil.ifPlanet(level.dimension().location(), planet -> {
            if(planet.stormParameters().stormy()) {
                ChunkPos chunkPos = chunk.getPos();
                int i = chunkPos.getMinBlockX();
                int j = chunkPos.getMinBlockZ();
                if (level.random.nextInt(planet.stormParameters().lightningFrequency()) == 0) {
                    BlockPos blockPos = level.findLightningTargetAround(level.getBlockRandomPos(i, 0, j, 15));

                    CustomLightningBolt lightningBolt = EntityRegistry.VENUS_LIGHTNING_BOLT.get().create(level);
                    lightningBolt.setCustomColor(planet.stormParameters().lightningColor());


                    if (lightningBolt != null) {
                        lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
                        lightningBolt.setVisualOnly(false);
                        level.addFreshEntity(lightningBolt);
                    }
                }
            }

        });
    }
}