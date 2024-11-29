package com.st0x0ef.stellaris.common.data.recipes.input;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record SpaceStationInput(Player entity, Inventory stack) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        if (slot > stack.getContainerSize()) throw new IllegalArgumentException("No item for index " + slot);
        return this.stack().getItem(slot);
    }

    @Override
    public int size() {
        return 4;
    }
}
