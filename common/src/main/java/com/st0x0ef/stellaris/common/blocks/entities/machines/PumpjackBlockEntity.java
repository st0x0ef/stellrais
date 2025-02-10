package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.blocks.machines.CoalGeneratorBlock;
import com.st0x0ef.stellaris.common.menus.PumpjackMenu;
import com.st0x0ef.stellaris.common.network.packets.SyncFluidPacketWithoutDirection;
import com.st0x0ef.stellaris.common.network.packets.SyncOilLevelPacket;
import com.st0x0ef.stellaris.common.registry.BlockEntityRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidUtil;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.SingleFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.Nullable;

public class PumpjackBlockEntity extends BaseEnergyContainerBlockEntity implements FluidProvider.BLOCK {

    private boolean isGenerating = false;
    private static final long oilToExtract = 10;
    public final SingleFluidStorage resultTank;

    public PumpjackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PUMPJACK.get(), pos, state);

        resultTank = new SingleFluidStorage(10000) {
            @Override
            protected void onChange() {
                setChanged();
                if (level != null && level.getServer() != null && !level.getServer().getPlayerList().getPlayers().isEmpty() && !this.getFluidInTank(0).isEmpty())
                    NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(),
                            new SyncFluidPacketWithoutDirection(this.getFluidInTank(0), 0, getBlockPos()));
            }
        };
    }

    @Override
    public void tick() {
        FluidUtil.moveFluidToItem(resultTank.getTanks(), resultTank, getItem(1), 1000);

        ChunkAccess access = this.level.getChunk(this.worldPosition);

        if (!level.isClientSide()) {
            ChunkPos pos = access.getPos();
            NetworkManager.sendToPlayers(level.getServer().getPlayerList().getPlayers(), new SyncOilLevelPacket(access.stellaris$getChunkOilLevel(), pos.x, pos.z));
        }

        int actualOilToExtract = (int) oilToExtract;

        if (access.stellaris$getChunkOilLevel() < oilToExtract) {
            actualOilToExtract = access.stellaris$getChunkOilLevel();

            if (actualOilToExtract == 0) return;
        }

        if (energyContainer.getEnergy() >= 2 * actualOilToExtract) {
            if (resultTank.getFluidValueInTank() + actualOilToExtract <= resultTank.getTankCapacity(0)) {
                access.stellaris$setChunkOilLevel(access.stellaris$getChunkOilLevel() - actualOilToExtract);
                resultTank.fill(FluidStack.create(FluidRegistry.OIL_STILL.get(), actualOilToExtract), false);

                energyContainer.extract(2 * actualOilToExtract, false);
                isGenerating = true;
                setChanged();
            } else {
                isGenerating = false;
            }
        }

        BlockState state;
        if (isGenerating) {
            state = getBlockState().setValue(CoalGeneratorBlock.LIT, true);
        } else {
            state = getBlockState().setValue(CoalGeneratorBlock.LIT, false);
        }
        level.setBlock(getBlockPos(), state, 3);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.stellaris.pumpjack");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new PumpjackMenu(containerId, inventory, this,  this);
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        resultTank.load(tag, provider, "oil");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        resultTank.save(tag, provider, "oil");
    }

    public SingleFluidStorage getResultTank() {
        return resultTank;
    }

    public int chunkOilLevel(Level level) {
        return level.getChunk(getBlockPos()).stellaris$getChunkOilLevel();
    }


    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return this.resultTank;
    }
}