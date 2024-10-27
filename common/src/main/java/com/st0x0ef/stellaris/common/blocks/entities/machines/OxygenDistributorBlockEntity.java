package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.menus.OxygenGeneratorMenu;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenDistributorBlockEntity extends BaseEnergyContainerBlockEntity implements WrappedFluidBlockEntity {

    public final FluidTank oxygenTank = new FluidTank("oxygenTank", 10);

    public OxygenDistributorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.OXYGEN_DISTRIBUTOR.get(), pos, state);
    }

    @Override
    public void tick() {
        if (this.getItem(0).has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()) && oxygenTank.canGrow(1)) {
            if (OxygenUtils.removeOxygen(getItem(0), 1)) {
                addOyxgen(1);
            }
        }
    }

    public boolean useOxygenAndEnergy() {
        if (oxygenTank.getStack().isEmpty()) {
            return false;
        }

        if (oxygenTank.getAmount() > 0 && getWrappedEnergyContainer().getStoredEnergy() > 0) {
            oxygenTank.shrink(1);
            getWrappedEnergyContainer().extractEnergy(1, false);
            return true;
        }

        return false;
    }

    public void addOyxgen(long amount) {
        if (oxygenTank.getStack().isEmpty()) {
            oxygenTank.setFluid(FluidRegistry.OXYGEN_ATTRIBUTES.getSourceFluid(), amount);
        } else {
            oxygenTank.grow(amount);
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.oxygen_distributor");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new OxygenGeneratorMenu(containerId, inventory, this, this);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        oxygenTank.load(provider, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        oxygenTank.save(provider, tag);
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{oxygenTank};
    }
}
