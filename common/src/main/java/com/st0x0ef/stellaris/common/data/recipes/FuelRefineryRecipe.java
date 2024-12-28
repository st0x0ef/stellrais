package com.st0x0ef.stellaris.common.data.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.blocks.entities.machines.FuelRefineryBlockEntity;
import com.st0x0ef.stellaris.common.data.recipes.input.FluidInput;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Optional;

import static com.st0x0ef.stellaris.common.data.recipes.WaterSeparatorRecipe.Serializer.convertFluidStack;

public record FuelRefineryRecipe(FluidStack ingredientStack, FluidStack resultStack, boolean isMb, long energy) implements Recipe<FluidInput> {
    @Override
    public boolean matches(FluidInput input, Level level) {
        FluidStack stack = ((FuelRefineryBlockEntity) input.entity()).getIngredientTank().getStack();
        return stack.isFluidEqual(ingredientStack) && stack.getAmount() >= ingredientStack.getAmount();
    }

    @Override
    public ItemStack assemble(FluidInput input, HolderLookup.Provider registries) {
        return null;
    }

    @Override
    public RecipeSerializer<? extends Recipe<FluidInput>> getSerializer() {
        return RecipesRegistry.FUEL_REFINERY_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<FluidInput>> getType() {
        return RecipesRegistry.FUEL_REFINERY_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return null;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static class Serializer implements RecipeSerializer<FuelRefineryRecipe> {

        private static final MapCodec<FuelRefineryRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                FluidStack.CODEC.fieldOf("ingredient").forGetter(FuelRefineryRecipe::ingredientStack),
                FluidStack.CODEC.fieldOf("result").forGetter(FuelRefineryRecipe::resultStack),
                Codec.BOOL.optionalFieldOf("isFluidMB").forGetter(recipe -> Optional.of(recipe.isMb)),
                Codec.LONG.fieldOf("energy").forGetter(FuelRefineryRecipe::energy)
        ).apply(instance, (ingredientStack, resultStack, isFluidMb, energy) -> {
            boolean isMb = isFluidMb.orElse(true);
            convertFluidStack(ingredientStack, isMb);
            convertFluidStack(resultStack, isMb);
            return new FuelRefineryRecipe(ingredientStack, resultStack, isMb, energy);
        }));

        private static final StreamCodec<RegistryFriendlyByteBuf, FuelRefineryRecipe> STREAM_CODEC = StreamCodec.of((buf, recipe) -> {
            recipe.ingredientStack().write(buf);
            recipe.resultStack().write(buf);
            buf.writeBoolean(recipe.isMb());
            buf.writeLong(recipe.energy());
        }, buf -> new FuelRefineryRecipe(FluidStack.read(buf), FluidStack.read(buf), buf.readBoolean(), buf.readLong()));

        @Override
        public MapCodec<FuelRefineryRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FuelRefineryRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
