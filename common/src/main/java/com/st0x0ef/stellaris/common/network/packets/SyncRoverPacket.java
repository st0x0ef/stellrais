package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.common.entities.vehicles.base.AbstractRoverBase;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SyncRoverPacket implements CustomPacketPayload {
    private final boolean forward;
    private final boolean backward;
    private final boolean left;
    private final boolean right;
    private final UUID uuid;

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncRoverPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public SyncRoverPacket decode(RegistryFriendlyByteBuf buf) {
            return fromBytes(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, SyncRoverPacket packet) {
            packet.toBytes(buf);
        }
    };

    public SyncRoverPacket(boolean forward, boolean backward, boolean left, boolean right, Player player) {
        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
        this.uuid = player.getUUID();
    }

    public SyncRoverPacket(boolean forward, boolean backward, boolean left, boolean right, UUID uuid) {
        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
        this.uuid = uuid;
    }

    public static SyncRoverPacket fromBytes(RegistryFriendlyByteBuf buf) {
        boolean forward = buf.readBoolean();
        boolean backward = buf.readBoolean();
        boolean left = buf.readBoolean();
        boolean right = buf.readBoolean();
        UUID uuid = buf.readUUID();
        return new SyncRoverPacket(forward, backward, left, right, uuid);
    }

    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(forward);
        buf.writeBoolean(backward);
        buf.writeBoolean(left);
        buf.writeBoolean(right);
        buf.writeUUID(uuid);
    }

    @Override
    public Type<SyncRoverPacket> type() {
        return NetworkRegistry.SYNC_ROVER_CONTROLS;
    }

    public void handle(NetworkManager.PacketContext packetContext) {
        Player player = packetContext.getPlayer();
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getVehicle() instanceof AbstractRoverBase rover && rover.getUUID().equals(this.uuid)) {
                rover.updateControls(forward, backward, left, right, serverPlayer);

            }
        }
    }
}
