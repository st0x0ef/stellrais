package com.st0x0ef.stellaris.mixin;

import com.st0x0ef.stellaris.common.items.CustomTabletEntry;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArchitecturySpawnEggItem.class)
public abstract class MobSpawnEggMixin extends SpawnEggItem implements CustomTabletEntry {


    public MobSpawnEggMixin(EntityType<? extends Mob> defaultType, int backgroundColor, int highlightColor, Item.Properties properties) {
        super(defaultType, backgroundColor, highlightColor, properties);
    }

    @Override
    public ResourceLocation getEntryName(ItemStack stack) {
        ArchitecturySpawnEggItem item = (ArchitecturySpawnEggItem) (Object) this;

        return ResourceLocation.fromNamespaceAndPath("mobs", item.getType(stack).arch$registryName().getPath());
    }
}
