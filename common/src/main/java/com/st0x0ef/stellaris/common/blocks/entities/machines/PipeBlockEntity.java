package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PipeBlockEntity extends BlockEntity implements FluidProvider.BLOCK, TickingBlockEntity {
    private final FluidStorage fluidTank = new FluidStorage(1, 1024) {
        @Override
        protected void onChange(int tank) {
            setChanged();
        }
    };

    public PipeBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.PIPE_ENTITY.get(), pos, blockState);
    }

    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return fluidTank;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fluidTank.save(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        fluidTank.load(tag, registries);
    }

    @Override
    public void tick() {
        //TODO distribute
    }
}
