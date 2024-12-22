package com.st0x0ef.stellaris.common.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import org.jetbrains.annotations.Nullable;


public class InfernalSpireColumn extends Feature<ColumnFeatureConfiguration> {

    private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);

    public InfernalSpireColumn(Codec<ColumnFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    public boolean place(FeaturePlaceContext<ColumnFeatureConfiguration> context) {
        int seaLevel = context.chunkGenerator().getSeaLevel();
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        RandomSource random = context.random();
        ColumnFeatureConfiguration columnfeatureconfiguration = context.config();
        if (!canPlaceAt(worldgenlevel, seaLevel, blockpos.mutable())) {
            return false;
        } else {
            int j = columnfeatureconfiguration.height().sample(random);
            boolean flag = random.nextFloat() < 0.9F;
            int k = Math.min(j, flag ? 5 : 8);
            int l = flag ? 50 : 15;
            boolean flag1 = false;

            for(BlockPos blockpos1 : BlockPos.randomBetweenClosed(random, l, blockpos.getX() - k, blockpos.getY(), blockpos.getZ() - k, blockpos.getX() + k, blockpos.getY(), blockpos.getZ() + k)) {
                int i1 = j - blockpos1.distManhattan(blockpos);
                if (i1 >= 0) {
                    flag1 |= this.placeColumn(worldgenlevel, seaLevel, blockpos1, i1, columnfeatureconfiguration.reach().sample(random));
                }
            }

            return flag1;
        }
    }

    private boolean placeColumn(LevelAccessor p_65168_, int p_65169_, BlockPos p_65170_, int p_65171_, int p_65172_) {
        boolean flag = false;

        for(BlockPos blockpos : BlockPos.betweenClosed(p_65170_.getX() - p_65172_, p_65170_.getY(), p_65170_.getZ() - p_65172_, p_65170_.getX() + p_65172_, p_65170_.getY(), p_65170_.getZ() + p_65172_)) {
            int i = blockpos.distManhattan(p_65170_);
            BlockPos blockpos1 = isAirOrLavaOcean(p_65168_, p_65169_, blockpos) ? findSurface(p_65168_, p_65169_, blockpos.mutable(), i) : findAir(p_65168_, blockpos.mutable(), i);
            if (blockpos1 != null) {
                int j = p_65171_ - i / 2;

                for(BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos1.mutable(); j >= 0; --j) {
                    if (isAirOrLavaOcean(p_65168_, p_65169_, blockpos$mutableblockpos)) {
                        this.setBlock(p_65168_, blockpos$mutableblockpos, BlocksRegistry.INFERNAL_SPIRE.get().defaultBlockState());
                        blockpos$mutableblockpos.move(Direction.UP);
                        flag = true;
                    } else {
                        if (!p_65168_.getBlockState(blockpos$mutableblockpos).is(BlocksRegistry.INFERNAL_SPIRE.get())) {
                            break;
                        }

                        blockpos$mutableblockpos.move(Direction.UP);
                    }
                }
            }
        }

        return flag;
    }



    @Nullable
    private static BlockPos findSurface(LevelAccessor p_65159_, int p_65160_, BlockPos.MutableBlockPos p_65161_, int p_65162_) {
        while(p_65161_.getY() > p_65159_.getMinY() + 1 && p_65162_ > 0) {
            --p_65162_;
            if (canPlaceAt(p_65159_, p_65160_, p_65161_)) {
                return p_65161_;
            }

            p_65161_.move(Direction.DOWN);
        }

        return null;
    }

    private static boolean canPlaceAt(LevelAccessor p_65155_, int p_65156_, BlockPos.MutableBlockPos p_65157_) {
        if (!isAirOrLavaOcean(p_65155_, p_65156_, p_65157_)) {
            return false;
        } else {
            BlockState blockstate = p_65155_.getBlockState(p_65157_.move(Direction.DOWN));
            p_65157_.move(Direction.UP);
            return !blockstate.isAir() && !CANNOT_PLACE_ON.contains(blockstate.getBlock());
        }
    }

    @Nullable
    private static BlockPos findAir(LevelAccessor p_65174_, BlockPos.MutableBlockPos p_65175_, int p_65176_) {
        while(p_65175_.getY() < p_65174_.getMaxY() && p_65176_ > 0) {
            --p_65176_;
            BlockState blockstate = p_65174_.getBlockState(p_65175_);
            if (CANNOT_PLACE_ON.contains(blockstate.getBlock())) {
                return null;
            }

            if (blockstate.isAir()) {
                return p_65175_;
            }

            p_65175_.move(Direction.UP);
        }

        return null;
    }

    private static boolean isAirOrLavaOcean(LevelAccessor p_65164_, int p_65165_, BlockPos p_65166_) {
        BlockState blockstate = p_65164_.getBlockState(p_65166_);
        return blockstate.isAir() || blockstate.is(Blocks.LAVA) && p_65166_.getY() <= p_65165_;
    }

}
