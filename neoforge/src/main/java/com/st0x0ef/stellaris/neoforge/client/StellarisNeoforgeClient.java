package com.st0x0ef.stellaris.neoforge.client;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.StellarisClient;
import com.st0x0ef.stellaris.client.registries.KeyMappingsRegistry;
import com.st0x0ef.stellaris.client.renderers.armors.JetSuitModel;
import com.st0x0ef.stellaris.client.renderers.armors.SpaceSuitModel;
import com.st0x0ef.stellaris.client.renderers.entities.alien.AlienModel;
import com.st0x0ef.stellaris.client.renderers.entities.alien.AlienRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.alienzombie.AlienZombieModel;
import com.st0x0ef.stellaris.client.renderers.entities.alienzombie.AlienZombieRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.cheeseboss.CheeseBossModel;
import com.st0x0ef.stellaris.client.renderers.entities.cheeseboss.CheeseBossRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.martianraptor.MartianRaptorModel;
import com.st0x0ef.stellaris.client.renderers.entities.martianraptor.MartianRaptorRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.mogler.MoglerModel;
import com.st0x0ef.stellaris.client.renderers.entities.mogler.MoglerRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.projectiles.IceShardArrowRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.pygro.PygroBruteRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.pygro.PygroModel;
import com.st0x0ef.stellaris.client.renderers.entities.pygro.PygroRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.starcrawler.StarCrawlerModel;
import com.st0x0ef.stellaris.client.renderers.entities.starcrawler.StarCrawlerRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.lander.LanderModel;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.lander.LanderRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.big.BigRocketModel;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.big.BigRocketRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.normal.NormalRocketModel;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.normal.NormalRocketRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.small.SmallRocketModel;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.small.SmallRocketRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.tiny.TinyRocketModel;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.tiny.TinyRocketRenderer;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rover.RoverModel;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rover.RoverRenderer;
import com.st0x0ef.stellaris.client.renderers.globe.GlobeBlockRenderer;
import com.st0x0ef.stellaris.client.renderers.globe.GlobeModel;
import com.st0x0ef.stellaris.client.screens.*;
import com.st0x0ef.stellaris.common.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = Stellaris.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class StellarisNeoforgeClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(StellarisClient::initClient);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.ALIEN.get(), AlienRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ALIEN_ZOMBIE.get(), AlienZombieRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MARTIAN_RAPTOR.get(), MartianRaptorRenderer::new);
        event.registerEntityRenderer(EntityRegistry.PYGRO.get(), PygroRenderer::new);
        event.registerEntityRenderer(EntityRegistry.PYGRO_BRUTE.get(), PygroBruteRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MOGLER.get(), MoglerRenderer::new);
        event.registerEntityRenderer(EntityRegistry.STAR_CRAWLER.get(), StarCrawlerRenderer::new);
        event.registerEntityRenderer(EntityRegistry.CHEESE_BOSS.get(), CheeseBossRenderer::new);

        event.registerEntityRenderer(EntityRegistry.ICE_SPIT.get(), renderManager -> new ThrownItemRenderer<>(renderManager, 1, true));
        event.registerEntityRenderer(EntityRegistry.ICE_SHARD_ARROW.get(), IceShardArrowRenderer::new);

        event.registerEntityRenderer(EntityRegistry.TINY_ROCKET.get(), TinyRocketRenderer::new);
        event.registerEntityRenderer(EntityRegistry.SMALL_ROCKET.get(), SmallRocketRenderer::new);
        event.registerEntityRenderer(EntityRegistry.NORMAL_ROCKET.get(), NormalRocketRenderer::new);
        event.registerEntityRenderer(EntityRegistry.BIG_ROCKET.get(), BigRocketRenderer::new);

        event.registerEntityRenderer(EntityRegistry.LANDER.get(), LanderRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ROVER.get(), RoverRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityRegistry.GLOBE_BLOCK_ENTITY.get(), GlobeBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AlienModel.LAYER_LOCATION, AlienModel::createBodyLayer);
        event.registerLayerDefinition(AlienZombieModel.LAYER_LOCATION, AlienZombieModel::createBodyLayer);
        event.registerLayerDefinition(MartianRaptorModel.LAYER_LOCATION, MartianRaptorModel::createBodyLayer);
        event.registerLayerDefinition(PygroModel.LAYER_LOCATION, PygroModel::createBodyLayer);
        event.registerLayerDefinition(MoglerModel.LAYER_LOCATION, MoglerModel::createBodyLayer);
        event.registerLayerDefinition(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::createBodyLayer);
        event.registerLayerDefinition(CheeseBossModel.LAYER_LOCATION, CheeseBossModel::createBodyLayer);

        event.registerLayerDefinition(GlobeModel.LAYER_LOCATION, GlobeModel::createLayer);
        event.registerLayerDefinition(LanderModel.LAYER_LOCATION, LanderModel::createBodyLayer);

        event.registerLayerDefinition(TinyRocketModel.LAYER_LOCATION, TinyRocketModel::createBodyLayer);
        event.registerLayerDefinition(SmallRocketModel.LAYER_LOCATION, SmallRocketModel::createBodyLayer);
        event.registerLayerDefinition(NormalRocketModel.LAYER_LOCATION, NormalRocketModel::createBodyLayer);
        event.registerLayerDefinition(BigRocketModel.LAYER_LOCATION, BigRocketModel::createBodyLayer);

        event.registerLayerDefinition(RoverModel.LAYER_LOCATION, RoverModel::createBodyLayer);

        event.registerLayerDefinition(JetSuitModel.LAYER_LOCATION, JetSuitModel::createBodyLayer);
        event.registerLayerDefinition(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(MenuTypesRegistry.ROCKET_STATION.get(), RocketStationScreen::new);
        event.register(MenuTypesRegistry.UPGRADE_STATION_MENU.get(), UpgradeStationScreen::new);
        event.register(MenuTypesRegistry.ROCKET_MENU.get(), RocketScreen::new);
        event.register(MenuTypesRegistry.ROVER_MENU.get(), RoverScreen::new);
        event.register(MenuTypesRegistry.VACUMATOR_MENU.get(), VacumatorScreen::new);
        event.register(MenuTypesRegistry.SOLAR_PANEL_MENU.get(), SolarPanelScreen::new);
        event.register(MenuTypesRegistry.COAL_GENERATOR_MENU.get(), CoalGeneratorScreen::new);
        event.register(MenuTypesRegistry.RADIOACTIVE_GENERATOR_MENU.get(), RadioactiveGeneratorScreen::new);
        event.register(MenuTypesRegistry.PLANET_SELECTION_MENU.get(), PlanetSelectionScreen::new);
        event.register(MenuTypesRegistry.MILKYWAY_MENU.get(), MilkyWayScreen::new);
        event.register(MenuTypesRegistry.LANDER_MENU.get(), LanderScreen::new);
        event.register(MenuTypesRegistry.OXYGEN_DISTRIBUTOR.get(), OxygenGeneratorScreen::new);
        event.register(MenuTypesRegistry.WATER_SEPARATOR_MENU.get(), WaterSeparatorScreen::new);
        event.register(MenuTypesRegistry.FUEL_REFINERY.get(), FuelRefineryScreen::new);
        event.register(MenuTypesRegistry.WATER_PUMP_MENU.get(), WaterPumpScreen::new);
        event.register(MenuTypesRegistry.WAIT_MENU.get(), WaitScreen::new);
        event.register(MenuTypesRegistry.PUMPJACK_MENU.get(), PumpjackScreen::new);

    }

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(KeyMappingsRegistry.CHANGE_JETSUIT_MODE);
        event.register(KeyMappingsRegistry.FREEZE_PLANET_MENU);

        NeoForge.EVENT_BUS.addListener(StellarisNeoforgeClient::clientTick);
    }

    /*@SubscribeEvent
    private static void initializeClient(RegisterClientExtensionsEvent event) {
        FluidRegistry.FLUIDS_INFOS.forEach((attributes -> event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return attributes.getSourceTexture();
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return attributes.getFlowingTexture();
            }
        }, attributes.getFlowingFluid().getFluidType())));
    }*/

    private static void clientTick(ClientTickEvent.Post event) {
        KeyMappingsRegistry.clientTick(Minecraft.getInstance());
    }
}
