package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.common.blocks.entities.machines.WrappedEnergyBlockEntity;
import com.st0x0ef.stellaris.common.systems.energy.EnergyApi;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class LookupApiRegistry {
    public static void registerEnergy() {

        registerToEnergyApi(BlocksRegistry.CABLE, BlockEntityRegistry.CABLE_ENTITY);
        registerToEnergyApi(BlocksRegistry.OXYGEN_DISTRIBUTOR, BlockEntityRegistry.OXYGEN_DISTRIBUTOR);
        registerToEnergyApi(BlocksRegistry.WATER_SEPARATOR, BlockEntityRegistry.WATER_SEPARATOR_ENTITY);
        registerToEnergyApi(BlocksRegistry.FUEL_REFINERY, BlockEntityRegistry.FUEL_REFINERY);
        registerToEnergyApi(BlocksRegistry.SOLAR_PANEL, BlockEntityRegistry.SOLAR_PANEL);
        registerToEnergyApi(BlocksRegistry.COAL_GENERATOR, BlockEntityRegistry.COAL_GENERATOR);
        registerToEnergyApi(BlocksRegistry.RADIOACTIVE_GENERATOR, BlockEntityRegistry.RADIOACTIVE_GENERATOR);
    }

    private static void registerToEnergyApi(Supplier<Block> energyBlock, Supplier<BlockEntityType<?>> energyBlockEntity) {
        EnergyApi.registerEnergyBlock(energyBlock, (level, blockPos, blockState, blockEntity, direction) -> {
            if(blockEntity instanceof WrappedEnergyBlockEntity blockEntity1) return blockEntity1.getWrappedEnergyContainer();
            return null;
        });
        EnergyApi.registerEnergyBlockEntity(energyBlockEntity, (level, blockPos, blockState, blockEntity, direction) -> {
            if(blockEntity instanceof WrappedEnergyBlockEntity blockEntity1) return blockEntity1.getWrappedEnergyContainer();
            return null;
        });
    }

}
