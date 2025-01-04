package com.st0x0ef.stellaris.common.network.packets;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SyncEnergyPacket(int energy, BlockPos pos, Direction direction) implements CustomPacketPayload {

    public static final Type<SyncEnergyPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "energy_sync_packet"));
    public static final StreamCodec<ByteBuf, SyncEnergyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SyncEnergyPacket::energy,
            BlockPos.STREAM_CODEC, SyncEnergyPacket::pos,
            Direction.STREAM_CODEC, SyncEnergyPacket::direction,
            SyncEnergyPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SyncEnergyPacket data, final NetworkManager.PacketContext context) {
        context.queue(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null && level.getBlockEntity(data.pos) instanceof EnergyProvider.BLOCK energyProvider)
                if (energyProvider.getEnergy(data.direction) instanceof BaseEnergyStorage energyStorage)
                    energyStorage.setEnergyStored(data.energy);
        });
    }
}
