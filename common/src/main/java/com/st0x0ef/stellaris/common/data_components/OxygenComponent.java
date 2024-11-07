package com.st0x0ef.stellaris.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.io.Serializable;

public record OxygenComponent(long oxygen, long capacity) implements Serializable {
    public static final Codec<OxygenComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("oxygen").forGetter(OxygenComponent::oxygen),
            Codec.LONG.fieldOf("capacity").forGetter(OxygenComponent::capacity)
    ).apply(instance, OxygenComponent::new));

    public static final StreamCodec<ByteBuf, OxygenComponent> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_LONG, OxygenComponent::oxygen, ByteBufCodecs.VAR_LONG, OxygenComponent::capacity, OxygenComponent::new);
}
