package com.st0x0ef.stellaris.common.registry;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class EntityData {

    public static EntityDataAccessor<Boolean> DATA_PLANET_MENU_OPEN;

    public static void register() {
        DATA_PLANET_MENU_OPEN = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    }
}
