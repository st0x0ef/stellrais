package com.st0x0ef.stellaris.common.registry;

import com.fej1fun.potentials.capabilities.Capabilities;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CapabilitiesRegistry {
    public static void registerEnergy() {
        registerToEnergyApi(BlocksRegistry.CABLE);
        registerToEnergyApi(BlocksRegistry.OXYGEN_DISTRIBUTOR);
        registerToEnergyApi(BlocksRegistry.WATER_SEPARATOR);
        registerToEnergyApi(BlocksRegistry.FUEL_REFINERY);
        registerToEnergyApi(BlocksRegistry.SOLAR_PANEL);
        registerToEnergyApi(BlocksRegistry.COAL_GENERATOR);
        registerToEnergyApi(BlocksRegistry.RADIOACTIVE_GENERATOR);
    }

    private static void registerToEnergyApi(Supplier<Block> energyBlock) {
        Capabilities.Energy.BLOCK.registerForBlock(energyBlock);
    }

}
