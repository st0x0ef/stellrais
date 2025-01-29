package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundRegistry {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Stellaris.MODID,Registries.SOUND_EVENT);

    /** SOUNDS */
    public static final RegistrySupplier<SoundEvent> ROCKET_SOUND = SOUNDS.register("rocket_fly",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "rocket_fly")));
    public static final RegistrySupplier<SoundEvent> BOOST_SOUND = SOUNDS.register("boost",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "boost")));
    public static final RegistrySupplier<SoundEvent> BEEP_SOUND = SOUNDS.register("beep",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "beep")));

    public static final RegistrySupplier<SoundEvent> RADIOACTIVE = SOUNDS.register("radioactive", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "radioactive")));
    public static final RegistrySupplier<SoundEvent> SONIC_BOOM_SOUND = SOUNDS.register("sonic_boom",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "sonic_boom")));
}
