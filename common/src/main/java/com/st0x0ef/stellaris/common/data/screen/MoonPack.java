package com.st0x0ef.stellaris.common.data.screen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.PlanetSelectionScreen;
import com.st0x0ef.stellaris.client.screens.info.MoonInfo;
import com.st0x0ef.stellaris.client.screens.record.MoonRecord;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class MoonPack extends SimpleJsonResourceReloadListener<JsonElement>  {

    public static final Map<String, MoonRecord> MOON = new HashMap<>();
    public static int count = 0;

    public MoonPack() {
        super(ExtraCodecs.JSON, FileToIdConverter.json("renderer/planet_screen/moon"));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        if (count > 0) return;
        MOON.clear();
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "moons");
            MoonRecord moon = MoonRecord.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();

            MOON.put(moon.name(), moon);

            MoonInfo screenMoon = new MoonInfo(
                    moon.texture(),
                    moon.name(),
                    (int) moon.distance(),
                    moon.period(),
                    (int) moon.width(),
                    (int) moon.height(),
                    PlanetSelectionScreen.findByNamePlanet(moon.parent()),
                    moon.dimensionId(),
                    Component.translatable(moon.translatable()),
                    moon.id()
            );

            moon.clickable().ifPresent(screenMoon::setClickable);

            PlanetSelectionScreen.MOONS.add(screenMoon);
            Stellaris.LOG.info("Added a moon to PlanetSelectionScreen : {}", moon.name());
        });
        count++;
    }
}