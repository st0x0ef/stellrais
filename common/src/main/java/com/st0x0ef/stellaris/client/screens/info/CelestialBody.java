package com.st0x0ef.stellaris.client.screens.info;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CelestialBody {
    public final ResourceLocation texture;
    public final String name;
    public final float width;
    public final float height;
    public final int orbitColor;
    public float x;
    public double y;
    public ResourceLocation dimension;
    public Component translatable;
    public String id;
    public boolean clickable = true;

    public CelestialBody(ResourceLocation texture, String name, float x, float y, float width, float height, int orbitColor, ResourceLocation dimension, Component translatable, String id) {
        this.texture = texture;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.orbitColor = orbitColor;
        this.dimension = dimension;
        this.translatable = translatable;
        this.id = id;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Component getTranslatable() {
        return translatable;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}