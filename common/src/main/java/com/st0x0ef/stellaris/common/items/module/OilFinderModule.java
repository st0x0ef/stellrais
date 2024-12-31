package com.st0x0ef.stellaris.common.items.module;

import com.st0x0ef.stellaris.common.oil.GlobalOilManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class OilFinderModule extends Item implements SpaceSuitModule {

    int oilLevel = 0;

    public OilFinderModule(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void tick(ItemStack stack, Level level, Player player) {
        SpaceSuitModule.super.tick(stack, level, player);
        if(!level.isClientSide) {
            this.oilLevel = GlobalOilManager.getInstance().getOrCreateDimensionManager((ServerLevel) level).getOrCreateOilLevel(level.getChunk(player.blockPosition()).getPos());
        }
    }

    @Override
    public int renderStackedGui(GuiGraphics graphics, DeltaTracker deltaTracker, Player player, ItemStack stack, int y) {
        graphics.drawString(Minecraft.getInstance().font, Component.literal("Oil Level: " + oilLevel), 5, y, 0xFFFFFF);
        y += Minecraft.getInstance().font.lineHeight;
        return y;
    }

    @Override
    public MutableComponent displayName() {
        return Component.translatable("spacesuit.stellaris.oil_finder");
    }

    @Override
    public void addToTooltips(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("spacesuit.stellaris.oil_finder.tooltip"));
    }

}
