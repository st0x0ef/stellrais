package com.st0x0ef.stellaris.common.entities.vehicles;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.menus.LanderMenu;
import com.st0x0ef.stellaris.common.registry.EntityRegistry;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LanderEntity extends IVehicleEntity implements HasCustomInventoryScreen {
    protected SimpleContainer inventory;

    public LanderEntity(Level level) {
        this(EntityRegistry.LANDER.get(), level);
    }

    public LanderEntity(EntityType<? extends LanderEntity> type, Level level) {
        super(type, level);

        this.inventory = new SimpleContainer(15);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void push(Entity p_21294_) {
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        return this.position().add(this.getPassengerAttachmentPoint(entity, getDimensions(this.getPose()),1.0F)).add(0d,2d,0d);
    }

    @Override
    public void kill(ServerLevel level) {
        this.dropEquipment();

        if (!this.level().isClientSide) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (source.getEntity() != null && source.getEntity().isCrouching()
                && !this.isVehicle()) {
            this.dropEquipment();

            if (!this.level().isClientSide) {
                this.remove(RemovalReason.DISCARDED);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean causeFallDamage(float p_150347_, float p_150348_, DamageSource p_150349_) {
        if (p_150347_ >= 3.0F) {

            if (!this.level().isClientSide) {
                this.level().explode(null, this.getX(), this.getY(), this.getZ(), 10, true,
                        Level.ExplosionInteraction.TNT);

                this.remove(RemovalReason.DISCARDED);
            }
        }

        return super.causeFallDamage(p_150347_, p_150348_, p_150349_);
    }

    protected void dropEquipment() {
        for (int i = 0; i < this.inventory.getItems().size(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                this.spawnAtLocation((ServerLevel) level(), itemstack);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.put("InventoryCustom", this.inventory.createTag(registryAccess()));

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        ListTag inventoryCustom = compound.getList("InventoryCustom", 15);
        this.inventory.fromTag(inventoryCustom, registryAccess());

    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        InteractionResult result = InteractionResult.SUCCESS;

        if (!this.level().isClientSide) {
            if (player.isCrouching()) {
                this.openCustomInventoryScreen(player);

                return InteractionResult.CONSUME;
            }

            player.startRiding(this);
            return InteractionResult.CONSUME;
        }

        return result;
    }


    @Override
    public void tick() {
        super.tick();

        if (KeyVariables.isHoldingJump(getFirstPlayerPassenger())) {
            slowDownLander();
        }
    }

    public Player getFirstPlayerPassenger() {
        if (!this.getPassengers().isEmpty() && this.getPassengers().getFirst() instanceof Player player) {
            return player;
        }
        return null;
    }


    public void slowDownLander() {
        Vec3 vec = this.getDeltaMovement();

        if (!this.onGround()) {
            if (vec.y() < -0.05) {
                this.setDeltaMovement(vec.x(), vec.y() * 0.75, vec.z());
            }

            this.fallDistance = (float) (vec.y() * (-1) * 4.5);

            if (this.level() instanceof ServerLevel level) {
                for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
                    level.sendParticles(player, ParticleTypes.SPIT, true, this.getX(),
                            this.getY() - 0.3, this.getZ(), 3, 0.1, 0.1, 0.1, 0.001);
                }
            }
        }
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        MenuRegistry.openExtendedMenu((ServerPlayer) player, new ExtendedMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buf) {

            }

            @Override
            public Component getDisplayName() {
                return Component.translatable("container.entity." + Stellaris.MODID + ".lander");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
                RegistryFriendlyByteBuf packetBuffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess());
                packetBuffer.writeVarInt(LanderEntity.this.getId());
                return new LanderMenu(id, playerInv, inventory);
            }
        });
    }

    public Container getInventory() {
        return this.inventory;
    }
}