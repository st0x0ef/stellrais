package com.st0x0ef.stellaris.common.oxygen;

import com.st0x0ef.stellaris.common.blocks.entities.machines.OxygenDistributorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;

import java.util.*;

public class OxygenRoom {
    private final BlockPos distributorPos;
    private final Set<BlockPos> oxygenatedPositions;
    private final Queue<BlockPos> positionsToCheck;
    private final ServerLevel level;
    private final DimensionOxygenManager oxygenManager;

    private static final int HALF_ROOM_SIZE = 16;

    public OxygenRoom(ServerLevel level, BlockPos distributorPos) {
        this.distributorPos = distributorPos;
        this.oxygenatedPositions = new LinkedHashSet<>();
        this.positionsToCheck = new LinkedList<>();
        this.level = level;
        this.oxygenManager = GlobalOxygenManager.getInstance().getOrCreateDimensionManager(level);
    }

    public BlockPos getDistributorPosition() {
        return distributorPos;
    }

    public OxygenDistributorBlockEntity getDistributorBlockEntity() {
        return level.getBlockEntity(distributorPos) instanceof OxygenDistributorBlockEntity distributor ? distributor : null;
    }

    public void updateOxygenRoom() {
        positionsToCheck.clear();
        Set<BlockPos> visited = new HashSet<>();

        for (Direction direction : Direction.values()) {
            positionsToCheck.offer(distributorPos.relative(direction));
        }

        while (!positionsToCheck.isEmpty()) {
            BlockPos currentPos = positionsToCheck.poll();
            visited.add(currentPos);
            if (isAirBlock(currentPos)) {
                // Check if the distributor has oxygen and energy
                OxygenDistributorBlockEntity distributor = getDistributorBlockEntity();
                if (distributor != null && distributor.oxygenTank.getFluidValueInTank(distributor.oxygenTank.getTanks()) > 0 && distributor.getEnergy(null).getEnergy() > 0) {
                    // Add hardcoded positions within a 32x32x32 area
                    for (int x = -HALF_ROOM_SIZE; x <= HALF_ROOM_SIZE; x++) {
                        for (int y = -HALF_ROOM_SIZE; y <= HALF_ROOM_SIZE; y++) {
                            for (int z = -HALF_ROOM_SIZE; z <= HALF_ROOM_SIZE; z++) {
                                BlockPos pos = distributorPos.offset(x, y, z);
                                oxygenatedPositions.add(pos);
                                // Consume energy for each position
                                distributor.getEnergy(null).extract(3, false);
                            }
                        }
                    }
                    distributor.setChanged(); // Mark the block entity as changed
                    break; // Exit after adding hardcoded positions
                }
            }
        }
    }

    public void removeOxygenInRoom() {
        oxygenatedPositions.clear();
    }

    public boolean hasOxygenAt(BlockPos pos) {
        return oxygenatedPositions.contains(pos);
    }

    private boolean isAirBlock(BlockPos pos) {
        return level.getBlockState(pos).isAir();
    }


    public boolean breathOxygenAt(BlockPos pos) {
        if (hasOxygenAt(pos)) {
            OxygenDistributorBlockEntity distributor = getDistributorBlockEntity();
            if (distributor != null && distributor.useOxygenAndEnergy()) {
                distributor.setChanged(); // Mark the block entity as changed to trigger updates
                return true;
            }
            oxygenatedPositions.remove(pos);
        }
        return false;
    }
}