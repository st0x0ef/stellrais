package com.st0x0ef.stellaris.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OxygenTankOverlay {

    public static final ResourceLocation OXYGEN_TANK = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/oxygen_tank.png");
    public static final ResourceLocation OXYGEN_TANK_FULL = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/oxygen_tank_full.png");


    public static void render(GuiGraphics graphics, float partialTick) {
        Player player = Minecraft.getInstance().player;

        if (Utils.isLivingInJetSuit(player)) {
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            Minecraft mc = Minecraft.getInstance();

            if(chest.getItem() instanceof AbstractSpaceArmor.Chestplate chestplate) {
                long oxygen = chestplate.oxygenTank.getFluidValueInTank(0);
                long maxOxygen = chestplate.oxygenTank.getTankCapacity(0);

                int x = 5;
                int y = 5;

                int textureWidth = 62;
                int textureHeight = 52;

                /** DRAW OXYGEN TANK */
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                ScreenHelper.drawTexture(x, y, textureWidth, textureHeight, OXYGEN_TANK, false);
                ScreenHelper.drawVertical(graphics, x, y, textureWidth, textureHeight, oxygen, maxOxygen, OXYGEN_TANK_FULL, false);

                /** OXYGEN AMOUNT TEXT */
                Font font = mc.font;
                Component text = Component.translatable("general." + Stellaris.MODID + ".oxygen").append(": ").withStyle(ChatFormatting.BLUE).append("§7" + oxygen / (maxOxygen / 100) + "%");
                graphics.drawString(font, text, (x + (textureWidth - font.width(text)) / 2), y + textureHeight + 3, 0xFFFFFF);
            }

        }

    }
}
