package com.st0x0ef.stellaris.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.st0x0ef.stellaris.common.data.recipes.SpaceStationRecipesManager;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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

                .then(Commands.literal("test")
                        .requires(c -> c.hasPermission(2))
                        .then(Commands.literal("placeSpaceStation")
                                .executes((CommandContext<CommandSourceStack> context) -> {
                                    Utils.placeSpaceStation(context.getSource().getPlayer(), (ServerLevel) context.getSource().getPlayer().level(), SpaceStationRecipesManager.SPACE_STATION_RECIPES.getLast());
                                    return 0;
                                }))
                )

        );
    }
}
