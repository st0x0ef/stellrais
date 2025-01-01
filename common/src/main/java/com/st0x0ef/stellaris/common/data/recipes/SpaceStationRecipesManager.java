package com.st0x0ef.stellaris.common.data.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpaceStationRecipesManager extends SimpleJsonResourceReloadListener {

    public static final List<SpaceStationRecipe> SPACE_STATION_RECIPES = new ArrayList<>();


    public SpaceStationRecipesManager() {
        super(Stellaris.GSON, "space_stations");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        SPACE_STATION_RECIPES.clear();
        resourceLocationJsonElementMap.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "space_stations");
            SpaceStationRecipe recipe = SpaceStationRecipe.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            SPACE_STATION_RECIPES.add(recipe);
        });

    }

    public static RegistryFriendlyByteBuf toBuffer(List<SpaceStationRecipe> recipes, final RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(recipes.size());

        recipes.forEach(((recipe) -> {
            SpaceStationRecipe.toBuffer(recipe, buffer);
        }));

        return buffer;

    }
    public static List<SpaceStationRecipe> readFromBuffer(RegistryFriendlyByteBuf buffer) {
        List<SpaceStationRecipe> recipes = new ArrayList<>();

        int k = buffer.readInt();

        for (int i = 0; i < k; i++) {
            recipes.add(SpaceStationRecipe.readFromBuffer(buffer));
        }

        return recipes;
    }

    public static void addRecipes(List<SpaceStationRecipe> recipes) {
        SPACE_STATION_RECIPES.clear();
        SPACE_STATION_RECIPES.addAll(recipes);
    }

    public static MutableComponent getTotalTooltip(List<SpaceStationRecipeState> recipes) {
        MutableComponent tooltip = Component.empty();

        for (SpaceStationRecipeState recipe : recipes) {
            tooltip.append(recipe.tooltip + "\n");
        }
        return tooltip;
    }

    public static class SpaceStationRecipeState {

        public final SpaceStationRecipe recipe;
        public final boolean isUnlocked;
        public final MutableComponent tooltip;

        public SpaceStationRecipeState(SpaceStationRecipe recipe, MutableComponent tooltip, boolean isUnlocked) {
            this.recipe = recipe;
            this.isUnlocked = isUnlocked;
            this.tooltip = tooltip;
        }


    }
}
