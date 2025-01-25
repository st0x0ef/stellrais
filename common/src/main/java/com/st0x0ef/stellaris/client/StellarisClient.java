package com.st0x0ef.stellaris.client;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.overlays.*;
import com.st0x0ef.stellaris.client.particles.*;
import com.st0x0ef.stellaris.client.renderers.armors.JetSuitModel;
import com.st0x0ef.stellaris.client.renderers.armors.SpaceSuitModel;
import com.st0x0ef.stellaris.client.screens.ConfigScreen;
import com.st0x0ef.stellaris.common.data.screen.TabletPack;
import com.st0x0ef.stellaris.common.data.screen.MoonPack;
import com.st0x0ef.stellaris.common.data.screen.PlanetPack;
import com.st0x0ef.stellaris.common.data.screen.StarPack;
import com.st0x0ef.stellaris.common.handlers.GlobalExceptionHandler;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.common.registry.ParticleRegistry;
import com.st0x0ef.stellaris.common.registry.StatsRegistry;
import com.st0x0ef.stellaris.platform.ClientUtilsPlatform;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.component.DyedItemColor;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallback;

@Environment(EnvType.CLIENT)
public class StellarisClient {
    public static void initClient() {
        Minecraft.getInstance().execute(() -> {
            setupOpenGLDebugMessageCallback();
            Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        });
        registerParticle();
        registerOverlays();
        registerArmors();
        Platform.getMod(Stellaris.MODID).registerConfigurationScreen(ConfigScreen::new);
    }

    private static void registerArmors() {
        ClientUtilsPlatform.registerArmor(JetSuitModel.LAYER_LOCATION, JetSuitModel::new,
                ItemsRegistry.JETSUIT_BOOTS.get(), ItemsRegistry.JETSUIT_LEGGINGS.get(),
                ItemsRegistry.JETSUIT_HELMET.get(), ItemsRegistry.JETSUIT_SUIT.get());

        ClientUtilsPlatform.registerArmor(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::new,
                ItemsRegistry.SPACESUIT_BOOTS.get(), ItemsRegistry.SPACESUIT_LEGGINGS.get(),
                ItemsRegistry.SPACESUIT_HELMET.get(), ItemsRegistry.SPACESUIT_SUIT.get());

        ColorHandlerRegistry.registerItemColors(
                (stack, color) -> color > 0 ? -1 : DyedItemColor.getOrDefault(stack, -1),
                ItemsRegistry.SPACESUIT_SUIT.get(), ItemsRegistry.SPACESUIT_HELMET.get(), ItemsRegistry.SPACESUIT_LEGGINGS.get());

    }

    public static void registerParticle() {
        ParticleProviderRegistry.register(ParticleRegistry.VENUS_RAIN_PARTICLE.get(), VenusRainParticle.ParticleFactory::new);
        ParticleProviderRegistry.register(ParticleRegistry.LARGE_FLAME_PARTICLE.get(), LargeFlameParticle.ParticleFactory::new);
        ParticleProviderRegistry.register(ParticleRegistry.LARGE_SMOKE_PARTICLE.get(), LargeSmokeParticle.ParticleFactory::new);
        ParticleProviderRegistry.register(ParticleRegistry.SMALL_FLAME_PARTICLE.get(), SmallFlameParticle.ParticleFactory::new);
        ParticleProviderRegistry.register(ParticleRegistry.SMALL_SMOKE_PARTICLE.get(), SmallSmokeParticle.ParticleFactory::new);
    }

    public static void registerOverlays() {
        ClientGuiEvent.RENDER_HUD.register(RocketStartOverlay::render);
        ClientGuiEvent.RENDER_HUD.register(RocketBarOverlay::render);
        ClientGuiEvent.RENDER_HUD.register(LanderOverlay::render);
        ClientGuiEvent.RENDER_HUD.register(JetSuitOverlay::render);
        ClientGuiEvent.RENDER_HUD.register(SpaceSuitOverlay::render);
    }

    public static void setupOpenGLDebugMessageCallback() {
        if (GL.getCapabilities().GL_KHR_debug) {
            GL43.glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
                if (id == 1282 || id == 1281) {
                    return;
                }
                String errorMessage = GLDebugMessageCallback.getMessage(length, message);
                Stellaris.LOG.error("OpenGL debug message: id={}, source={}, type={}, severity={}, message='{}'",
                        id, source, type, severity, errorMessage);
            }, 0);
            GL43.glEnable(GL43.GL_DEBUG_OUTPUT);
            GL43.glEnable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);
        }
    }

    public static void registerPacks() {
        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, new StarPack());
        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, new PlanetPack());
        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, new MoonPack());
        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, new TabletPack());
    }
}
