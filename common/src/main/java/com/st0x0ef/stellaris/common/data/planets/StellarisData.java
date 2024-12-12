package com.st0x0ef.stellaris.common.data.planets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StellarisData extends SimpleJsonResourceReloadListener<JsonElement>  {

    private static final List<Planet> PLANETS = new ArrayList<>();

    public StellarisData() {
        super(ExtraCodecs.JSON, "planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        PLANETS.clear();
        resourceLocationJsonElementMap.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
            Planet planet = Planet.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            PLANETS.add(planet);
        });
    }

    public static List<Planet> getPlanets() {
        return PLANETS;
    }

    public static void addPlanets(List<Planet> planets) {
        PLANETS.clear();
        PLANETS.addAll(planets);
    }
}
