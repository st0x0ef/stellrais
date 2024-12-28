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
            if (isAirBlock(currentPos) && addOxygenatedPosition(currentPos)) {
                if (isOnBorderBox(currentPos)) {
                    oxygenManager.addRoomToCheckIfOpen(currentPos, this);
                } else {
                    for (Direction direction : Direction.values()) {
                        BlockPos nextPos = currentPos.relative(direction);
                        if (!visited.contains(nextPos)) {
                            positionsToCheck.offer(nextPos);
                        }
                    }
                }
            }
        }
    }

    public void removeOxygenInRoom() {
        oxygenatedPositions.clear();
    }

    private boolean isOnBorderBox(BlockPos pos) {
        int dx = Math.abs(pos.getX() - distributorPos.getX());
        int dy = Math.abs(pos.getY() - distributorPos.getY());
        int dz = Math.abs(pos.getZ() - distributorPos.getZ());
        return dx == HALF_ROOM_SIZE || dy == HALF_ROOM_SIZE || dz == HALF_ROOM_SIZE;
    }

    public boolean hasOxygenAt(BlockPos pos) {
        return oxygenatedPositions.contains(pos);
    }

    private boolean isAirBlock(BlockPos pos) {
        return level.getBlockState(pos).isAir();
    }

    private boolean addOxygenatedPosition(BlockPos pos) {
        OxygenDistributorBlockEntity distributor = getDistributorBlockEntity();
        if (distributor != null && distributor.useOxygenAndEnergy()) {
            oxygenatedPositions.add(pos);
            return true;
        }
        return false;
    }

    public boolean breathOxygenAt(BlockPos pos) {
        if (hasOxygenAt(pos)) {
            OxygenDistributorBlockEntity distributor = getDistributorBlockEntity();
            if (distributor != null && distributor.useOxygenAndEnergy()) {
                return true;
            }
            oxygenatedPositions.remove(pos);
        }
        return false;
    }
}