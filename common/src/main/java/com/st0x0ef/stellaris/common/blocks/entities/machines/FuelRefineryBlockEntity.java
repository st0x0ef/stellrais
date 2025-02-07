package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.data.recipes.FuelRefineryRecipe;
import com.st0x0ef.stellaris.common.data.recipes.input.FluidInput;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
import com.st0x0ef.stellaris.common.menus.FuelRefineryMenu;
import com.st0x0ef.stellaris.common.network.packets.SyncFluidPacket;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import com.st0x0ef.stellaris.common.utils.FuelUtils;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FilteredFluidStorage;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidStorage;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidUtil;
import dev.architectury.fluid.FluidStack;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FuelRefineryBlockEntity extends BaseEnergyContainerBlockEntity implements FluidProvider.BLOCK {

    private final FluidStorage inputTank;
    private final FluidStorage outputTank;

    private final RecipeManager.CachedCheck<FluidInput, FuelRefineryRecipe> cachedCheck = RecipeManager.createCheck(RecipesRegistry.FUEL_REFINERY_TYPE.get());

    public FuelRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FUEL_REFINERY.get(), pos, state);
        this.inputTank = new FilteredFluidStorage(1, 10000, 10000, 0, (n,fluidStack) -> fluidStack.getFluid().isSame(FluidRegistry.FLOWING_OIL.get())) {
            @Override
            protected void onChange(int i) {
                setChanged();
                if (level != null && level.getServer() != null && !level.getServer().getPlayerList().getPlayers().isEmpty() && !this.getFluidInTank(0).isEmpty())
                    NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(),
                            new SyncFluidPacket(this.getFluidInTank(0), 0, getBlockPos(), Direction.UP));
            }
        };
        this.outputTank = new FilteredFluidStorage(1, 10000, 0, 10000, (n,fluidStack) -> fluidStack.getFluid().isSame(FluidRegistry.FLOWING_FUEL.get())) {
            @Override
            protected void onChange(int i) {
                setChanged();
                if (level != null && level.getServer() != null && !level.getServer().getPlayerList().getPlayers().isEmpty() && !this.getFluidInTank(0).isEmpty())
                    NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(),
                            new SyncFluidPacket(this.getFluidInTank(0), 0, getBlockPos(), Direction.DOWN));
            }
        };
    }

    @Override
    public void tick() {
        if (getItem(2).getItem() instanceof JetSuit.Suit) {
            int fuel = 10;

            if (outputTank.getFluidValueInTank(0) < fuel) {
                fuel = (int) outputTank.getFluidValueInTank(0);
            }

            else if (FuelUtils.getFuel(getItem(2)) + fuel > JetSuit.MAX_FUEL_CAPACITY) {
                fuel = (int) (JetSuit.MAX_FUEL_CAPACITY - (int) FuelUtils.getFuel(getItem(2)));
            }

            if (FuelUtils.addFuel(getItem(2), fuel)) {
                outputTank.drain(FluidStack.create(FluidRegistry.FLOWING_FUEL.get(), fuel), false);
                this.setChanged();
            }
        } else {
            FluidUtil.moveFluidToItem(0, outputTank, getItem(3), 1000);
        }

        if (FluidUtil.moveFluidFromItem(0, getItem(0), inputTank, 1000)) {
            getItem(0).setCount(getItem(0).getCount() - 1);
            if (items.get(1).isEmpty()) {
                items.set(1, Items.BUCKET.getDefaultInstance());
            } else {
                items.get(1).grow(1);
            }
        } else {
            FluidUtil.moveFluidToItem(0, inputTank, getItem(1), 1000);
        }

        if (level == null) return;

        Optional<RecipeHolder<FuelRefineryRecipe>> recipeHolder = cachedCheck.getRecipeFor(new FluidInput(level.getBlockEntity(getBlockPos())), level);
        if (recipeHolder.isPresent()) {
            FuelRefineryRecipe recipe = recipeHolder.get().value();

            if (energyContainer.getEnergy() >= recipe.energy()) {
                FluidStack resultStack = recipe.resultStack();

                if (outputTank.getFluidInTank(0).isEmpty() || outputTank.getFluidInTank(0).isFluidEqual(resultStack)) {
                    if (outputTank.getFluidValueInTank(0) + resultStack.getAmount() < outputTank.getTankCapacity(0)) {
                        energyContainer.extract(recipe.energy(), false);
                        inputTank.drain(FluidStack.create(recipe.ingredientStack().getFluid(), recipe.ingredientStack().getAmount()), false);
                        outputTank.fill(resultStack, false);
                        setChanged();
                    }
                }
            }
        }
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("block.stellaris.fuel_refinery");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new FuelRefineryMenu(containerId, inventory, this, this);
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        inputTank.save(tag, provider, "input");
        outputTank.save(tag, provider, "output");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        inputTank.load(tag, provider, "input");
        outputTank.load(tag, provider, "output");
    }

    public FluidStorage getIngredientTank() {
        return inputTank;
    }
    public FluidStorage getResultTank() {
        return outputTank;
    }

    @Override
    public @Nullable FluidStorage getFluidTank(@Nullable Direction direction) {
        //TODO better directions
        if (direction == null) {
            return outputTank;
        }

        return switch (direction) {
            case UP, WEST, SOUTH -> inputTank;
            case DOWN, EAST, NORTH -> outputTank;
        };
    }
}
