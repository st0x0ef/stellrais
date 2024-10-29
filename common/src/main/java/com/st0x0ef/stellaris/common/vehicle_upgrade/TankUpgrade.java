package com.st0x0ef.stellaris.common.vehicle_upgrade;

public class TankUpgrade extends VehicleUpgrade {
    private final int tankCapacity;

    public TankUpgrade(int tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public int getTankCapacity() {
        return tankCapacity;
    }

    public static TankUpgrade getBasic() {
        return new TankUpgrade(3000);
    }
}
