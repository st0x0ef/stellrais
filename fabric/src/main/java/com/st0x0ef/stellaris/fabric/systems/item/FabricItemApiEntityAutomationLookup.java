package com.st0x0ef.stellaris.fabric.systems.item;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.fabric.systems.generic.FabricEntityContainerLookup;
import com.st0x0ef.stellaris.platform.systems.item.base.ItemContainer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class FabricItemApiEntityAutomationLookup extends FabricEntityContainerLookup<ItemContainer, Direction> {
    public static final FabricItemApiEntityAutomationLookup INSTANCE = new FabricItemApiEntityAutomationLookup();

    public FabricItemApiEntityAutomationLookup() {
        super(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "entity_automation"), ItemContainer.class, Direction.class);
    }

    @SafeVarargs
    @Override
    public final void registerEntityTypes(EntityGetter<ItemContainer, Direction> getter, Supplier<EntityType<?>>... containers) {
        super.registerEntityTypes((entity, context) -> UpdatingItemContainer.of(getter.getContainer(entity, context)), containers);
    }
}
