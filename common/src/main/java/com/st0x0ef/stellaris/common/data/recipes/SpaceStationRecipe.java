package com.st0x0ef.stellaris.common.data.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.data.recipes.input.SpaceStationInput;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SpaceStationRecipe implements Recipe<SpaceStationInput> {

    private final ItemStack output;
    private final List<Ingredient> recipeItems;
    public static RecipeType<SpaceStationRecipe> Type = RecipesRegistry.SPACE_STATION_TYPE.get();

    public SpaceStationRecipe(List<Ingredient> recipeItems, ItemStack output) {
        this.recipeItems = recipeItems;
        this.output = output;
    }

    @Override
    public boolean matches(SpaceStationInput container, Level level) {
        for (int i = 0; i < ((Player) container.entity()).getInventory().getContainerSize() - 1; i++) {
            if (!recipeItems.get(i).test(container.getItem(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(SpaceStationInput container, HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.createWithCapacity(this.recipeItems.size());
        list.addAll(this.recipeItems);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.SPACE_STATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type;
    }

    public static class Serializer implements RecipeSerializer<SpaceStationRecipe> {

        public static final MapCodec<SpaceStationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.listOf(1, 4).fieldOf("ingredients").forGetter(r -> r.recipeItems),
                ItemStack.CODEC.fieldOf("output").forGetter(r -> r.output)
        ).apply(instance, SpaceStationRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> INGREDIENT_LIST_STREAM_CODEC = ByteBufCodecs.collection(ArrayList::new, Ingredient.CONTENTS_STREAM_CODEC, 14);
        public static final StreamCodec<RegistryFriendlyByteBuf, SpaceStationRecipe> STREAM_CODEC = StreamCodec.of((buf, recipe) -> {
            INGREDIENT_LIST_STREAM_CODEC.encode(buf, recipe.recipeItems);
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }, buf -> new SpaceStationRecipe(INGREDIENT_LIST_STREAM_CODEC.decode(buf), ItemStack.STREAM_CODEC.decode(buf)));

        @Override
        public MapCodec<SpaceStationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpaceStationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
