package com.st0x0ef.stellaris.common.registry;


import net.minecraft.tags.TagKey;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Tool.Rule;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public record ToolsRegister (TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems) {
    public static final ToolMaterial STEEL;


    public ToolsRegister(TagKey<Block> tagKey, int i, float f, float g, int j, TagKey<Item> tagKey2) {
        this.incorrectBlocksForDrops = tagKey;
        this.durability = i;
        this.speed = f;
        this.attackDamageBonus = g;
        this.enchantmentValue = j;
        this.repairItems = tagKey2;
    }

    private Item.Properties applyCommonProperties(Item.Properties properties) {
        return properties.durability(this.durability).repairable(this.repairItems).enchantable(this.enchantmentValue);
    }

    public Item.Properties applyToolProperties(Item.Properties properties, TagKey<Block> tagKey, float f, float g) {
        HolderGetter<Block> holderGetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyCommonProperties(properties).component(DataComponents.TOOL, new Tool(List.of(Rule.deniesDrops(holderGetter.getOrThrow(this.incorrectBlocksForDrops)), Rule.minesAndDrops(holderGetter.getOrThrow(tagKey), this.speed)), 1.0F, 1)).attributes(this.createToolAttributes(f, g));
    }

    private ItemAttributeModifiers createToolAttributes(float f, float g) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, f + this.attackDamageBonus, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, g, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public Item.Properties applySwordProperties(Item.Properties properties, float f, float g) {
        HolderGetter<Block> holderGetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyCommonProperties(properties).component(DataComponents.TOOL, new Tool(List.of(Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F), Rule.overrideSpeed(holderGetter.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2)).attributes(this.createSwordAttributes(f, g));
    }

    private @NotNull ItemAttributeModifiers createSwordAttributes(float f, float g) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, f + this.attackDamageBonus, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, g, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public TagKey<Block> incorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    public int durability() {
        return this.durability;
    }

    public float speed() {
        return this.speed;
    }

    public float attackDamageBonus() {
        return this.attackDamageBonus;
    }

    public int enchantmentValue() {
        return this.enchantmentValue;
    }

    public TagKey<Item> repairItems() {
        return this.repairItems;
    }

    static {
        STEEL = new ToolMaterial(TagRegistry.INCORRECT_FOR_STEEL_TOOL, 589, 7.0F, 2.6F, 20, ItemTags.WOODEN_TOOL_MATERIALS);

    }
}


