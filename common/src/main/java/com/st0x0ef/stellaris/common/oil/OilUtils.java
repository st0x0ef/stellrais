package com.st0x0ef.stellaris.common.oil;

import java.util.Random;

public class OilUtils {
    public static int getRandomOilLevel() {
        Random random = new Random();
        if (random.nextInt(0, 16) == 0) {
            return random.nextInt(10, 50) * 1000;
        }

        return 0;
    }
}
