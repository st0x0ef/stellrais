package com.st0x0ef.stellaris.common.world;

import com.google.common.base.Suppliers;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeature {
    // OVERWORLD
    static final ResourceKey<ConfiguredFeature<?, ?>> STEEL_ORE_KEY = registerKey("steel_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STEEL_ORE_DEEPSLATE_ORE_KEY = registerKey("steel_ore_deepslate_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_OIL = registerKey("lake_oil");

    // MARS
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_DIAMOND_ORE_KEY = registerKey("mars_diamond_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_ICE_SHARD_ORE_KEY = registerKey("mars_ice_shard_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_IRON_ORE_KEY = registerKey("mars_iron_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_OSTRUM_ORE_KEY = registerKey("mars_ostrum_ore_key");

    // MERCURY
    public static final ResourceKey<ConfiguredFeature<?, ?>> MERCURY_IRON_ORE_KEY = registerKey("mercury_iron_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MERCURY_URANIUM_ORE_KEY = registerKey("mercury_uranium_ore_key");

    // MOON
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_CHEESE_ORE_KEY = registerKey("moon_cheese_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_DESH_ORE_KEY = registerKey("moon_desh_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_ICE_SHARD_ORE_KEY = registerKey("moon_ice_shard_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_IRON_ORE_KEY = registerKey("moon_iron_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_SOUL_SOIL_KEY = registerKey("moon_soul_soil_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_STEEL_ORE_KEY = registerKey("moon_steel_ore_key");

    // VENUS
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_CALORITE_ORE_KEY = registerKey("venus_calorite_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_COAL_ORE_KEY = registerKey("venus_coal_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_DIAMOND_ORE_KEY = registerKey("venus_diamond_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_GOLD_ORE_KEY = registerKey("venus_gold_ore_key");



    // MARS
    public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_DIAMOND_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MARS_STONE.get()), BlocksRegistry.MARS_DIAMOND_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_ICE_SHARD_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MARS_STONE.get()), BlocksRegistry.MARS_ICE_SHARD_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> STEEL_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.STONE), BlocksRegistry.STEEL_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> STEEL_ORE_DEEPSLATE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.DEEPSLATE), BlocksRegistry.DEEPSLATE_STEEL_ORE.get().defaultBlockState())));



    public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_IRON_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MARS_STONE.get()), BlocksRegistry.MARS_IRON_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_OSTRUM_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MARS_STONE.get()), BlocksRegistry.MARS_OSTRUM_ORE.get().defaultBlockState())));

    // MERCURY
    public static final Supplier<List<OreConfiguration.TargetBlockState>> MERCURY_IRON_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MERCURY_STONE.get()), BlocksRegistry.MERCURY_IRON_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> MERCURY_URANIUM_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MERCURY_STONE.get()), BlocksRegistry.MERCURY_URANIUM_ORE.get().defaultBlockState())));

    // MOON
    public static final Supplier<List<OreConfiguration.TargetBlockState>> MOON_DESH_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MOON_STONE.get()), BlocksRegistry.MOON_DESH_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MOON_ICE_SHARD_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MOON_STONE.get()), BlocksRegistry.MOON_ICE_SHARD_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MOON_IRON_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MOON_STONE.get()), BlocksRegistry.MOON_IRON_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> MOON_SOUL_SOIL_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MOON_STONE.get()), Blocks.SOUL_SOIL.defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> MOON_STEEL_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.MOON_STONE.get()), BlocksRegistry.MOON_STEEL_ORE.get().defaultBlockState())));


    // VENUS
    public static final Supplier<List<OreConfiguration.TargetBlockState>> VENUS_COAL_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.VENUS_STONE.get()), BlocksRegistry.VENUS_COAL_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> VENUS_DIAMOND_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.VENUS_STONE.get()), BlocksRegistry.VENUS_DIAMOND_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> VENUS_GOLD_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlocksRegistry.VENUS_STONE.get()), BlocksRegistry.VENUS_GOLD_ORE.get().defaultBlockState())));

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, STEEL_ORE_KEY, Feature.ORE, new OreConfiguration(STEEL_ORE_REPLACEABLES.get(), 9));
        register(context, STEEL_ORE_DEEPSLATE_ORE_KEY, Feature.ORE, new OreConfiguration(STEEL_ORE_DEEPSLATE_REPLACEABLES.get(), 6));

        // MARS
        register(context, MARS_DIAMOND_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_DIAMOND_ORE_REPLACEABLES.get(), 7));
        register(context, MARS_ICE_SHARD_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_ICE_SHARD_ORE_REPLACEABLES.get(), 10));
        register(context, MARS_IRON_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_IRON_ORE_REPLACEABLES.get(), 11));
        register(context, MARS_OSTRUM_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_OSTRUM_ORE_REPLACEABLES.get(), 8));

        // MERCURY
        register(context, MERCURY_IRON_ORE_KEY, Feature.ORE, new OreConfiguration(MERCURY_IRON_ORE_REPLACEABLES.get(), 8));
        register(context, MERCURY_URANIUM_ORE_KEY, Feature.ORE, new OreConfiguration(MERCURY_URANIUM_ORE_REPLACEABLES.get(), 4));

        // MOON
        register(context, MOON_DESH_ORE_KEY, Feature.ORE, new OreConfiguration(MOON_DESH_ORE_REPLACEABLES.get(), 9));
        register(context, MOON_ICE_SHARD_ORE_KEY, Feature.ORE, new OreConfiguration(MOON_ICE_SHARD_ORE_REPLACEABLES.get(), 10));
        register(context, MOON_IRON_ORE_KEY, Feature.ORE, new OreConfiguration(MOON_IRON_ORE_REPLACEABLES.get(), 11));
        register(context, MOON_SOUL_SOIL_KEY, Feature.ORE, new OreConfiguration(MOON_SOUL_SOIL_REPLACEABLES.get(), 60));
        register(context, MOON_STEEL_ORE_KEY, Feature.ORE, new OreConfiguration(MOON_STEEL_ORE_REPLACEABLES.get(), 9));

        // VENUS
        register(context, VENUS_COAL_ORE_KEY, Feature.ORE, new OreConfiguration(VENUS_COAL_ORE_REPLACEABLES.get(), 17));
        register(context, VENUS_DIAMOND_ORE_KEY, Feature.ORE, new OreConfiguration(VENUS_DIAMOND_ORE_REPLACEABLES.get(), 9));
        register(context, VENUS_GOLD_ORE_KEY, Feature.ORE, new OreConfiguration(VENUS_GOLD_ORE_REPLACEABLES.get(), 10));

        register(context, LAKE_OIL, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(BlocksRegistry.OIL_BLOCK.get().defaultBlockState()), BlockStateProvider.simple(Blocks.STONE.defaultBlockState())));
    }
}
