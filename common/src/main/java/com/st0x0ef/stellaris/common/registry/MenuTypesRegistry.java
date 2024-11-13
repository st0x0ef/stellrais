package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.menus.*;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class MenuTypesRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Stellaris.MODID, Registries.MENU);
    public static final RegistrySupplier<MenuType<RocketStationMenu>> ROCKET_STATION = MENU_TYPE.register("rocket_station", () -> MenuRegistry.ofExtended(RocketStationMenu::new));
    public static final RegistrySupplier<MenuType<RocketMenu>> ROCKET_MENU = MENU_TYPE.register("rocket_menu", () -> MenuRegistry.ofExtended(RocketMenu::new));
    public static final RegistrySupplier<MenuType<LanderMenu>> LANDER_MENU = MENU_TYPE.register("lander_menu", () -> MenuRegistry.ofExtended(LanderMenu::new));
    public static final RegistrySupplier<MenuType<RoverMenu>> ROVER_MENU = MENU_TYPE.register("rover_menu",()->
            MenuRegistry.ofExtended(RoverMenu::new));

    public static final RegistrySupplier<MenuType<SolarPanelMenu>> SOLAR_PANEL_MENU = MENU_TYPE.register("solar_panel", () -> MenuRegistry.ofExtended(SolarPanelMenu::create));
    public static final RegistrySupplier<MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU = MENU_TYPE.register("coal_generator", () -> MenuRegistry.ofExtended(CoalGeneratorMenu::create));
    public static final RegistrySupplier<MenuType<RadioactiveGeneratorMenu>> RADIOACTIVE_GENERATOR_MENU = MENU_TYPE.register("radioactive_generator", () -> MenuRegistry.ofExtended(RadioactiveGeneratorMenu::create));

    public static final RegistrySupplier<MenuType<VacuumatorMenu>> VACUMATOR_MENU = MENU_TYPE.register("vacuumator", () -> MenuRegistry.ofExtended(VacuumatorMenu::new));
    public static final RegistrySupplier<MenuType<WaterSeparatorMenu>> WATER_SEPARATOR_MENU = MENU_TYPE.register("water_separator", () -> MenuRegistry.ofExtended(WaterSeparatorMenu::create));
    public static final RegistrySupplier<MenuType<OxygenGeneratorMenu>> OXYGEN_DISTRIBUTOR = MENU_TYPE.register("oxygen_distributor", () -> MenuRegistry.ofExtended(OxygenGeneratorMenu::create));
    public static final RegistrySupplier<MenuType<FuelRefineryMenu>> FUEL_REFINERY = MENU_TYPE.register("fuel_refinery", () -> MenuRegistry.ofExtended(FuelRefineryMenu::create));
    public static final RegistrySupplier<MenuType<WaterPumpMenu>> WATER_PUMP_MENU = MENU_TYPE.register("water_pump", () -> MenuRegistry.ofExtended(WaterPumpMenu::create));
    public static final RegistrySupplier<MenuType<PumpjackMenu>> PUMPJACK_MENU = MENU_TYPE.register("pumpjack_menu", () -> MenuRegistry.ofExtended(PumpjackMenu::create));

    public static final RegistrySupplier<MenuType<PlanetSelectionMenu>> PLANET_SELECTION_MENU = MENU_TYPE.register("planet_selection_menu", () -> MenuRegistry.ofExtended(PlanetSelectionMenu::create));
    public static final RegistrySupplier<MenuType<TabletMenu>> TABLET_MENU = MENU_TYPE.register("tablet_menu", () -> MenuRegistry.ofExtended(TabletMenu::create));

    public static final RegistrySupplier<MenuType<MilkyWayMenu>> MILKYWAY_MENU = MENU_TYPE.register("milkyway_menu", () -> MenuRegistry.ofExtended(MilkyWayMenu::create));
    public static final RegistrySupplier<MenuType<WaitMenu>> WAIT_MENU = MENU_TYPE.register("wait_menu", () -> MenuRegistry.ofExtended(WaitMenu::create));

    public static final RegistrySupplier<MenuType<UpgradeStationMenu>> UPGRADE_STATION_MENU = MENU_TYPE.register("upgrade_station", () -> MenuRegistry.ofExtended(UpgradeStationMenu::create));

}
