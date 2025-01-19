package com.st0x0ef.stellaris.client.screens.tablet;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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


    public record Info(String type, String id, String title, String description, Optional<Image> image, Optional<Item> item, Optional<Entity> entity) {

            public static final Codec<Info> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("type").forGetter(Info::type),
                    Codec.STRING.fieldOf("id").forGetter(Info::id),

                    Codec.STRING.fieldOf("title").forGetter(Info::title),
                    Codec.STRING.fieldOf("description").forGetter(Info::description),
                    Image.CODEC.optionalFieldOf("image").forGetter(Info::image),
                    Item.CODEC.optionalFieldOf("item").forGetter(Info::item),
                    Entity.CODEC.optionalFieldOf("entity").forGetter(Info::entity)

            ).apply(instance, Info::new));
    }

    public record Image(ResourceLocation location, int width, int height) {
        public static final Codec<Image> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("location").forGetter(Image::location),
                Codec.INT.fieldOf("width").forGetter(Image::width),
                Codec.INT.fieldOf("height").forGetter(Image::height)
        ).apply(instance, Image::new));
    }

    public record Item(ItemStack stack,  float size) {
        public static final Codec<Item> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ItemStack.CODEC.fieldOf("stack").forGetter(Item::stack),
                Codec.FLOAT.fieldOf("size").forGetter(Item::size)
        ).apply(instance, Item::new));
    }

    public record Entity(ResourceLocation entity, int scale) {
        public static final Codec<Entity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(Entity::entity),
                Codec.INT.fieldOf("scale").forGetter(Entity::scale)
        ).apply(instance, Entity::new));
    }
}
