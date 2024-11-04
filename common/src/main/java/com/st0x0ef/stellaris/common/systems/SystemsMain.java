package com.st0x0ef.stellaris.common.systems;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.st0x0ef.stellaris.Stellaris;
import earth.terrarium.common_storage_lib.data.DataManager;
import earth.terrarium.common_storage_lib.data.DataManagerRegistry;
import earth.terrarium.common_storage_lib.fluid.util.FluidStorageData;
import net.minecraft.network.codec.ByteBufCodecs;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SystemsMain {

    public static final DataManagerRegistry REGISTRY = new DataManagerRegistry(Stellaris.MODID);

    public static final DataManager<FluidStorageData> FLUID_CONTENTS = REGISTRY.builder(FluidStorageData.DEFAULT).serialize(FluidStorageData.CODEC).networkSerializer(FluidStorageData.NETWORK_CODEC).withDataComponent().copyOnDeath().buildAndRegister("fluids");
    public static final DataManager<Long> VALUE_CONTENT = REGISTRY.builder(() -> 0L).serialize(Codec.LONG).networkSerializer(ByteBufCodecs.VAR_LONG).withDataComponent().copyOnDeath().buildAndRegister("energy");



    //public static final Logger LOGGER = (Logger) Stellaris.LOG;

//    public static final RegistryHolder<ParticleType<?>> PARTICLES = new RegistryHolder<>(BuiltInRegistries.PARTICLE_TYPE, MOD_ID);
//
//    public static final Supplier<ParticleType<FluidParticleOptions>> FLUID_PARTICLE = PARTICLES.register("fluid", () -> new ParticleType<>(false, FluidParticleOptions.DESERIALIZER) {
//        @Override
//        public @NotNull Codec<FluidParticleOptions> codec() {
//            return FluidParticleOptions.CODEC;
//        }
//    });
//
//    public static void init() {
//        PARTICLES.initialize();
//    }

    public static <T, U> Map<T, U> finalizeRegistration(Map<Supplier<T>, U> unfinalized, @Nullable Map<T, U> finalized) {
        if (finalized == null) {
            Map<T, U> collected = unfinalized.entrySet().stream().map(entry -> Pair.of(entry.getKey().get(), entry.getValue())).collect(Collectors.toUnmodifiableMap(Pair::getFirst, Pair::getSecond));
            unfinalized.clear();
            return collected;
        }

        return finalized;
    }

}