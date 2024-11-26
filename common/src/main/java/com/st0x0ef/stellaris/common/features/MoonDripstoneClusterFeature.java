package com.st0x0ef.stellaris.common.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneClusterFeature;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

import java.util.Optional;
import java.util.OptionalInt;

public class MoonDripstoneClusterFeature extends DripstoneClusterFeature {
    public MoonDripstoneClusterFeature(Codec<DripstoneClusterConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DripstoneClusterConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();
        DripstoneClusterConfiguration dripstoneClusterConfiguration = context.config();
        RandomSource randomSource = context.random();
        if (!DripstoneUtils.isEmptyOrWater(worldGenLevel, blockPos)) {
            return false;
        } else {
            int i = dripstoneClusterConfiguration.height.sample(randomSource);
            float f = dripstoneClusterConfiguration.wetness.sample(randomSource);
            float g = dripstoneClusterConfiguration.density.sample(randomSource);
            int j = dripstoneClusterConfiguration.radius.sample(randomSource);
            int k = dripstoneClusterConfiguration.radius.sample(randomSource);

            for(int l = -j; l <= j; ++l) {
                for(int m = -k; m <= k; ++m) {
                    double d = this.getChanceOfStalagmiteOrStalactite(j, k, l, m, dripstoneClusterConfiguration);
                    BlockPos blockPos2 = blockPos.offset(l, 0, m);
                    this.placeColumn(worldGenLevel, randomSource, blockPos2, l, m, f, d, i, g, dripstoneClusterConfiguration);
                }
            }

            return true;
        }
    }


    private void placeColumn(WorldGenLevel level, RandomSource random, BlockPos pos, int x, int z, float wetness, double chance, int height, float density, DripstoneClusterConfiguration config) {
        Optional<Column> optional = Column.scan(level, pos, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater);
        if (optional.isPresent()) {
            OptionalInt optionalInt = optional.get().getCeiling();
            OptionalInt optionalInt2 = optional.get().getFloor();
            if (optionalInt.isPresent() || optionalInt2.isPresent()) {
                boolean bl = random.nextFloat() < wetness;
                Column column;
                if (bl && optionalInt2.isPresent() && this.canPlacePool(level, pos.atY(optionalInt2.getAsInt()))) {
                    int i = optionalInt2.getAsInt();
                    column = optional.get().withFloor(OptionalInt.of(i - 1));
                    level.setBlock(pos.atY(i), Blocks.AIR.defaultBlockState(), 2);
                } else {
                    column = optional.get();
                }

                OptionalInt optionalInt3 = column.getFloor();
                boolean bl2 = random.nextDouble() < chance;
                int l;
                int j;
                if (optionalInt.isPresent() && bl2 && !this.isLava(level, pos.atY(optionalInt.getAsInt()))) {
                    j = config.dripstoneBlockLayerThickness.sample(random);
                    this.replaceBlocksWithDripstoneBlocks(level, pos.atY(optionalInt.getAsInt()), j, Direction.UP);
                    int k;
                    if (optionalInt3.isPresent()) {
                        k = Math.min(height, optionalInt.getAsInt() - optionalInt3.getAsInt());
                    } else {
                        k = height;
                    }

                    l = this.getDripstoneHeight(random, x, z, density, k, config);
                } else {
                    l = 0;
                }

                boolean bl3 = random.nextDouble() < chance;
                int m;
                if (optionalInt3.isPresent() && bl3 && !this.isLava(level, pos.atY(optionalInt3.getAsInt()))) {
                    m = config.dripstoneBlockLayerThickness.sample(random);
                    this.replaceBlocksWithDripstoneBlocks(level, pos.atY(optionalInt3.getAsInt()), m, Direction.DOWN);
                    if (optionalInt.isPresent()) {
                        j = Math.max(0, l + Mth.randomBetweenInclusive(random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
                    } else {
                        j = this.getDripstoneHeight(random, x, z, density, height, config);
                    }
                } else {
                    j = 0;
                }

                int t;
                if (optionalInt.isPresent() && optionalInt3.isPresent() && optionalInt.getAsInt() - l <= optionalInt3.getAsInt() + j) {
                    int n = optionalInt3.getAsInt();
                    int o = optionalInt.getAsInt();
                    int p = Math.max(o - l, n + 1);
                    int q = Math.min(n + j, o - 1);
                    int r = Mth.randomBetweenInclusive(random, p, q + 1);
                    int s = r - 1;
                    m = o - r;
                    t = s - n;
                } else {
                    m = l;
                    t = j;
                }

                boolean bl4 = random.nextBoolean() && m > 0 && t > 0 && column.getHeight().isPresent() && m + t == column.getHeight().getAsInt();
                if (optionalInt.isPresent()) {
                    DripstoneUtils.growPointedDripstone(level, pos.atY(optionalInt.getAsInt() - 1), Direction.DOWN, m, bl4);
                }

                if (optionalInt3.isPresent()) {
                    DripstoneUtils.growPointedDripstone(level, pos.atY(optionalInt3.getAsInt() + 1), Direction.UP, t, bl4);
                }

            }
        }
    }

}
