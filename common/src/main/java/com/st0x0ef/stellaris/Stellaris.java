package com.st0x0ef.stellaris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.st0x0ef.stellaris.common.config.CustomConfig;
import com.st0x0ef.stellaris.common.data.planets.StellarisData;
import com.st0x0ef.stellaris.common.data.screen.MoonPack;
import com.st0x0ef.stellaris.common.data.screen.PlanetPack;
import com.st0x0ef.stellaris.common.data.screen.StarPack;
import com.st0x0ef.stellaris.common.events.Events;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import com.st0x0ef.stellaris.common.network.packets.SyncPlanetsDatapackPacket;
import com.st0x0ef.stellaris.common.registry.*;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

public class Stellaris {
    public static final String MODID = "stellaris";
    public static final Logger LOG = LoggerFactory.getLogger("Stellaris");
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create();

    public static void init() {
        CustomConfig.init();
        EntityData.register();
        NetworkRegistry.init();

        ProcessorsRegistry.STRUCTURE_PROCESSORS.register();
        SoundRegistry.SOUNDS.register();
        DataComponentsRegistry.DATA_COMPONENT_TYPE.register();
        FluidRegistry.init();
        ParticleRegistry.PARTICLES.register();
        BlocksRegistry.BLOCKS.register();
        EntityRegistry.ENTITY_TYPE.register();
        EntityRegistry.SENSOR.register();
        BlockEntityRegistry.BLOCK_ENTITY_TYPE.register();
        ItemsRegistry.ITEMS.register();
        ArmorMaterialsRegistry.ARMOR_MATERIAL.register();
        CreativeTabsRegistry.TABS.register();
        MenuTypesRegistry.MENU_TYPE.register();
        FeaturesRegistry.FEATURES.register();
        CommandsRegistry.register();
        Events.registerEvents();
        LookupApiRegistry.registerEnergy();
        RecipesRegistry.register();
        EntityRegistry.registerSpawnPlacements();
        EffectsRegistry.register();

        ReloadListenerRegistry.register(PackType.SERVER_DATA, new StellarisData());
    }

    public static void onDatapackSyncEvent(ServerPlayer player, boolean joined) {
        if (joined) {
            NetworkManager.sendToPlayer(player, new SyncPlanetsDatapackPacket(StellarisData.getPlanets()));
        }
    }

    public static void onAddReloadListenerEvent(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "planets"), new StellarisData());

        registry.accept(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "stars_pack"), new StarPack());
        registry.accept(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "planets_pack"), new PlanetPack());
        registry.accept(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "moon_packs"), new MoonPack());
    }
}
