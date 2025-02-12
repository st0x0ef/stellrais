package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.event.custom.PlanetSelectionClientEvents;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.events.custom.PlanetSelectionServerEvents;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import com.st0x0ef.stellaris.common.registry.EntityData;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import dev.architectury.event.EventResult;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportEntityToPlanetPacket implements CustomPacketPayload {

    public final ResourceLocation dimension;

    public static final StreamCodec<RegistryFriendlyByteBuf, TeleportEntityToPlanetPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull TeleportEntityToPlanetPacket decode(RegistryFriendlyByteBuf buf) {
            return new TeleportEntityToPlanetPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, TeleportEntityToPlanetPacket packet) {
            buf.writeResourceLocation(packet.dimension);
        }
    };

    public TeleportEntityToPlanetPacket(ResourceLocation dimension) {
        this.dimension = dimension;
    }

    public TeleportEntityToPlanetPacket(RegistryFriendlyByteBuf buffer) {
        this.dimension = buffer.readResourceLocation();
    }

    public static void handle(TeleportEntityToPlanetPacket packet, NetworkManager.PacketContext context) {
        Player player = context.getPlayer();
        Planet planet = PlanetUtil.getPlanet(packet.dimension);
        Entity rocket = player.getVehicle();

        if(PlanetSelectionServerEvents.LAUNCH_BUTTON.invoker().launchButton(player, planet, rocket, context) == EventResult.interruptTrue()) {
            return;
        }

        if(planet != null ) {

            if(rocket == null) {
                Utils.changeDimension(player, planet);
                return;
            }

            if(rocket.getPassengers().size() == 1) {
                Utils.changeDimension((Player) rocket.getPassengers().getFirst(), planet);
                player.getEntityData().set(EntityData.DATA_PLANET_MENU_OPEN, false);
            } else {
                Utils.changeDimensionForPlayers(rocket.getPassengers(), planet);
            }

        } else {
            Stellaris.LOG.error("Planet is null");
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.TELEPORT_ENTITY_ID;
    }
}