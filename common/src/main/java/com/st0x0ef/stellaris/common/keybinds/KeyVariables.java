package com.st0x0ef.stellaris.common.keybinds;

import com.st0x0ef.stellaris.Stellaris;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KeyVariables {

    public static final Map<UUID, Boolean> KEY_UP = new HashMap<>();
    public static final Map<UUID, Boolean> KEY_DOWN = new HashMap<>();
    public static final Map<UUID, Boolean> KEY_RIGHT = new HashMap<>();
    public static final Map<UUID, Boolean> KEY_LEFT = new HashMap<>();
    public static final Map<UUID, Boolean> KEY_JUMP = new HashMap<>();
    public static final Map<UUID, Boolean> KEY_TABLET = new HashMap<>();

    public static boolean isHoldingUp(Player player) {
        return player != null && KEY_UP.getOrDefault(player.getUUID(), false);
    }

    public static boolean isHoldingDown(Player player) {
        return player != null && KEY_DOWN.getOrDefault(player.getUUID(), false);
    }

    public static boolean isHoldingRight(Player player) {
        return player != null && KEY_RIGHT.getOrDefault(player.getUUID(), false);
    }

    public static boolean isHoldingLeft(Player player) {
        return player != null && KEY_LEFT.getOrDefault(player.getUUID(), false);
    }

    public static boolean isHoldingJump(Player player) {
        return player != null && KEY_JUMP.getOrDefault(player.getUUID(), false);
    }

    public static boolean getHoldingTabletPress(Player player) {
        return player != null && KEY_TABLET.getOrDefault(player.getUUID(), false);
    }


}
