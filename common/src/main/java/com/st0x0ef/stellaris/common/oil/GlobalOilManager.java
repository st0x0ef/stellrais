package com.st0x0ef.stellaris.common.oil;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class GlobalOilManager {
    private static final GlobalOilManager INSTANCE;
    private final Map<ResourceKey<Level>, OilData> dimensionManagers;

    static {
        INSTANCE = new GlobalOilManager();
    }

    private GlobalOilManager() {
        this.dimensionManagers = new HashMap<>();
    }

    public static GlobalOilManager getInstance() {
        return INSTANCE;
    }

    public OilData getOrCreateDimensionManager(ServerLevel level) {
        return dimensionManagers.computeIfAbsent(level.dimension(), l -> new OilData(level));
    }
}
