package com.st0x0ef.stellaris.client.overlays;

import com.st0x0ef.stellaris.client.screens.GUISprites;
import com.st0x0ef.stellaris.common.items.armors.SpaceSuit;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

@Environment(EnvType.CLIENT)
public class SpaceSuitOverlay {


    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;

        if (player!=null) {
            if (Utils.isLivingInSpaceSuit(player)) {
                ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
                if (itemStack.getItem() instanceof SpaceSuit spaceSuit) {


                    //We can add default gui stuff for the space suit here
                    graphics.blit(RenderType::guiTextured, GUISprites.SPACESUIT_OXYGEN_BAR, 5, 5, 0, 0, 37, 10, 37, 10);

                    int i = Mth.ceil(Mth.clamp((float) OxygenUtils.getOxygen(itemStack) / (float) OxygenUtils.getOxygenCapacity(itemStack),
                            0.0F, 1.0F) * (24 - 1));
                    graphics.blitSprite(RenderType::guiTextured, GUISprites.SPACESUIT_FULL_BAR_SPRITE, 24, 4, 0, 0, 15, 8, i, 4);


                    //Logic for modules
                    AtomicInteger y = new AtomicInteger(37);
                    spaceSuit.getModules(itemStack).forEach(module -> {
                        int y1 = module.renderStackedGui(graphics, deltaTracker, player, itemStack, y.get());
                        if (y1 != y.get()) y.set(y1+1);
                        module.renderToGui(graphics, deltaTracker, player, itemStack);
                    });
                    y.set(37);


                }
            }
        }

    }
}
