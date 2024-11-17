package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.data.recipes.WaterSeparatorRecipe;
import com.st0x0ef.stellaris.common.data.recipes.input.FluidInput;
import com.st0x0ef.stellaris.common.menus.WaterSeparatorMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import com.st0x0ef.stellaris.common.systems.energy.impl.WrappedBlockEnergyContainer;
import dev.architectury.fluid.FluidStack;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class WaterSeparatorBlockEntity extends BaseEnergyContainerBlockEntity implements RecipeInput, WrappedFluidBlockEntity {

    private static final int TANK_CAPACITY = 3;

    public final FluidTank ingredientTank = new FluidTank("ingredientTank", TANK_CAPACITY);

    public final NonNullList<FluidTank> resultTanks = Util.make(NonNullList.createWithCapacity(2), list -> {
        //HYDROGEN
        list.add(0, new FluidTank("resultTank1", TANK_CAPACITY));

        //OXYGEN
        list.add(1, new FluidTank("resultTank2", TANK_CAPACITY));
    });

    private final RecipeManager.CachedCheck<FluidInput, WaterSeparatorRecipe> cachedCheck = RecipeManager.createCheck(RecipesRegistry.WATER_SEPERATOR_TYPE.get());

    public WaterSeparatorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WATER_SEPARATOR_ENTITY.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.water_separator");
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new WaterSeparatorMenu(containerId, inventory, this, this);
    }

    @Override
    public void tick() {
        for (int i = 0; i < 2; i++) {
            int slot = i + 2;
            FluidTank tank = resultTanks.get(i);
            FluidTankHelper.extractFluidToItem(this, tank, slot);
        }

        if (!FluidTankHelper.addFluidFromBucket(this, ingredientTank, 1, 0)) {
            FluidTankHelper.extractFluidToItem(this, ingredientTank, 1, 0);
        }

        Optional<RecipeHolder<WaterSeparatorRecipe>> recipeHolder = cachedCheck.getRecipeFor(new FluidInput(getLevel().getBlockEntity(getBlockPos()), getItems()), level);
        if (recipeHolder.isPresent()) {
            WaterSeparatorRecipe recipe = recipeHolder.get().value();
            WrappedBlockEnergyContainer energyContainer = getWrappedEnergyContainer();

            if (energyContainer.getStoredEnergy() >= recipe.energy()) {
                List<FluidStack> stacks = recipe.resultStacks();
                FluidStack stack1 = stacks.getFirst();
                FluidStack stack2 = stacks.get(1);
                FluidTank tank1 = resultTanks.getFirst();
                FluidTank tank2 = resultTanks.get(1);

                if ((tank1.isEmpty() || tank1.getStack().isFluidEqual(stack1)) && (tank2.isEmpty() || tank2.getStack().isFluidEqual(stack2))) {
                    if (tank1.getAmount() + stack1.getAmount() <= tank1.getMaxCapacity() && tank2.getAmount() + stack2.getAmount() <= tank2.getMaxCapacity()) {
                        energyContainer.extractEnergy(recipe.energy(), false);
                        ingredientTank.shrink(recipe.ingredientStack().getAmount());
                        FluidTankHelper.addToTank(tank1, stack1);
                        FluidTankHelper.addToTank(tank2, stack2);
                        setChanged();
                    }
                }
            }
        }

        if (!resultTanks.getLast().isEmpty()) {
            FluidTankHelper.transferFluidNearby(this, resultTanks.getLast());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ingredientTank.save(provider, tag);
        resultTanks.forEach(tank -> tank.save(provider, tag));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        ingredientTank.load(provider, tag);
        resultTanks.forEach(tank -> tank.load(provider, tag));
    }

    @Override
    protected int getMaxCapacity() {
        return 12000;
    }

    public FluidTank getIngredientTank() {
        return ingredientTank;
    }

    public NonNullList<FluidTank> getResultTanks() {
        return resultTanks;
    }

    @Override
    public int size() {
        return this.getContainerSize();
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{ingredientTank, resultTanks.getFirst(), resultTanks.getFirst()};
    }
}
