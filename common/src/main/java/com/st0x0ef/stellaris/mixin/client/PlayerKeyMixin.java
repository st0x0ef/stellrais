package com.st0x0ef.stellaris.mixin.client;

import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.network.packets.KeyHandlerPacket;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Mixin(KeyboardHandler.class)
public abstract class PlayerKeyMixin {

    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(at = @At(value = "TAIL"), method = "keyPress")
    private void keyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo info) {
        if (windowPointer == this.minecraft.getWindow().getWindow()) {
            if (this.minecraft.player == null) return;

            stellaris$sendKeyToServerAndClientHashMap(key, action, this.minecraft.player, this.minecraft.options.keyUp, KeyVariables.KEY_UP, "key_up", KeyVariables.isHoldingUp(this.minecraft.player));
            stellaris$sendKeyToServerAndClientHashMap(key, action, this.minecraft.player, this.minecraft.options.keyDown, KeyVariables.KEY_DOWN, "key_down", KeyVariables.isHoldingDown(this.minecraft.player));
            stellaris$sendKeyToServerAndClientHashMap(key, action, this.minecraft.player, this.minecraft.options.keyRight, KeyVariables.KEY_RIGHT, "key_right", KeyVariables.isHoldingRight(this.minecraft.player));
            stellaris$sendKeyToServerAndClientHashMap(key, action, this.minecraft.player, this.minecraft.options.keyLeft, KeyVariables.KEY_LEFT, "key_left", KeyVariables.isHoldingLeft(this.minecraft.player));
            stellaris$sendKeyToServerAndClientHashMap(key, action, this.minecraft.player, this.minecraft.options.keyJump, KeyVariables.KEY_JUMP, "key_jump", KeyVariables.isHoldingJump(this.minecraft.player));
        }
    }

    private static void stellaris$sendKeyToServerAndClientHashMap(int key, int action, Player player, KeyMapping keyWanted, Map<UUID, Boolean> variableKey, String keyString, boolean isPressed) {
        if (keyWanted.getDefaultKey().getValue() == key && action == GLFW.GLFW_RELEASE && isPressed) {
            variableKey.put(player.getUUID(), false);
            NetworkManager.sendToServer(new KeyHandlerPacket(keyString, false));
        }

        else if (keyWanted.getDefaultKey().getValue() == key && action == GLFW.GLFW_PRESS) {
            variableKey.put(player.getUUID(), true);
            NetworkManager.sendToServer(new KeyHandlerPacket(keyString, true));
        }
    }
}
