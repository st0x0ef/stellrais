package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.menus.OxygenGeneratorMenu;
import com.st0x0ef.stellaris.common.oxygen.GlobalOxygenManager;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidTank;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OxygenDistributorBlockEntity extends BaseEnergyContainerBlockEntity implements FluidProvider.BLOCK {

    public final FluidTank oxygenTank = new FluidTank(10);


    public OxygenDistributorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.OXYGEN_DISTRIBUTOR.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level instanceof ServerLevel serverLevel) {
            if (this.getItem(0).has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()) && oxygenTank.canGrow() && this.energyContainer.getEnergy() > 1) {
                if (OxygenUtils.removeOxygen(getItem(0), 1)) {
                    addOxygen(1);
                    this.energyContainer.extract(1, false);
                }
            }

            if (oxygenTank.getFluidValue() > 0) {
                GlobalOxygenManager.getInstance().getOrCreateDimensionManager(serverLevel).addOxygenRoomIfMissing(getBlockPos());
            }
        }
    }

    public boolean useOxygenAndEnergy() {
        if (oxygenTank.getFluidStack().isEmpty() || oxygenTank.getFluidValue() == 0) {
            if (this.getItem(0).has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get()) && this.energyContainer.getEnergy() > 0) {
                if (OxygenUtils.removeOxygen(getItem(0), 1)) {
                    this.energyContainer.extract(1, false);
                    return true;
                }
            }
        }

        if (oxygenTank.getFluidValue() > 0 && this.energyContainer.getEnergy() > 0) {
            oxygenTank.drainFluid(FluidStack.create(FluidRegistry.OXYGEN_ATTRIBUTES.getSourceFluid(), 1), false);
            this.energyContainer.extract(1, false);
            return true;
        }

        return false;
    }

    public void addOxygen(long amount) {
        oxygenTank.fillFluid(FluidStack.create(FluidRegistry.OXYGEN_ATTRIBUTES.getSourceFluid(), amount), false);
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
        oxygenTank.load(tag, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        oxygenTank.save(tag, provider);
    }


    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return oxygenTank;
    }
}
