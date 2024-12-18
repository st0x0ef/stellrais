package com.st0x0ef.stellaris.common.blocks.machines;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.mojang.serialization.MapCodec;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class CableBlock extends BaseCableBlock {

    public CableBlock(Properties properties) {
        super(properties);
    }

    @Override
    boolean isConnectable(Level level, BlockPos pos, Direction direction) {
        return Capabilities.Energy.BLOCK.getCapability(level, pos, direction) != null;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return BlockEntityRegistry.CABLE_ENTITY.get();
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CableBlock::new);
    }
}
