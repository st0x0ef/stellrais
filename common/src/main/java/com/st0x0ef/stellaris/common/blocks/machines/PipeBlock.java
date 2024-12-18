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

public class PipeBlock extends BaseCableBlock {

    public PipeBlock(Properties properties) {
        super(properties);
    }

    @Override
    boolean isConnectable(Level level, BlockPos pos, Direction direction) {
        return Capabilities.Fluid.BLOCK.getCapability(level, pos, direction) != null;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return BlockEntityRegistry.PIPE_ENTITY.get();
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PipeBlock::new);
    }

}
