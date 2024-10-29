package com.st0x0ef.stellaris.common.blocks.entities;

import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GlobeBlockEntity extends BlockEntity {

    private float rotationalInertia = 0.0f;
    private float yaw = 0.0f;
    private float yaw0 = 0.0f;

    public GlobeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GLOBE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.rotationalInertia = tag.getFloat("inertia");
        this.yaw = tag.getFloat("yaw");
        this.yaw0 = tag.getFloat("yaw0");
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putFloat("inertia", this.rotationalInertia);
        tag.putFloat("yaw", this.yaw);
        tag.putFloat("yaw0", this.yaw0);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();

        Level level = this.getLevel();

        if (level instanceof ServerLevel serverLevel) {

            for (ServerPlayer p : serverLevel.getServer().getPlayerList().getPlayers()) {
                p.connection.send(this.getUpdatePacket());
            }
        }
    }

    public void tick() {
        if (this.level != null) {
            if (this.getRotationalInertia() > 0) {
                this.setRotationalInertia(this.getRotationalInertia() - 0.0075f);

                if (this.getRotationalInertia() < 0) {
                    this.setRotationalInertia(0);
                }

                this.setYaw0(this.getYaw());
                this.setYaw(this.getYaw() - this.getRotationalInertia());

                if (this.getRotationalInertia() == 0) {
                    if (!this.level.isClientSide) {
                        this.setChanged();
                    }
                }
            }
        }
    }

    public float getRotationalInertia() {
        return this.rotationalInertia;
    }

    public void setRotationalInertia(float value) {
        this.rotationalInertia = value;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float value) {
        this.yaw = value;
    }

    public float getYaw0() {
        return this.yaw0;
    }

    public void setYaw0(float value) {
        this.yaw0 = value;
    }


}
