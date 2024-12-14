package com.st0x0ef.stellaris.common.compats.rei;


import com.st0x0ef.stellaris.common.data.recipes.RocketStationRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RocketStationDisplay extends BasicDisplay {

    public RocketStationDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public RocketStationDisplay(RecipeHolder<RocketStationRecipe> recipe) {
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getResultItem(null)))));
    }

    private static List<EntryIngredient> getInputList(RocketStationRecipe recipe){
        if (recipe==null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        recipe.getIngredients().forEach(ingredient -> list.add(EntryIngredients.ofIngredient(ingredient)));

        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return RocketStationCategory.ROCKET_CRAFTING;
    }
}
