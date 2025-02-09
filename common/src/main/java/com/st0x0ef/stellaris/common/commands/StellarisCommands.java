package com.st0x0ef.stellaris.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.tablet.TabletMainScreen;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data.planets.StellarisData;
import com.st0x0ef.stellaris.common.registry.StatsRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkAccess;

public class StellarisCommands {

    public StellarisCommands(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("stellaris")
                .then(Commands.literal("oil")
                        .requires(c -> c.hasPermission(2))
                        .then(Commands.literal("set")
                                .then(Commands.argument("level", IntegerArgumentType.integer())
                                        .executes((CommandContext<CommandSourceStack> context) -> {
                                            ChunkAccess access = context.getSource().getPlayer().level().getChunk(context.getSource().getPlayer().getOnPos());
                                            access.stellaris$setChunkOilLevel(context.getArgument("level", Integer.class));
                                            context.getSource().getPlayer().sendSystemMessage(Component.literal("Oil Level : " + access.stellaris$getChunkOilLevel()));
                                            return 0;
                                        })))
                        .then(Commands.literal("get")
                                .executes((CommandContext<CommandSourceStack> context) -> {
                                    ChunkAccess access = context.getSource().getPlayer().level().getChunk(context.getSource().getPlayer().getOnPos());
                                    context.getSource().getPlayer().sendSystemMessage(Component.literal("Oil Level : " + access.stellaris$getChunkOilLevel()));
                                    return 0;
                                })))

                .then(Commands.literal("screen")
                        .requires(c -> c.hasPermission(2))
                        .then(Commands.literal("galaxyScreen")
                                .executes((CommandContext<CommandSourceStack> context) -> {
                                    PlanetUtil.openMilkyWayMenu(context.getSource().getPlayer());
                                    return 0;
                                }))
                        .then(Commands.literal("tablet")
                                .executes((CommandContext<CommandSourceStack> context) -> {
                                    PlanetUtil.openTabletMenu(context.getSource().getPlayer(), ResourceLocation.parse("null:null"));
                                    return 0;
                                }))
                        .then(Commands.literal("waitScreen")
                                .executes((CommandContext<CommandSourceStack> context) -> {
                                    PlanetUtil.openWaitMenu(context.getSource().getPlayer(), context.getSource().getPlayer().getDisplayName().getString());
                                    return 0;
                                }))
                        .then(Commands.literal("planetScreen")
                                .executes((CommandContext<CommandSourceStack> context) -> {
                                    PlanetUtil.openPlanetSelectionMenu(context.getSource().getPlayer(), true);
                                    return 0;
                                }))
                )
                .then(Commands.literal("dev")
                        .requires(c -> c.hasPermission(2))
                        .then(Commands.literal("dumpPlanetInfos")
                                .executes((CommandContext<CommandSourceStack> context) -> {

                                    for(Planet planet : StellarisData.getPlanets()) {
                                        Stellaris.LOG.info(planet.name());
                                        Stellaris.LOG.info("[br] [br] Temperature : [color=red]{}°c [br] Gravity : [color=red]{} [br] Oxygen : [color=red]{} [br] Distance From Earth : {}km", planet.temperature(), planet.gravity(), planet.oxygen(), planet.distanceFromEarth());
                                    }

                                    return 0;
                                }))

                )



        );
    }
}
