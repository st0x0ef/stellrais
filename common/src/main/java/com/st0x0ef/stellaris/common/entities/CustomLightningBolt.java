package com.st0x0ef.stellaris.common.entities;

import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CustomLightningBolt extends LightningBolt {

    public static final EntityDataAccessor<Float> RED;
    public static final EntityDataAccessor<Float> GREEN;
    public static final EntityDataAccessor<Float> BLUE;

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(RED, 0.45F);
        builder.define(GREEN, 0.45F);
        builder.define(BLUE, 0.5F);

        super.defineSynchedData(builder);
    }

    public CustomLightningBolt(EntityType<? extends LightningBolt> entityType, Level level) {
        super(entityType, level);
    }

    public CustomLightningBolt setCustomColor(Vec3 color) {
        this.entityData.set(RED, (float) color.x / 255);
        this.entityData.set(GREEN, (float) color.y / 255);
        this.entityData.set(BLUE, (float) color.z / 255);
        return this;
    }


    static {
        RED = SynchedEntityData.defineId(CustomLightningBolt.class, EntityDataSerializers.FLOAT);
        GREEN = SynchedEntityData.defineId(CustomLightningBolt.class, EntityDataSerializers.FLOAT);
        BLUE = SynchedEntityData.defineId(CustomLightningBolt.class, EntityDataSerializers.FLOAT);
    }

}
