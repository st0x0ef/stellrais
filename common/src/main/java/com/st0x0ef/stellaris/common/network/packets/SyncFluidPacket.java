package com.st0x0ef.stellaris.common.network.packets;

import com.fej1fun.potentials.fluid.BaseFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.fluid.FluidStack;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SyncFluidPacket(FluidStack fluidStack, int tank, BlockPos pos, Direction direction) implements CustomPacketPayload {

    public static final Type<SyncFluidPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "fluid_sync_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncFluidPacket> STREAM_CODEC = StreamCodec.composite(
            FluidStack.STREAM_CODEC, SyncFluidPacket::fluidStack,
            ByteBufCodecs.VAR_INT, SyncFluidPacket::tank,
            BlockPos.STREAM_CODEC, SyncFluidPacket::pos,
            Direction.STREAM_CODEC, SyncFluidPacket::direction,
            SyncFluidPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SyncFluidPacket data, final NetworkManager.PacketContext context) {
        context.queue(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null && level.getBlockEntity(data.pos) instanceof FluidProvider.BLOCK fluidProvider)
                if (fluidProvider.getFluidTank(data.direction) instanceof BaseFluidStorage fluidStorage)
                    fluidStorage.setFluidInTank(data.tank, data.fluidStack);
        });
    }
}
