package com.st0x0ef.stellaris.mixin.client;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.events.ClientEvents;
import com.st0x0ef.stellaris.client.registries.KeyMappingsRegistry;
import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.network.packets.KeyHandlerPacket;
import com.st0x0ef.stellaris.common.network.packets.OpenTabletEntryPacket;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class TabletKeyMenuMixin {

    @Inject(at = @At("HEAD"), method = "keyPressed")
    private void onTabletKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (KeyMappingsRegistry.OPEN_TABLET_INFO.matches(KeyMappingsRegistry.OPEN_TABLET_INFO.key.getValue(), keyCode)) {
            if(ClientEvents.entryHovered != null) {
                ClientEvents.timeClicked++;
                if(ClientEvents.timeClicked == 20) {
                    NetworkManager.sendToServer(new OpenTabletEntryPacket(ClientEvents.entryHovered));
                    ClientEvents.timeClicked = 0;
                    ClientEvents.entryHovered = null;
                }
            }
        } else {
            ClientEvents.timeClicked--;
        }
    }
}
