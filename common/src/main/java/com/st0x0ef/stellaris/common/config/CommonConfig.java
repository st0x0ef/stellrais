package com.st0x0ef.stellaris.common.config;

import net.darkhax.pricklemc.common.api.annotations.Value;

public class CommonConfig {

    @Value(
            writeDefault = false
    )
    public Oil oilConfig = new Oil();

    public static class Oil {
        @Value(
                comment = "The chance of a biome to be a chunk with oil. The chance is 1 in X."
        )
        public int chunkOilChance = 16;


        @Value(
                comment = "The minimum amount of oil (bucket) in an oil chunk."
        )
        public int minOil = 10;
        @Value(
                comment = "The maximum amount of oil (bucket) in an oil chunk."
        )
        public int maxOil = 50;
    }

}
