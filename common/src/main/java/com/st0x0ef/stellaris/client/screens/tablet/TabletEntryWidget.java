package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import com.st0x0ef.stellaris.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TabletEntryWidget extends AbstractScrollWidget {

    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "icon/scroller");

    private final AtomicInteger finalHeight = new AtomicInteger(0);
    private TabletEntry.Info info;
    private final int baseScreenWidth;

    public TabletEntryWidget(int x, int y, int width, int height, Component message, TabletEntry.Info info, int baseScreenWidth) {
        super(x, y, width, height, message);
        this.info = info;
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

        if (this.info == null) return;

        finalHeight.set(0);
        guiGraphics.drawCenteredString(getFont(), info.title(), this.baseScreenWidth / 2,
                getY() + finalHeight.get() +20 , Utils.getColorHexCode("white"));

        int descriptionHeight = renderDescriptionWithEveryWords(info.description(), getX() + 5, getY() + finalHeight.get() + 20 + 20, getWidth() - 5, guiGraphics);
        finalHeight.addAndGet(descriptionHeight);

        info.item().ifPresent((item) -> {
            ScreenHelper.renderItemWithCustomSize(guiGraphics, Minecraft.getInstance(), item.stack(), this.baseScreenWidth / 2 -(int) item.size() / 2, getY() + finalHeight.get() + 35 + 20, item.size());
            finalHeight.addAndGet(35 + (int) (item.size() / 4));
        });


        info.image().ifPresent((image) -> {
            int height = getY() + 40 + finalHeight.get() + 20;
            guiGraphics.blitSprite(image.location(), this.baseScreenWidth / 2 - image.width() / 2, height, image.width(), image.height());
            finalHeight.addAndGet(image.height() + 40 );
        });

        info.entity().ifPresent((entity) -> {
            int height = getY() + 40 + finalHeight.get() + entity.scale();
            Entity entity1 = ScreenHelper.createEntity(Minecraft.getInstance().level, entity.entity());
            ScreenHelper.renderEntityInInventory(guiGraphics, this.baseScreenWidth / 2, height + 45, entity.scale(), new Vector3f(), new Quaternionf(-1, 0, 0, 0), null, entity1);
            finalHeight.addAndGet(80);

        });

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

    public int renderDescriptionWithEveryWords(String description, int x, int y, int maxWidth, GuiGraphics guiGraphics) {
        List<ArrayList<String>> lines = createLines(description, maxWidth);
        for(int i = 0; i < lines.size(); i++) {
            ArrayList<String> words = lines.get(i);
            AtomicInteger width = new AtomicInteger(0);
            for (String word : words) {

                String color = "white";

                if (word.contains("[color=")) {
                    color = word.substring(7, word.indexOf("]"));
                    word = word.replace("[color=" + color + "]", "");
                }

                guiGraphics.drawString(getFont(), word, x + width.get(), y + (i * getFont().lineHeight), Utils.getColorHexCode(color));
                width.addAndGet(Minecraft.getInstance().font.width(word + " "));
            }
            width.set(0);
        }
        return lines.size() * getFont().lineHeight;
    }


    public List<ArrayList<String>> createLines(String message, int maxWidth) {
        String[] words = message.split("\\s+");

        List<ArrayList<String>> lines2 = new ArrayList<>();

        ArrayList<String> wordsInLine = new ArrayList<>();

        AtomicInteger remainingWords = new AtomicInteger(words.length);
        AtomicInteger width = new AtomicInteger(words.length);

        for(String word : words) {
            remainingWords.getAndDecrement();

            int wordWidth = Minecraft.getInstance().font.width(word + " ");

            if (word.contains("[br]")) {
                lines2.add(wordsInLine);
                wordsInLine.clear();

                word = word.replace("[br]", "");
            } else if (word.contains("[color=")) {
                String wordWithoutColor = word.replace("[color=" + word.substring(7, word.indexOf("]")) + "]", "");
                wordWidth = Minecraft.getInstance().font.width(wordWithoutColor + " ");
            }


            if(wordWidth + width.get() < maxWidth) {
                if(remainingWords.get() == 0) {
                    wordsInLine.add(word);
                    lines2.add(wordsInLine);
                    break;
                }

                wordsInLine.add(word);
                width.addAndGet(wordWidth);
            } else {
                width.set(0);
                lines2.add(wordsInLine);
                wordsInLine = new ArrayList<>();
                wordsInLine.add(word);
            }

        }
        return lines2;
    }


    public Font getFont() {
        return Minecraft.getInstance().font;
    }

    public boolean setInfo(ResourceLocation location) {
        try {
            this.info = TabletMainScreen.INFOS.get(location);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
