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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabletEntryWidget extends AbstractScrollWidget {

    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "icon/scroller");

    private final AtomicInteger finalHeight = new AtomicInteger(0);
    private TabletEntry.Info info;
    private int baseScreenWidth;
    private final TabletEntryScreen screen;
    public ArrayList<ClickBox> clickBoxes = new ArrayList<>();

    public TabletEntryWidget(int x, int y, int width, int height, Component message, TabletEntry.Info info, TabletEntryScreen screen) {
        super(x, y, width, height, message);
        this.info = info;
        this.baseScreenWidth = screen.width;
        this.screen = screen;
    }

    @Override
    protected int getInnerHeight() {
        return finalHeight.get() + finalHeight.get() / 2;
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

    public void resize(TabletEntryScreen screen) {
        this.baseScreenWidth = screen.width;

        this.setInfo(ResourceLocation.parse(screen.currentPage));
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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        for (ClickBox clickBox : clickBoxes) {
            if (clickBox.isHovered((int) mouseX, (int) mouseY, (int) scrollAmount()) ) {
                clickBox.changePage(screen);
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void addClickBox(double x, double y, double width, double height, String brutText) {
        Pattern pattern = Pattern.compile("\\[ref=.*?\\]");
        Matcher matcher = pattern.matcher(brutText);
        while (matcher.find()) {
            String ref = matcher.group().replace("[ref=", "").replace("]", "");
            ClickBox clickBox = new ClickBox((int) x, (int) y, (int) width, (int) height, ref);
            if (!clickBoxes.contains(clickBox)) {
                clickBoxes.add(clickBox);
            }
        }
    }

    public String removeRef(String text) {
        String regex = "\\[ref=.*?\\]";
        return text.replaceAll(regex, "");
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
                } else if (word.contains("[ref=")) {
                    addClickBox(x + width.get(), y + (i * getFont().lineHeight), getFont().width(removeRef(word)), getFont().lineHeight, word);
                    word = removeRef(word);
                    color = "blue";
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

        List<ArrayList<String>> lines = new ArrayList<>();

        ArrayList<String> wordsInLine = new ArrayList<>();

        AtomicInteger remainingWords = new AtomicInteger(words.length);
        AtomicInteger width = new AtomicInteger(words.length);

        for(String word : words) {
            remainingWords.getAndDecrement();

            int wordWidth = Minecraft.getInstance().font.width(word + " ");

            if (word.contains("[br]")) {
                lines.add(wordsInLine);
                wordsInLine = new ArrayList<>();
                width.set(0);
            } else {
                if (word.contains("[color=")) {
                    String wordWithoutColor = word.replace("[color=" + word.substring(7, word.indexOf("]")) + "]", "");
                    wordWidth = Minecraft.getInstance().font.width(wordWithoutColor + " ");
                } else if (word.contains("[ref=")) {
                    wordWidth = Minecraft.getInstance().font.width(removeRef(word) + " ");
                }


                if(wordWidth + width.get() < maxWidth) {
                    if(remainingWords.get() == 0) {
                        wordsInLine.add(word);
                        lines.add(wordsInLine);
                        break;
                    }

                    wordsInLine.add(word);
                    width.addAndGet(wordWidth);
                } else {
                    width.set(0);
                    lines.add(wordsInLine);
                    wordsInLine = new ArrayList<>();
                    wordsInLine.add(word);
                }
            }

        }

        return lines;
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

    private static class ClickBox {
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private final String action;

        public ClickBox(int x, int y, int width, int height, String action) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.action = action;
        }

        public boolean isHovered(int mouseX, int mouseY, int finalHeight) {
            mouseY += finalHeight;
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        public void changePage(TabletEntryScreen entryScreen) {
            ResourceLocation location = ResourceLocation.parse(action);
            entryScreen.widget.setInfo(location);
        }

        public String getAction() {
            return action;
        }

    }
    
}
