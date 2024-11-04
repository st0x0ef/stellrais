package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends BaseEnergyBlockEntity {

    public CableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.CABLE_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public void tick() {
        this.distributeEnergy(null);
    }

    @Override
    public int getMaxCapacity() {
        return 1000;
    }
}
