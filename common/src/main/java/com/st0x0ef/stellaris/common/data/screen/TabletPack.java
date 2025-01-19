package com.st0x0ef.stellaris.common.data.screen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.tablet.TabletEntry;
import com.st0x0ef.stellaris.client.screens.tablet.TabletMainScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class TabletPack extends SimpleJsonResourceReloadListener {

    public TabletPack() {
        super(Stellaris.GSON, "renderer/tablet");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        Stellaris.LOG.info("Loading Assets for Tablet Pack");
        resourceLocationJsonElementMap.forEach((key, value) -> {
            Stellaris.LOG.info("Loading tablet entry: " + key);
            JsonObject json = GsonHelper.convertToJsonObject(value, "tablet");
            TabletEntry entry = TabletEntry.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            TabletMainScreen.ENTRIES.put(entry.id(), entry);
            entry.infos().forEach(info -> {
                TabletMainScreen.INFOS.put(ResourceLocation.fromNamespaceAndPath(entry.id(), info.id()), info);
            });
        });


    }
}
