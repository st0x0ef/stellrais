package com.st0x0ef.stellaris.client.event;

import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.network.packets.KeyHandlerPacket;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.networking.NetworkManager;
import org.lwjgl.glfw.GLFW;

public class ClientEvents {
    public static void registerEvents() {
        ClientRawInputEvent.KEY_PRESSED.register(((client, keyCode, scanCode, action, modifiers) -> {
            KeyVariables.getKey(client).forEach((key, name) -> {
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
}
