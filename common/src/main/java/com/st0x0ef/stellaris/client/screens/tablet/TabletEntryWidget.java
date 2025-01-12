package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TabletEntryWidget extends AbstractScrollWidget {

    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "icon/scroller");

    private AtomicInteger finalHeight = new AtomicInteger(0);
    private final TabletEntry entry;
    private final int baseScreenWidth;

    public TabletEntryWidget(int x, int y, int width, int height, Component message, TabletEntry entry, int baseScreenWidth) {
        super(x, y, width, height, message);
        this.entry = entry;
        this.baseScreenWidth = baseScreenWidth;
    }

    @Override
    protected int getInnerHeight() {
        return finalHeight.get() + finalHeight.get() / 5;
    }

    @Override
    protected void renderBorder(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        //We don't want to render the border
    }


    @Override
    protected double scrollRate() {
        return 9;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        finalHeight.set(0);
        for (int i = 0; i < this.entry.infos().size(); i++) {
            TabletEntry.Info info = this.entry.infos().get(i);

            guiGraphics.drawCenteredString(getFont(), info.title(), this.baseScreenWidth / 2,
                    getY() + finalHeight.get() + 1 + (i * 20) , Utils.getColorHexCode("white"));


            int descriptionHeight = renderDescription(info.description(), getX() + 5, getY() + finalHeight.get() + 20 + (i * 20), getWidth() - 5, guiGraphics);
            finalHeight.addAndGet(descriptionHeight);

            int finalI = i;


            info.item().ifPresent((item) -> {
                guiGraphics.renderItem(item.stack(), this.baseScreenWidth / 2, getY() + finalHeight.get() + 35 + (finalI * 20));
                if (item.decoration()) {
                    guiGraphics.renderItemDecorations(getFont(), new ItemStack(ItemsRegistry.OXYGEN_TANK), this.baseScreenWidth / 2, getY() + finalHeight.get() + 35 + (finalI * 20));
                }

                finalHeight.addAndGet(35);
            });


            info.image().ifPresent((image) -> {
                int height = getY() + 40 + finalHeight.get() + (finalI * 20);
                guiGraphics.blitSprite(image.location(), this.baseScreenWidth / 2 - image.width() / 2, height, image.width(), image.height());
                finalHeight.addAndGet(image.height() + 40 );
            });

            info.entity().ifPresent((entity) -> {
                int height = getY() + 40 + finalHeight.get() + (finalI * 20);
                Entity entity1 = ScreenHelper.createEntity(Minecraft.getInstance().level, entity.entity());

                ScreenHelper.renderEntityInInventory(guiGraphics, this.baseScreenWidth / 2, height + 45, entity.scale(), new Vector3f(), new Quaternionf(-1, 0, 0, 0), null, entity1);
                finalHeight.addAndGet(80);

            });
        }

    }

    public boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
       return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    public void renderScrollBar(GuiGraphics guiGraphics) {
        int i = this.getScrollBarHeight();
        int j = this.getX() + this.width;

        int k = Math.max(this.getY(), (int) this.scrollAmount() * (this.height - i) / this.getMaxScrollAmount() + this.getY());
        RenderSystem.enableBlend();
        guiGraphics.blitSprite(SCROLLER_SPRITE, j, k, 8, i);
        RenderSystem.disableBlend();
    }

    public int renderDescription(String description, int x, int y, int maxWidth, GuiGraphics guiGraphics) {
        List<String> lines = createLines(description, maxWidth);
        for(int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(getFont(), lines.get(i), x, y + (i * 10), Utils.getColorHexCode("white"));
        }
        return lines.size() * getFont().lineHeight;
    }

    public List<String> createLines(String message, int maxWidth) {
        String[] words = message.split("\\s+");
        List<String> lines = new ArrayList<>();

        String currentLine = "";
        AtomicInteger remainingWords = new AtomicInteger(words.length);
        for(String word : words) {
            remainingWords.getAndDecrement();
            if (word.contains("[br]")) {
                lines.add(currentLine);
                currentLine = "";
                continue;
            }

            if(Minecraft.getInstance().font.width(currentLine + word) < maxWidth) {
                if(remainingWords.get() == 0) {
                    lines.add(currentLine + " " + word);
                    break;
                }

                if(currentLine.isEmpty()) {
                    currentLine = word;
                } else {
                    currentLine += " " + word;
                }
            } else {
                lines.add(currentLine);
                currentLine = word;
            }
        }
        return lines;
    }

    public Font getFont() {
        return Minecraft.getInstance().font;
    }
}
