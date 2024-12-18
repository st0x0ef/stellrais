package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.UniversalFluidTank;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.capabilities.fluid.FluidTank;
import com.st0x0ef.stellaris.common.data.recipes.WaterSeparatorRecipe;
import com.st0x0ef.stellaris.common.data.recipes.input.FluidInput;
import com.st0x0ef.stellaris.common.menus.WaterSeparatorMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import dev.architectury.fluid.FluidStack;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class WaterSeparatorBlockEntity extends BaseEnergyContainerBlockEntity implements RecipeInput, FluidProvider.BLOCK {

    private static final int TANK_CAPACITY = 3;
    public final FluidTank ingredientTank = new FluidTank(10_000);
    public final NonNullList<FluidTank> resultTanks = Util.make(NonNullList.createWithCapacity(2), list -> {
        //HYDROGEN
        list.add(0, new FluidTank(10_000));
        //OXYGEN
        list.add(1, new FluidTank(10_000));
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

            if (energy.getEnergy() >= recipe.energy()) {
                List<FluidStack> stacks = recipe.resultStacks();
                FluidStack stack1 = stacks.getFirst();
                FluidStack stack2 = stacks.get(1);
                FluidTank tank1 = resultTanks.getFirst();
                FluidTank tank2 = resultTanks.get(1);

                if ((tank1.getFluidStack().isEmpty() || tank1.getFluidStack().isFluidEqual(stack1)) && (tank2.getFluidStack().isEmpty() || tank2.getFluidStack().isFluidEqual(stack2))) {

                    if (tank1.getFluidValue() + stack1.getAmount() <= tank1.getMaxAmount() && tank2.getFluidValue() + stack2.getAmount() <= tank2.getMaxAmount()) {
                        energy.extract(recipe.energy(), false);
                        ingredientTank.drainFluid(FluidStack.create(recipe.ingredientStack().getFluid(), recipe.ingredientStack().getAmount()), false);
                        FluidTankHelper.addToTank(tank1, stack1);
                        FluidTankHelper.addToTank(tank2, stack2);
                        setChanged();
                    }
                }
            }
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
    public @Nullable UniversalFluidTank getFluidTank(@Nullable Direction direction) {
        return null;
    }
}
