package com.st0x0ef.stellaris.common.vehicle_upgrade;

import com.st0x0ef.stellaris.client.screens.GUISprites;
import net.minecraft.resources.ResourceLocation;

public class MotorUpgrade extends VehicleUpgrade {
    private final FuelType.Type type;
    private final ResourceLocation fluidTexture;

    public MotorUpgrade(FuelType.Type type, ResourceLocation fluidTexture) {
        this.type = type;
        this.fluidTexture = fluidTexture;
    }

    public FuelType.Type getFuelType() {
        if (this.type == null) {
            return getBasic().getFuelType();
        }
        return this.type;
    }
    public ResourceLocation getFluidTexture() {
        return this.fluidTexture;
    }

    public static MotorUpgrade getBasic() {
        return new MotorUpgrade(FuelType.Type.FUEL, GUISprites.FUEL_OVERLAY);
    }
}
