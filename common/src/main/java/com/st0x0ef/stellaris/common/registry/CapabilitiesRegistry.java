package com.st0x0ef.stellaris.common.registry;

import com.fej1fun.potentials.capabilities.Capabilities;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CapabilitiesRegistry {
    public static void init() {
        registerEnergyBlocks();
        registerEnergyBlockEntities();
    }

    static void registerEnergyBlocks() {
//        Capabilities.Energy.BLOCK.registerForBlock(BlocksRegistry.CABLE);
//        Capabilities.Energy.BLOCK.registerForBlock(BlocksRegistry.OXYGEN_DISTRIBUTOR);
//        Capabilities.Energy.BLOCK.registerForBlock(BlocksRegistry.WATER_SEPARATOR);
//        Capabilities.Energy.BLOCK.registerForBlock(BlocksRegistry.FUEL_REFINERY);
//        Capabilities.Energy.BLOCK.registerForBlock(BlocksRegistry.SOLAR_PANEL);
//        Capabilities.Energy.BLOCK.registerForBlock(BlocksRegistry.COAL_GENERATOR);
//        Capabilities.Energy.BLOCK.registerForBlock(BlocksRegistry.RADIOACTIVE_GENERATOR);
    }
    static void registerEnergyBlockEntities() {
        Capabilities.Energy.BLOCK.registerForBlockEntity(BlockEntityRegistry.SOLAR_PANEL);
        Capabilities.Energy.BLOCK.registerForBlockEntity(BlockEntityRegistry.CABLE_ENTITY);
        Capabilities.Energy.BLOCK.registerForBlockEntity(BlockEntityRegistry.COAL_GENERATOR);
        Capabilities.Energy.BLOCK.registerForBlockEntity(BlockEntityRegistry.RADIOACTIVE_GENERATOR);
        Capabilities.Energy.BLOCK.registerForBlockEntity(BlockEntityRegistry.OXYGEN_DISTRIBUTOR);
        Capabilities.Energy.BLOCK.registerForBlockEntity(BlockEntityRegistry.WATER_SEPARATOR_ENTITY);
        Capabilities.Energy.BLOCK.registerForBlockEntity(BlockEntityRegistry.FUEL_REFINERY);
    }

}
