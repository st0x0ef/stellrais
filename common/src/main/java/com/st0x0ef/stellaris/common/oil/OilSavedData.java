package com.st0x0ef.stellaris.common.oil;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public final  class OilSavedData extends SavedData {

    private final Map<ChunkPos, Integer> oilLevels;
    private final ServerLevel level;

    public static Factory<OilSavedData> factory(ServerLevel level) {
        return new Factory<>(() -> new OilSavedData(level), (compoundTag, provider) -> load(compoundTag, level), DataFixTypes.LEVEL);
    }

    public static OilSavedData getData(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(factory(level), "oil-levels");
    }

    public OilSavedData(ServerLevel level) {
        this.oilLevels = new HashMap<>();
        this.level = level;
        this.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        OilData data = GlobalOilManager.getInstance().getOrCreateDimensionManager(this.level);

        int i = 0;

        if (data.getOilLevels() != null && !data.getOilLevels().isEmpty()) {
            for (ChunkPos pos : data.getOilLevels().keySet()) {
                tag.putIntArray("oilLevel" + i, new int[]{pos.x, pos.z, data.getOilLevels().get(pos)});
            }
        }

        tag.putInt("i", i);

        return tag;
    }

    public static OilSavedData load(CompoundTag tag, ServerLevel level) {
        OilSavedData data = new OilSavedData(level);

        int i = tag.getInt("i");

        if (i == 0) {
            return data;
        }

        for (int x = 0; x < i; x++) {
            int[] oilLevelData = tag.getIntArray("oilLevel" + i);
            data.oilLevels.put(new ChunkPos(oilLevelData[0], oilLevelData[1]), oilLevelData[2]);
        }

        GlobalOilManager.getInstance().getOrCreateDimensionManager(level).setOilLevels(data.oilLevels);

        return data;
    }
}
