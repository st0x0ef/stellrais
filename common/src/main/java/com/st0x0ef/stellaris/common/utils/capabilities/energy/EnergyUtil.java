package com.st0x0ef.stellaris.common.utils.capabilities.energy;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EnergyUtil {
    public static int moveEnergyToItem(EnergyStorage from, ItemStack stackTo, int amount) {
        UniversalEnergyStorage to = Capabilities.Energy.ITEM.getCapability(stackTo);
        if (to == null) return 0;
        return moveEnergy(from, to, amount);
    }

    public static int distributeEnergyNearby(Level level, BlockPos pos, int amount) {
        return distributeEnergyNearby(level, pos, amount, List.of());
    }

    public static int distributeEnergyNearby(Level level, BlockPos pos, int amount, List<Direction> outputDirections) {
        if (outputDirections==null || outputDirections.isEmpty()) return distributeInAllDirections(level, pos, amount);
        else return distributeInDirections(level, pos, amount, outputDirections);
    }

    private static int distributeInDirections(Level level, BlockPos pos, int amount, List<Direction> outputDirections) {
        Map<UniversalEnergyStorage, UniversalEnergyStorage> pairs = new HashMap<>();
        UniversalEnergyStorage from;
        UniversalEnergyStorage to;
        for (Direction direction : outputDirections) {
            from = Capabilities.Energy.BLOCK.getCapability(level, pos, direction);
            if (from==null) continue;
            if (!from.canExtractEnergy()) continue;
            if (!(from.extract(amount, true)>0)) continue;
            to = Capabilities.Energy.BLOCK.getCapability(level, pos, direction);
            if (to==null) continue;
            if (!to.canInsertEnergy()) continue;
            if (!(to.insert(amount, true)>0)) continue;
            pairs.put(from, to);
        }

        AtomicInteger toDistribute = new AtomicInteger(amount);
        AtomicInteger receivers = new AtomicInteger(pairs.size());
        pairs.forEach((energyFrom, energyTo) -> {
            toDistribute.addAndGet(-moveEnergy(energyFrom, energyTo, toDistribute.get() / receivers.get()));
            receivers.getAndDecrement();
        });
        return amount - toDistribute.get();
    }

    private static int distributeInAllDirections(Level level, BlockPos pos, int amount) {
        UniversalEnergyStorage from = Capabilities.Energy.BLOCK.getCapability(level, pos, null);
        if (from==null) return 0;
        if (!from.canExtractEnergy()) return 0;
        if (!(from.extract(amount, true) > 0)) return 0;

        List<UniversalEnergyStorage> toSend = Direction.stream()
                .map(direction -> Capabilities.Energy.BLOCK.getCapability(level, pos.relative(direction), direction.getOpposite()))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(energyStorage -> energyStorage.insert(amount, true)))
                .filter(UniversalEnergyStorage::canInsertEnergy)
                .toList();
        if (toSend.isEmpty()) return 0;

        int receivers = toSend.size();
        int toDistribute = amount;
        for (UniversalEnergyStorage to : toSend) {
            toDistribute -= moveEnergy(from, to, toDistribute/receivers);
            receivers--;
        }
        return amount - toDistribute;
    }


    public static int moveEnergy(UniversalEnergyStorage from, UniversalEnergyStorage to, int amount) {
        int inserted = to.insert(from.extract(amount, true), true);
        if (inserted > 0) {
            from.extract(inserted, false);
            to.insert(inserted, false);
        } else return 0;
        return inserted;
    }
}
