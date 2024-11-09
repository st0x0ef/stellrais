package com.st0x0ef.stellaris.common.registry;

import com.google.common.base.Suppliers;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.function.Supplier;


public enum ToolsRegister implements Tier {
    STEEL(TagRegistry.INCORRECT_FOR_STEEL_TOOL, 589, 7.0F, 2.6F, 20, () -> Ingredient.of(ItemsRegistry.STEEL_INGOT.get()));
        private final TagKey<Block> incorrectBlocksForDrops;
        private final int uses;
        private final float speed;
        private final float damage;
        private final int enchantmentValue;
        private final Supplier<Ingredient> repairIngredient;

         ToolsRegister(final TagKey incorrectBlockForDrops, final int uses, final float speed, final float damage, final int enchantmentValue, final Supplier<Ingredient> repairIngredient) {
            this.incorrectBlocksForDrops = incorrectBlockForDrops;
            this.uses = uses;
            this.speed = speed;
            this.damage = damage;
            this.enchantmentValue = enchantmentValue;
            Objects.requireNonNull(repairIngredient);
             this.repairIngredient = Suppliers.memoize(repairIngredient::get);
        }

        public int getUses() {
            return this.uses;
        }

        public float getSpeed() {
            return this.speed;
        }

        public float getAttackDamageBonus() {
            return this.damage;
        }

        public TagKey<Block> getIncorrectBlocksForDrops() {
            return this.incorrectBlocksForDrops;
        }

        public int getEnchantmentValue() {
            return this.enchantmentValue;
        }

        public Ingredient getRepairIngredient() {
            return this.repairIngredient.get();
        }
    }

