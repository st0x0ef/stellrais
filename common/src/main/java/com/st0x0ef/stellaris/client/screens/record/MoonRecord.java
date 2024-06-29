package com.st0x0ef.stellaris.client.screens.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data.planets.PlanetTextures;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record MoonRecord(
        ResourceLocation texture, String name,
        float distance, long period, float width,
        float height, String parent, ResourceKey<Level> dimensionId, String translatable, String id) {


    public static final Codec<MoonRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(MoonRecord::texture),
            Codec.STRING.fieldOf("name").forGetter(MoonRecord::name),
            Codec.FLOAT.fieldOf("distance").forGetter(MoonRecord::distance),
            Codec.LONG.fieldOf("period").forGetter(MoonRecord::period),
            Codec.FLOAT.fieldOf("width").forGetter(MoonRecord::width),
            Codec.FLOAT.fieldOf("height").forGetter(MoonRecord::height),
            Codec.STRING.fieldOf("parent").forGetter(MoonRecord::parent),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimensionId").forGetter(MoonRecord::dimensionId),
            Codec.STRING.fieldOf("translatable").forGetter(MoonRecord::translatable),
            Codec.STRING.fieldOf("id").forGetter(MoonRecord::id)
    ).apply(instance, MoonRecord::new));

}