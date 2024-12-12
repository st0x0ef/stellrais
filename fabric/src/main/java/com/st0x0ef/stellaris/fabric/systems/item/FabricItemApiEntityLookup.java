package com.st0x0ef.stellaris.fabric.systems.item;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.fabric.systems.generic.FabricEntityContainerLookup;
import com.st0x0ef.stellaris.platform.systems.item.base.ItemContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class FabricItemApiEntityLookup extends FabricEntityContainerLookup<ItemContainer, Void> {
    public static final FabricItemApiEntityLookup INSTANCE = new FabricItemApiEntityLookup();

    public FabricItemApiEntityLookup() {
        super(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "entity"), ItemContainer.class, Void.class);
    }

    @SafeVarargs
    @Override
    public final void registerEntityTypes(EntityGetter<ItemContainer, Void> getter, Supplier<EntityType<?>>... containers) {
        super.registerEntityTypes((entity, context) -> UpdatingItemContainer.of(getter.getContainer(entity, context)), containers);
    }
}
