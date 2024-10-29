package com.st0x0ef.stellaris.common.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data_components.*;
import com.st0x0ef.stellaris.common.data_components.OxygenComponent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;

public class DataComponentsRegistry {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Stellaris.MODID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<RocketComponent>> ROCKET_COMPONENT = DATA_COMPONENT_TYPE.register("rocket_component",
            () -> DataComponentType.<RocketComponent>builder().persistent(RocketComponent.CODEC).networkSynchronized(RocketComponent.STREAM_CODEC).build());

    public static final RegistrySupplier<DataComponentType<RoverComponent>> ROVER_COMPONENT = DATA_COMPONENT_TYPE.register("rover_component",
            () -> DataComponentType.<RoverComponent >builder().persistent(RoverComponent.CODEC).networkSynchronized(RoverComponent.STREAM_CODEC).build());

    public static final RegistrySupplier<DataComponentType<JetSuitComponent>> JET_SUIT_COMPONENT = DATA_COMPONENT_TYPE.register("jet_suit_component",
            () -> DataComponentType.<JetSuitComponent>builder().persistent(JetSuitComponent.CODEC).networkSynchronized(JetSuitComponent.STREAM_CODEC).build());

    public static final RegistrySupplier<DataComponentType<CappedLongComponent>> STORED_OXYGEN_COMPONENT = DATA_COMPONENT_TYPE.register("stored_oxygen",
            () -> DataComponentType.<CappedLongComponent>builder().persistent(CappedLongComponent.CODEC).networkSynchronized(CappedLongComponent.STREAM_CODEC).build());

    public static final RegistrySupplier<DataComponentType<RadioactiveComponent>> RADIOACTIVE = DATA_COMPONENT_TYPE.register("radioactive_component",
            () -> DataComponentType.<RadioactiveComponent>builder().persistent(RadioactiveComponent.CODEC).networkSynchronized(RadioactiveComponent.STREAM_CODEC).build());

    public static final RegistrySupplier<DataComponentType<SpaceSuitModules>> SPACE_SUIT_MODULES = DATA_COMPONENT_TYPE.register("space_suit_modules",
            () -> DataComponentType.<SpaceSuitModules>builder().persistent(SpaceSuitModules.CODEC).networkSynchronized(SpaceSuitModules.STREAM_CODEC).build());


    public static final RegistrySupplier<DataComponentType<CappedLongComponent>> STORED_FUEL_COMPONENT = DATA_COMPONENT_TYPE.register("stored_fuel",
            () -> DataComponentType.<CappedLongComponent>builder().persistent(CappedLongComponent.CODEC).networkSynchronized(CappedLongComponent.STREAM_CODEC).build());

}