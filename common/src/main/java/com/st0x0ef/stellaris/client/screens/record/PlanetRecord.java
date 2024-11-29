package com.st0x0ef.stellaris.client.screens.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record PlanetRecord(
        ResourceLocation texture, String name,
        int distance, long period, int width,
        int height, String parent,
        ResourceKey<Level> dimensionId, String translatable, Optional<Boolean> spaceStation, String id) {


    public static final Codec<PlanetRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(PlanetRecord::texture),
            Codec.STRING.fieldOf("name").forGetter(PlanetRecord::name),
            Codec.INT.fieldOf("distance").forGetter(PlanetRecord::distance),
            Codec.LONG.fieldOf("period").forGetter(PlanetRecord::period),
            Codec.INT.fieldOf("width").forGetter(PlanetRecord::width),
            Codec.INT.fieldOf("height").forGetter(PlanetRecord::height),
            Codec.STRING.fieldOf("parent").forGetter(PlanetRecord::parent),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimensionId").forGetter(PlanetRecord::dimensionId),
            Codec.STRING.fieldOf("translatable").forGetter(PlanetRecord::translatable),
            Codec.BOOL.optionalFieldOf("space_station").forGetter(PlanetRecord::spaceStation),
            Codec.STRING.fieldOf("id").forGetter(PlanetRecord::id)
    ).apply(instance, PlanetRecord::new));
}