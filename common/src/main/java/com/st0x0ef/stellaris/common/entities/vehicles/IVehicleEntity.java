package com.st0x0ef.stellaris.common.entities.vehicles;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.phys.Vec3;

public abstract class IVehicleEntity extends Entity{
    public int FUEL;

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;

    private float speed;
    private boolean discardFriction = false;
    public float xxa;
    public float yya;
    public float zza;

    public IVehicleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    /** Enable Interact with the Entity */
    @Override
    public boolean isPickable() {
        return true;
    }

    /** Interact with the Entity Gui,Spawn Egg... */
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();

        /** ROT Anim */
        this.tickLerp();
        this.rotAnim();

        /** Movement Physic */
        Vec3 vec3 = this.getDeltaMovement();
        double d1 = vec3.x;
        double d3 = vec3.y;
        double d5 = vec3.z;
        if (Math.abs(vec3.x) < 0.003D) {
            d1 = 0.0D;
        }

        if (Math.abs(vec3.y) < 0.003D) {
            d3 = 0.0D;
        }

        if (Math.abs(vec3.z) < 0.003D) {
            d5 = 0.0D;
        }

        this.setDeltaMovement(d1, d3, d5);

        this.xxa *= 0.98F;
        this.zza *= 0.98F;
        this.travel(new Vec3(this.xxa, this.yya, this.zza));
    }

    public void rotAnim() {
        while(this.getYRot() - this.yRotO < -180.0F) {
            this.yRotO -= 360.0F;
        }

        while(this.getYRot() - this.yRotO >= 180.0F) {
            this.yRotO += 360.0F;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        this.lerpSteps = steps;
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d2 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d4 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d6 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)d6 / (float)this.lerpSteps);
            this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d2, d4);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }

    /** Movement Physic */
    public void travel(Vec3 vec3) {
        if (this.isControlledByLocalInstance()) {
            double d0 = 0.08D;

            boolean flag = this.getDeltaMovement().y <= 0.0D;

            if (this.isInWater()) {
                double d8 = this.getY();
                float f5 = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
                float f6 = 0.02F;

                this.moveRelative(f6, vec3);
                this.move(MoverType.SELF, this.getDeltaMovement());
                Vec3 vec36 = this.getDeltaMovement();
                if (this.horizontalCollision) {
                    vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
                }

                this.setDeltaMovement(vec36.multiply(f5, 0.8F, f5));
                Vec3 vec32 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
                this.setDeltaMovement(vec32);
                if (this.horizontalCollision && this.isFree(vec32.x, vec32.y + (double)0.6F - this.getY() + d8, vec32.z)) {
                    this.setDeltaMovement(vec32.x, 0.3F, vec32.z);
                }
            } else if (this.isInLava()) {
                double d7 = this.getY();
                this.moveRelative(0.02F, vec3);
                this.move(MoverType.SELF, this.getDeltaMovement());
                if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.5D, 0.8F, 0.5D));
                    Vec3 vec33 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
                    this.setDeltaMovement(vec33);
                } else {
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                }

                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vec3 vec34 = this.getDeltaMovement();
                if (this.horizontalCollision && this.isFree(vec34.x, vec34.y + (double)0.6F - this.getY() + d7, vec34.z)) {
                    this.setDeltaMovement(vec34.x, 0.3F, vec34.z);
                }
            } else {
                BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
                float f3 = 1.0F;
                float f4 = this.onGround() ? f3 * 0.91F : 0.91F;
                Vec3 vec35 = this.handleRelativeFrictionAndCalculateMovement(vec3, f3);
                double d2 = vec35.y;
                if (this.level().isClientSide && !this.level().hasChunkAt(blockpos)) {
                    if (this.getY() > (double)this.level().getMinBuildHeight()) {
                        d2 = -0.1D;
                    } else {
                        d2 = 0.0D;
                    }
                } else if (!this.isNoGravity()) {
                    d2 -= d0;
                }

                if (this.shouldDiscardFriction()) {
                    this.setDeltaMovement(vec35.x, d2, vec35.z);
                } else {
                    this.setDeltaMovement(vec35.x * (double)f4, d2 * (double)0.98F, vec35.z * (double)f4);
                }
            }
        }
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    protected float getWaterSlowDown() {
        return 0.8F;
    }


    public Vec3 getFluidFallingAdjustedMovement(double p_20995_, boolean p_20996_, Vec3 p_20997_) {
        if (!this.isNoGravity() && !this.isSprinting()) {
            double d0;
            if (p_20996_ && Math.abs(p_20997_.y - 0.005D) >= 0.003D && Math.abs(p_20997_.y - p_20995_ / 16.0D) < 0.003D) {
                d0 = -0.003D;
            } else {
                d0 = p_20997_.y - p_20995_ / 16.0D;
            }

            return new Vec3(p_20997_.x, d0, p_20997_.z);
        } else {
            return p_20997_;
        }
    }

    public float getFrictionInfluencedSpeed(float delta) {
        return this.getSpeed() * (0.216F / (delta * delta * delta));
    }

    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 vector, float delta) {
        this.moveRelative(this.getFrictionInfluencedSpeed(delta), vector);
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 vec3 = this.getDeltaMovement();
        if ((this.horizontalCollision) && (this.getBlockStateOn().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
            vec3 = new Vec3(vec3.x, 0.2D, vec3.z);
        }

        return vec3;
    }

    public boolean shouldDiscardFriction() {
        return this.discardFriction;
    }

    public void setDiscardFriction(boolean p_147245_) {
        this.discardFriction = p_147245_;
    }

    public void setEntityRotation(Entity vehicle, float rotation) {
        vehicle.setYRot(vehicle.getYRot() + rotation);
        vehicle.setYBodyRot(vehicle.getYRot());
        vehicle.yRotO = vehicle.getYRot();
    }

    public int getFuel() {
        return this.FUEL;
    }


    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return NetworkManager.createAddEntityPacket(this, entity);
    }
}
