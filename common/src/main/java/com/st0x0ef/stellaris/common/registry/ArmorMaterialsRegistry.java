package com.st0x0ef.stellaris.common.registry;

import com.st0x0ef.stellaris.Stellaris;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

public class ArmorMaterialsRegistry {
    public static final ArmorMaterial JET_SUIT = new ArmorMaterial(
            10,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 8);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 11);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_ELYTRA,
            2.0F, 0.0F,
            TagRegistry.DESH_INGOT,
            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"jetsuit"));

    public static final ArmorMaterial SPACE_SUIT = new ArmorMaterial(
            10,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 5);
                map.put(ArmorType.CHESTPLATE, 6);
                map.put(ArmorType.HELMET, 2);
                map.put(ArmorType.BODY, 9);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_ELYTRA,
            2.0F, 0.0F,
            TagRegistry.IRON_INGOT,
            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"spacesuit"));
}
