package com.st0x0ef.stellaris.common.compats.jei;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new RocketStationCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        ClientLevel level = Minecraft.getInstance().level;

        registry.addRecipes(RocketStationCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(RecipesRegistry.ROCKET_STATION_TYPE.get()).stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(ItemsRegistry.ROCKET_STATION.get().getDefaultInstance(), RocketStationCategory.RECIPE);
    }
}
