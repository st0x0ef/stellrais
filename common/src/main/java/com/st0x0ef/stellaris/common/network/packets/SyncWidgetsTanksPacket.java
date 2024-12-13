package com.st0x0ef.stellaris.common.network.packets;

import com.st0x0ef.stellaris.common.blocks.entities.machines.FuelRefineryBlockEntity;
import com.st0x0ef.stellaris.common.blocks.entities.machines.PumpjackBlockEntity;
import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterPumpBlockEntity;
import com.st0x0ef.stellaris.common.blocks.entities.machines.WaterSeparatorBlockEntity;
import com.st0x0ef.stellaris.common.menus.*;
import com.st0x0ef.stellaris.common.network.NetworkRegistry;
import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SyncWidgetsTanksPacket implements CustomPacketPayload {

    private final int[] component;
    private final ResourceLocation[] locations;


    public static final StreamCodec<RegistryFriendlyByteBuf, SyncWidgetsTanksPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull SyncWidgetsTanksPacket decode(RegistryFriendlyByteBuf buf) {
            return new SyncWidgetsTanksPacket(buf);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, SyncWidgetsTanksPacket packet) {
            buf.writeVarIntArray(packet.component);

            buf.writeInt(packet.locations.length);
            for (ResourceLocation location : packet.locations) {
                buf.writeResourceLocation(location);
            }
        }
    };


    public SyncWidgetsTanksPacket(RegistryFriendlyByteBuf buffer) {
        this.component = buffer.readVarIntArray();
        int length = buffer.readInt();
        this.locations = new ResourceLocation[length];

        for (int i = 0; i < length; i++) {
            locations[i] = buffer.readResourceLocation();
        }
    }

    public SyncWidgetsTanksPacket(int[] component) {
        this(component, new ResourceLocation[] {});
    }

    public SyncWidgetsTanksPacket(int[] values, ResourceLocation[] locations) {
        this.component = values;
        this.locations = locations;
    }


    public static void handle(SyncWidgetsTanksPacket syncWidgetsTanks, NetworkManager.PacketContext context) {
        if (context.getEnv() != EnvType.CLIENT) {
            return;
        }

        LocalPlayer player = (LocalPlayer) context.getPlayer();
        switch (player.containerMenu) {
            case WaterSeparatorMenu menu -> {
                WaterSeparatorBlockEntity blockEntity = menu.getBlockEntity();
                if (syncWidgetsTanks.component.length == 2 && syncWidgetsTanks.locations.length == 2) {
                    blockEntity.resultTanks.getFirst().setFluid(BuiltInRegistries.FLUID.get(syncWidgetsTanks.locations[0]), syncWidgetsTanks.component[0]);
                    blockEntity.resultTanks.getLast().setFluid(BuiltInRegistries.FLUID.get(syncWidgetsTanks.locations[1]), syncWidgetsTanks.component[1]);
                }
                else if (syncWidgetsTanks.component.length == 1 && syncWidgetsTanks.locations.length == 1) {
                    blockEntity.ingredientTank.setFluid(BuiltInRegistries.FLUID.get(syncWidgetsTanks.locations[0]), syncWidgetsTanks.component[0]);
                }
                else if (syncWidgetsTanks.component.length == 3) {
                    blockEntity.getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);
                }
            }
            case FuelRefineryMenu menu -> {
                FuelRefineryBlockEntity blockEntity = menu.getBlockEntity();
                if (syncWidgetsTanks.component.length == 2 && syncWidgetsTanks.locations.length == 2) {
                    blockEntity.getIngredientTank().setFluid(BuiltInRegistries.FLUID.get(syncWidgetsTanks.locations[0]), syncWidgetsTanks.component[0]);
                    blockEntity.getResultTank().setFluid(BuiltInRegistries.FLUID.get(syncWidgetsTanks.locations[1]), syncWidgetsTanks.component[1]);
                }
                else if (syncWidgetsTanks.component.length == 1) {
                    blockEntity.getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);
                }
            }
            case WaterPumpMenu menu -> {
                WaterPumpBlockEntity blockEntity = menu.getBlockEntity();
                if (syncWidgetsTanks.component.length == 1 && syncWidgetsTanks.locations.length == 1) {
                    blockEntity.getWaterTank().setFluid(BuiltInRegistries.FLUID.get(syncWidgetsTanks.locations[0]), syncWidgetsTanks.component[0]);
                }
                else if (syncWidgetsTanks.component.length == 2) {
                    blockEntity.getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);
                }
            }
            case SolarPanelMenu menu -> menu.getBlockEntity().getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);
            case CoalGeneratorMenu menu ->
                    menu.getBlockEntity().getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);
            case RadioactiveGeneratorMenu menu ->
                    menu.getBlockEntity().getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);
            case OxygenGeneratorMenu menu -> {
                    menu.getBlockEntity().getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);
                    menu.getBlockEntity().oxygenTank.setAmount(syncWidgetsTanks.component[1]);
            }
            case PumpjackMenu menu -> {
                PumpjackBlockEntity blockEntity = menu.getBlockEntity();
                if (syncWidgetsTanks.component.length == 2 && syncWidgetsTanks.locations.length == 1) {
                    blockEntity.resultTank.setFluid(BuiltInRegistries.FLUID.get(syncWidgetsTanks.locations[0]), syncWidgetsTanks.component[0]);
                    blockEntity.chunkOilLevel = syncWidgetsTanks.component[1];
                } else {
                    menu.getBlockEntity().getEnergy(null).setEnergyStored(syncWidgetsTanks.component[0]);

                }

            }
            default -> {
            }
        }
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkRegistry.SYNC_FLUID_TANKS_ID;
    }
}
