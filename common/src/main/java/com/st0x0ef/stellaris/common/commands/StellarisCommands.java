package com.st0x0ef.stellaris.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.st0x0ef.stellaris.common.oil.GlobalOilManager;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
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
                                            ServerLevel level = (ServerLevel) context.getSource().getPlayer().level();
                                            ChunkAccess access = level.getChunk(context.getSource().getPlayer().getOnPos());
                                            int oilLevel = IntegerArgumentType.getInteger(context, "oilLevel");
                                            GlobalOilManager.getInstance().getOrCreateDimensionManager(level).setOilLevel(access.getPos(), oilLevel);
                                            context.getSource().getPlayer().sendSystemMessage(Component.literal("Oil Level : " + oilLevel));
                                            return 0;
                                        })))
                        .then(Commands.literal("get")
                                .executes((CommandContext<CommandSourceStack> context) -> {
                                    ServerLevel level = (ServerLevel) context.getSource().getPlayer().level();
                                    ChunkAccess access = level.getChunk(context.getSource().getPlayer().getOnPos());
                                    context.getSource().getPlayer().sendSystemMessage(Component.literal("Oil Level : " + GlobalOilManager.getInstance().getOrCreateDimensionManager(level).getOrCreateOilLevel(access.getPos())));
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
                                })))
        );
    }
}
