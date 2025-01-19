package com.st0x0ef.stellaris.common.oxygen;

import com.st0x0ef.stellaris.common.registry.TagRegistry;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DimensionOxygenManager {
    private final Set<OxygenRoom> oxygenRooms;
    private final Map<BlockPos, OxygenRoom> roomToCheckIfOpen;
    private final boolean planetHasOxygen;
    private final ServerLevel level;

    public DimensionOxygenManager(ServerLevel level) {
        this.oxygenRooms = new HashSet<>();
        this.roomToCheckIfOpen = new HashMap<>();
        this.level = level;
        this.planetHasOxygen = PlanetUtil.hasOxygen(level);
    }

    public void addOxygenRoomIfMissing(BlockPos distributorPos) {
        if (getOxygenRoom(distributorPos) == null) {
            oxygenRooms.add(new OxygenRoom(level, distributorPos));
            this.updateOxygen();
            this.setChanged();
        }
    }

    public void removeOxygenRoom(BlockPos pos) {
        oxygenRooms.removeIf(room -> room.getDistributorPosition().equals(pos));
        setChanged();
    }

    public void addRoomToCheckIfOpen(BlockPos pos, OxygenRoom room) {
        if (checkIfRoomOpen(pos)) {
            roomToCheckIfOpen.put(pos, room);
        }
    }

    public boolean checkIfRoomOpen(BlockPos pos) {
        return roomToCheckIfOpen.remove(pos) == null;
    }

    private void setChanged() {
        OxygenSavedData data = OxygenSavedData.getData(level);
        data.setDirty();
    }

    public void updateOxygen() {
        if (planetHasOxygen) return;

        oxygenRooms.forEach(OxygenRoom::updateOxygenRoom);
        roomToCheckIfOpen.values().forEach(OxygenRoom::removeOxygenInRoom);
        roomToCheckIfOpen.clear();
    }

    public boolean breath(LivingEntity entity) {
        if (planetHasOxygen || entity.getType().is(TagRegistry.ENTITY_NO_OXYGEN_NEEDED_TAG)) {
            return true;
        }

        if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return true;
        }

        if (breathOxygenAt(entity.getOnPos().above())) {
            return true;
        }

        if (Utils.isLivingInJetSuit(entity) || Utils.isLivingInSpaceSuit(entity)) {
            return OxygenUtils.removeOxygen(entity.getItemBySlot(EquipmentSlot.CHEST), 1L);
        }

        return false;
    }

    public boolean breathOxygenAt(BlockPos pos) {
        return oxygenRooms.stream().anyMatch(room -> room.breathOxygenAt(pos));
    }

    public boolean hasOxygenAt(BlockPos pos) {
        return oxygenRooms.stream().anyMatch(room -> room.hasOxygenAt(pos));
    }

    public Set<OxygenRoom> getOxygenRooms() {
        return oxygenRooms;
    }

    public OxygenRoom getOxygenRoom(BlockPos distributorPos) {
        return oxygenRooms.stream()
                .filter(room -> room.getDistributorPosition().equals(distributorPos))
                .findFirst()
                .orElse(null);
    }

    public void setOxygensRooms(Set<OxygenRoom> rooms) {
        this.oxygenRooms.clear();
        this.oxygenRooms.addAll(rooms);
        this.updateOxygen();
    }
}