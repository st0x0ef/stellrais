package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.blocks.machines.CoalGeneratorBlock;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidTank;
import com.st0x0ef.stellaris.common.menus.PumpjackMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.Nullable;

public class PumpjackBlockEntity extends BaseEnergyContainerBlockEntity implements FluidProvider.BLOCK {

    private boolean isGenerating = false;
    private final long oilToExtract = FluidTankHelper.convertFromNeoMb(10);
    public final FluidTank resultTank = new FluidTank(10_000);
    public int chunkOilLevel = 0;
    public PumpjackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PUMPJACK.get(), pos, state);
    }


    @Override
    public void tick() {
        FluidTankHelper.extractFluidToItem(this, resultTank, 0, 1);

        ChunkAccess access = this.level.getChunk(this.worldPosition);

        chunkOilLevel = access.stellaris$getChunkOilLevel();


        int actualOilToExtract = (int) oilToExtract;

        if (access.stellaris$getChunkOilLevel() < oilToExtract) {
            actualOilToExtract = access.stellaris$getChunkOilLevel();

            if (actualOilToExtract == 0) return;
        }

        if (energy.getEnergy() >= 2 * actualOilToExtract) {
            if (resultTank.getFluidValue() + actualOilToExtract <= resultTank.getMaxAmount()) {
                access.stellaris$setChunkOilLevel(access.stellaris$getChunkOilLevel() - actualOilToExtract);
                resultTank.fill(FluidStack.create(FluidRegistry.OIL_ATTRIBUTES.getSourceFluid(), actualOilToExtract), false);

                energy.extract(2 * actualOilToExtract, false);
                isGenerating = true;
                setChanged();
            } else {
                isGenerating = false;
            }
        }

        if (isGenerating) {
            BlockState state = getBlockState().setValue(CoalGeneratorBlock.LIT, true);
            level.setBlock(getBlockPos(), state, 3);
        } else {
            BlockState state = getBlockState().setValue(CoalGeneratorBlock.LIT, false);
            level.setBlock(getBlockPos(), state, 3);
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.pumpjack");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new PumpjackMenu(containerId, inventory, this,  this);
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        resultTank.load(provider, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        resultTank.save(provider, tag);
    }

    public FluidTank getResultTank() {
        return resultTank;
    }

    public int chunkOilLevel() {
        return chunkOilLevel;
    }


    @Override
    public @Nullable FluidTank getFluidTank(@Nullable Direction direction) {
        return this.resultTank;
    }
}