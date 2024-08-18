package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.common.data_components.RocketComponent;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import com.st0x0ef.stellaris.common.menus.RocketMenu;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public class SyncRocketComponentPacket implements CustomPacketPayload {

    private final RocketComponent component;


    public static final StreamCodec<RegistryFriendlyByteBuf, SyncRocketComponentPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull SyncRocketComponentPacket decode(RegistryFriendlyByteBuf buf) {
            return new SyncRocketComponentPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, SyncRocketComponentPacket packet) {
            packet.component.toNetwork(buf);
        }
    };



    public SyncRocketComponentPacket(RegistryFriendlyByteBuf buffer) {
        this(RocketComponent.fromNetwork(buffer));
    }

    public SyncRocketComponentPacket(RocketComponent component) {
        this.component = component;
    }


    public static void handle(SyncRocketComponentPacket packet,  NetworkManager.PacketContext context) {
        LocalPlayer player = (LocalPlayer) context.getPlayer();
        if (player.containerMenu instanceof RocketMenu menu) {
            RocketEntity rocket = menu.getRocket();
            rocket.rocketComponent = packet.component;

            rocket.MODEL_UPGRADE = packet.component.getModelUpgrade();
            rocket.SKIN_UPGRADE = packet.component.getSkinUpgrade();
            rocket.TANK_UPGRADE = packet.component.getTankUpgrade();
            rocket.MOTOR_UPGRADE = packet.component.getMotorUpgrade();

        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.SYNC_ROCKET_COMPONENT_ID;
    }
}
