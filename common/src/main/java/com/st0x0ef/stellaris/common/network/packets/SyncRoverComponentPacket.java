package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.common.data_components.RoverComponent;
import com.st0x0ef.stellaris.common.menus.RoverMenu;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public class SyncRoverComponentPacket implements CustomPacketPayload
{
    private  RoverComponent component;

    public static final StreamCodec<RegistryFriendlyByteBuf,SyncRoverComponentPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull SyncRoverComponentPacket decode(RegistryFriendlyByteBuf object) {
            return new SyncRoverComponentPacket(object);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf object, SyncRoverComponentPacket packet) {
            packet.component.toNetwork(object);
        }
    };

    public static void handle(SyncRoverComponentPacket packet,  NetworkManager.PacketContext context) {
        LocalPlayer player = (LocalPlayer) context.getPlayer();
        if (player.containerMenu instanceof RoverMenu menu && menu.getRover() != null) {
            menu.getRover().setRoverComponent(packet.component);
        }
    }

    public SyncRoverComponentPacket(RegistryFriendlyByteBuf buf){this(RoverComponent.fromNetwork(buf));}
    public SyncRoverComponentPacket(RoverComponent component) {
        this.component = component;
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.SYNC_ROVER_COMPONENT_ID;
    }
}
