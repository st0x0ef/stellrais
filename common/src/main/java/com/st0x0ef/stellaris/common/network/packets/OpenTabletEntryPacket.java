package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class OpenTabletEntryPacket implements CustomPacketPayload {
    public final ResourceLocation entry;


    public static final StreamCodec<RegistryFriendlyByteBuf, OpenTabletEntryPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull OpenTabletEntryPacket decode(RegistryFriendlyByteBuf buf) {
            return new OpenTabletEntryPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, OpenTabletEntryPacket packet) {
            buf.writeResourceLocation(packet.entry);

        }
    };


    public OpenTabletEntryPacket(ResourceLocation condition) {
        this.entry = condition;
    }

    public OpenTabletEntryPacket(RegistryFriendlyByteBuf buffer) {
        this.entry = buffer.readResourceLocation();
    }

    public static void handle(OpenTabletEntryPacket packet, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        context.queue(() -> {
            PlanetUtil.openTabletMenu(player, packet.entry);
            NetworkManager.sendToServer(new KeyHandlerPacket("key_tablet", false));

        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.TABLET_OPEN_HANDLER_ID;
    }


}
