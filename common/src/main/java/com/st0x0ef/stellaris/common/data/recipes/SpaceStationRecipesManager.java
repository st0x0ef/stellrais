package com.st0x0ef.stellaris.common.data.recipes;

import com.google.gson.JsonElement;
import com.st0x0ef.stellaris.Stellaris;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpaceStationRecipesManager extends SimpleJsonResourceReloadListener {

    private static final List<SpaceStationRecipe> SPACE_STATION_RECIPES = new ArrayList<>();


    public SpaceStationRecipesManager() {
        super(Stellaris.GSON, "directory");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {

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
}
