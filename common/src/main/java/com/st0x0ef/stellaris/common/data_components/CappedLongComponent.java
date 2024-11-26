package com.st0x0ef.stellaris.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.io.Serializable;

public record CappedLongComponent(long amount, long capacity) implements Serializable {
    public static final Codec<CappedLongComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("amount").forGetter(CappedLongComponent::amount),
            Codec.LONG.fieldOf("capacity").forGetter(CappedLongComponent::capacity)
    ).apply(instance, CappedLongComponent::new));

    public static final StreamCodec<ByteBuf, CappedLongComponent> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_LONG, CappedLongComponent::amount, ByteBufCodecs.VAR_LONG, CappedLongComponent::capacity, CappedLongComponent::new);

    public static CappedLongComponent fromNetwork(RegistryFriendlyByteBuf buffer) {
        return new CappedLongComponent(buffer.readLong(), buffer.readLong());
    }

    public RegistryFriendlyByteBuf toNetwork(RegistryFriendlyByteBuf buffer) {
        buffer.writeLong(this.amount);
        buffer.writeLong(this.capacity);
        return buffer;
    }
}
