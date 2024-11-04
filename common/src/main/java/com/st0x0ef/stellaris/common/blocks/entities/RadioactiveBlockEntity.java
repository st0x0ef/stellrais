package com.st0x0ef.stellaris.common.blocks.entities;

import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.EffectsRegistry;
import com.st0x0ef.stellaris.common.registry.TagRegistry;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RadioactiveBlockEntity extends BlockEntity {
    public int radioactivityLevel;

    public RadioactiveBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RADIOACTIVE_BLOCK.get(), pos, state);
    }
    public RadioactiveBlockEntity(BlockPos pos, BlockState state, int level) {
        this(pos, state);
        this.radioactivityLevel = level;
    }

    int tickCount=0;
    public void tick() {
        AABB area = new AABB(this.getBlockPos()).inflate(5);
        if (tickCount >= 100) {
            List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, area);
            for (LivingEntity entity : entities) {
                if (!Utils.isLivingInJetSuit(entity) && !entity.getType().is(TagRegistry.ENTITY_RADIATION_INVULNERABLE_TAG)) {
                    entity.addEffect(new MobEffectInstance(EffectsRegistry.RADIOACTIVE, 100, radioactivityLevel - 1));
                }
            }
            tickCount = 0;
        }
        tickCount++;
    }
}
