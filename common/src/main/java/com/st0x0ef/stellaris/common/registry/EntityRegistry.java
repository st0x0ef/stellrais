package com.st0x0ef.stellaris.common.registry;

import com.google.common.base.Suppliers;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.IceShardArrowEntity;
import com.st0x0ef.stellaris.common.entities.IceSpit;
import com.st0x0ef.stellaris.common.entities.mobs.*;
import com.st0x0ef.stellaris.common.entities.mobs.alien.Alien;
import com.st0x0ef.stellaris.common.entities.mobs.cheese_boss.CheeseBoss;
import com.st0x0ef.stellaris.common.entities.mobs.cheese_boss.CheeseBossEntitySensor;
import com.st0x0ef.stellaris.common.entities.mobs.cheese_boss.attack_entities.CheeseSpit;
import com.st0x0ef.stellaris.common.entities.mobs.pygro.Pygro;
import com.st0x0ef.stellaris.common.entities.mobs.pygro.PygroMobsSensor;
import com.st0x0ef.stellaris.common.entities.vehicles.LanderEntity;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import com.st0x0ef.stellaris.common.entities.vehicles.RoverEntity;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

//For everything related to entities
public class EntityRegistry {
    //Entity type

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Stellaris.MODID, Registries.ENTITY_TYPE);
    public static final RegistrySupplier<EntityType<Alien>> ALIEN = ENTITY_TYPE.register("alien",
            Suppliers.memoize(() -> EntityType.Builder.of(Alien::new, MobCategory.MONSTER).sized(0.75f, 2.5f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "alien").toString())));
    public static final RegistrySupplier<EntityType<AlienZombie>> ALIEN_ZOMBIE = ENTITY_TYPE.register("alien_zombie",
            Suppliers.memoize(() -> EntityType.Builder.of(AlienZombie::new, MobCategory.MONSTER).sized(0.6f, 2.4f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "alien_zombie").toString())));
    public static final RegistrySupplier<EntityType<MartianRaptor>> MARTIAN_RAPTOR = ENTITY_TYPE.register("martian_raptor",
            Suppliers.memoize(() -> EntityType.Builder.of(MartianRaptor::new, MobCategory.MONSTER).sized(0.75f, 2.0f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "martian_raptor").toString())));
    public static final RegistrySupplier<EntityType<Pygro>> PYGRO = ENTITY_TYPE.register("pygro",
            Suppliers.memoize(() -> EntityType.Builder.of(Pygro::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "pygro").toString())));
    public static final RegistrySupplier<EntityType<PygroBrute>> PYGRO_BRUTE = ENTITY_TYPE.register("pygro_brute",
            Suppliers.memoize(() -> EntityType.Builder.of(PygroBrute::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "pygro_brute").toString())));
    public static final RegistrySupplier<EntityType<Mogler>> MOGLER = ENTITY_TYPE.register("mogler",
            Suppliers.memoize(() -> EntityType.Builder.of(Mogler::new, MobCategory.MONSTER).sized(1.4f, 1.4f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "mogler").toString())));
    public static final RegistrySupplier<EntityType<StarCrawler>> STAR_CRAWLER = ENTITY_TYPE.register("star_crawler",
            Suppliers.memoize(() -> EntityType.Builder.of(StarCrawler::new, MobCategory.MONSTER).sized(1.3f, 1.0f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "star_crawler").toString())));
    public static final RegistrySupplier<EntityType<? extends IceSpit>> ICE_SPIT = ENTITY_TYPE.register("ice_spit",
            Suppliers.memoize(() -> EntityType.Builder.of(IceSpit::new, MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "ice_spit").toString())));
    public static final RegistrySupplier<EntityType<IceShardArrowEntity>> ICE_SHARD_ARROW = ENTITY_TYPE.register("ice_shard_arrow",
            Suppliers.memoize(() -> EntityType.Builder.<IceShardArrowEntity>of(IceShardArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "ice_shard_arrow").toString())));
    public static final RegistrySupplier<EntityType<CheeseBoss>> CHEESE_BOSS = ENTITY_TYPE.register("cheese_boss",
            Suppliers.memoize(() -> EntityType.Builder.of(CheeseBoss::new, MobCategory.MONSTER).sized(1.0f, 3.7f).eyeHeight(3.5f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "cheese_boss").toString())));
    public static final RegistrySupplier<EntityType<? extends CheeseSpit>> CHEESE_SPIT = ENTITY_TYPE.register("cheese_spit",
            Suppliers.memoize(() -> EntityType.Builder.of(CheeseSpit::new, MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "cheese_spit").toString())));
    /**
     * Vehicles
     */

    public static final RegistrySupplier<EntityType<RocketEntity>> TINY_ROCKET = ENTITY_TYPE.register("tiny_rocket", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC).sized(1.1f, 4.4f).fireImmune().build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "tiny_rocket").toString()));
    public static final RegistrySupplier<EntityType<RocketEntity>> SMALL_ROCKET = ENTITY_TYPE.register("small_rocket", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC).sized(0.8f, 3.2f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "small_rocket").toString()));
    public static final RegistrySupplier<EntityType<RocketEntity>> NORMAL_ROCKET = ENTITY_TYPE.register("normal_rocket", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC).sized(1.1f, 4.4f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "normal_rocket").toString()));
    public static final RegistrySupplier<EntityType<RocketEntity>> BIG_ROCKET = ENTITY_TYPE.register("big_rocket", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC).sized(1.1f, 4.4f).fireImmune().build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "big_rocket").toString()));

    public static final RegistrySupplier<EntityType<RoverEntity>> ROVER = ENTITY_TYPE.register("rover", () -> EntityType.Builder.of(RoverEntity::new, MobCategory.MISC).sized(2.7F,1.3F).fireImmune().build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "rover").toString()));

    public static final RegistrySupplier<EntityType<LanderEntity>> LANDER = ENTITY_TYPE.register("lander", () -> EntityType.Builder.<LanderEntity>of(LanderEntity::new, MobCategory.MISC).sized(2.5f, 1.0f).build(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "lander").toString()));

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
        attributes.accept(ALIEN,  Alien::setCustomAttributes);
        attributes.accept(ALIEN_ZOMBIE,  AlienZombie::setCustomAttributes);
        attributes.accept(MARTIAN_RAPTOR,  MartianRaptor::setCustomAttributes);
        attributes.accept(PYGRO_BRUTE, PygroBrute::setCustomAttributes);
        attributes.accept(PYGRO, Pygro::setCustomAttributes);
        attributes.accept(MOGLER, Mogler::setCustomAttributes);
        attributes.accept(STAR_CRAWLER, StarCrawler::setCustomAttributes);
        attributes.accept(CHEESE_BOSS, CheeseBoss::setCustomAttributes);
    }

    public static void registerSpawnPlacements() {
        SpawnPlacementsRegistry.register(ALIEN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Alien::checkMobSpawnRules);
        SpawnPlacementsRegistry.register(ALIEN_ZOMBIE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AlienZombie::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(MARTIAN_RAPTOR, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MartianRaptor::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(PYGRO_BRUTE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PygroBrute::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(PYGRO, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pygro::checkMonsterSpawnRules);
        SpawnPlacementsRegistry.register(MOGLER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mogler::checkMoglerSpawnRules);
        SpawnPlacementsRegistry.register(STAR_CRAWLER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StarCrawler::checkMonsterSpawnRules);
        //SpawnPlacementsRegistry.register(CHEESE_BOSS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CheeseBoss::checkMonsterSpawnRules);
    }

    //Entity Sensor
    public static final DeferredRegister<SensorType<?>> SENSOR = DeferredRegister.create(Stellaris.MODID, Registries.SENSOR_TYPE);
    public static final RegistrySupplier<SensorType<PygroMobsSensor>> PYGRO_SENSOR = SENSOR.register("pygro_sensor", () -> new SensorType<>(PygroMobsSensor::new));
    public static final RegistrySupplier<SensorType<CheeseBossEntitySensor>> CHEESE_BOSS_SENSOR = SENSOR.register("cheese_boss_sensor", () -> new SensorType<>(CheeseBossEntitySensor::new));
}
