package com.st0x0ef.stellaris.common.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.TeleportTransition;

public class TeleportUtil {
    public static void teleportToPlanet(Entity entity, ServerLevel level, int yPos) {
        entity.teleport(new TeleportTransition(level, entity, arg -> {}));
        entity.setPos(entity.getX(), yPos, entity.getZ());
    }
}
