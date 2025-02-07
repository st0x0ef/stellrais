package com.st0x0ef.stellaris.client.events;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.tablet.TabletMainScreen;
import com.st0x0ef.stellaris.common.items.CustomTabletEntry;
import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.menus.TabletMenu;
import com.st0x0ef.stellaris.common.network.packets.KeyHandlerPacket;
import com.st0x0ef.stellaris.common.network.packets.OpenTabletEntryPacket;
import com.st0x0ef.stellaris.common.utils.GuiHelper;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ClientEvents {


    public static void registerEvents() {

        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            if (stack.getItem() instanceof CustomTabletEntry customTabletEntry) {
                lines.add(Component.literal("For more infos, press [R] "));
                checkIfTabletKeyIsPressed(customTabletEntry.getEntryName());
            } else {
                if (TabletMainScreen.INFOS.containsKey(ResourceLocation.fromNamespaceAndPath("items", stack.getItem().arch$registryName().getPath()))) {
                    lines.add(Component.literal("For more infos, press [R] "));
                    checkIfTabletKeyIsPressed(ResourceLocation.fromNamespaceAndPath("items", stack.getItem().arch$registryName().getPath()));
                }
            }
        });
    }

    public static void checkIfTabletKeyIsPressed(ResourceLocation key) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (KeyVariables.getHoldingTabletPress(player)) {
            player.closeContainer();
            NetworkManager.sendToServer(new OpenTabletEntryPacket(key));

            if(Minecraft.getInstance().screen instanceof TabletMainScreen screen) {
                Stellaris.LOG.info("Opening entry: {}", key);
                screen.openEntry(key);
            }
        }
    }

}
