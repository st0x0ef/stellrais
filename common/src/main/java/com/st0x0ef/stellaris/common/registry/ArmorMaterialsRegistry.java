package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;
import java.util.List;

public class ArmorMaterialsRegistry {
    public static final ArmorMaterial JET_SUIT = new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 8);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 11);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_ELYTRA,
            () -> Ingredient.of(ItemsRegistry.DESH_INGOT.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"jetsuit"))),
            2.0F, 0.0F);

    public static final ArmorMaterial SPACE_SUIT = new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 5);
                map.put(ArmorType.CHESTPLATE, 6);
                map.put(ArmorType.HELMET, 2);
                map.put(ArmorType.BODY, 9);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.of(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"spacesuit")),
                    new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"spacesuit"), "_overlay", true)
                    ),
            2.0F, 0.0F);
}
