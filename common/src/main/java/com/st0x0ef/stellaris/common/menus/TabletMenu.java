package com.st0x0ef.stellaris.common.menus;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class TabletMenu extends AbstractContainerMenu {
    private final Player player;

    public static TabletMenu create(int syncId, Inventory inventory, FriendlyByteBuf data) {
        return new TabletMenu(syncId, inventory);
    }
    public TabletMenu(int syncId, Inventory playerInventory)
    {
        super(MenuTypesRegistry.TABLET_MENU.get(), syncId);
        this.player = playerInventory.player;
    }
    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        return ItemStack.EMPTY;
    }
    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying();
    }

    public Player getPlayer() {
        return player;
    }
}

