package com.st0x0ef.stellaris.common.entities.mobs.cheese_boss;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class CheeseBossEntitySensor extends NearestLivingEntitySensor<CheeseBoss> {

    public CheeseBossEntitySensor() {
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.copyOf(Iterables.concat(super.requires(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    @Override
    protected void doTick(ServerLevel level, CheeseBoss entity) {
        super.doTick(level, entity);
        CheeseBossEntitySensor.getClosest(entity, arg -> arg.getType() == EntityType.PLAYER).or(() -> CheeseBossEntitySensor.getClosest(entity, arg -> arg.getType() != EntityType.PLAYER)).ifPresentOrElse(arg2 -> entity.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, arg2), () -> entity.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE));
    }

    private static Optional<LivingEntity> getClosest(CheeseBoss cheeseBoss, Predicate<LivingEntity> predicate) {
        return cheeseBoss.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).stream().flatMap(Collection::stream).filter(cheeseBoss::canTargetEntity).filter(predicate).findFirst();
    }
}
