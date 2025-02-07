package com.st0x0ef.stellaris.client.events;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.registries.KeyMappingsRegistry;
import com.st0x0ef.stellaris.client.screens.tablet.TabletMainScreen;
import com.st0x0ef.stellaris.common.items.CustomTabletEntry;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ClientEvents {

    public static ResourceLocation entryHovered = null;
    public static int timeClicked = 0;

    public static void registerEvents() {

        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            if (stack.getItem() instanceof CustomTabletEntry customTabletEntry) {
                addTooltip(lines);
                entryHovered = customTabletEntry.getEntryName();
            } else {
                if (TabletMainScreen.INFOS.containsKey(ResourceLocation.fromNamespaceAndPath("items", stack.getItem().arch$registryName().getPath()))) {
                    addTooltip(lines);
                    entryHovered = ResourceLocation.fromNamespaceAndPath("items", stack.getItem().arch$registryName().getPath());
                } else {
                    entryHovered = null;
                }
            }
        });
    }

    public static void addTooltip(List<Component> lines) {
        lines.add(Component.literal("For more infos, press [" + KeyMappingsRegistry.OPEN_TABLET_INFO.key.getName() +"] "));
        if(timeClicked != 0) {
            StringBuilder message = new StringBuilder("|");
            for (int i = 0; i < timeClicked; i++) {
                message.append("|");
            }
            lines.add(Component.literal(message.toString()));
        }
    }

}
