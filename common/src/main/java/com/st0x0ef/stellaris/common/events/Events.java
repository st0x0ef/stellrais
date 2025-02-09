package com.st0x0ef.stellaris.common.events;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.blocks.CoalLanternBlock;
import com.st0x0ef.stellaris.common.blocks.WallCoalTorchBlock;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data.planets.PlanetTextures;
import com.st0x0ef.stellaris.common.events.custom.PlanetEvents;
import com.st0x0ef.stellaris.common.oxygen.GlobalOxygenManager;
import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.EffectsRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.AABB;

public class Events {
    private static final int RADIATION_CHECK_INTERVAL = 100;
    private static int tickBeforeNextRadioactiveCheck = RADIATION_CHECK_INTERVAL;

    public static void registerEvents() {
        TickEvent.PLAYER_POST.register(player -> {
            if (tickBeforeNextRadioactiveCheck <= 0 && !Utils.isLivingInJetSuit(player)) {
                if (!player.level().isClientSide()) {
                    int maxRadiationLevel = player.getInventory().items.stream()
                            .filter(itemStack -> itemStack.has(DataComponentsRegistry.RADIOACTIVE.get()))
                            .mapToInt(itemStack -> itemStack.get(DataComponentsRegistry.RADIOACTIVE.get()).level())
                            .max()
                            .orElse(0);

                    if (maxRadiationLevel > 0) {
                        player.addEffect(new MobEffectInstance(EffectsRegistry.RADIOACTIVE, 100, maxRadiationLevel - 1));
                    }
                }
                tickBeforeNextRadioactiveCheck = RADIATION_CHECK_INTERVAL;
            }

            tickBeforeNextRadioactiveCheck--;
        });

        BlockEvent.BREAK.register((level, pos, state, player, value) -> {
            if (level instanceof ServerLevel serverLevel && state.is(BlocksRegistry.OXYGEN_DISTRIBUTOR)) {
                if (level.getBlockStates(new AABB(pos).inflate(32)).anyMatch(blockState -> blockState.is(BlocksRegistry.OXYGEN_DISTRIBUTOR))) {
                    removeOxygenRoom(serverLevel, pos);
                }
            }
            return EventResult.pass();
        });

        BlockEvent.PLACE.register((level, pos, state, player) -> {
            if (level instanceof ServerLevel serverLevel && !PlanetUtil.hasOxygen(level)) {
                if (state.is(Blocks.TORCH)) {
                    serverLevel.setBlock(pos, BlocksRegistry.COAL_TORCH_BLOCK.get().defaultBlockState(), 3);
                    return EventResult.interruptFalse();
                } else if (state.is(Blocks.WALL_TORCH)) {
                    serverLevel.setBlock(pos, BlocksRegistry.WALL_COAL_TORCH_BLOCK.get().defaultBlockState().setValue(WallCoalTorchBlock.FACING, state.getValue(WallTorchBlock.FACING)), 3);
                    return EventResult.interruptFalse();
                } else if (state.is(Blocks.LANTERN)) {
                    serverLevel.setBlock(pos, BlocksRegistry.COAL_LANTERN_BLOCK.get().defaultBlockState().setValue(CoalLanternBlock.HANGING, state.getValue(LanternBlock.HANGING)), 3);
                    return EventResult.interruptFalse();
                } else if (state.is(Blocks.CAMPFIRE)) {
                    serverLevel.setBlock(pos, state.setValue(CampfireBlock.LIT, false), 3);
                    return EventResult.interruptFalse();
                }
            }

            return EventResult.pass();
        });
    }

    private static void removeOxygenRoom(ServerLevel level, BlockPos pos) {
        GlobalOxygenManager.getInstance().getOrCreateDimensionManager(level).removeOxygenRoom(pos);
    }
}
