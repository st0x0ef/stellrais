package com.st0x0ef.stellaris.common.items.armors;

import com.st0x0ef.stellaris.common.data_components.SpaceSuitModules;
import com.st0x0ef.stellaris.common.items.module.SpaceSuitModule;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SpaceSuit extends AbstractSpaceArmor.AbstractSpaceChestplate {

    public SpaceSuit(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, ServerLevel level, Player player) {
        super.onArmorTick(stack, level, player);
        List<SpaceSuitModule> modules = getModules(stack);
        if (!modules.isEmpty()) {
            modules.forEach(spaceSuitModule -> spaceSuitModule.tick(stack, level, player));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);List<SpaceSuitModule> modules = getModules(stack);

        if (!modules.isEmpty()) {
            modules.forEach(spaceSuitModule -> spaceSuitModule.addToTooltips(stack, context, tooltipComponents, tooltipFlag));
        }

        if(Screen.hasShiftDown()) {
            if (!modules.isEmpty()) {
                tooltipComponents.add(Component.translatable("spacesuit.stellaris.modules"));
                modules.forEach(spaceSuitModule -> tooltipComponents.add(spaceSuitModule.displayName().withStyle(ChatFormatting.GRAY)));
            }
        } else {
            tooltipComponents.add(Component.translatable("spacesuit.stellaris.shift_for_modules"));
        }

//
    }

    public NonNullList<ItemStack> scrapArmorModules(ItemStack stack) {
        Iterable<ItemStack> items = stack.getOrDefault(DataComponentsRegistry.SPACE_SUIT_MODULES.get(), new SpaceSuitModules(List.of())).itemsCopy();
        NonNullList<ItemStack> itemsToReturn = NonNullList.create();
        items.forEach(itemsToReturn::add);
        stack.set(DataComponentsRegistry.SPACE_SUIT_MODULES.get(), SpaceSuitModules.empty());
        return itemsToReturn;
    }

    public List<SpaceSuitModule> getModules(ItemStack stack) {
        return stack.getOrDefault(DataComponentsRegistry.SPACE_SUIT_MODULES.get(), SpaceSuitModules.empty()).getModules();
    }
}
