package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class SyncOilLevelPacket implements CustomPacketPayload {

    final int oilLevel;
    final int chunkX;
    final int chunkZ;

    public static final Type<SyncOilLevelPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "energy_oil_level_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncOilLevelPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull SyncOilLevelPacket decode(RegistryFriendlyByteBuf buf) {
            return new SyncOilLevelPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, SyncOilLevelPacket packet) {
            buf.writeInt(packet.oilLevel);
            buf.writeInt(packet.chunkX);
            buf.writeInt(packet.chunkZ);
        }
    };



    public SyncOilLevelPacket(RegistryFriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public SyncOilLevelPacket(int oilLevel, int chunkX, int chunkZ) {
        this.oilLevel = oilLevel;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }


    public static void handle(SyncOilLevelPacket packet, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        player.level().getChunk(packet.chunkX, packet.chunkZ).stellaris$setChunkOilLevel(packet.oilLevel);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
