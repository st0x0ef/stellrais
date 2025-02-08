package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.items.IOxygenStorageItem;
import com.st0x0ef.stellaris.common.menus.OxygenDistributorMenu;
import com.st0x0ef.stellaris.common.network.packets.SyncFluidPacket;
import com.st0x0ef.stellaris.common.network.packets.SyncFluidPacketWithoutDirection;
import com.st0x0ef.stellaris.common.oxygen.GlobalOxygenManager;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.networking.NetworkManager;
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

    public final FluidStorage oxygenTank = new FluidStorage(1, 3000) {
        @Override
        protected void onChange(int tank) {
            setChanged();
            if (level != null && level.getServer() != null && !level.getServer().getPlayerList().getPlayers().isEmpty() && !this.getFluidInTank(0).isEmpty())
                NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(),
                        new SyncFluidPacketWithoutDirection(this.getFluidInTank(0), 0, getBlockPos()));
        }
    };;


    public OxygenDistributorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.OXYGEN_DISTRIBUTOR.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level instanceof ServerLevel serverLevel) {
            if (this.getItem(0).getItem() instanceof IOxygenStorageItem oxygenStorage && oxygenTank.canGrow() && this.energyContainer.getEnergy() > 1) {
                if (oxygenStorage.getOxygenTank().drain(oxygenStorage.getOxygenTank().getFluidInTank(0).copyWithAmount(1), false).getAmount() > 0) {
                    addOxygen(1);
                    this.energyContainer.extract(1, false);
                }
            }

            if (oxygenTank.getFluidValueInTank(oxygenTank.getTanks()) > 0) {
                GlobalOxygenManager.getInstance().getOrCreateDimensionManager(serverLevel).addOxygenRoomIfMissing(getBlockPos());
            }
        }
    }

    public boolean useOxygenAndEnergy() {
        if (oxygenTank.getFluidInTank(oxygenTank.getTanks()).isEmpty() || oxygenTank.getFluidValueInTank(oxygenTank.getTanks()) == 0) {
            if (this.getItem(0).getItem() instanceof IOxygenStorageItem oxygenStorage && this.energyContainer.getEnergy() > 0) {
                if (oxygenStorage.getOxygenTank().drain(oxygenStorage.getOxygenTank().getFluidInTank(0).copyWithAmount(1), false).getAmount() > 0) {
                    this.energyContainer.extract(1, false);
                    return true;
                }
            }
        }

        if (oxygenTank.getFluidValueInTank(oxygenTank.getTanks()) > 0 && this.energyContainer.getEnergy() > 0) {
            oxygenTank.drain(FluidStack.create(FluidRegistry.OXYGEN_ATTRIBUTES.getSourceFluid(), 1), false);
            this.energyContainer.extract(1, false);
            return true;
        }

        return false;
    }

    public void addOxygen(long amount) {
        oxygenTank.fill(FluidStack.create(FluidRegistry.OXYGEN_ATTRIBUTES.getSourceFluid(), amount), false);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.oxygen_distributor");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new OxygenDistributorMenu(containerId, inventory, this, this);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        oxygenTank.load(tag, provider, "oxygen");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        oxygenTank.save(tag, provider, "oxygen");
    }


    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return oxygenTank;
    }
}
