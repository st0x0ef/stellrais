package com.st0x0ef.stellaris.common.data.recipes.input;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;

public record RocketStationInput(BlockEntity entity, NonNullList<ItemStack> stack) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        if (slot > stack.size()) throw new IllegalArgumentException("No item for index " + slot);
        return this.stack().get(slot);
    }

    @Override
    public int size() {
        return 15;
    }
}
