package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data.recipes.SpaceStationRecipe;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import com.st0x0ef.stellaris.common.registry.EntityData;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceStationPacket implements CustomPacketPayload {

    public final ResourceLocation dimension;
    public final SpaceStationRecipe recipe;

    public static final StreamCodec<RegistryFriendlyByteBuf, PlaceStationPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull PlaceStationPacket decode(RegistryFriendlyByteBuf buf) {
            return new PlaceStationPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, PlaceStationPacket packet) {
            buf.writeResourceLocation(packet.dimension);
            SpaceStationRecipe.toBuffer(packet.recipe, buf);
        }
    };

    public PlaceStationPacket(ResourceLocation dimension, SpaceStationRecipe recipe) {
        this.dimension = dimension;
        this.recipe = recipe;
    }

    public PlaceStationPacket(RegistryFriendlyByteBuf buffer) {
        this.dimension = buffer.readResourceLocation();
        this.recipe = SpaceStationRecipe.readFromBuffer(buffer);
    }

    public static void handle(PlaceStationPacket packet, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        Planet planet = PlanetUtil.getPlanet(packet.dimension);
        if(planet != null ) {

            ServerLevel level = player.level().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, planet.dimension()));
            if (level != null) {
                Stellaris.LOG.info("Placing space station");
                Utils.placeSpaceStation(player, level, packet.recipe);
                packet.recipe.removeMaterials(player);
            }

        } else {
            Stellaris.LOG.error("Planet is null");
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.PLACE_STATION_ID;
    }
}