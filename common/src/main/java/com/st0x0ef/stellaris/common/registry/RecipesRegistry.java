package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data.recipes.FuelRefineryRecipe;
import com.st0x0ef.stellaris.common.data.recipes.RocketStationRecipe;
import com.st0x0ef.stellaris.common.data.recipes.SpaceStationRecipe;
import com.st0x0ef.stellaris.common.data.recipes.WaterSeparatorRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipesRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Stellaris.MODID, Registries.RECIPE_TYPE);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Stellaris.MODID, Registries.RECIPE_SERIALIZER);


    public static final RegistrySupplier<RecipeType<RocketStationRecipe>> ROCKET_STATION_TYPE = RECIPE_TYPES.register("rocket_station",
            () -> new Type<>("rocket_station"));
    public static final RegistrySupplier<RecipeType<WaterSeparatorRecipe>> WATER_SEPERATOR_TYPE = RECIPE_TYPES.register("water_seperator",
            () -> new Type<>("water_seperator"));
    public static final RegistrySupplier<RecipeType<FuelRefineryRecipe>> FUEL_REFINERY_TYPE = RECIPE_TYPES.register("fuel_refinery",
            () -> new Type<>("fuel_refinery"));


    public static final RegistrySupplier<RecipeSerializer<RocketStationRecipe>> ROCKET_STATION = RECIPE_SERIALIZERS.register(
            "rocket_station",
            RocketStationRecipe.Serializer::new
    );
    public static final RegistrySupplier<RecipeSerializer<WaterSeparatorRecipe>> WATER_SEPERATOR_SERIALIZER = RECIPE_SERIALIZERS.register(
            "water_seperator",
            WaterSeparatorRecipe.Serializer::new
    );
    public static final RegistrySupplier<RecipeSerializer<FuelRefineryRecipe>> FUEL_REFINERY_SERIALIZER = RECIPE_SERIALIZERS.register(
            "fuel_refinery",
            FuelRefineryRecipe.Serializer::new
    );


    public static void register() {
        RECIPE_TYPES.register();
        RECIPE_SERIALIZERS.register();
    }

    public record Type<T extends Recipe<?>>(String id) implements RecipeType<T> {
        @Override
        public String toString() {
            return Stellaris.MODID + ":" + id;
        }
    }
}
