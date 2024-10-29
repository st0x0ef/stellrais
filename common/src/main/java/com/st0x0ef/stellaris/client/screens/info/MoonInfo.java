package com.st0x0ef.stellaris.client.screens.info;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MoonInfo extends CelestialBody {
    public final PlanetInfo orbitCenter;
    private final long orbitalPeriod;
    public final double orbitRadius;
    public double currentAngle;

    public MoonInfo(ResourceLocation texture, String name, double orbitRadius, long orbitalPeriod, int width, int height, PlanetInfo orbitCenter, ResourceKey<Level> dimension, Component translatable, String id) {
        super(texture, name, 0, 0, width, height, 0xFFFFFF, dimension.location(), translatable, id);
        this.orbitRadius = orbitRadius;
        this.orbitalPeriod = orbitalPeriod;
        this.orbitCenter = orbitCenter;
        this.currentAngle = 0;
        this.translatable = translatable;
        this.id = id;
    }

    public MoonInfo setClickable(boolean clickable) {
        this.clickable = clickable;
        return this;
    }

    public double updateAngle(long currentTime) {
        this.currentAngle = (currentTime % orbitalPeriod) * (2 * Math.PI / orbitalPeriod);

        return this.currentAngle;
    }

    public void updatePosition() {
        this.x = (float) (orbitCenter.x + orbitRadius * Math.cos(currentAngle));
        this.y = orbitCenter.y + orbitRadius * Math.sin(currentAngle);
    }
}
