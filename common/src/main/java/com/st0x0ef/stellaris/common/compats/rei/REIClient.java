package com.st0x0ef.stellaris.common.compats.rei;

import com.st0x0ef.stellaris.common.data.recipes.RocketStationRecipe;
import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class REIClient implements REIClientPlugin{
    @Override
    public void registerCategories(CategoryRegistry registry) {
        //registry.add(new RocketStationCategory());

        //registry.addWorkstations(RocketStationCategory.ROCKET_CRAFTING, EntryStacks.of(BlocksRegistry.ROCKET_STATION.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        //registry.registerDisplayGenerator(RocketStationRecipe.class, RocketStationRecipe.Type, RocketStationDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        REIClientPlugin.super.registerScreens(registry);
    }
}
