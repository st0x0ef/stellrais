package com.st0x0ef.stellaris.common.blocks.machines;

import com.mojang.serialization.MapCodec;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class WaterSeparatorBlock extends BaseLitMachineBlock {

    public WaterSeparatorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(WaterSeparatorBlock::new);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return BlockEntityRegistry.WATER_SEPARATOR_ENTITY.get();
    }

    @Override
    public boolean hasTicker(Level level) {
        return !level.isClientSide;
    }
}
