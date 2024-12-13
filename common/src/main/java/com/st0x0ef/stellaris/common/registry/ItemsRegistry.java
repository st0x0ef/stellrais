package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.RocketModel;
import com.st0x0ef.stellaris.client.screens.GUISprites;
import com.st0x0ef.stellaris.common.blocks.entities.machines.FluidTankHelper;
import com.st0x0ef.stellaris.common.data_components.*;
import com.st0x0ef.stellaris.common.items.*;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
import com.st0x0ef.stellaris.common.items.armors.SpaceSuit;
import com.st0x0ef.stellaris.common.items.module.*;
import com.st0x0ef.stellaris.common.vehicle_upgrade.*;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.ArrayList;

public class ItemsRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Stellaris.MODID, Registries.ITEM);

    /**
     * ITEMS
     */

    public static final RegistrySupplier<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> STEEL_ORE_ITEM = ITEMS.register("steel_ore", () -> new BlockItem(BlocksRegistry.STEEL_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> DEEPSLATE_STEEL_ORE_ITEM = ITEMS.register("deepslate_steel_ore", () -> new BlockItem(BlocksRegistry.DEEPSLATE_STEEL_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> HEAVY_METAL_INGOT_ITEM = ITEMS.register("heavy_metal_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> HEAVY_METAL_NUGGET_ITEM = ITEMS.register("heavy_metal_nugget", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> RAW_STEEL_INGOT = ITEMS.register("raw_steel_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> RAW_DESH_INGOT = ITEMS.register("raw_desh_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> DESH_INGOT = ITEMS.register("desh_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    /**
     * Food Cans
     */

    public static final RegistrySupplier<Item> BIG_BLUE_CAN = ITEMS.register("big_blue_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 20));
    public static final RegistrySupplier<Item> BIG_GREEN_CAM = ITEMS.register("big_green_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 20));
    public static final RegistrySupplier<Item> BIG_PURPLE_CAN = ITEMS.register("big_purple_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 20));
    public static final RegistrySupplier<Item> BIG_RED_CAN = ITEMS.register("big_red_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 20));
    public static final RegistrySupplier<Item> BIG_YELLOW_CAN = ITEMS.register("big_yellow_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 20));
    public static final RegistrySupplier<Item> SMALL_BLUE_CAN = ITEMS.register("small_blue_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 10));
    public static final RegistrySupplier<Item> SMALL_GREEN_CAN = ITEMS.register("small_green_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 10));
    public static final RegistrySupplier<Item> SMALL_PURPLE_CAN = ITEMS.register("small_purple_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 10));
    public static final RegistrySupplier<Item> SMALL_RED_CAN = ITEMS.register("small_red_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 10));
    public static final RegistrySupplier<Item> SMALL_YELLOW_CAN = ITEMS.register("small_yellow_can", () -> new CanItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), 10));

    /**
     * Food Items
     */

    public static final RegistrySupplier<Item> COSMO_BREAD = ITEMS.register("cosmo_bread", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).food(FoodRegistry.COSMO_BREAD)));
    public static final RegistrySupplier<Item> CHEESE = ITEMS.register("cheese", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).food(FoodRegistry.CHEESE)));
    public static final RegistrySupplier<Item> COSMO_COFFEE = ITEMS.register("cosmo_coffee", () -> new CoffeeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).food(FoodRegistry.COSMO_COFFEE)));
    public static final RegistrySupplier<Item> CHEESE_BLOCK_ITEM = ITEMS.register("cheese_block", () -> new BlockItem(BlocksRegistry.CHEESE_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MARS_FRUIT = ITEMS.register("mars_fruit", () -> new ItemNameBlockItem(BlocksRegistry.MARS_CROP.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6F).build()).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MOON_FRUIT = ITEMS.register("moon_fruit", () -> new ItemNameBlockItem(BlocksRegistry.MOON_CROPS.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6F).build()).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> COFFEE_CUP = ITEMS.register("coffee_cup", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    /**
     * Other
     */

    public static final RegistrySupplier<Item> ICE_SHARD_ARROW = ITEMS.register("ice_shard_arrow", () -> new IceShardArrow(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> OIL_FINDER = ITEMS.register("oil_finder", () -> new OilFinderItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).durability(1000)));
    public static final RegistrySupplier<Item> PUMPJACK_DRILL = ITEMS.register("pumpjack_drill", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    /**
     * BLOCKS ITEMS
     */

    public static final RegistrySupplier<Item> STEEL_BLOCK_ITEM = ITEMS.register("steel_block", () -> new BlockItem(BlocksRegistry.STEEL_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> RAW_STEEL_BLOCK_ITEM = ITEMS.register("raw_steel_block", () -> new BlockItem(BlocksRegistry.RAW_STEEL_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> STEEL_PLATING_BLOCK_ITEM = ITEMS.register("steel_plating_block", () -> new BlockItem(BlocksRegistry.STEEL_PLATING_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> STEEL_PILLAR_ITEM = ITEMS.register("steel_pillar", () -> new BlockItem(BlocksRegistry.STEEL_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> STEEL_STAIRS_ITEM = ITEMS.register("steel_stairs", () -> new BlockItem(BlocksRegistry.STEEL_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> STEEL_PLATING_STAIRS_ITEM = ITEMS.register("steel_plating_stairs", () -> new BlockItem(BlocksRegistry.STEEL_PLATING_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> STEEL_SLAB_ITEM = ITEMS.register("steel_slab", () -> new BlockItem(BlocksRegistry.STEEL_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> STEEL_PLATING_SLAB_ITEM = ITEMS.register("steel_plating_slab", () -> new BlockItem(BlocksRegistry.STEEL_plating_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STEEL_ORE = ITEMS.register("moon_steel_ore", () -> new BlockItem(BlocksRegistry.MOON_STEEL_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));

    public static final RegistrySupplier<Item> HEAVY_METAL_PLATE_ITEM = ITEMS.register("heavy_metal_plate", () -> new BlockItem(BlocksRegistry.HEAVY_METAL_PLATE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> HEAVY_METAL_CASING_ITEM = ITEMS.register("heavy_metal_casing", () -> new BlockItem(BlocksRegistry.HEAVY_METAL_CASING.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> HEAVY_METAL_STAIRS_ITEM = ITEMS.register("heavy_metal_stairs", () -> new BlockItem(BlocksRegistry.HEAVY_METAL_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> HEAVY_METAL_SLAB_ITEM = ITEMS.register("heavy_metal_slab", () -> new BlockItem(BlocksRegistry.HEAVY_METAL_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> IRON_PLATING_BLOCK_ITEM = ITEMS.register("iron_plating_block", () -> new BlockItem(BlocksRegistry.IRON_PLATING_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> IRON_PILLAR_ITEM = ITEMS.register("iron_pillar", () -> new BlockItem(BlocksRegistry.IRON_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> IRON_PLATING_STAIRS_ITEM = ITEMS.register("iron_plating_stairs", () -> new BlockItem(BlocksRegistry.IRON_PLATING_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> IRON_PLATING_SLAB_ITEM = ITEMS.register("iron_plating_slab", () -> new BlockItem(BlocksRegistry.IRON_PLATING_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CONGLOMORATE = ITEMS.register("conglomorate", () -> new BlockItem(BlocksRegistry.CONGLOMORATE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> POLISHED_CONGLOMORATE = ITEMS.register("polished_conglomorate", () -> new BlockItem(BlocksRegistry.POLISHED_CONGLOMORATE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CONGLOMORATE_STAIRS_ITEM = ITEMS.register("conglomorate_stairs", () -> new BlockItem(BlocksRegistry.CONGLOMORATE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> POLISHED_CONGLOMORATE_STAIRS_ITEM = ITEMS.register("polished_conglomorate_stairs", () -> new BlockItem(BlocksRegistry.POLISHED_CONGLOMORATE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CONGLOMORATE_SLAB_ITEM = ITEMS.register("conglomorate_slab", () -> new BlockItem(BlocksRegistry.CONGLOMORATE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> POLISHED_CONGLOMORATE_SLAB_ITEM = ITEMS.register("polished_conglomorate_slab", () -> new BlockItem(BlocksRegistry.POLISHED_CONGLOMORATE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> OXYGEN_DISTRIBUTOR = ITEMS.register("oxygen_distributor", () -> new BlockItem(BlocksRegistry.OXYGEN_DISTRIBUTOR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    //Globes
    public static final RegistrySupplier<Item> EARTH_GLOBE_ITEM = ITEMS.register("earth_globe", () -> new GlobeItem(BlocksRegistry.EARTH_GLOBE_BLOCK.get(), ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/block/globes/earth_globe.png"), new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MOON_GLOBE_ITEM = ITEMS.register("moon_globe", () -> new GlobeItem(BlocksRegistry.MOON_GLOBE_BLOCK.get(), ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/block/globes/moon_globe.png"), new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MARS_GLOBE_ITEM = ITEMS.register("mars_globe", () -> new GlobeItem(BlocksRegistry.MARS_GLOBE_BLOCK.get(), ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/block/globes/mars_globe.png"), new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_GLOBE_ITEM = ITEMS.register("mercury_globe", () -> new GlobeItem(BlocksRegistry.MERCURY_GLOBE_BLOCK.get(), ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/block/globes/mercury_globe.png"), new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> VENUS_GLOBE_ITEM = ITEMS.register("venus_globe", () -> new GlobeItem(BlocksRegistry.VENUS_GLOBE_BLOCK.get(), ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/block/globes/venus_globe.png"), new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    //RADIOACTIVITY
    public static final RegistrySupplier<Item> MERCURY_URANIUM_ORE_ITEM = ITEMS.register("mercury_uranium_ore", () -> new BlockItem(BlocksRegistry.MERCURY_URANIUM_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(0, true))));
    public static final RegistrySupplier<Item> URANIUM_BLOCK_ITEM = ITEMS.register("uranium_block", () -> new BlockItem(BlocksRegistry.URANIUM_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(0, true))));
    public static final RegistrySupplier<Item> RAW_URANIUM_BLOCK_ITEM = ITEMS.register("raw_uranium_block", () -> new BlockItem(BlocksRegistry.RAW_URANIUM_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(0, true))));
    public static final RegistrySupplier<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(0, false))));
    public static final RegistrySupplier<Item> RAW_URANIUM = ITEMS.register("raw_uranium", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(0, false))));
    public static final RegistrySupplier<Item> NEPTUNIUM_BLOCK = ITEMS.register("neptunium_block", () -> new BlockItem(BlocksRegistry.NEPTUNIUM_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(1, true))));
    public static final RegistrySupplier<Item> NEPTUNIUM_PIECE = ITEMS.register("neptunium_piece", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(1, false))));
    public static final RegistrySupplier<Item> RAW_NEPTUNIUM = ITEMS.register("raw_neptunium", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(1, false))));
    public static final RegistrySupplier<Item> NEPTUNIUM_INGOT = ITEMS.register("neptunium_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(1, false))));
    public static final RegistrySupplier<Item> PLUTONIUM_BLOCK = ITEMS.register("plutonium_block", () -> new BlockItem(BlocksRegistry.PLUTONIUM_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(2, true))));
    public static final RegistrySupplier<Item> PLUTONIUM_GRAIN = ITEMS.register("plutonium_grain", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(2, false))));
    public static final RegistrySupplier<Item> PLUTONIUM_PIECE = ITEMS.register("plutonium_piece", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(2, false))));
    public static final RegistrySupplier<Item> PLUTONIUM_NUGGET = ITEMS.register("plutonium_nugget", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(2, false))));
    public static final RegistrySupplier<Item> PLUTONIUM_INGOT = ITEMS.register("plutonium_ingot", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.RADIOACTIVE.get(), new RadioactiveComponent(2, false))));

    /**
     * Mars Items
     */

    public static final RegistrySupplier<Item> CHISELED_MARS_STONE_BRICKS = ITEMS.register("chiseled_mars_stone_bricks", () -> new BlockItem(BlocksRegistry.CHISELED_MARS_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CRACKED_MARS_STONE_BRICKS = ITEMS.register("cracked_mars_stone_bricks", () -> new BlockItem(BlocksRegistry.CRACKED_MARS_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_COBBLESONE = ITEMS.register("mars_cobblestone", () -> new BlockItem(BlocksRegistry.MARS_COBBLESONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_IRON_ORE = ITEMS.register("mars_iron_ore", () -> new BlockItem(BlocksRegistry.MARS_IRON_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_OSTRUM_ORE = ITEMS.register("mars_ostrum_ore", () -> new BlockItem(BlocksRegistry.MARS_OSTRUM_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_PLUTONIUM_ORE = ITEMS.register("mars_plutonium_ore", () -> new BlockItem(BlocksRegistry.MARS_PLUTONIUM_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_SAND = ITEMS.register("mars_sand", () -> new BlockItem(BlocksRegistry.MARS_SAND.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_STONE = ITEMS.register("mars_stone", () -> new BlockItem(BlocksRegistry.MARS_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_STONE_BRICKS = ITEMS.register("mars_stone_bricks", () -> new BlockItem(BlocksRegistry.MARS_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_PILLAR_ITEM = ITEMS.register("mars_pillar", () -> new BlockItem(BlocksRegistry.MARS_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> POLISHED_MARS_STONE = ITEMS.register("polished_mars_stone", () -> new BlockItem(BlocksRegistry.POLISHED_MARS_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_STONE_BRICK_STAIRS_ITEM = ITEMS.register("mars_stone_brick_stairs", () -> new BlockItem(BlocksRegistry.MARS_STONE_BRICK_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_POLISHED_STONE_STAIRS_ITEM = ITEMS.register("mars_polished_stone_stairs", () -> new BlockItem(BlocksRegistry.MARS_POLISHED_STONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_STONE_STAIRS_ITEM = ITEMS.register("mars_stone_stairs", () -> new BlockItem(BlocksRegistry.MARS_STONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_STONE_BRICK_SLAB_ITEM = ITEMS.register("mars_stone_brick_slab", () -> new BlockItem(BlocksRegistry.MARS_STONE_BRICK_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_POLISHED_STONE_SLAB_ITEM = ITEMS.register("mars_polished_stone_slab", () -> new BlockItem(BlocksRegistry.MARS_POLISHED_STONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_STONE_SLAB_ITEM = ITEMS.register("mars_stone_slab", () -> new BlockItem(BlocksRegistry.MARS_STONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_ICE_SHARD_ORE = ITEMS.register("mars_ice_shard_ore", () -> new BlockItem(BlocksRegistry.MARS_ICE_SHARD_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MARS_DIAMOND_ORE = ITEMS.register("mars_diamond_ore", () -> new BlockItem(BlocksRegistry.MARS_DIAMOND_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));


    /**
     * Venus Items
     */

    public static final RegistrySupplier<Item> CHISELED_VENUS_STONE_ITEM = ITEMS.register("chiseled_venus_stone", () -> new BlockItem(BlocksRegistry.CHISELED_VENUS_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CRACKED_VENUS_SANDSTONE_BRICKS_ITEM = ITEMS.register("cracked_venus_sandstone_bricks", () -> new BlockItem(BlocksRegistry.CRACKED_VENUS_SANDSTONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CRACKED_VENUS_STONE_BRICKS_ITEM = ITEMS.register("cracked_venus_stone_bricks", () -> new BlockItem(BlocksRegistry.CRACKED_VENUS_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> POLISHED_VENUS_STONE_ITEM = ITEMS.register("polished_venus_stone", () -> new BlockItem(BlocksRegistry.POLISHED_VENUS_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_COAL_ORE_ITEM_ITEM = ITEMS.register("venus_coal_ore", () -> new BlockItem(BlocksRegistry.VENUS_COAL_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_NEPTUNIUM_ORE_item = ITEMS.register("venus_neptunium_ore", () -> new BlockItem(BlocksRegistry.VENUS_NEPTUNIUM_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CHISELED_MOON_STONE_ITEM_ITEM = ITEMS.register("venus_cobblestone", () -> new BlockItem(BlocksRegistry.VENUS_COBBLESTONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_COBBLESTONE_ITEM = ITEMS.register("venus_gold_ore", () -> new BlockItem(BlocksRegistry.VENUS_GOLD_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SAND_ITEM = ITEMS.register("venus_sand", () -> new BlockItem(BlocksRegistry.VENUS_SAND.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SANDSTONE_ITEM = ITEMS.register("venus_sandstone", () -> new BlockItem(BlocksRegistry.VENUS_SANDSTONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SANDSTONE_BRICKS_ITEM = ITEMS.register("venus_sandstone_bricks", () -> new BlockItem(BlocksRegistry.VENUS_SANDSTONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_STONE_ITEM = ITEMS.register("venus_stone", () -> new BlockItem(BlocksRegistry.VENUS_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_STONE_BRICKS_ITEM = ITEMS.register("venus_stone_bricks", () -> new BlockItem(BlocksRegistry.VENUS_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_PILLAR_ITEM = ITEMS.register("venus_pillar", () -> new BlockItem(BlocksRegistry.VENUS_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SMOOTH_SANDSTONE_ITEM = ITEMS.register("venus_smooth_sandstone", () -> new BlockItem(BlocksRegistry.VENUS_SMOOTH_SANDSTONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SMOOTH_SANDSTONE_BRICK_ITEM = ITEMS.register("venus_smooth_sandstone_brick", () -> new BlockItem(BlocksRegistry.VENUS_SMOOTH_SANDSTONE_BRICK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_STONE_BRICK_STAIRS_ITEM = ITEMS.register("venus_stone_brick_stairs", () -> new BlockItem(BlocksRegistry.VENUS_STONE_BRICK_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_POLISHED_STONE_STAIRS_ITEM = ITEMS.register("venus_polished_stone_stairs", () -> new BlockItem(BlocksRegistry.VENUS_POLISHED_STONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SANDSTONE_BRICKS_STAIRS_ITEM = ITEMS.register("venus_sandstone_brick_stairs", () -> new BlockItem(BlocksRegistry.VENUS_SANDSTONE_BRICKS_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SMOOTH_SANDSTONE_BRICK_STAIRS_ITEM = ITEMS.register("venus_smooth_sandstone_brick_stairs", () -> new BlockItem(BlocksRegistry.VENUS_SMOOTH_SANDSTONE_BRICK_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SMOOTH_SANDSTONE_STAIRS_ITEM = ITEMS.register("venus_smooth_sandstone_stairs", () -> new BlockItem(BlocksRegistry.VENUS_SMOOTH_SANDSTONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_STONE_STAIRS_ITEM = ITEMS.register("venus_stone_stairs", () -> new BlockItem(BlocksRegistry.VENUS_STONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_STONE_BRICK_SLAB_ITEM = ITEMS.register("venus_stone_brick_slab", () -> new BlockItem(BlocksRegistry.VENUS_STONE_BRICK_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_POLISHED_STONE_SLAB_ITEM = ITEMS.register("venus_polished_stone_slab", () -> new BlockItem(BlocksRegistry.VENUS_POLISHED_STONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SANDSTONE_BRICK_SLAB_ITEM = ITEMS.register("venus_sandstone_brick_slab", () -> new BlockItem(BlocksRegistry.VENUS_SANDSTONE_BRICK_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SMOOTH_SANDSTONE_BRICK_SLAB_ITEM = ITEMS.register("venus_smooth_sandstone_brick_slab", () -> new BlockItem(BlocksRegistry.VENUS_SMOOTH_SANDSTONE_BRICK_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_SMOOTH_SANDSTONE_SLAB_ITEM = ITEMS.register("venus_smooth_sandstone_slab", () -> new BlockItem(BlocksRegistry.VENUS_SMOOTH_SANDSTONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_STONE_SLAB_ITEM = ITEMS.register("venus_stone_slab", () -> new BlockItem(BlocksRegistry.VENUS_STONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VENUS_DIAMOND_ORE = ITEMS.register("venus_diamond_ore", () -> new BlockItem(BlocksRegistry.VENUS_DIAMOND_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));


    //Desh
    public static final RegistrySupplier<Item> MOON_DESH_ORE_ITEM = ITEMS.register("moon_desh_ore", () -> new BlockItem(BlocksRegistry.MOON_DESH_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> DESH_BLOCK_ITEM = ITEMS.register("desh_block", () -> new BlockItem(BlocksRegistry.DESH_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> RAW_DESH_BLOCK_ITEM = ITEMS.register("raw_desh_block", () -> new BlockItem(BlocksRegistry.RAW_DESH_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> DESH_PLATING_BLOCK_ITEM = ITEMS.register("desh_plating_block", () -> new BlockItem(BlocksRegistry.DESH_PLATING_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> DESH_PILLAR_ITEM = ITEMS.register("desh_pillar", () -> new BlockItem(BlocksRegistry.DESH_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> DESH_PLATING_STAIRS_ITEM = ITEMS.register("desh_plating_stairs", () -> new BlockItem(BlocksRegistry.DESH_PLATING_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> DESH_PLATING_SLAB = ITEMS.register("desh_plating_slab", () -> new BlockItem(BlocksRegistry.DESH_plating_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));


    /**
     * Moon Items
     */

    public static final RegistrySupplier<Item> CHISELED_MOON_STONE_ITEM = ITEMS.register("chiseled_moon_stone", () -> new BlockItem(BlocksRegistry.CHISELED_MOON_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_COBBLESTONE_ITEM = ITEMS.register("moon_cobblestone", () -> new BlockItem(BlocksRegistry.MOON_COBBLESTONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_SAND_ITEM = ITEMS.register("moon_sand", () -> new BlockItem(BlocksRegistry.MOON_SAND.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_ITEM = ITEMS.register("moon_stone", () -> new BlockItem(BlocksRegistry.MOON_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_DEEPSLATE_ITEM = ITEMS.register("moon_deepslate", () -> new BlockItem(BlocksRegistry.MOON_DEEPSLATE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_DUST_ITEM = ITEMS.register("moon_stone_dust", () -> new BlockItem(BlocksRegistry.MOON_STONE_DUST.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CRACKED_MOON_STONE_BRICKS_ITEM = ITEMS.register("cracked_moon_stone_bricks", () -> new BlockItem(BlocksRegistry.CRACKED_MOON_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_BRICKS_ITEM = ITEMS.register("moon_stone_bricks", () -> new BlockItem(BlocksRegistry.MOON_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_PILLAR_ITEM = ITEMS.register("moon_stone_pillar", () -> new BlockItem(BlocksRegistry.MOON_STONE_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_PILLAR_ITEM = ITEMS.register("moon_pillar", () -> new BlockItem(BlocksRegistry.MOON_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> POLISHED_MOON_STONE_ITEM = ITEMS.register("polished_moon_stone", () -> new BlockItem(BlocksRegistry.POLISHED_MOON_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_BRICK_STAIRS_ITEM = ITEMS.register("moon_stone_brick_stairs", () -> new BlockItem(BlocksRegistry.MOON_STONE_BRICK_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_STAIRS_ITEM = ITEMS.register("moon_stone_stairs", () -> new BlockItem(BlocksRegistry.MOON_STONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_POLISHED_STONE_BRICK_STAIRS_ITEM = ITEMS.register("moon_polished_stone_brick_stairs", () -> new BlockItem(BlocksRegistry.MOON_POLISHED_STONE_BRICK_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_BRICK_SLAB_ITEM = ITEMS.register("moon_stone_brick_slab", () -> new BlockItem(BlocksRegistry.MOON_STONE_BRICK_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_STONE_SLAB_ITEM = ITEMS.register("moon_stone_slab", () -> new BlockItem(BlocksRegistry.MOON_STONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_POLISHED_STONE_BRICK_SLAB_ITEM = ITEMS.register("moon_polished_stone_brick_slab", () -> new BlockItem(BlocksRegistry.MOON_POLISHED_STONE_BRICK_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_ICE_SHARD_ORE = ITEMS.register("moon_ice_shard_ore", () -> new BlockItem(BlocksRegistry.MOON_ICE_SHARD_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_IRON_ORE = ITEMS.register("moon_iron_ore", () -> new BlockItem(BlocksRegistry.MOON_IRON_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MOON_BERRIES = ITEMS.register("moon_berries", () -> new ItemNameBlockItem(BlocksRegistry.MOON_VINES.get(), (new Item.Properties()).food(Foods.GLOW_BERRIES)));

    /**
     * Mercury
     */

    public static final RegistrySupplier<Item> MERCURY_COBBLESTONE_ITEM = ITEMS.register("mercury_cobblestone", () -> new BlockItem(BlocksRegistry.MERCURY_COBBLESTONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_IRON_ORE_ITEM = ITEMS.register("mercury_iron_ore", () -> new BlockItem(BlocksRegistry.MERCURY_IRON_ORE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_STONE_ITEM = ITEMS.register("mercury_stone", () -> new BlockItem(BlocksRegistry.MERCURY_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_STONE_BRICKS_ITEM = ITEMS.register("mercury_stone_bricks", () -> new BlockItem(BlocksRegistry.MERCURY_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_STONE_PILLAR_ITEM = ITEMS.register("mercury_stone_pillar", () -> new BlockItem(BlocksRegistry.MERCURY_STONE_PILLAR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CHISELED_MERCURY_STONE_ITEM = ITEMS.register("chiseled_mercury_stone", () -> new BlockItem(BlocksRegistry.CHISELED_MERCURY_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> CRACKED_MERCURY_STONE_BRICKS_ITEM = ITEMS.register("cracked_mercury_stone_bricks", () -> new BlockItem(BlocksRegistry.CRACKED_MERCURY_STONE_BRICKS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> POLISHED_MERCURY_STONE_ITEM = ITEMS.register("polished_mercury_stone", () -> new BlockItem(BlocksRegistry.POLISHED_MERCURY_STONE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_STONE_BRICK_STAIRS_ITEM = ITEMS.register("mercury_stone_brick_stairs", () -> new BlockItem(BlocksRegistry.MERCURY_STONE_BRICK_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_POLISHED_STONE_STAIRS_ITEM = ITEMS.register("mercury_polished_stone_stairs", () -> new BlockItem(BlocksRegistry.MERCURY_POLISHED_STONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_STONE_STAIRS_ITEM = ITEMS.register("mercury_stone_stairs", () -> new BlockItem(BlocksRegistry.MERCURY_STONE_STAIRS.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_STONE_BRICK_SLAB_ITEM = ITEMS.register("mercury_stone_brick_slab", () -> new BlockItem(BlocksRegistry.MERCURY_STONE_BRICK_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_POLISHED_STONE_SLAB_ITEM = ITEMS.register("marcury_polished_stone_slab", () -> new BlockItem(BlocksRegistry.MERCURY_POLISHED_STONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> MERCURY_STONE_SLAB_ITEM = ITEMS.register("marcury_stone_slab", () -> new BlockItem(BlocksRegistry.MERCURY_STONE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));

    public static final RegistrySupplier<Item> METEORITE_ITEM = ITEMS.register("meteorite", () -> new BlockItem(BlocksRegistry.METEORITE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));

    /**
     * Rocket Parts
     */

    public static final RegistrySupplier<Item> ROCKET_NOSE_CONE = ITEMS.register("rocket_nose_cone", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> ROCKET_FIN = ITEMS.register("rocket_fin", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> ROCKET_FAN = ITEMS.register("engine_fan", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> ROCKET_ENGINE = ITEMS.register("rocket_engine", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    /**
     * Machines
     */

    public static final RegistrySupplier<Item> ROCKET_STATION = ITEMS.register("rocket_station", () -> new BlockItem(BlocksRegistry.ROCKET_STATION.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> ROCKET_LAUNCH_PAD = ITEMS.register("rocket_launch_pad", () -> new BlockItem(BlocksRegistry.ROCKET_LAUNCH_PAD.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> SOLAR_PANEL = ITEMS.register("solar_panel", () -> new BlockItem(BlocksRegistry.SOLAR_PANEL.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> CABLE = ITEMS.register("cable", () -> new BlockItem(BlocksRegistry.CABLE.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> COAL_GENERATOR = ITEMS.register("coal_generator", () -> new BlockItem(BlocksRegistry.COAL_GENERATOR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> RADIOACTIVE_GENERATOR = ITEMS.register("radioactive_generator", () -> new BlockItem(BlocksRegistry.RADIOACTIVE_GENERATOR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> WATER_SEPARATOR = ITEMS.register("water_separator", () -> new BlockItem(BlocksRegistry.WATER_SEPARATOR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> FUEL_REFINERY = ITEMS.register("fuel_refinery", () -> new BlockItem(BlocksRegistry.FUEL_REFINERY.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> WATER_PUMP = ITEMS.register("water_pump", () -> new BlockItem(BlocksRegistry.WATER_PUMP.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> VACUMATOR = ITEMS.register("vacuumator", () -> new BlockItem(BlocksRegistry.VACUMATOR.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> PUMPJACK = ITEMS.register("pumpjack", () -> new BlockItem(BlocksRegistry.PUMPJACK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> UPGRADE_STATION = ITEMS.register("upgrade_station", () -> new BlockItem(BlocksRegistry.UPGRADE_STATION.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));


    /**
     * Mob Eggs
     */

    public static final RegistrySupplier<SpawnEggItem> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.ALIEN, 0xc4c4c4, 0xadadad, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<SpawnEggItem> ALIEN_ZOMBIE_SPAWN_EGG = ITEMS.register("alien_zombie_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.ALIEN_ZOMBIE, 0xc4c4c4, 0xadadad, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<SpawnEggItem> PYGRO_SPAWN_EGG = ITEMS.register("pygro_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.PYGRO, 0xc4c4c4, 0xadadad, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<SpawnEggItem> PYGRO_BRUTE_SPAWN_EGG = ITEMS.register("pygro_brute_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.PYGRO_BRUTE, 0xc4c4c4, 0xadadad, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<SpawnEggItem> MARTIAN_RAPTOR_SPAWN_EGG = ITEMS.register("martian_raptor_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.MARTIAN_RAPTOR, 0xc4c4c4, 0xadadad, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<SpawnEggItem> STAR_CRAWLER_SPAWN_EGG = ITEMS.register("star_crawler_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.STAR_CRAWLER, 0xc4c4c4, 0xadadad, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<SpawnEggItem> MOGLER_SPAWN_EGG = ITEMS.register("mogler_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.MOGLER, 0xc4c4c4, 0xadadad, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    /**
     * Bucket Items
     */
    public static final RegistrySupplier<Item> FUEL_BUCKET = ITEMS.register("fuel_bucket", () -> new ArchitecturyBucketItem(FluidRegistry.FUEL_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new ArchitecturyBucketItem(FluidRegistry.OIL_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> HYDROGEN_BUCKET = ITEMS.register("hydrogen_bucket", () -> new ArchitecturyBucketItem(FluidRegistry.HYDROGEN_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));


    public static final RegistrySupplier<Item> FLAG_ITEM = ITEMS.register("flag", () -> new BlockItem(BlocksRegistry.FLAG.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<Item> VERTICAL_MOON_SLAB_ITEM = ITEMS.register("vertical_moon_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_MOON_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_MARS_SLAB_ITEM = ITEMS.register("vertical_mars_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_MARS_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_VENUS_SLAB_ITEM = ITEMS.register("vertical_venus_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_VENUS_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_MERCURY_SLAB_ITEM = ITEMS.register("vertical_mercury_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_MERCURY_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));

    public static final RegistrySupplier<Item> VERTICAL_HEAVY_METAL_PLATE_SLAB = ITEMS.register("vertical_heavy_metal_plate_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_HEAVY_METAL_PLATE_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_STEEL_SLAB = ITEMS.register("vertical_steel_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_STEEL_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_STEEL_PLATING_SLAB = ITEMS.register("vertical_steel_plating_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_STEEL_PLATING_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_IRON_PLATING_SLAB = ITEMS.register("vertical_iron_plating_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_IRON_PLATING_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_DESH_PLATING_SLAB = ITEMS.register("vertical_desh_plating_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_DESH_PLATING_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));
    public static final RegistrySupplier<Item> VERTICAL_DESH_SLAB = ITEMS.register("vertical_desh_slab", () -> new BlockItem(BlocksRegistry.VERTICAL_DESH_SLAB.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_BLOCKS_TAB)));

    /**
     * Rocket
     */

    public static final RegistrySupplier<Item> ROCKET = ITEMS.register("rocket", () -> new RocketItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.ROCKET_COMPONENT.get(), new RocketComponent(SkinUpgrade.getBasic().getNameSpace(), ModelUpgrade.getBasic().getModel(), MotorUpgrade.getBasic().getFuelType().getSerializedName(), 0, GUISprites.FUEL_OVERLAY, TankUpgrade.getBasic().getTankCapacity()))));
    // Upgrade
    public static final RegistrySupplier<Item> FROSTY_ROCKET_SKIN = ITEMS.register("skin_frozy", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new SkinUpgrade(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/vehicle/rocket_skin/normal/frozy.png"))));
    public static final RegistrySupplier<Item> NORMAL_ROCKET_SKIN = ITEMS.register("skin_normal", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new SkinUpgrade(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/vehicle/rocket_skin/normal/standard.png"))));
    public static final RegistrySupplier<Item> GALAXY_ROCKET_SKIN = ITEMS.register("skin_galaxy", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new SkinUpgrade(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/vehicle/rocket_skin/normal/galaxy.png"))));
    public static final RegistrySupplier<Item> MILITARY_ROCKET_SKIN = ITEMS.register("skin_military", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new SkinUpgrade(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/vehicle/rocket_skin/normal/military.png"))));
    public static final RegistrySupplier<Item> RUSTY_ROCKET_SKIN = ITEMS.register("skin_rusty", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new SkinUpgrade(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/vehicle/rocket_skin/normal/rusty.png"))));

    public static final RegistrySupplier<Item> TINY_ROCKET_UPGRADE = ITEMS.register("tiny_rocket_upgrade", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new ModelUpgrade(RocketModel.TINY)));
    public static final RegistrySupplier<Item> SMALL_ROCKET_UPGRADE = ITEMS.register("small_rocket_upgrade", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new ModelUpgrade(RocketModel.SMALL)));
    public static final RegistrySupplier<Item> NORMAL_ROCKET_UPGRADE = ITEMS.register("normal_rocket_upgrade", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new ModelUpgrade(RocketModel.NORMAL)));
    public static final RegistrySupplier<Item> BIG_ROCKET_UPGRADE = ITEMS.register("big_rocket_upgrade", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new ModelUpgrade(RocketModel.BIG)));

    public static final RegistrySupplier<Item> BIG_FUEL_TANK_UPGRADE = ITEMS.register("big_fuel_tank_upgrade", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new TankUpgrade(5000)));

    public static final RegistrySupplier<Item> HYDROGEN_MOTOR_UPGRADE = ITEMS.register("hydrogen_motor", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new MotorUpgrade(FuelType.Type.HYDROGEN, GUISprites.HYDROGEN_OVERLAY)));
    public static final RegistrySupplier<Item> RADIOACTIVE_MOTOR_UPGRADE = ITEMS.register("radioactive_motor", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new MotorUpgrade(FuelType.Type.RADIOACTIVE, GUISprites.ENERGY_FULL)));

    public static final RegistrySupplier<Item> ROVER = ITEMS.register("rover", () -> new RoverItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.ROVER_COMPONENT.get(), new RoverComponent( MotorUpgrade.getBasic().getFuelType().getSerializedName(), 0, GUISprites.FUEL_OVERLAY, TankUpgrade.getBasic().getTankCapacity(), 1))));
    public static final RegistrySupplier<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new VehicleUpgradeItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB), new SpeedUpgrade(1.8f)));

    /**
     * Oxygen
     */

    public static final RegistrySupplier<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new OxygenTankItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(), new CappedLongComponent(0L, FluidTankHelper.convertFromNeoMb(3600)))));
    public static final RegistrySupplier<Item> BIG_OXYGEN_TANK = ITEMS.register("big_oxygen_tank", () -> new OxygenTankItem(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(), new CappedLongComponent(0L, FluidTankHelper.convertFromNeoMb(10800)))));

    /**
     * Suit
     */

    public static final RegistrySupplier<Item> JETSUIT_HELMET = ITEMS.register("jet_suit_helmet", () -> new CustomArmorItem(ArmorMaterialsRegistry.JET_SUIT, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> JETSUIT_SUIT = ITEMS.register("jet_suit_chestplate", () -> new com.st0x0ef.stellaris.common.items.armors.JetSuit.Suit(ArmorMaterialsRegistry.JET_SUIT, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(), new CappedLongComponent(0L, FluidTankHelper.convertFromNeoMb(10800))).component(DataComponentsRegistry.STORED_FUEL_COMPONENT.get(), new CappedLongComponent(0L, FluidTankHelper.convertFromNeoMb(1000))).component(DataComponentsRegistry.JET_SUIT_COMPONENT.get(), new JetSuitComponent(com.st0x0ef.stellaris.common.items.armors.JetSuit.ModeType.DISABLED))));
    public static final RegistrySupplier<Item> JETSUIT_LEGGINGS = ITEMS.register("jet_suit_leggings", () -> new CustomArmorItem(ArmorMaterialsRegistry.JET_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> JETSUIT_BOOTS = ITEMS.register("jet_suit_boots", () -> new CustomArmorItem(ArmorMaterialsRegistry.JET_SUIT, ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    public static final RegistrySupplier<Item> SPACESUIT_HELMET = ITEMS.register("space_suit_helmet", () -> new CustomArmorItem(ArmorMaterialsRegistry.SPACE_SUIT, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponents.DYED_COLOR, new DyedItemColor(16777215, true))));
    public static final RegistrySupplier<Item> SPACESUIT_SUIT = ITEMS.register("space_suit_chestplate", () -> new SpaceSuit(ArmorMaterialsRegistry.SPACE_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(), new CappedLongComponent(0L, FluidTankHelper.convertFromNeoMb(10800))).component(DataComponents.DYED_COLOR, new DyedItemColor(16777215, true))));
    public static final RegistrySupplier<Item> SPACESUIT_LEGGINGS = ITEMS.register("space_suit_leggings", () -> new CustomArmorItem(ArmorMaterialsRegistry.SPACE_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB).component(DataComponents.DYED_COLOR, new DyedItemColor(16777215, true))));
    public static final RegistrySupplier<Item> SPACESUIT_BOOTS = ITEMS.register("space_suit_boots", () -> new CustomArmorItem(ArmorMaterialsRegistry.SPACE_SUIT, ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    //Modules
    public static final RegistrySupplier<Item> BASE_MODULE_TIER_1 = ITEMS.register("base_module_tier_1", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> BASE_MODULE_TIER_2 = ITEMS.register("base_module_tier_2", () -> new Item(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));


    public static final RegistrySupplier<Item> MODULE_FUEL = ITEMS.register("fuel_module", () -> new FuelModule(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MODULE_AUTO_FEEDER = ITEMS.register("auto_feeder_module", () -> new AutoFeederModule(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MODULE_OIL_FINDER = ITEMS.register("oil_finder_module", () -> new OilFinderModule(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MODULE_GRAVITY_NORMALIZER = ITEMS.register("gravity_normalizer_module", () -> new GravityNormalizerModule(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> MODULE_JET = ITEMS.register("jet_module", () -> new JetModule(new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    /**
     * Tools
     */

    public static final RegistrySupplier<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(ToolsRegister.STEEL, (new Item.Properties()).attributes(SwordItem.createAttributes(ToolsRegister.STEEL, 3, -2.8F)).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> new HoeItem(ToolsRegister.STEEL, (new Item.Properties()).attributes(HoeItem.createAttributes(ToolsRegister.STEEL, -3.0F, -1.0F)).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> new PickaxeItem(ToolsRegister.STEEL, (new Item.Properties()).attributes(PickaxeItem.createAttributes(ToolsRegister.STEEL, 1.0F, -2.8F)).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> new ShovelItem(ToolsRegister.STEEL, (new Item.Properties()).attributes(ShovelItem.createAttributes(ToolsRegister.STEEL, 1.5F, -3.0F)).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(ToolsRegister.STEEL, (new Item.Properties()).attributes(AxeItem.createAttributes(ToolsRegister.STEEL, 6.0F, -3.1F)).arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));

    /**
     * Coal
     */

    public static final RegistrySupplier<Item> COAL_TORCH_ITEM = ITEMS.register("coal_torch", () -> new CoalTorchItem(BlocksRegistry.COAL_TORCH_BLOCK.get(), BlocksRegistry.WALL_COAL_TORCH_BLOCK.get(),new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));
    public static final RegistrySupplier<Item> COAL_LANTERN_ITEM = ITEMS.register("coal_lantern", () -> new BlockItem(BlocksRegistry.COAL_LANTERN_BLOCK.get(), new Item.Properties().arch$tab(CreativeTabsRegistry.STELLARIS_TAB)));


    public static ArrayList<ItemStack> fullItemsToAdd() {
        ArrayList<ItemStack> list = new ArrayList<>();

        ItemStack JET_SUIT_FULL = new ItemStack(ItemsRegistry.JETSUIT_SUIT);
        JET_SUIT_FULL.set(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(),
                new CappedLongComponent(FluidTankHelper.convertFromNeoMb(10800), FluidTankHelper.convertFromNeoMb(10800)));
        JET_SUIT_FULL.set(DataComponentsRegistry.STORED_FUEL_COMPONENT.get(),
                new CappedLongComponent(FluidTankHelper.convertFromNeoMb(1000), FluidTankHelper.convertFromNeoMb(1000)));
        JET_SUIT_FULL.set(DataComponentsRegistry.JET_SUIT_COMPONENT.get(),
                new JetSuitComponent(JetSuit.ModeType.DISABLED));
        list.add(JET_SUIT_FULL);

        ItemStack OXYGEN_TANK_FULL = new ItemStack(ItemsRegistry.OXYGEN_TANK);
        OXYGEN_TANK_FULL.set(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(), new CappedLongComponent(FluidTankHelper.convertFromNeoMb(3600), FluidTankHelper.convertFromNeoMb(3600)));
        list.add(OXYGEN_TANK_FULL);

        ItemStack BIG_OXYGEN_TANK_FULL = new ItemStack(ItemsRegistry.BIG_OXYGEN_TANK);
        BIG_OXYGEN_TANK_FULL.set(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get(), new CappedLongComponent(FluidTankHelper.convertFromNeoMb(10800), FluidTankHelper.convertFromNeoMb(10800)));
        list.add(BIG_OXYGEN_TANK_FULL);

        ItemStack ROCKET_FULL = new ItemStack(ItemsRegistry.ROCKET);
        ROCKET_FULL.set(DataComponentsRegistry.ROCKET_COMPONENT.get(), new RocketComponent(SkinUpgrade.getBasic().getNameSpace(), RocketModel.TINY, FuelType.Type.RADIOACTIVE.getSerializedName(), 5000, GUISprites.ENERGY_FULL, 5000));
        list.add(ROCKET_FULL);

        return list;
    }
}