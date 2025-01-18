package com.st0x0ef.stellaris.common.items.module;

import com.st0x0ef.stellaris.client.screens.GUISprites;
import com.st0x0ef.stellaris.common.data_components.CappedLongComponent;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.utils.FuelUtils;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FuelModule extends Item implements SpaceSuitModule {

    public FuelModule(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public MutableComponent displayName() {
        return Component.translatable("spacesuit.stellaris.fuel_module");
    }

    @Override
    public void renderToGui(GuiGraphics graphics, DeltaTracker deltaTracker, Player player, ItemStack stack) {
        graphics.blit(GUISprites.SPACESUIT_FUEL_BAR, 5, 16, 0, 0, 37, 10, 37, 10);

        int i = Mth.ceil(Mth.clamp((float) FuelUtils.getFuel(stack) / (float) FuelUtils.getFuelCapacity(stack),
                0.0F, 1.0F) * (24 - 1));
        graphics.blitSprite(GUISprites.SPACESUIT_FULL_BAR_SPRITE, 24, 4, 0, 0, 15, 19, i, 4);
    }

    @Override
    public void addToTooltips(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (!stack.has(DataComponentsRegistry.STORED_FUEL_COMPONENT.get()))
            stack.set(DataComponentsRegistry.STORED_FUEL_COMPONENT.get(), new CappedLongComponent(0, FluidUtil.convertFromNeoMb(10000)));

        tooltipComponents.add(Component.translatable("jetsuit.stellaris.fuel", FuelUtils.getFuel(stack)).append(" §r/§8 " + FuelUtils.getFuelCapacity(stack)));
    }

}
