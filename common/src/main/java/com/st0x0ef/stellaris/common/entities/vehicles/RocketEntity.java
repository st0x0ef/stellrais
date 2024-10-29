package com.st0x0ef.stellaris.common.entities.vehicles;

import com.google.common.collect.Sets;
import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.RocketModel;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data_components.RocketComponent;
import com.st0x0ef.stellaris.common.items.VehicleUpgradeItem;
import com.st0x0ef.stellaris.common.keybinds.KeyVariables;
import com.st0x0ef.stellaris.common.menus.RocketMenu;
import com.st0x0ef.stellaris.common.network.packets.SyncRocketComponentPacket;
import com.st0x0ef.stellaris.common.registry.*;
import com.st0x0ef.stellaris.common.vehicle_upgrade.*;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class RocketEntity extends IVehicleEntity implements HasCustomInventoryScreen, ContainerListener {
    public int START_TIMER;

    public boolean needsModelChange = false;

    public SkinUpgrade SKIN_UPGRADE;
    public ModelUpgrade MODEL_UPGRADE;
    public MotorUpgrade MOTOR_UPGRADE;
    public TankUpgrade TANK_UPGRADE;

    private Item currentFuelItem;

    protected SimpleContainer inventory;

    private RocketComponent rocketComponent;
    private Player lastPlayer;

    private static final EntityDataAccessor<String> DATA_SKIN;
    public static final EntityDataAccessor<Boolean> ROCKET_START;
    private static final EntityDataAccessor<String> DATA_MODEL;

    static {
        DATA_SKIN = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.STRING);
        DATA_MODEL = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.STRING);
        ROCKET_START = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
    }

    public RocketEntity(EntityType<? extends RocketEntity> entityType, Level level) {
        this(entityType, level, SkinUpgrade.getBasic());
    }

    protected RocketEntity(EntityType<? extends RocketEntity> entityType, Level level, SkinUpgrade skinUpgrade) {
        super(entityType, level);

        this.SKIN_UPGRADE = skinUpgrade;
        this.MODEL_UPGRADE = ModelUpgrade.getBasic();
        this.MOTOR_UPGRADE = MotorUpgrade.getBasic();
        this.TANK_UPGRADE = TankUpgrade.getBasic();

        this.START_TIMER = 0;
        this.FUEL = 0;

        this.currentFuelItem = ItemsRegistry.FUEL_BUCKET.get();
        this.rocketComponent = new RocketComponent(SKIN_UPGRADE.getRocketSkinLocation().toString(), RocketModel.fromString(MODEL_UPGRADE.getModel().toString()), currentFuelItem.toString(), FUEL, MOTOR_UPGRADE.getFluidTexture(), TANK_UPGRADE.getTankCapacity());
        this.inventory = new SimpleContainer(14);
    }

    public void setRocketComponent(RocketComponent rocketComponent) {
        this.rocketComponent = rocketComponent;

        this.MODEL_UPGRADE = rocketComponent.getModelUpgrade();
        this.SKIN_UPGRADE = rocketComponent.getSkinUpgrade();
        this.MOTOR_UPGRADE = rocketComponent.getMotorUpgrade();
        this.TANK_UPGRADE = rocketComponent.getTankUpgrade();
        this.FUEL = rocketComponent.getFuel();
        this.currentFuelItem = FuelType.getItemBasedOnTypeName(rocketComponent.fuelType());
    }

    @Override
    public void tick() {
        super.tick();

        this.rocketExplosion();
        this.burnEntities();
        this.checkContainer();

        if (KeyVariables.isHoldingJump(getFirstPlayerPassenger())) {
            startRocket();
        }

        if (this.entityData.get(ROCKET_START)) {
            this.spawnParticle();
            this.startTimerAndFlyMovement();
        }

        if (this.getY() > 600) {
            AtomicBoolean firstPlayer = new AtomicBoolean(true);

            this.getPassengers().forEach((entity -> {
                if (entity instanceof Player passenger) {
                    if(firstPlayer.get()) {
                        this.openPlanetMenu(passenger);
                        firstPlayer.set(false);
                    } else {
                        this.openWaitMenu(passenger);
                    }
                }
            }));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.put("InventoryCustom", this.inventory.createTag(registryAccess()));
        compound.putInt("fuel", FUEL);

        if (FUEL != 0 && currentFuelItem != null) {
            if (FuelType.Type.getTypeBasedOnItem(currentFuelItem) != null) {
                compound.putString("currentFuelItemType", FuelType.Type.getTypeBasedOnItem(currentFuelItem).getSerializedName());
            } else if (FuelType.Type.Radioactive.getTypeBasedOnItem(currentFuelItem) != null) {
                compound.putString("currentFuelItemType", FuelType.Type.Radioactive.getTypeBasedOnItem(currentFuelItem).getSerializedName());
            }
        }

        ListTag listTag = new ListTag();

        for(int i = 1; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemStack = this.inventory.getItem(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)(i - 1));
                listTag.add(itemStack.save(this.registryAccess(), compoundTag));
            }
        }

        compound.put("Items", listTag);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        ListTag inventoryCustom = compound.getList("InventoryCustom", 14);
        this.inventory.fromTag(inventoryCustom, registryAccess());
        FUEL = compound.getInt("fuel");

        if (FUEL != 0) {
            currentFuelItem = FuelType.getItemBasedOnTypeName(compound.getString("currentFuelItemType"));
        }

        ListTag listTag = compound.getList("Items", 10);

        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            int j = compoundTag.getByte("Slot") & 255;
            if (j < this.inventory.getContainerSize() - 1) {
                this.inventory.setItem(j + 1, ItemStack.parse(this.registryAccess(), compoundTag).orElse(ItemStack.EMPTY));
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SKIN, SkinUpgrade.getBasic().getRocketSkinLocation().toString());
        builder.define(DATA_MODEL, ModelUpgrade.getBasic().getModel().toString());
        builder.define(ROCKET_START, false);
    }

    public SkinUpgrade getSkinData() {
        return new SkinUpgrade(ResourceLocation.parse(this.entityData.get(DATA_SKIN)));
    }

    public void setSkinData() {
        this.entityData.set(DATA_SKIN, SKIN_UPGRADE.getRocketSkinLocation().toString());
    }

    public ModelUpgrade getModelData() {
        return new ModelUpgrade(RocketModel.fromString(this.entityData.get(DATA_MODEL)));
    }

    public void setModelData() {
        this.entityData.set(DATA_MODEL, MODEL_UPGRADE.getModel().toString());
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        InteractionResult result = InteractionResult.sidedSuccess(this.level().isClientSide);

        if (!this.level().isClientSide) {
            if (player.isCrouching()) {
                if (!tryFillUpRocket(player.getMainHandItem().getItem())) {
                    this.openCustomInventoryScreen(player);
                } else {
                    player.getItemInHand(hand).grow(-1);
                    player.getInventory().add(new ItemStack(Items.BUCKET));
                }
                return InteractionResult.CONSUME;
            }

            if (this.canPlayerRide()) {
                this.doPlayerRide(player);
            }

            return InteractionResult.CONSUME;
        }

        return result;
    }

    public boolean canPlayerRide() {
        int maxPlayer = this.MODEL_UPGRADE.getModel().getMaxPlayerNumber();
        return this.getPassengers().size() < maxPlayer;
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        return this.position().add(this.getPassengerAttachmentPoint(entity, getDimensions(this.getPose()),1.0F)).subtract(0d,3.15d,0d);
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        this.lastPlayer = player;
        if (player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(FriendlyByteBuf packetByteBuf) {
                    packetByteBuf.writeVarInt(RocketEntity.this.getId());
                }

                @Override
                public Component getDisplayName() {
                    return Component.literal("Rocket");
                }

                @Override
                public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                    packetBuffer.writeInt(RocketEntity.this.FUEL);
                    packetBuffer.writeVarInt(RocketEntity.this.getId());
                    return new RocketMenu(syncId, inv, inventory, RocketEntity.this.getId());
                }
            });
        }
    }

    @Override
    public void kill() {
        this.dropEquipment();
        this.spawnRocketItem();

        if (!this.level().isClientSide) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity sourceEntity = source.getEntity();

        if (sourceEntity != null && sourceEntity.isCrouching() && !this.isVehicle()) {
            this.spawnRocketItem();
            this.dropEquipment();

            if (!this.level().isClientSide) {
                this.remove(RemovalReason.DISCARDED);
            }

            return true;
        }

        return false;
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        Vec3[] avector3d = new Vec3[]{getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot()), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 22.5F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 22.5F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 45.0F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 45.0F)};
        Set<BlockPos> set = Sets.newLinkedHashSet();
        double d0 = this.getBoundingBox().maxY;
        double d1 = this.getBoundingBox().minY - 0.5D;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for(Vec3 vector3d : avector3d) {
            blockpos$mutable.set(this.getX() + vector3d.x, d0, this.getZ() + vector3d.z);

            for(double d2 = d0; d2 > d1; --d2) {
                set.add(blockpos$mutable.immutable());
                blockpos$mutable.move(Direction.DOWN);
            }
        }

        for(BlockPos blockpos : set) {
            if (!this.level().getFluidState(blockpos).is(FluidTags.LAVA)) {
                double d3 = this.level().getBlockFloorHeight(blockpos);
                if (DismountHelper.isBlockFloorValid(d3)) {
                    Vec3 vector3d1 = Vec3.upFromBottomCenterOf(blockpos, d3);

                    for(Pose pose : livingEntity.getDismountPoses()) {
                        if (DismountHelper.isBlockFloorValid(this.level().getBlockFloorHeight(blockpos))) {
                            livingEntity.setPose(pose);
                            return vector3d1;
                        }
                    }
                }
            }
        }

        return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return null;
    }


    public void spawnParticle() {
        if (this.level() instanceof ServerLevel level) {
            Vec3 vec = this.getDeltaMovement();

            if (START_TIMER == 200) {
                for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
                    level.sendParticles(player, (ParticleOptions) ParticleTypes.FLAME, true, this.getX() - vec.x, this.getY() - vec.y - 2.2, this.getZ() - vec.z, 20, 0.1, 0.1, 0.1, 0.001);
                    level.sendParticles(player, (ParticleOptions) ParticleTypes.FLAME, true, this.getX() - vec.x, this.getY() - vec.y - 3.2, this.getZ() - vec.z, 10, 0.1, 0.1, 0.1, 0.04);
                }
            } else {
                for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
                    level.sendParticles(player, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX() - vec.x, this.getY() - vec.y - 0.1, this.getZ() - vec.z, 6, 0.1, 0.1, 0.1, 0.023);
                }
            }
        }
    }

    public void startRocket() {
        Player player = this.getFirstPlayerPassenger();

        if (player != null) {
            if (this.FUEL > 0 || player.isCreative()) {
                if (!this.entityData.get(ROCKET_START)) {
                    this.entityData.set(ROCKET_START, true);
                    this.level().playSound(null, this, SoundRegistry.ROCKET_SOUND.get(), SoundSource.NEUTRAL, 1, 1);
                }
            } else {
                player.displayClientMessage(Component.translatable("text.stellaris.rocket.fuel", this.MOTOR_UPGRADE.getFuelType().getSerializedName()), true);
            }
        }
    }


    public void startTimerAndFlyMovement() {
        if (START_TIMER < 200) {
            START_TIMER++;
        }

        if (START_TIMER == 200) {
            if (this.getDeltaMovement().y < this.getRocketSpeed() - 0.1) {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getRocketSpeed(), this.getDeltaMovement().z);
            }
        }
    }

    private void destroyRocket(boolean explode) {
        if (!this.level().isClientSide) {
            if (explode) {
                this.level().explode(this, this.getX(), this.getBoundingBox().maxY, this.getZ(), 10, true, Level.ExplosionInteraction.TNT);
            }
            this.remove(RemovalReason.DISCARDED);
        }
    }


    public void rocketExplosion() {
        if (START_TIMER == 200) {
            if (this.getDeltaMovement().y < -0.07) {
                destroyRocket(true);
            }
        }
    }

    public Player getFirstPlayerPassenger() {
        if (!this.getPassengers().isEmpty() && this.getPassengers().getFirst() instanceof Player player) {
            return player;
        }
        return null;
    }

    protected void dropEquipment() {
        for (int i = 0; i < this.inventory.getItems().size(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                this.spawnAtLocation(itemstack);
            }
        }
    }

    public ItemStack getRocketItem() {
        ItemStack rocket = new ItemStack(ItemsRegistry.ROCKET.get(), 1);
        rocket.set(DataComponentsRegistry.ROCKET_COMPONENT.get(), rocketComponent);

        return rocket;
    }

    @Override
    public void containerChanged(Container container) {

    }

    protected void doPlayerRide(Entity player) {
        if (!this.level().isClientSide) {
            Vec3 entityPos = player.getPosition(0);
            player.setPosRaw(entityPos.x, entityPos.y + 40.0, entityPos.z);
            player.startRiding(this, true);
        }
    }

    private void checkContainer() {
        if (this.level().isClientSide) return;

        if (this.getInventory().getItem(10).getItem() instanceof VehicleUpgradeItem item) {
            if (item.getUpgrade() instanceof MotorUpgrade upgrade) {
                this.MOTOR_UPGRADE = upgrade;
            }
        } else if (this.getInventory().getItem(10).isEmpty()) {
            this.MOTOR_UPGRADE = MotorUpgrade.getBasic();
        }

        if (this.getInventory().getItem(11).getItem() instanceof VehicleUpgradeItem item) {
            if (item.getUpgrade() instanceof TankUpgrade upgrade) {
                this.TANK_UPGRADE = upgrade;
            }
        } else if (this.getInventory().getItem(11).isEmpty()) {
            this.TANK_UPGRADE = TankUpgrade.getBasic();
        }

        if (this.getInventory().getItem(12).getItem() instanceof VehicleUpgradeItem item) {
            if (item.getUpgrade() instanceof SkinUpgrade upgrade) {
                this.SKIN_UPGRADE = upgrade;
                setSkinData();
            }
        } else if (this.getInventory().getItem(12).isEmpty()) {
            this.SKIN_UPGRADE = SkinUpgrade.getBasic();
            setSkinData();
        }

        if (this.getInventory().getItem(13).getItem() instanceof VehicleUpgradeItem item) {
            if (item.getUpgrade() instanceof ModelUpgrade upgrade) {
                if (this.MODEL_UPGRADE.getModel() != upgrade.getModel()){
                    this.MODEL_UPGRADE = upgrade;
                    setModelData();
                    needsModelChange = true;
                    changeRocketModel();
                }
            }
        } else if (this.getInventory().getItem(13).isEmpty()) {
            this.MODEL_UPGRADE = ModelUpgrade.getBasic();
            setModelData();
            if (needsModelChange) {
                needsModelChange = false;
                changeRocketModel();
            }
        }

        tryFillUpRocket(this.getInventory().getItem(0).getItem());
    }

    public boolean tryFillUpRocket(Item item) {
        if (this.level().isClientSide) return false;
        if (FUEL == TANK_UPGRADE.getTankCapacity() || item == null) {
            return false;
        }

        if (MOTOR_UPGRADE.getFuelType().equals(FuelType.Type.RADIOACTIVE) && FuelType.Type.Radioactive.getTypeBasedOnItem(item) != null && canPutFuelBasedOnCurrentFuelItem(item)) {
            FUEL += 1000;
            if (FUEL > TANK_UPGRADE.getTankCapacity()) {
                FUEL = TANK_UPGRADE.getTankCapacity();
            }

            inventory.removeItem(0, 1);

            return true;
        }

        if (FuelType.Type.getTypeBasedOnItem(item) == MOTOR_UPGRADE.getFuelType() && canPutFuelBasedOnCurrentFuelItem(item)) {
            FUEL += 1000;
            if (FUEL > TANK_UPGRADE.getTankCapacity()) {
                FUEL = TANK_UPGRADE.getTankCapacity();
            }

            if (inventory.removeItem(0, 1).is(ItemsRegistry.FUEL_BUCKET.get())) {
                inventory.setItem(1, new ItemStack(Items.BUCKET, inventory.getItem(1).getCount()+1));
            }

            return true;
        }

        return false;
    }

    private boolean canPutFuelBasedOnCurrentFuelItem(Item item) {
        if (FUEL == 0) {
            currentFuelItem = item;
            return true;
        }

        return currentFuelItem == item;
    }

    private void openPlanetMenu(Player player) {
        if(player == null) return;

        if(!player.getEntityData().get(EntityData.DATA_PLANET_MENU_OPEN)) {
            player.setNoGravity(true);
            player.getVehicle().setNoGravity(true);
            PlanetUtil.openPlanetSelectionMenu(player, false);
            player.getEntityData().set(EntityData.DATA_PLANET_MENU_OPEN, true);
        }
    }

    private void openWaitMenu(Player player) {
        if(player == null) return;

        if(!player.getEntityData().get(EntityData.DATA_PLANET_MENU_OPEN)) {
            player.setNoGravity(true);
            player.getVehicle().setNoGravity(true);
            PlanetUtil.openWaitMenu(player, this.getFirstPlayerPassenger().getDisplayName().getString());
            player.getEntityData().set(EntityData.DATA_PLANET_MENU_OPEN, true);
        }
    }

    public void burnEntities() {
        if (START_TIMER == 200) {
            AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 2, this.getZ()), 2, 2, 2);
            List<LivingEntity> entities = this.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, aabb);

            for (LivingEntity entity : entities) {
                entity.setRemainingFireTicks(40);
            }
        }
    }

    protected void spawnRocketItem() {
        ItemEntity entityToSpawn = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getRocketItem());
        entityToSpawn.setPickUpDelay(10);
        entityToSpawn.getItem().set(DataComponentsRegistry.ROCKET_COMPONENT.get(), rocketComponent);

        this.level().addFreshEntity(entityToSpawn);
    }

    public int getTankCapacity() {
        return this.TANK_UPGRADE.getTankCapacity();
    }

    public Container getInventory() {
        return this.inventory;
    }

    public double getRocketSpeed() {
        return 0.8;
    }

    public ResourceLocation getFullSkinTexture() {
        String texture = getSkinData().getRocketSkinLocation().toString();
        if (MODEL_UPGRADE != null) {
            texture = texture.replace("normal", getModelData().getModel().toString());
        }

        return ResourceLocation.parse(texture);
    }

    public boolean canGoTo (Planet actual, Planet destination) {
        return Mth.abs(actual.distanceFromEarth() - destination.distanceFromEarth()) <= FuelType.getMegametersTraveled(this.rocketComponent.fuel(), FuelType.getItemBasedOnLoacation(ResourceLocation.parse(this.rocketComponent.fuelType())));
    }

    public void syncRocketData(ServerPlayer player) {
        this.rocketComponent = new RocketComponent(SKIN_UPGRADE.getRocketSkinLocation().toString(), RocketModel.fromString(MODEL_UPGRADE.getModel().toString()), currentFuelItem.toString(), FUEL, MOTOR_UPGRADE.getFluidTexture(), TANK_UPGRADE.getTankCapacity());
        if (!level().isClientSide()) {
            NetworkManager.sendToPlayer(player, new SyncRocketComponentPacket(rocketComponent));
        }
    }

    public void changeRocketModel() {
        if (lastPlayer != null) {
            lastPlayer.closeContainer();
        }

        NonNullList<ItemStack> itemStacks = this.inventory.getItems();
        Vec3 pos = this.position();
        EntityType<? extends RocketEntity> newRocketType = getEntityType(this.MODEL_UPGRADE);
        RocketEntity newRocketEntity = new RocketEntity(newRocketType, this.level());
        newRocketEntity.setPos(pos);
        newRocketEntity.setYRot(this.getYRot());
        newRocketEntity.MODEL_UPGRADE = this.MODEL_UPGRADE;
        newRocketEntity.setModelData();
        newRocketEntity.SKIN_UPGRADE = this.SKIN_UPGRADE;
        newRocketEntity.setSkinData();
        newRocketEntity.MOTOR_UPGRADE = this.MOTOR_UPGRADE;
        newRocketEntity.TANK_UPGRADE = this.TANK_UPGRADE;
        newRocketEntity.FUEL = this.FUEL;
        newRocketEntity.needsModelChange = this.needsModelChange;

        for (int i = 0; i < inventory.getContainerSize(); i++) newRocketEntity.inventory.setItem(i, itemStacks.get(i));

        this.remove(RemovalReason.DISCARDED);
        newRocketEntity.level().addFreshEntity(newRocketEntity);
        for (Entity passenger : getPassengers()) passenger.startRiding(newRocketEntity);
        newRocketEntity.openCustomInventoryScreen(lastPlayer);
    }

    public EntityType<? extends RocketEntity> getEntityType(ModelUpgrade upgrade) {
        return switch (upgrade.getModel()) {
            case TINY -> EntityRegistry.TINY_ROCKET.get();
            case SMALL -> EntityRegistry.SMALL_ROCKET.get();
            case NORMAL -> EntityRegistry.NORMAL_ROCKET.get();
            case BIG -> EntityRegistry.BIG_ROCKET.get();
        };
    }

    public RocketComponent getRocketComponent() {
        return this.rocketComponent;
    }
}
