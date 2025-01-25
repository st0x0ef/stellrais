package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.TexturedButton;
import com.st0x0ef.stellaris.common.menus.TabletMenu;
import com.st0x0ef.stellaris.common.registry.EntityRegistry;
import com.st0x0ef.stellaris.common.registry.TagRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TabletMainScreen extends AbstractContainerScreen<TabletMenu> {

    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/tablet/tablet_background.png");
    public static Map<String, TabletEntry> ENTRIES = new HashMap<>();
    public static Map<ResourceLocation, TabletEntry.Info> INFOS = new HashMap<>();

    public ArrayList<TexturedButton> BUTTONS = new ArrayList<>();


    public TabletMainScreen(TabletMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, Component.literal("Tablet"));
        this.imageHeight = 162;
        this.imageWidth = 250;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        guiGraphics.blit(BACKGROUND, this.leftPos , this.topPos , 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void init() {
        super.init();

        AtomicInteger row = new AtomicInteger(0);
        AtomicInteger column = new AtomicInteger(0);

        ENTRIES.forEach((id, entry) -> {

            TexturedButton button = new TexturedButton(this.leftPos + 68 + (column.get() * 30), this.topPos + 60 + (row.get() * 30), 20, 20, Component.translatable(entry.id()), (button1) -> {
                this.minecraft.setScreen(new TabletEntryScreen(Component.translatable(entry.id()), this, this.leftPos, this.topPos, entry));
            }).tex(entry.icon(), entry.hoverIcon()).tooltip(Tooltip.create(Component.translatable(entry.id())));

            if(column.get() == 3) {
                column.set(0);
                row.getAndIncrement();
            } else {
                column.getAndIncrement();
            }
            BUTTONS.add(button);
            this.addRenderableWidget(button);
        });

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.minecraft.player.getStats();
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics,  this.leftPos + 50, this.topPos + 45, this.leftPos + 80, this.topPos + 115, 30, 0.0625F, mouseX, mouseY, this.minecraft.player);

    }

    public void getStats() {
        StatsCounter statsCounter = this.minecraft.player.getStats();


        AtomicInteger bossKilled = new AtomicInteger(0);
        for(EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType.is(TagRegistry.ENTITY_BOSS) && statsCounter.getValue(Stats.ENTITY_KILLED.get(entityType)) > 0 || statsCounter.getValue(Stats.ENTITY_KILLED_BY.get(entityType)) > 0) {
                bossKilled.incrementAndGet();
            }
        }

        statsCounter.getValue(Stats.ENTITY_KILLED.get(EntityRegistry.ALIEN.get()));

    }
}
