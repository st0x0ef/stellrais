package com.st0x0ef.stellaris.common.oil;

import com.st0x0ef.stellaris.common.blocks.entities.machines.FluidTankHelper;
import com.st0x0ef.stellaris.common.oxygen.OxygenSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OilData {
    private final Map<ChunkPos, Integer> oilLevels = new HashMap<>();
    private final ServerLevel level;

    public OilData(ServerLevel level) {
        this.level = level;
    }

    public int getOrCreateOilLevel(ChunkPos chunkPos) {
        if (!oilLevels.containsKey(chunkPos)) {
            oilLevels.put(chunkPos, getRandomOilLevel());
            setChanged();
        }

        return oilLevels.get(chunkPos);
    }

    public void setOilLevel(ChunkPos chunkPos, int oilLevel) {
        if (oilLevels.containsKey(chunkPos)) {
            oilLevels.replace(chunkPos, oilLevel);
        } else {
            oilLevels.put(chunkPos, oilLevel);
        }

        this.setChanged();
    }

    public void setOilLevels(Map<ChunkPos, Integer> oilLevels) {
        this.oilLevels.clear();
        this.oilLevels.putAll(oilLevels);
    }

    public Map<ChunkPos, Integer> getOilLevels() {
        return oilLevels;
    }

    private void setChanged() {
        OxygenSavedData data = OxygenSavedData.getData(level);
        data.setDirty();
    }

    public int getRandomOilLevel() {
        Random random = new Random();
        if (random.nextInt(0, 16) == 0) {
            return FluidTankHelper.convertFromNeoMb(random.nextInt(10, 50) * 1000);
        }

        return 0;
    }
}
