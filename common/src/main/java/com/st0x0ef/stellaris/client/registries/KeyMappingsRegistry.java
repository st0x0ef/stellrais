package com.st0x0ef.stellaris.client.registries;

import com.mojang.blaze3d.platform.InputConstants;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.network.packets.KeyHandlerPacket;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import com.st0x0ef.stellaris.common.utils.Utils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class KeyMappingsRegistry {
    public static String CATEGORY = "category." + Stellaris.MODID + ".default";

    public static KeyMapping FREEZE_PLANET_MENU = new KeyMapping("key." + Stellaris.MODID + ".freeze_planet_menu", InputConstants.KEY_X, CATEGORY);
    public static KeyMapping CHANGE_JETSUIT_MODE = new KeyMapping("key." + Stellaris.MODID + ".jetsuit_mode", InputConstants.KEY_V, CATEGORY);

    public static void clientTick(Minecraft minecraft) {
        Player player = minecraft.player;

        if (player == null) {
            return;
        }

        if (player.containerMenu == MenuTypesRegistry.PLANET_SELECTION_MENU) {
            while (FREEZE_PLANET_MENU.consumeClick()) {
                NetworkManager.sendToServer(new KeyHandlerPacket("freeze_planet_menu", true));
            }
        }

        else if (Utils.isLivingInJetSuit(player) || Utils.isLivingInSpaceSuit(player)) {
            while (CHANGE_JETSUIT_MODE.consumeClick()) {
                NetworkManager.sendToServer(new KeyHandlerPacket("switch_jet_suit_mode", true));
            }
        }
    }
}
