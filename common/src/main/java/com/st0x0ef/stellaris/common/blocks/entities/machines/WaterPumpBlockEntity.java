package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.systems.energy.impl.WrappedBlockEnergyContainer;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterPumpBlockEntity extends BaseEnergyBlockEntity implements WrappedFluidBlockEntity{

    private static final long NEEDED_ENERGY = 100;
    private final FluidTank waterTank = new FluidTank("waterTank", 3);

    public WaterPumpBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WATER_PUMP.get(), pos, state);
    }

    @Override
    public int getMaxCapacity() {
        return 1000;
    }

    @Override
    public void tick() {
        WrappedBlockEnergyContainer energyContainer = getWrappedEnergyContainer();
        if (energyContainer.getStoredEnergy() >= NEEDED_ENERGY) {
            BlockPos belowPos = worldPosition.below();
            BlockState belowState = level.getBlockState(belowPos);
            FluidState belowFluidState = level.getFluidState(belowPos);

            if (belowFluidState.is(Fluids.WATER) && belowFluidState.isSource()) {
                if (waterTank.isEmpty()) {
                    if (belowState.getBlock() instanceof BucketPickup bucketPickup) {
                        if (!bucketPickup.pickupBlock(null, level, belowPos, belowState).isEmpty()) {
                            waterTank.setFluid(Fluids.WATER, FluidTankHelper.BUCKET_AMOUNT);
                            energyContainer.extractEnergy(NEEDED_ENERGY, false);
                            setChanged();
                        }
                    }
                }

                else if (waterTank.getAmount() + FluidTankHelper.BUCKET_AMOUNT <= waterTank.getMaxCapacity()) {
                    if (belowState.getBlock() instanceof BucketPickup bucketPickup) {
                        if (!bucketPickup.pickupBlock(null, level, belowPos, belowState).isEmpty()) {
                            FluidTankHelper.addToTank(waterTank, FluidStack.create(Fluids.WATER, FluidTankHelper.BUCKET_AMOUNT));
                            energyContainer.extractEnergy(NEEDED_ENERGY, false);
                            setChanged();
                        }
                    }
                }
            }
        }

        FluidTankHelper.transferFluidNearby(this, waterTank);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        waterTank.load(provider, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        waterTank.save(provider, tag);
    }

    public FluidTank getWaterTank() {
        return waterTank;
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{waterTank};
    }
}
