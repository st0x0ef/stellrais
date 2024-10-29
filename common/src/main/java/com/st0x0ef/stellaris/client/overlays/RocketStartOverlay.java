package com.st0x0ef.stellaris.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class RocketStartOverlay {

    public static final ResourceLocation TIMER_1 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_1.png");
    public static final ResourceLocation TIMER_2 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_2.png");
    public static final ResourceLocation TIMER_3 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_3.png");
    public static final ResourceLocation TIMER_4 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_4.png");
    public static final ResourceLocation TIMER_5 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_5.png");
    public static final ResourceLocation TIMER_6 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_6.png");
    public static final ResourceLocation TIMER_7 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_7.png");
    public static final ResourceLocation TIMER_8 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_8.png");
    public static final ResourceLocation TIMER_9 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_9.png");
    public static final ResourceLocation TIMER_10 = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/overlay/timer/timer_10.png");

    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player.getVehicle() instanceof RocketEntity) {
            Entity vehicle = Minecraft.getInstance().player.getVehicle();
            int timer = 0;

            /** GET TIMER */
            if (vehicle instanceof RocketEntity rocket) {
                timer = rocket.START_TIMER;

                /** CHECK IF ROCKET IS STARTED */
                if(!rocket.getEntityData().get(RocketEntity.ROCKET_START)) return;
            }

            int timerWidth = graphics.guiWidth() / 2 - 31;
            int timerHeight = graphics.guiHeight() / 2 / 2;

            /** TIMER */
            if (timer > -1 && timer < 20) {
                RenderSystem.setShaderTexture(0, TIMER_10);
                graphics.blit(RenderType::guiTextured, TIMER_10, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 20 && timer < 40) {
                RenderSystem.setShaderTexture(0, TIMER_9);
                graphics.blit(RenderType::guiTextured, TIMER_9, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 40 && timer < 60) {
                RenderSystem.setShaderTexture(0, TIMER_8);
                graphics.blit(RenderType::guiTextured, TIMER_8, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 60 && timer < 80) {
                RenderSystem.setShaderTexture(0, TIMER_7);
                graphics.blit(RenderType::guiTextured, TIMER_7, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 80 && timer < 100) {
                RenderSystem.setShaderTexture(0, TIMER_6);
                graphics.blit(RenderType::guiTextured, TIMER_6, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 100 && timer < 120) {
                RenderSystem.setShaderTexture(0, TIMER_5);
                graphics.blit(RenderType::guiTextured, TIMER_5, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 120 && timer < 140) {
                RenderSystem.setShaderTexture(0, TIMER_4);
                graphics.blit(RenderType::guiTextured, TIMER_4, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 140 && timer < 160) {
                RenderSystem.setShaderTexture(0, TIMER_3);
                graphics.blit(RenderType::guiTextured, TIMER_3, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 160 && timer < 180) {
                RenderSystem.setShaderTexture(0, TIMER_2);
                graphics.blit(RenderType::guiTextured, TIMER_2, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 180 && timer < 200) {
                RenderSystem.setShaderTexture(0, TIMER_1);
                graphics.blit(RenderType::guiTextured, TIMER_1, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
        }
    }
}
