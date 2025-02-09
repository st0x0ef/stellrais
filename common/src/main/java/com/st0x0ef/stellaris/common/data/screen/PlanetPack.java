package com.st0x0ef.stellaris.common.data.screen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.event.custom.PlanetSelectionEvents;
import com.st0x0ef.stellaris.client.screens.PlanetSelectionScreen;
import com.st0x0ef.stellaris.client.screens.info.PlanetInfo;
import com.st0x0ef.stellaris.client.screens.record.PlanetRecord;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class PlanetPack extends SimpleJsonResourceReloadListener {
    public PlanetPack() {
        super(Stellaris.GSON, "renderer/planet_screen/planet");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
            PlanetRecord planet = PlanetRecord.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();

            PlanetInfo screenPlanet = new PlanetInfo(
                    planet.texture(),
                    planet.name(),
                    planet.distance(),
                    planet.period(),
                    planet.width(),
                    planet.height(),
                    PlanetSelectionScreen.findByNameStar(planet.parent()),
                    planet.dimensionId(),
                    Component.translatable(planet.translatable()),
                    planet.id()
            );

            PlanetSelectionScreen.PLANETS.add(screenPlanet);
            Stellaris.LOG.info("Added a planet to PlanetSelectionScreen : {}", planet.name());
        });
        PlanetSelectionEvents.POST_PLANET_PACK_REGISTRY.invoker().moonRegistered(PlanetSelectionScreen.PLANETS);

    }
}