package com.st0x0ef.stellaris.common.menus;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class TabletMenu extends AbstractContainerMenu {
    private final Player player;
    private ResourceLocation entry = null;

    public static TabletMenu create(int syncId, Inventory inventory, FriendlyByteBuf data) {
        return new TabletMenu(syncId, inventory, data.readResourceLocation());
    }
    public TabletMenu(int syncId, Inventory playerInventory, ResourceLocation entry)
    {
        super(MenuTypesRegistry.TABLET_MENU.get(), syncId);
        this.player = playerInventory.player;
        this.entry = entry;
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

    public ResourceLocation getEntry() {
        return entry;
    }
}

