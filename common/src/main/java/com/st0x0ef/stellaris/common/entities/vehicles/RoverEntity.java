package com.st0x0ef.stellaris.common.entities.vehicles;

import com.st0x0ef.stellaris.common.data_components.RoverComponent;
import com.st0x0ef.stellaris.common.entities.vehicles.base.AbstractRoverBase;
import com.st0x0ef.stellaris.common.items.VehicleUpgradeItem;
import com.st0x0ef.stellaris.common.menus.RoverMenu;
import com.st0x0ef.stellaris.common.network.packets.SyncRoverComponentPacket;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType;
import com.st0x0ef.stellaris.common.vehicle_upgrade.MotorUpgrade;
import com.st0x0ef.stellaris.common.vehicle_upgrade.SpeedUpgrade;
import com.st0x0ef.stellaris.common.vehicle_upgrade.TankUpgrade;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

public class RoverEntity extends AbstractRoverBase implements HasCustomInventoryScreen, ContainerListener {

    public MotorUpgrade motorUpgrade;
    public TankUpgrade tankUpgrade;
    public SpeedUpgrade speedUpgrade;
    private Item currentFuelItem;
    public final SimpleContainer inventory;

    public RoverComponent roverComponent;

    public RoverEntity(EntityType type, Level worldIn) {
        super(type, worldIn);
        this.inventory = new SimpleContainer(13);

        this.motorUpgrade = MotorUpgrade.getBasic();
        this.tankUpgrade = TankUpgrade.getBasic();
        this.speedUpgrade = SpeedUpgrade.getBasic();
        this.currentFuelItem = ItemsRegistry.FUEL_BUCKET.get();
        this.FUEL = 0;
        this.roverComponent = new RoverComponent(currentFuelItem.toString(), FUEL, motorUpgrade.getFluidTexture(), tankUpgrade.getTankCapacity(), speedUpgrade.getSpeedModifier());
    }

    public void setRoverComponent(RoverComponent roverComponent) {
        this.roverComponent = roverComponent;

        this.motorUpgrade = roverComponent.getMotorUpgrade();
        this.tankUpgrade = roverComponent.getTankUpgrade();
        this.speedUpgrade = roverComponent.getSpeedUpgrade();
        this.FUEL = roverComponent.getFuel();
        this.currentFuelItem = FuelType.getItemBasedOnTypeName(roverComponent.fuelType());
    }

    @Override
    public float getMaxSpeed() {
        return 0.8F * speedUpgrade.getSpeedModifier();
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.6F * speedUpgrade.getSpeedModifier();
    }

    @Override
    public float getAcceleration() {
        return (1.8F * speedUpgrade.getSpeedModifier() * 0.5f) / 2;
    }

    @Override
    public float getMaxRotationSpeed() {
        return 6.8F;
    }
    @Override
    public float getMinRotationSpeed() {
        return 4.8F;
    }

    @Override
    public float getRollResistance() {
        return 1.5F;
    }
    @Override
    public float getRotationModifier() {
        return 2.9F;
    }

    @Override
    public float getPitch() {
        return 0.75F;
    }

    @Override
    public double getPlayerYOffset() {
        return 1f;
    }

    @Override
    public int getPassengerSize() {
        return 2;
    }

    @Override
    public Vector3d[] getPlayerOffsets() {
        return new Vector3d[]{
                new Vector3d(0.45D, 0.5D, -0.35D),
                new Vector3d(0.45D, 0.5D, 0.35D)
        };
    }

    // Enter third-person mode by default for better camera angles
    @Override
    public boolean doesEnterThirdPerson() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        InteractionResult result = InteractionResult.sidedSuccess(this.level().isClientSide);

        if (!this.level().isClientSide) {
            if (player.isCrouching()) {
                if (!tryFillUpRover(player.getMainHandItem().getItem())) {
                    this.openCustomInventoryScreen(player);
                } else {
                    player.getItemInHand(hand).grow(-1);
                    player.getInventory().add(new ItemStack(Items.BUCKET));
                }
                return InteractionResult.CONSUME;
            }
            return InteractionResult.CONSUME;
        }

        return result;
    }

    @Override
    public void tick() {
        super.tick();
        this.checkContainer();
    }

    @Override
    protected boolean isEnoughFuel() {
        return this.getFuel() > 0;
    }

    private void checkContainer() {
        if (this.level().isClientSide) return;

        if (this.getInventory().getItem(2).getItem() instanceof VehicleUpgradeItem item) {
            if (item.getUpgrade() instanceof MotorUpgrade upgrade) {
                this.motorUpgrade = upgrade;
            }
        } else if (this.getInventory().getItem(2).isEmpty()) {
            this.motorUpgrade = MotorUpgrade.getBasic();
        }

        if (this.getInventory().getItem(3).getItem() instanceof VehicleUpgradeItem item) {
            if (item.getUpgrade() instanceof SpeedUpgrade upgrade) {
                this.speedUpgrade = upgrade;
            }
        } else if (this.getInventory().getItem(3).isEmpty()) {
            this.speedUpgrade = SpeedUpgrade.getBasic();
        }

        if (this.getInventory().getItem(4).getItem() instanceof VehicleUpgradeItem item) {
            if (item.getUpgrade() instanceof TankUpgrade upgrade) {
                this.tankUpgrade = upgrade;
            }
        } else if (this.getInventory().getItem(4).isEmpty()) {
            this.tankUpgrade = TankUpgrade.getBasic();
        }

        tryFillUpRover(this.getInventory().getItem(0).getItem());
    }

    private SimpleContainer getInventory()
    {
        return inventory;
    }

    public boolean tryFillUpRover(Item item) {
        if (this.level().isClientSide) return false;
        if (FUEL == tankUpgrade.getTankCapacity() || item == null) {
            return false;
        }

        if (motorUpgrade.getFuelType().equals(FuelType.Type.RADIOACTIVE) && FuelType.Type.Radioactive.getTypeBasedOnItem(item) != null && canPutFuelBasedOnCurrentFuelItem(item)) {
            FUEL += 1000;
            if (FUEL > tankUpgrade.getTankCapacity()) {
                FUEL = tankUpgrade.getTankCapacity();
            }

            inventory.removeItem(0, 1);

            return true;
        }

        if (FuelType.Type.getTypeBasedOnItem(item) == motorUpgrade.getFuelType() && canPutFuelBasedOnCurrentFuelItem(item)) {
            FUEL += 1000;
            if (FUEL > tankUpgrade.getTankCapacity()) {
                FUEL = tankUpgrade.getTankCapacity();
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

    @Override
    public void kill() {
        this.dropEquipment();
        this.spawnRoverItem();

        if (!this.level().isClientSide) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity sourceEntity = source.getEntity();

        if (sourceEntity != null && sourceEntity.isCrouching() && !this.isVehicle()) {
            this.dropEquipment();
            this.spawnRoverItem();

            if (!this.level().isClientSide) {
                this.remove(RemovalReason.DISCARDED);
            }

            return true;
        }

        return false;
    }

    public void syncRocketData(ServerPlayer player) {
        this.roverComponent = new RoverComponent(currentFuelItem.toString(), FUEL, motorUpgrade.getFluidTexture(), tankUpgrade.getTankCapacity(), speedUpgrade.getSpeedModifier());
        if (!level().isClientSide()) {
            NetworkManager.sendToPlayer(player, new SyncRoverComponentPacket(roverComponent));
        }
    }
    private void spawnRoverItem() {
        ItemEntity entityToSpawn = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getRoverItem());
        entityToSpawn.setPickUpDelay(10);
        entityToSpawn.getItem().set(DataComponentsRegistry.ROVER_COMPONENT.get(), roverComponent);

        this.level().addFreshEntity(entityToSpawn);
    }

    private ItemStack getRoverItem() {
        return ItemsRegistry.ROVER.get().getDefaultInstance();
    }

    protected void dropEquipment() {
        for (int i = 0; i < this.inventory.getItems().size(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                this.spawnAtLocation(itemstack);
            }
        }
    }

    @Override
    public void containerChanged(Container container) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

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
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
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
    public void openCustomInventoryScreen(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(FriendlyByteBuf packetByteBuf) {
                    packetByteBuf.writeVarInt(RoverEntity.this.getId());
                }

                @Override
                public Component getDisplayName() {
                    return Component.literal("Rover");
                }

                @Override
                public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                    packetBuffer.writeInt(RoverEntity.this.FUEL);
                    packetBuffer.writeVarInt(RoverEntity.this.getId());
                    return new RoverMenu(syncId, inv, inventory, RoverEntity.this.getId());
                }
            });
        }
    }

    public RoverComponent getRoverComponent()
    {
        return roverComponent;
    }
}
