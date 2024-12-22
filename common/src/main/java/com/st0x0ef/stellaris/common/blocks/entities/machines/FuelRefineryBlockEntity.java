package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.armors.JetSuit;
import com.st0x0ef.stellaris.common.data.recipes.FuelRefineryRecipe;
import com.st0x0ef.stellaris.common.data.recipes.input.FluidInput;
import com.st0x0ef.stellaris.common.menus.FuelRefineryMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import com.st0x0ef.stellaris.common.systems.energy.impl.WrappedBlockEnergyContainer;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class FuelRefineryBlockEntity extends BaseEnergyContainerBlockEntity implements WrappedFluidBlockEntity {

    private final FluidTank ingredientTank = new FluidTank("ingredientTank", 5);
    private final FluidTank resultTank = new FluidTank("resultTank", 5);
    private final RecipeManager.CachedCheck<FluidInput, FuelRefineryRecipe> cachedCheck = RecipeManager.createCheck(RecipesRegistry.FUEL_REFINERY_TYPE.get());

    public FuelRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FUEL_REFINERY.get(), pos, state);
    }

    @Override
    public void tick() {
        FluidTankHelper.extractFluidToItem(this, resultTank, 2, 3);
        addFuelToJetSuit();

        if (!FluidTankHelper.addFluidFromBucket(this, ingredientTank, 0, 1)) {
            FluidTankHelper.extractFluidToItem(this, ingredientTank, 0, 1);
        }

        Optional<RecipeHolder<FuelRefineryRecipe>> recipeHolder = cachedCheck.getRecipeFor(new FluidInput(getLevel().getBlockEntity(getBlockPos()), getItems()), (ServerLevel) level);
        if (recipeHolder.isPresent()) {
            FuelRefineryRecipe recipe = recipeHolder.get().value();
            WrappedBlockEnergyContainer energyContainer = getWrappedEnergyContainer();

            if (energyContainer.getStoredEnergy() >= recipe.energy()) {
                FluidStack resultStack = recipe.resultStack();

                if (resultTank.isEmpty() || resultTank.getStack().isFluidEqual(resultStack)) {
                    if (resultTank.getAmount() + resultStack.getAmount() < resultTank.getMaxCapacity()) {
                        energyContainer.extractEnergy(recipe.energy(), false);
                        ingredientTank.shrink(recipe.ingredientStack().getAmount());
                        FluidTankHelper.addToTank(resultTank, resultStack);
                        setChanged();
                    }
                }
            }
        }
    }

    public void addFuelToJetSuit() {
        if (this.level.isClientSide) return;

        if (getItem(2).getItem() instanceof JetSuit.Suit) {
            int fuel = 1000;

            if(resultTank.getAmount() < 1000) {
                fuel = (int) resultTank.getAmount();
            }
            if (JetSuit.Suit.getFuel(getItem(2)) + fuel > 243_000) {
                fuel = 243000 -  (int)JetSuit.Suit.getFuel(getItem(2));
            }

            JetSuit.Suit.addFuel(getItem(2), fuel);
            resultTank.grow(-fuel);

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
    protected int getMaxCapacity() {
        return 6000;
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
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{resultTank, ingredientTank};
    }
}
