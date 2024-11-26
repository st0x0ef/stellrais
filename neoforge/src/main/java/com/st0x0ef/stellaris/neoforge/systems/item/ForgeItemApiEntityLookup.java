package com.st0x0ef.stellaris.neoforge.systems.item;

import com.st0x0ef.stellaris.common.systems.generic.base.EntityContainerLookup;
import com.st0x0ef.stellaris.neoforge.systems.generic.NeoForgeEntityContainerLookup;
import com.st0x0ef.stellaris.platform.systems.item.base.ItemContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.function.Supplier;

public class ForgeItemApiEntityLookup implements EntityContainerLookup<ItemContainer, Void> {
    public static final ForgeItemApiEntityLookup INSTANCE = new ForgeItemApiEntityLookup();
    private final NeoForgeEntityContainerLookup<IItemHandler, Void> lookup = new NeoForgeEntityContainerLookup<>(Capabilities.ItemHandler.ENTITY);

    @Override
    public ItemContainer find(Entity entity, Void context) {
        return PlatformItemContainer.of(lookup.find(entity, context));
    }

    @SafeVarargs
    @Override
    public final void registerEntityTypes(EntityGetter<ItemContainer, Void> getter, Supplier<EntityType<?>>... containers) {
        lookup.registerEntityTypes((entity, context) -> ForgeItemContainer.of(getter.getContainer(entity, context)), containers);
    }
}
