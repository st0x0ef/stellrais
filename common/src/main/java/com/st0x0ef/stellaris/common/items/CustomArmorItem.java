package com.st0x0ef.stellaris.common.items;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorType;

public class CustomArmorItem extends ArmorItem {

    public CustomArmorItem(net.minecraft.world.item.equipment.ArmorMaterial material, ArmorType type, Properties properties) {
        super(material, type, properties.stacksTo(1));
    }

}
