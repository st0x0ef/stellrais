package com.st0x0ef.stellaris.common.oil;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.blocks.entities.machines.FluidTankHelper;
import com.st0x0ef.stellaris.common.config.CommonConfig;

import java.util.Random;

public class OilUtils {
    public static int getRandomOilLevel() {
        Random random = new Random();
        if (random.nextInt(0, Stellaris.CONFIG.oilConfig.chunkOilChance) == 0) {
            return FluidTankHelper.convertFromNeoMb(random.nextInt(10, 50) * 1000);
        }
        return 0;
    }
}
