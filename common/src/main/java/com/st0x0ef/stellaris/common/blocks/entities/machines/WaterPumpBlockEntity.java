package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidStorage;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class WaterPumpBlockEntity extends BaseEnergyBlockEntity implements FluidProvider.BLOCK {

    private static final int NEEDED_ENERGY = 100;
    private final FluidStorage waterTank = new FluidStorage(1, 2000) {
        @Override
        protected void onChange(int tank) {
            setChanged();
        }
    };

    public WaterPumpBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WATER_PUMP.get(), pos, state, 2000);
    }

    @Override
    public void tick() {
        if (energyContainer.getEnergy() < NEEDED_ENERGY) return;

        BlockPos belowPos = worldPosition.below();
        FluidState belowFluidState = level.getFluidState(belowPos);

        if (!(belowFluidState.is(Fluids.WATER) && belowFluidState.isSource())) return;

        BlockState belowState = level.getBlockState(belowPos);
        if (belowState.getBlock() instanceof BucketPickup bucketPickup) {
            if (!bucketPickup.pickupBlock(null, level, belowPos, belowState).isEmpty()) {
                waterTank.fill(FluidStack.create(Fluids.WATER, 1000), false);
                energyContainer.extract(NEEDED_ENERGY, false);
                setChanged();
            }
        }
        else if (waterTank.getFluidValueInTank(waterTank.getTanks()) + FluidTankHelper.BUCKET_AMOUNT <= waterTank.getTankCapacity(waterTank.getTanks())) {
            if (belowState.getBlock() instanceof BucketPickup bucketPickup) {
                if (!bucketPickup.pickupBlock(null, level, belowPos, belowState).isEmpty()) {
                    FluidTankHelper.addToTank(waterTank, FluidStack.create(Fluids.WATER, FluidTankHelper.BUCKET_AMOUNT));
                    energyContainer.extract(NEEDED_ENERGY, false);
                    setChanged();
                }
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        waterTank.load(tag, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        waterTank.save(tag, provider);
    }

    public FluidStorage getWaterTank() {
        return waterTank;
    }

    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return this.waterTank;
    }
}
