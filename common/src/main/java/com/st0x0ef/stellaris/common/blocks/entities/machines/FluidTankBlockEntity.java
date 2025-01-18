package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.SingleFluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlockEntity extends BlockEntity implements FluidProvider.BLOCK {

    private final SingleFluidStorage fluidTank;

    public FluidTankBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }
    public FluidTankBlockEntity(BlockPos pos, BlockState state, long capacity) {
        super(BlockEntityRegistry.TANK.get(), pos, state);
        fluidTank = new SingleFluidStorage(capacity) {
            @Override
            protected void onChange() {
                setChanged();
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        fluidTank.save(tag, registries, "tank");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fluidTank.load(tag, registries, "tank");
    }

    @Override
    public @Nullable SingleFluidStorage getFluidTank(@Nullable Direction direction) {
        return fluidTank;
    }
}
