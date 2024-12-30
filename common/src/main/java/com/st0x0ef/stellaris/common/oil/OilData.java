package com.st0x0ef.stellaris.common.oil;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;

import java.util.HashMap;
import java.util.Map;

public class OilData {
    private static final Map<ChunkPos, Integer> oilLevels = new HashMap<>();

    public static int getOrCreateOilLevel(ChunkPos chunkPos, SerializableChunkData data) {
        if (!oilLevels.containsKey(chunkPos) && data != null) {
            if (data.write().contains("oilLevel")) {
                oilLevels.put(chunkPos, data.write().getInt("oilLevel"));
            } else {
                oilLevels.put(chunkPos, OilUtils.getRandomOilLevel());
                data.write().putInt("oilLevel", oilLevels.get(chunkPos));
            }
        }
        return oilLevels.getOrDefault(chunkPos, 0);
    }

    public static void setOilLevel(ChunkPos chunkPos, int oilLevel) {
        if (oilLevels.containsKey(chunkPos)) {
            oilLevels.replace(chunkPos, oilLevel);
        } else {
            oilLevels.put(chunkPos, oilLevel);
        }
    }
}
