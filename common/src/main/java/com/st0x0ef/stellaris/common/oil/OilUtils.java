package com.st0x0ef.stellaris.common.oil;

import com.st0x0ef.stellaris.common.utils.Utils;

import java.util.Random;

public class OilUtils {
    public static int getRandomOilLevel() {
        Random random = new Random();
        if (random.nextInt(0, 16) == 0) {
            return random.nextInt(10, 50) * 1000;
        }

        return 0;
    }

    public static int getOilLevelColor(int oilLevel) {
        if (oilLevel > 40000) {
            return Utils.getColorHexCode("green");
        } else if (oilLevel > 0) {
            return Utils.getColorHexCode("orange");
        } else {
            return Utils.getColorHexCode("red");
        }
    }
}
