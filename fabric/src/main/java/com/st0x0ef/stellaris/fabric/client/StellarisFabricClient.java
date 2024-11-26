package com.st0x0ef.stellaris.fabric.client;

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
import com.st0x0ef.stellaris.common.entities.vehicles.base.AbstractRoverBase;
import com.st0x0ef.stellaris.common.registry.*;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.*;

import net.minecraft.client.renderer.RenderType;

import net.minecraft.client.Minecraft;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class StellarisFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TickEvent.LevelTick.PLAYER_POST.register(instance -> {
            Minecraft minecraft = Minecraft.getInstance();

            Player player = minecraft.player;

            if (player == null) {
                return;
            }

            Entity riding = player.getVehicle();

            if (!(riding instanceof AbstractRoverBase car)) {
                return;
            }

            if (player.equals(car.getDriver())) {
                car.updateControls(minecraft.options.keyUp.isDown(), minecraft.options.keyDown.isDown(), minecraft.options.keyLeft.isDown(), minecraft.options.keyRight.isDown(), player);
            }
        });

        StellarisClient.initClient();
        StellarisClient.registerPacks();
        registerScreen();
        registerEntityRenderer();
        registerEntityModelLayer();
        registerKeyBinding();
    }

    public static void registerEntityRenderer() {
        EntityRendererRegistry.register(EntityRegistry.ALIEN.get(), AlienRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ALIEN_ZOMBIE.get(), AlienZombieRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MARTIAN_RAPTOR.get(), MartianRaptorRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.PYGRO.get(), PygroRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.PYGRO_BRUTE.get(), PygroBruteRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MOGLER.get(), MoglerRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.STAR_CRAWLER.get(), StarCrawlerRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHEESE_BOSS.get(), CheeseBossRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.ICE_SPIT.get(), renderManager -> new ThrownItemRenderer<>(renderManager, 1, true));
        EntityRendererRegistry.register(EntityRegistry.ICE_SHARD_ARROW.get(), IceShardArrowRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.TINY_ROCKET.get(), TinyRocketRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SMALL_ROCKET.get(), SmallRocketRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.NORMAL_ROCKET.get(), NormalRocketRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.BIG_ROCKET.get(), BigRocketRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ROVER.get(), RoverRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.LANDER.get(), LanderRenderer::new);

        BlockEntityRenderers.register(BlockEntityRegistry.GLOBE_BLOCK_ENTITY.get(), GlobeBlockRenderer::new);

        BuiltinItemRendererRegistry.INSTANCE.register(ItemsRegistry.EARTH_GLOBE_ITEM.get(), ItemRendererRegistry.GLOBE_ITEM_RENDERER::renderByItem);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemsRegistry.MOON_GLOBE_ITEM.get(), ItemRendererRegistry.GLOBE_ITEM_RENDERER::renderByItem);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemsRegistry.MARS_GLOBE_ITEM.get(), ItemRendererRegistry.GLOBE_ITEM_RENDERER::renderByItem);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemsRegistry.MERCURY_GLOBE_ITEM.get(), ItemRendererRegistry.GLOBE_ITEM_RENDERER::renderByItem);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemsRegistry.VENUS_GLOBE_ITEM.get(), ItemRendererRegistry.GLOBE_ITEM_RENDERER::renderByItem);


        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.MOON_VINES.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.MOON_VINES_PLANT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.UPGRADE_STATION.get(), RenderType.cutout());



        BuiltinItemRendererRegistry.INSTANCE.register(ItemsRegistry.ROVER.get(), ItemRendererRegistry.ROVER_ITEM_RENDERER::renderByItem);

    }

    public static void registerEntityModelLayer() {
        EntityModelLayerRegistry.registerModelLayer(AlienModel.LAYER_LOCATION, AlienModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AlienZombieModel.LAYER_LOCATION, AlienZombieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(MartianRaptorModel.LAYER_LOCATION, MartianRaptorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PygroModel.LAYER_LOCATION, PygroModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(MoglerModel.LAYER_LOCATION, MoglerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CheeseBossModel.LAYER_LOCATION, CheeseBossModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(GlobeModel.LAYER_LOCATION, GlobeModel::createLayer);
        EntityModelLayerRegistry.registerModelLayer(LanderModel.LAYER_LOCATION, LanderModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(TinyRocketModel.LAYER_LOCATION, TinyRocketModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SmallRocketModel.LAYER_LOCATION, SmallRocketModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(NormalRocketModel.LAYER_LOCATION, NormalRocketModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BigRocketModel.LAYER_LOCATION, BigRocketModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RoverModel.LAYER_LOCATION, RoverModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(JetSuitModel.LAYER_LOCATION, JetSuitModel::createBodyLayer);
    }

    public static void registerScreen() {
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.ROCKET_STATION.get(), RocketStationScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.ROCKET_MENU.get(), RocketScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.ROVER_MENU.get(), RoverScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.VACUMATOR_MENU.get(), VacumatorScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.SOLAR_PANEL_MENU.get(), SolarPanelScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.COAL_GENERATOR_MENU.get(), CoalGeneratorScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.RADIOACTIVE_GENERATOR_MENU.get(), RadioactiveGeneratorScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.PLANET_SELECTION_MENU.get(), PlanetSelectionScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.MILKYWAY_MENU.get(), MilkyWayScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.LANDER_MENU.get(), LanderScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.OXYGEN_DISTRIBUTOR.get(), OxygenGeneratorScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.WATER_SEPARATOR_MENU.get(), WaterSeparatorScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.FUEL_REFINERY.get(), FuelRefineryScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.WATER_PUMP_MENU.get(), WaterPumpScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.WAIT_MENU.get(), WaitScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.PUMPJACK_MENU.get(), PumpjackScreen::new);
        MenuRegistry.registerScreenFactory(MenuTypesRegistry.UPGRADE_STATION_MENU.get(), UpgradeStationScreen::new);
    }

    public static void registerKeyBinding() {
        KeyBindingHelper.registerKeyBinding(KeyMappingsRegistry.CHANGE_JETSUIT_MODE);
        KeyBindingHelper.registerKeyBinding(KeyMappingsRegistry.FREEZE_PLANET_MENU);

        ClientTickEvents.END_CLIENT_TICK.register(KeyMappingsRegistry::clientTick);
    }
}
