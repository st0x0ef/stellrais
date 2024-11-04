package com.st0x0ef.stellaris.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.RocketModel;
import com.st0x0ef.stellaris.common.vehicle_upgrade.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.io.Serializable;

public record RocketComponent(String skin, RocketModel model, String fuelType, int fuel, ResourceLocation fuelTexture, int tankCapacity) implements Serializable {

    public static final Codec<RocketComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("skin").forGetter(RocketComponent::skin),
            RocketModel.CODEC.fieldOf("model").forGetter(RocketComponent::model),
            Codec.STRING.fieldOf("fuel_type").forGetter(RocketComponent::fuelType),
            Codec.INT.fieldOf("fuel").forGetter(RocketComponent::fuel),
            ResourceLocation.CODEC.fieldOf("fuel_texture").forGetter(RocketComponent::fuelTexture),
            Codec.INT.fieldOf("fuel_capacity").forGetter(RocketComponent::tankCapacity)
    ).apply(instance, RocketComponent::new));

    public static final StreamCodec<ByteBuf, RocketComponent> STREAM_CODEC;

    public int getFuel() {
        return fuel;
    }

    public ResourceLocation getFuelTexture() {
        return fuelTexture;
    }

    public ResourceLocation getSkin() {
        return ResourceLocation.parse(skin);
    }

    public SkinUpgrade getSkinUpgrade() {
        return new SkinUpgrade(getSkin()) ;
    }

    public RocketModel getModel() {
        return model;
    }

    public ModelUpgrade getModelUpgrade() {
        return new ModelUpgrade(model) ;
    }

    public MotorUpgrade getMotorUpgrade() {
        return new MotorUpgrade(FuelType.Type.fromString(fuelType), fuelTexture);
    }

    public TankUpgrade getTankUpgrade() {
        return new TankUpgrade(tankCapacity);
    }


    public int getTankCapacity() {
        return tankCapacity;
    }

    public static RocketComponent fromNetwork(RegistryFriendlyByteBuf buffer) {
        return new RocketComponent(buffer.readUtf(), RocketModel.fromString(buffer.readUtf()), buffer.readUtf(), buffer.readInt(), buffer.readResourceLocation(), buffer.readInt());
    }

    public RegistryFriendlyByteBuf toNetwork(RegistryFriendlyByteBuf buffer) {
        buffer.writeUtf(this.skin);
        buffer.writeUtf(this.model().getSerializedName());
        buffer.writeUtf(this.fuelType);
        buffer.writeInt(this.fuel);
        buffer.writeResourceLocation(this.fuelTexture);
        buffer.writeInt(this.tankCapacity);
        return buffer;
    }


    static {
        STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, RocketComponent::skin, ByteBufCodecs.fromCodec(RocketModel.CODEC), RocketComponent::model, ByteBufCodecs.STRING_UTF8, RocketComponent::fuelType, ByteBufCodecs.INT, RocketComponent::fuel, ResourceLocation.STREAM_CODEC, RocketComponent::fuelTexture, ByteBufCodecs.INT, RocketComponent::tankCapacity, RocketComponent::new);
    }
}
