package com.st0x0ef.stellaris.common.menus;

import com.st0x0ef.stellaris.common.data_components.SpaceSuitModules;
import com.st0x0ef.stellaris.common.items.armors.SpaceSuit;
import com.st0x0ef.stellaris.common.items.module.SpaceSuitModule;
import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.MenuTypesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


public class UpgradeStationMenu extends ItemCombinerMenu {

    public static UpgradeStationMenu create(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new UpgradeStationMenu(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public UpgradeStationMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuTypesRegistry.UPGRADE_STATION_MENU.get(), containerId, playerInventory, access);
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return true;
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        this.inputSlots.setItem(0, ItemStack.EMPTY);
        this.inputSlots.setItem(1, ItemStack.EMPTY);
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(BlocksRegistry.UPGRADE_STATION.get());
    }

    @Override
    public void createResult() {
        if (this.player.level().isClientSide) return;

        ItemStack itemStack = this.inputSlots.getItem(0).copy();
        ItemStack module = this.inputSlots.getItem(1);

        if (module.getItem() instanceof SpaceSuitModule validModule) {
            if (!itemStack.isEmpty() &&
                    !module.isEmpty() &&
                    !SpaceSuitModules.containsInModules(itemStack, module) &&
                    SpaceSuitModules.containsAllInModules(itemStack, validModule.requires())) {

                SpaceSuitModules.Mutable mutable = new SpaceSuitModules.Mutable(itemStack.getOrDefault(DataComponentsRegistry.SPACE_SUIT_MODULES.get(), SpaceSuitModules.empty()));
                itemStack.set(DataComponentsRegistry.SPACE_SUIT_MODULES.get(), mutable.insert(module).toImmutable());

                this.resultSlots.setItem(0, itemStack);
                this.broadcastChanges();

            } else this.resultSlots.setItem(0, ItemStack.EMPTY);
        }
    }



    @Override
    protected @NotNull ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 30, 40, itemStack -> itemStack.getItem() instanceof SpaceSuit)
                .withSlot(1, 78, 40, itemStack -> itemStack.getItem() instanceof SpaceSuitModule)
                .withResultSlot(2, 134, 40)
                .build();
    }
}
