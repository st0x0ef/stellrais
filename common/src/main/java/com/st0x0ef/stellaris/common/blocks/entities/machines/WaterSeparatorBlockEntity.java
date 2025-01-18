package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.data.recipes.WaterSeparatorRecipe;
import com.st0x0ef.stellaris.common.data.recipes.input.FluidInput;
import com.st0x0ef.stellaris.common.menus.WaterSeparatorMenu;
import com.st0x0ef.stellaris.common.network.packets.SyncFluidPacket;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.registry.RecipesRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FilteredFluidStorage;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidStorage;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidUtil;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.SingleFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.networking.NetworkManager;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WaterSeparatorBlockEntity extends BaseEnergyContainerBlockEntity implements FluidProvider.BLOCK {

    public static final int HYDROGEN_TANK = 0;
    public static final int OXYGEN_TANK = 1;

    public final SingleFluidStorage ingredientTank = new SingleFluidStorage(3000,3000,0) {
        @Override
        protected void onChange() {
            setChanged();
            if (level!=null && level.getServer()!=null)
                NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(),
                        new SyncFluidPacket(this.getFluidInTank(0), 0, getBlockPos(), null));
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }
    };
    public final FluidStorage resultTanks = new FilteredFluidStorage(2, 3000,0,3000, (tank, fluidStack) ->
            tank == HYDROGEN_TANK ? fluidStack.getFluid()== FluidRegistry.HYDROGEN_STILL.get() : fluidStack.getFluid()== FluidRegistry.OXYGEN_STILL.get()
    ) {
        @Override
        protected void onChange(int tank) {
            setChanged();
            if (level!=null && level.getServer()!=null)
                NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(),
                        new SyncFluidPacket(this.getFluidInTank(tank), tank, getBlockPos(), getBlockState().getValue(BlockStateProperties.FACING).getClockWise()));
        }
    };
    private final RecipeManager.CachedCheck<FluidInput, WaterSeparatorRecipe> cachedCheck = RecipeManager.createCheck(RecipesRegistry.WATER_SEPERATOR_TYPE.get());

    public WaterSeparatorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WATER_SEPARATOR_ENTITY.get(), pos, state);
    }

    @Override
    protected @NotNull Component getDefaultName() {
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
        for (int i = 0; i < 2; i++)
            FluidUtil.moveFluidToItem(i, resultTanks, items.get(i + 2), resultTanks.getTankCapacity(i));


//        if (!FluidUtil.addFluidFromBucket(this, ingredientTank, 1, 0)) {
//            FluidUtil.extractFluidToItem(this, ingredientTank, 1, 0);
//        }

        assert level != null;
        Optional<RecipeHolder<WaterSeparatorRecipe>> recipeHolder = cachedCheck.getRecipeFor(new FluidInput(level.getBlockEntity(getBlockPos()), getItems()), level);
        if (recipeHolder.isPresent()) {
            WaterSeparatorRecipe recipe = recipeHolder.get().value();

            if (energyContainer.getEnergy() >= recipe.energy()) {

                ingredientTank.drain(recipe.ingredientStack(), false);
                resultTanks.fillWithoutLimits(recipe.resultStacks().getFirst(), false);
                resultTanks.fillWithoutLimits(recipe.resultStacks().getLast(), false);

                energyContainer.extract(recipe.energy(), false);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ingredientTank.save(tag, provider, "ingredient");
        resultTanks.save(tag, provider, "result");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        ingredientTank.load(tag, provider, "ingredient");
        resultTanks.load(tag, provider, "result");
    }

    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        Direction facing = getBlockState().getValue(BlockStateProperties.FACING);
        if (facing.getCounterClockWise() == direction || facing.getClockWise() == direction)
            return resultTanks;
        return ingredientTank;
    }

    public SingleFluidStorage getIngredientTank() {
        return this.ingredientTank;
    }

    public FluidStorage getResultTanks() {
        return this.resultTanks;
    }
}
