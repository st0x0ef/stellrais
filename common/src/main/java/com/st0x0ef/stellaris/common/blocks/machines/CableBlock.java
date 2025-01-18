package com.st0x0ef.stellaris.common.blocks.machines;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.blocks.entities.machines.CableBlockEntity;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CableBlock extends BaseCableBlock {

    public final int capacity;
    public final int maxIn;
    public final int maxOut;

    public CableBlock(Properties properties, final int capacity, final int maxIn, final int maxOut) {
        super(properties);
        this.capacity = capacity;
        this.maxIn = maxIn;
        this.maxOut = maxOut;
    }

    @Override
    boolean isConnectable(Level level, BlockPos pos, Direction direction) {
        return Capabilities.Energy.BLOCK.getCapability(level, pos, direction) != null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state, capacity, maxIn, maxOut);
    }

    @Override
    protected @NotNull MapCodec<? extends CableBlock> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                propertiesCodec(),
                Codec.INT.fieldOf("capacity").forGetter(cable -> cable.capacity),
                Codec.INT.fieldOf("maxIn").forGetter(cable -> cable.maxIn),
                Codec.INT.fieldOf("maxOut").forGetter(cable -> cable.maxOut)
        ).apply(instance, CableBlock::new));
    }

}
