package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.energy.EnergyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends BaseEnergyBlockEntity {

    public CableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.CABLE_ENTITY.get(), blockPos, blockState, 1000);
    }

    @Override
    public void tick() {
        EnergyUtil.distributeEnergyNearby(level, worldPosition, 1000);
    }
}
