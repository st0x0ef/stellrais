package com.st0x0ef.stellaris.client.events;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.tablet.TabletMainScreen;
import com.st0x0ef.stellaris.common.items.CustomTabletEntry;
import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import dev.architectury.event.events.client.ClientTooltipEvent;
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
                    checkIfTabletKeyIsPressed(stack.getItem().arch$registryName().getPath());
                }
            }
        });
    }

    public static void checkIfTabletKeyIsPressed(String key) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (KeyVariables.isHoldingTabletKey(player)) {
            Stellaris.LOG.info("Tablet key is pressed {}", key);
            KeyVariables.KEY_TABLET.put(player.getUUID(), false);
        }
    }

}
