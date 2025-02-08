package com.st0x0ef.stellaris.client.events;

import com.st0x0ef.stellaris.client.registries.KeyMappingsRegistry;
import com.st0x0ef.stellaris.client.screens.tablet.TabletMainScreen;
import com.st0x0ef.stellaris.common.items.CustomTabletEntry;
import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.network.packets.KeyHandlerPacket;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.networking.NetworkManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ClientEvents {

    public static ResourceLocation entryHovered = null;
    public static int timeClicked = 0;

    public static void registerEvents() {

        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            if (stack.getItem() instanceof CustomTabletEntry customTabletEntry) {
                if (TabletMainScreen.INFOS.containsKey(customTabletEntry.getEntryName())) {
                    addTooltip(lines);
                    entryHovered = customTabletEntry.getEntryName();
                }
            } else {
                if (TabletMainScreen.INFOS.containsKey(ResourceLocation.fromNamespaceAndPath("items", stack.getItem().arch$registryName().getPath()))) {
                    addTooltip(lines);
                    entryHovered = ResourceLocation.fromNamespaceAndPath("items", stack.getItem().arch$registryName().getPath());
                } else {
                    entryHovered = null;
                }
            }
        });

        ClientRawInputEvent.KEY_PRESSED.register(((client, keyCode, scanCode, action, modifiers) -> {
            KeyVariables.getKey(client).forEach((key, name) -> {

                if(client.player == null) return;

                if (key.getDefaultKey().getValue() == keyCode && action == GLFW.GLFW_RELEASE) {
                    KeyVariables.setKeyVariable(name, client.player.getUUID(), false);
                    NetworkManager.sendToServer(new KeyHandlerPacket(name, false));
                }

                else if (key.getDefaultKey().getValue() == keyCode && action == GLFW.GLFW_PRESS) {
                    KeyVariables.setKeyVariable(name, client.player.getUUID(), true);
                    NetworkManager.sendToServer(new KeyHandlerPacket(name, true));
                }
            });
            return EventResult.pass();
        }));

    }

    public static void addTooltip(List<Component> lines) {
        lines.add(Component.translatable("tooltip.item.stellaris.open_tablet",KeyMappingsRegistry.OPEN_TABLET_INFO.getTranslatedKeyMessage().getString()));
        if(timeClicked != 0) {
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < timeClicked; i++) {
                message.append("||");
            }
            lines.add(Component.literal(message.toString()).withStyle(ChatFormatting.GRAY));
        }
    }

}
