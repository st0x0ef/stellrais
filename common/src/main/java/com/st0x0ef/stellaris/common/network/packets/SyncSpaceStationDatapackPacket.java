package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.common.data.recipes.SpaceStationRecipe;
import com.st0x0ef.stellaris.common.data.recipes.SpaceStationRecipesManager;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SyncSpaceStationDatapackPacket implements CustomPacketPayload {
    private final List<SpaceStationRecipe> recipes;


    public static final StreamCodec<RegistryFriendlyByteBuf, SyncSpaceStationDatapackPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull SyncSpaceStationDatapackPacket decode(RegistryFriendlyByteBuf buf) {
            return new SyncSpaceStationDatapackPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, SyncSpaceStationDatapackPacket packet) {
            SpaceStationRecipesManager.toBuffer(packet.recipes, buf);
        }
    };

    public SyncSpaceStationDatapackPacket(RegistryFriendlyByteBuf buffer) {
        this.recipes = SpaceStationRecipesManager.readFromBuffer(buffer);
    }

    public SyncSpaceStationDatapackPacket(List<SpaceStationRecipe> recipes) {
        this.recipes = recipes;
    }


    public static void handle(SyncSpaceStationDatapackPacket packet, NetworkManager.PacketContext context) {
        SpaceStationRecipesManager.addRecipes(packet.recipes);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.SYNC_SPACE_STATION_DATAPACK;
    }
}
