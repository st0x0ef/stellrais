package com.st0x0ef.stellaris.common.blocks.machines;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.blocks.entities.machines.PipeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PipeBlock extends BaseCableBlock {

    public final int capacity;
    public final int maxIn;
    public final int maxOut;

    public PipeBlock(Properties properties, int capacity, int maxIn, int maxOut) {
        super(properties);
        this.capacity = capacity;
        this.maxIn = maxIn;
        this.maxOut = maxOut;
    }

    @Override
    boolean isConnectable(Level level, BlockPos pos, Direction direction) {
        return Capabilities.Fluid.BLOCK.getCapability(level, pos, direction) != null;
    }

    @Override
    protected @NotNull MapCodec<? extends PipeBlock> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                propertiesCodec(),
                Codec.INT.fieldOf("capacity").forGetter(pipe -> pipe.capacity),
                Codec.INT.fieldOf("maxIn").forGetter(pipe -> pipe.maxIn),
                Codec.INT.fieldOf("maxOut").forGetter(pipe -> pipe.maxOut)
        ).apply(instance, PipeBlock::new));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PipeBlockEntity(pos, state, capacity, maxIn, maxOut);
    }
}
