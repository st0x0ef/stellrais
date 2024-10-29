package com.st0x0ef.stellaris.common.menus.slot.upgrade;

import com.st0x0ef.stellaris.common.items.VehicleUpgradeItem;
import com.st0x0ef.stellaris.common.menus.RocketMenu;
import com.st0x0ef.stellaris.common.vehicle_upgrade.TankUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TankUpgradeSlot extends Slot {

    public TankUpgradeSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() instanceof VehicleUpgradeItem item) {
            return item.getUpgrade() instanceof TankUpgrade;
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