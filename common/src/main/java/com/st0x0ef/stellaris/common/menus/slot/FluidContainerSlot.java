package com.st0x0ef.stellaris.common.menus.slot;

import com.st0x0ef.stellaris.common.items.OxygenTankItem;
import com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor;
import dev.architectury.hooks.fluid.FluidBucketHooks;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

public class FluidContainerSlot extends Slot {

    private final boolean emptyOnly;
    private final boolean allowTanks;

    public FluidContainerSlot(Container container, int slot, int x, int y, boolean emptyOnly, boolean allowTanks) {
        super(container, slot, x, y);
        this.emptyOnly = emptyOnly;
        this.allowTanks = allowTanks;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return (stack.getItem() instanceof BucketItem item
                && (!emptyOnly || FluidBucketHooks.getFluid(item).isSame(Fluids.EMPTY)))
                || (allowTanks && stack.getItem() instanceof OxygenTankItem)
                || (allowTanks && stack.getItem() instanceof AbstractSpaceArmor.AbstractSpaceChestplate);
        // TODO : check for fluid tank from other mods when potentials will be fully integrated
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
