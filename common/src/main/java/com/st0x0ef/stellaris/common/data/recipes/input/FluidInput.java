package com.st0x0ef.stellaris.common.data.recipes.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;

public record FluidInput(BlockEntity entity) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        // We need to return something... I know it's not the best solution, but it's just a placeholder
        return Items.BUCKET.getDefaultInstance();
    }

    @Override
    public int size() {
        return 4;
    }
}
