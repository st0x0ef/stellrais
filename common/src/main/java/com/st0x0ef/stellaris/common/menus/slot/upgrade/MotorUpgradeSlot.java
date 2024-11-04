package com.st0x0ef.stellaris.common.menus.slot.upgrade;

import com.st0x0ef.stellaris.common.entities.vehicles.IVehicleEntity;
import com.st0x0ef.stellaris.common.items.VehicleUpgradeItem;
import com.st0x0ef.stellaris.common.menus.RocketMenu;
import com.st0x0ef.stellaris.common.vehicle_upgrade.MotorUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MotorUpgradeSlot extends Slot {
    private final IVehicleEntity vehicle;

    public MotorUpgradeSlot(Container container, int slot, int x, int y, IVehicleEntity vehicle) {
        super(container, slot, x, y);
        this.vehicle = vehicle;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (vehicle.getFuel() == 0 && stack.getItem() instanceof VehicleUpgradeItem item) {
            return item.getUpgrade() instanceof MotorUpgrade;
        }

        return false;
    }

    @Override
    public boolean mayPickup(Player player) {
        if (player.containerMenu instanceof RocketMenu menu) {
            return menu.getRocket().getFuel() == 0;
        }

        return false;
    }
}