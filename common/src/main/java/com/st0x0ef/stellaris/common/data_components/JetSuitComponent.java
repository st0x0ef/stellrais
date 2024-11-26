package com.st0x0ef.stellaris.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.io.Serializable;

public record JetSuitComponent(JetSuit.ModeType type) implements Serializable {

    public static final Codec<JetSuitComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            JetSuit.ModeType.CODEC.fieldOf("type").forGetter(JetSuitComponent::type)
    ).apply(instance, JetSuitComponent::new));

    public static final StreamCodec<ByteBuf, JetSuitComponent> STREAM_CODEC;

    static {
        STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.fromCodec(JetSuit.ModeType.CODEC), JetSuitComponent::type, JetSuitComponent::new);
    }
}
