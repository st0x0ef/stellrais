package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.capabilities.fluid.FluidTank;
import com.st0x0ef.stellaris.common.data.recipes.FuelRefineryRecipe;
import com.st0x0ef.stellaris.common.data.recipes.input.FluidInput;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
import com.st0x0ef.stellaris.common.menus.FuelRefineryMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import com.st0x0ef.stellaris.common.utils.FuelUtils;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FuelRefineryBlockEntity extends BaseEnergyContainerBlockEntity implements FluidProvider.BLOCK {

    private final FluidTank ingredientTank = new FluidTank(10000);
    private final FluidTank resultTank = new FluidTank(10000);
    private final RecipeManager.CachedCheck<FluidInput, FuelRefineryRecipe> cachedCheck = RecipeManager.createCheck(RecipesRegistry.FUEL_REFINERY_TYPE.get());

    public FuelRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FUEL_REFINERY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (getItem(2).getItem() instanceof JetSuit.Suit) {
            int fuel = FluidTankHelper.convertFromNeoMb(10);

            if (resultTank.getFluidValue() < fuel) {
                fuel = (int) resultTank.getFluidValue();
            }

            else if (FuelUtils.getFuel(getItem(2)) + fuel > JetSuit.MAX_FUEL_CAPACITY) {
                fuel = (int) (JetSuit.MAX_FUEL_CAPACITY - (int) FuelUtils.getFuel(getItem(2)));
            }

            if (FuelUtils.addFuel(getItem(2), fuel)) {
                resultTank.drain(FluidStack.create(FluidRegistry.FLOWING_FUEL.get(), fuel), false);
                this.setChanged();
            }
        } else {
            FluidTankHelper.extractFluidToItem(this, resultTank, 2, 3);
        }

        if (!FluidTankHelper.addFluidFromBucket(this, ingredientTank, 0, 1)) {
            FluidTankHelper.extractFluidToItem(this, ingredientTank, 0, 1);
        }

        Optional<RecipeHolder<FuelRefineryRecipe>> recipeHolder = cachedCheck.getRecipeFor(new FluidInput(getLevel().getBlockEntity(getBlockPos()), getItems()), level);
        if (recipeHolder.isPresent()) {
            FuelRefineryRecipe recipe = recipeHolder.get().value();

            if (energy.getEnergy() >= recipe.energy()) {
                FluidStack resultStack = recipe.resultStack();

                if (resultTank.getFluidStack().isEmpty() || resultTank.getFluidStack().isFluidEqual(resultStack)) {
                    if (resultTank.getFluidValue() + resultStack.getAmount() < resultTank.getMaxAmount()) {
                        energy.extract(recipe.energy(), false);
                        ingredientTank.drain(FluidStack.create(recipe.ingredientStack().getFluid(), recipe.ingredientStack().getAmount()), false);
                        FluidTankHelper.addToTank(resultTank, resultStack);
                        setChanged();
                    }
                }
            }
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.fuel_refinery");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new FuelRefineryMenu(containerId, inventory, this, this);
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        ingredientTank.load(provider, tag);
        resultTank.load(provider, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ingredientTank.save(provider, tag);
        resultTank.save(provider, tag);
    }

    public FluidTank getIngredientTank() {
        return ingredientTank;
    }

    public FluidTank getResultTank() {
        return resultTank;
    }


    @Override
    public @Nullable FluidTank getFluidTank(@Nullable Direction direction) {

        if(direction == null) {
            return resultTank;
        }

        return switch (direction) {
            case UP, WEST, SOUTH -> ingredientTank;
            case DOWN, EAST, NORTH -> resultTank;
        };
    }
}
