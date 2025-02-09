package com.st0x0ef.stellaris.client.screens.tablet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.TabletButton;
import com.st0x0ef.stellaris.client.screens.components.TexturedButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TabletEntryScreen extends Screen {

    private int leftPos;
    private int topPos;
    private int imageHeight;
    private int imageWidth;
    private final TabletMainScreen screen;
    public TabletEntry entry;
    private ArrayList<TabletButton> PAGES_BUTTONS = new ArrayList<>();
    public String currentPage = "main";
    public TabletEntryWidget widget;

    public ArrayList<ArrayList<TabletButton>> ENTRY_BUTTONS = new ArrayList<>();
    public int currentEntryPage = 1;

    protected TabletEntryScreen(Component title, TabletMainScreen screen, int leftPos, int topPos, TabletEntry entry) {
        super(title);
        this.screen = screen;
        this.leftPos = leftPos;
        this.topPos = topPos;
        this.entry = entry;
        this.imageHeight = 162;
        this.imageWidth = 250;

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, this.topPos + 30, 16777215);

        if(currentPage.equals("main")) {
            showEntryButton();
            removeNonShowButtons();

            widget.visible = false;
        } else {
            removeAllButtons();
            widget.visible = true;

        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void init() {
        /** Back Button **/
        TexturedButton homeButton = new TexturedButton(this.leftPos + 18, this.topPos + 22, 16, 16, (button1 -> {
            if (Objects.equals(currentPage, "main")) {
                this.minecraft.setScreen(screen);
            } else {
                currentPage = "main";
                widget.visible = false;
            }
        }))
                .tex(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/tablet/main_page.png"), ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/tablet/main_page_hover.png"));
        this.addRenderableWidget(homeButton);

        AtomicInteger row = new AtomicInteger(0);
        AtomicInteger column = new AtomicInteger(0);

        entry.infos().forEach((infos) -> {

            TabletButton tabletButton = new TabletButton(this.leftPos + 68 + (column.get() * 30), this.topPos + 60 + (row.get() * 30), 20, 20, Component.translatable(infos.id()), (button -> {
                changeInfo(infos);
            }), infos)
                    .tex(ResourceLocation.parse("stellaris:textures/gui/tablet/button.png"), ResourceLocation.parse("stellaris:textures/gui/tablet/button_click.png"));

            if(column.get() == 3) {
                column.set(0);
                row.getAndIncrement();
            } else {
                column.getAndIncrement();
            }
            PAGES_BUTTONS.add(tabletButton);

            if(PAGES_BUTTONS.size() % 8 == 0) {
                column.set(0);
                row.set(0);
            }
            addButtonToList(tabletButton);
            tabletButton.visible = false;
            this.addRenderableWidget(tabletButton);
        });


        this.widget = new TabletEntryWidget(this.leftPos + 15, this.topPos + 50, 215, 100, Component.literal(""), null, this);
        this.widget.visible = false;
        this.addRenderableWidget(this.widget);
    }

    public void changeInfo(TabletEntry.Info info) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(entry.id(), info.id());
        if (widget.setInfo(location)) {
            currentPage = location.toString();
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);

        this.widget.resize(this);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TabletMainScreen.MENU_BACKGROUND);
        guiGraphics.blit(TabletMainScreen.BACKGROUND, this.leftPos , this.topPos , 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

    }

    public void showEntryButton() {
        ENTRY_BUTTONS.get(currentEntryPage).forEach(button -> {
            button.visible = true;
        });
    }

    public void removeNonShowButtons() {
        for (int i = 0; i < ENTRY_BUTTONS.size(); i++) {
            if (i != currentEntryPage) {
                ENTRY_BUTTONS.get(i).forEach(button -> {
                    button.visible = false;
                });
            }
        }
    }

    public void removeAllButtons() {
        for (int i = 0; i < ENTRY_BUTTONS.size(); i++) {
            ENTRY_BUTTONS.get(i).forEach(button -> {
                button.visible = false;
            });
        }
    }

    public void addButtonToList(TabletButton button){
        if (ENTRY_BUTTONS.isEmpty()) {
            ArrayList<TabletButton> list = new ArrayList<>();
            list.add(button);
            ENTRY_BUTTONS.add(list);
            return;
        }

        for (ArrayList<TabletButton> buttons : ENTRY_BUTTONS) {
            if(buttons.size() < 8){
                buttons.add(button);
                break;
            } else if (buttons.size() == 8) {
                if (ENTRY_BUTTONS.indexOf(buttons) + 1 >= ENTRY_BUTTONS.size()) {
                    ArrayList<TabletButton> list = new ArrayList<>();
                    list.add(button);
                    ENTRY_BUTTONS.add(list);
                    break;
                }
            }
        }
    }

}
