package com.st0x0ef.stellaris.common.vehicle_upgrade;

import com.st0x0ef.stellaris.client.renderers.entities.vehicle.rocket.RocketModel;

public class ModelUpgrade extends VehicleUpgrade {
    final private RocketModel model;

    public ModelUpgrade(RocketModel model) {
        this.model = model;
    }

    public RocketModel getModel() {
        return model;
    }

    public static ModelUpgrade getBasic() {
        return new ModelUpgrade(RocketModel.TINY);
    }
}
