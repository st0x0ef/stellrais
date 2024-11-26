package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.blocks.machines.CoalGeneratorBlock;
import com.st0x0ef.stellaris.common.menus.PumpjackMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.systems.energy.impl.WrappedBlockEnergyContainer;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class PumpjackBlockEntity extends BaseEnergyContainerBlockEntity implements WrappedFluidBlockEntity{

    private boolean isGenerating = false;
    private final long oilToExtract = FluidTankHelper.convertFromNeoMb(10);
    public final FluidTank resultTank = new FluidTank("resultTank", 5);
    public int chunkOilLevel = 0;
    public PumpjackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PUMPJACK.get(), pos, state);
    }


    @Override
    public void tick() {
        FluidTankHelper.extractFluidToItem(this, resultTank, 0, 1);

        ChunkAccess access = this.level.getChunk(this.worldPosition);

        chunkOilLevel = access.stellaris$getChunkOilLevel();

        WrappedBlockEnergyContainer energyContainer = getWrappedEnergyContainer();

        int actualOilToExtract = (int) oilToExtract;

        if (access.stellaris$getChunkOilLevel() < oilToExtract) {
            actualOilToExtract = access.stellaris$getChunkOilLevel();

            if (actualOilToExtract == 0) return;
        }

        if (energyContainer.getStoredEnergy() >= 2L * actualOilToExtract) {
            if (resultTank.getAmount() + actualOilToExtract <= resultTank.getMaxCapacity()) {
                access.stellaris$setChunkOilLevel(access.stellaris$getChunkOilLevel() - actualOilToExtract);
                FluidStack tankStack = resultTank.getStack();

                if (tankStack.isEmpty()) {
                    resultTank.setFluid(FluidRegistry.OIL_ATTRIBUTES.getSourceFluid(), actualOilToExtract);
                } else {
                    resultTank.grow(actualOilToExtract);
                }


                energyContainer.extractEnergy(2L * actualOilToExtract, false);
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
    protected int getMaxCapacity() {
        return 6000;
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
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{resultTank};
    }
}