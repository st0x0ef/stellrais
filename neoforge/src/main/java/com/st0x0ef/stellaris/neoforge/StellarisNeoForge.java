package com.st0x0ef.stellaris.neoforge;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.StellarisClient;
import com.st0x0ef.stellaris.common.registry.EntityRegistry;
import com.st0x0ef.stellaris.neoforge.client.renderer.MarsFog;
import com.st0x0ef.stellaris.neoforge.client.renderer.SkyRendererNeoForge;
import com.st0x0ef.stellaris.neoforge.systems.SystemsNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(Stellaris.MODID)
public class StellarisNeoForge {
    public StellarisNeoForge(IEventBus bus) {
        Stellaris.init();
        StellarisClient.registerPacks();

        NeoForge.EVENT_BUS.addListener(StellarisNeoForge::onAddReloadListenerEvent);
        NeoForge.EVENT_BUS.addListener(StellarisNeoForge::onDatapackSync);

        bus.addListener(StellarisNeoForge::onAttributes);

        NeoForge.EVENT_BUS.addListener(MarsFog::setupFog);
        NeoForge.EVENT_BUS.addListener(SkyRendererNeoForge::RenderWorldSky);

        SystemsNeoForge.init(bus);
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            Stellaris.onDatapackSyncEvent(event.getPlayer());
        } else {
            event.getPlayerList().getPlayers().forEach((Stellaris::onDatapackSyncEvent));
        }
    }

    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        Stellaris.onAddReloadListenerEvent((id, listener) -> event.addListener(listener));
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        EntityRegistry.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

}