package com.st0x0ef.stellaris.client.screens.tablet;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record TabletEntry(String id, String description, ResourceLocation icon, ResourceLocation hoverIcon, List<Info> infos) {

    public static final Codec<TabletEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(TabletEntry::id),
            Codec.STRING.fieldOf("description").forGetter(TabletEntry::description),
            ResourceLocation.CODEC.fieldOf("icon").forGetter(TabletEntry::icon),
            ResourceLocation.CODEC.fieldOf("hoverIcon").forGetter(TabletEntry::hoverIcon),
            Info.CODEC.listOf().fieldOf("infos").forGetter(TabletEntry::infos)
    ).apply(instance, TabletEntry::new));


    public  record Info(String title, String description, Optional<Image> image, Optional<ResourceLocation> entityId) {

            public static final Codec<Info> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("title").forGetter(Info::title),
                    Codec.STRING.fieldOf("description").forGetter(Info::description),
                    Image.CODEC.optionalFieldOf("image").forGetter(Info::image),
                    ResourceLocation.CODEC.optionalFieldOf("entityId").forGetter(Info::entityId)
            ).apply(instance, Info::new));
    }

    public record Image(ResourceLocation location, int width, int height) {
        public static final Codec<Image> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("location").forGetter(Image::location),
                Codec.INT.fieldOf("width").forGetter(Image::width),
                Codec.INT.fieldOf("height").forGetter(Image::height)
        ).apply(instance, Image::new));
    }
}
