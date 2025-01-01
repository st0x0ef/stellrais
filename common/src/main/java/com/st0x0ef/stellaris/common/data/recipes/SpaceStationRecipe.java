package com.st0x0ef.stellaris.common.data.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data.planets.PlanetTextures;
import com.st0x0ef.stellaris.common.data.recipes.input.SpaceStationInput;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record SpaceStationRecipe (List<ItemStack> items, ResourceLocation location) {

    public static final Codec<SpaceStationRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.listOf().fieldOf("items").forGetter(SpaceStationRecipe::items),
            ResourceLocation.CODEC.fieldOf("location").forGetter(SpaceStationRecipe::location)
    ).apply(instance, SpaceStationRecipe::new));

    public static RegistryFriendlyByteBuf toBuffer(SpaceStationRecipe recipe, final RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(recipe.items.size());

        recipe.items.forEach(((item) -> {
            ItemStack.STREAM_CODEC.encode(buffer, item);
        }));
        buffer.writeResourceLocation(recipe.location);
        return buffer;

    }
    public static SpaceStationRecipe readFromBuffer(RegistryFriendlyByteBuf buffer) {
        ArrayList<ItemStack> planets = new ArrayList<>();

        int k = buffer.readInt();

        for (int i = 0; i < k; i++) {
            planets.add(ItemStack.STREAM_CODEC.decode(buffer));
        }
        return new SpaceStationRecipe(planets, buffer.readResourceLocation());
    }


    public boolean haveMaterials(Player player) {
        for (ItemStack item : items) {
            if (!player.getInventory().contains(item)) {
                return false;
            }
        }
        return true;
    }


    public MutableComponent getTooltip(Player player) {
        MutableComponent tooltip = Component.translatable("tooltip." + this.location.getNamespace() + ".space_station_recipe." + this.location.getPath());
        for (ItemStack item : items) {

            String name = "\n" + item.getHoverName().getString() + " x" + item.getCount() ;
            if (!player.getInventory().contains(item)) {
                tooltip.append(Component.literal(name).withStyle(ChatFormatting.RED));
            } else {
                tooltip.append(Component.literal(name).withStyle(ChatFormatting.GREEN));
            }
        }

        return tooltip;
    }


    public SpaceStationRecipesManager.SpaceStationRecipeState fromRecipe(Player player) {
        return new SpaceStationRecipesManager.SpaceStationRecipeState(this, this.getTooltip(player), this.haveMaterials(player));
    }

}
