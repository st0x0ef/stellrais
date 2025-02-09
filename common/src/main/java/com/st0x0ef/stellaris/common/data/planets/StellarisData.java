package com.st0x0ef.stellaris.common.data.planets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.events.custom.PlanetEvents;
import dev.architectury.event.EventResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StellarisData extends SimpleJsonResourceReloadListener {

    private static final List<Planet> PLANETS = new ArrayList<>();

    public StellarisData() {
        super(Stellaris.GSON, "planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        PLANETS.clear();
        resourceLocationJsonElementMap.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
            Planet planet = Planet.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();

            if (PlanetEvents.PLANET_REGISTERED.invoker().planetRegistered(planet) == EventResult.interruptDefault() || PlanetEvents.PLANET_REGISTERED.invoker().planetRegistered(planet) ==  EventResult.interruptFalse()) {
                return;
            }
            PLANETS.add(planet);
        });
        PlanetEvents.POST_PLANET_REGISTRY.invoker().planetRegistered(PLANETS, false);
    }

    public static List<Planet> getPlanets() {
        return PLANETS;
    }

    public static void addPlanets(List<Planet> planets) {
        PLANETS.clear();
        PLANETS.addAll(planets);
        PlanetEvents.POST_PLANET_REGISTRY.invoker().planetRegistered(planets, true);
    }
}
