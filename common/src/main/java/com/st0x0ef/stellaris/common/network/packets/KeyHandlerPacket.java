package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data_components.SpaceSuitModules;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
import com.st0x0ef.stellaris.common.items.module.JetModule;
import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.menus.PlanetSelectionMenu;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KeyHandlerPacket implements CustomPacketPayload {
    public final String key;
    public final boolean condition;


    public static final StreamCodec<RegistryFriendlyByteBuf, KeyHandlerPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull KeyHandlerPacket decode(RegistryFriendlyByteBuf buf) {
            return new KeyHandlerPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, KeyHandlerPacket packet) {
            buf.writeUtf(packet.key);
            buf.writeBoolean(packet.condition);

        }
    };


    public KeyHandlerPacket(String key, boolean condition) {
        this.key = key;
        this.condition = condition;
    }

    public KeyHandlerPacket(RegistryFriendlyByteBuf buffer) {
        this.key = buffer.readUtf();
        this.condition = buffer.readBoolean();
    }

    public static void handle(KeyHandlerPacket packet, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        context.queue(() -> {
            switch (packet.key) {
                case "key_up":
                    KeyVariables.KEY_UP.put(player.getUUID(), packet.condition);
                    break;

                case "key_down":
                    KeyVariables.KEY_DOWN.put(player.getUUID(), packet.condition);
                    break;

                case "key_right":
                    KeyVariables.KEY_RIGHT.put(player.getUUID(), packet.condition);
                    break;

                case "key_left":
                    KeyVariables.KEY_LEFT.put(player.getUUID(), packet.condition);
                    break;
                case "switch_jet_suit_mode":
                    if (Utils.isLivingInJetSuit(player)) {
                        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
                        JetSuit.Suit item = (JetSuit.Suit) itemStack.getItem();
                        item.switchJetSuitMode(itemStack);
                    } else if (Utils.isLivingInSpaceSuit(player)) {
                        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
                        ItemStack jetModule = SpaceSuitModules.getIfContains(itemStack, ItemsRegistry.MODULE_JET.get());
                        if (!jetModule.isEmpty()) {
                            if (jetModule.getItem() instanceof JetModule module) {
                                module.switchJetSuitMode(itemStack);
                            }
                        }
                    }
                    break;
                case "key_jump":
                    KeyVariables.KEY_JUMP.put(player.getUUID(), packet.condition);
                    break;
                case "key_tablet":
                    KeyVariables.KEY_TABLET.put(player.getUUID(), packet.condition);
                    break;
                case "freeze_planet_menu":
                    if (player.containerMenu instanceof PlanetSelectionMenu menu) menu.switchFreezeGui();
                    break;
                default:
                    Stellaris.LOG.error("unknown key action {}", packet.key);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.KEY_HANDLER_ID;
    }


}
