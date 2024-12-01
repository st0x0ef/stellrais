package com.st0x0ef.stellaris.client.screens;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.config.ConfigEntry;
import com.st0x0ef.stellaris.common.config.CustomConfig;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    public ConfigScreen(Screen parent) {
        super(Component.literal("Stellaris Option"));
        this.parent = parent;
    }

    @Override
    protected void init() {

        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);

        CustomConfig.CONFIG.forEach( (string, configEntry) -> {
            StringWidget widget = new StringWidget(Component.literal(string), this.font);
            rowHelper.addChild(widget);
            addTypeWidgets(configEntry, rowHelper, string);
            rowHelper.addChild(SpacerElement.height(5), 2);

        });

        Button doneButton = Button.builder(CommonComponents.GUI_DONE, (button) -> this.onClose()).width(200).build();

        rowHelper.addChild(doneButton, 2, rowHelper.newCellSettings().paddingTop(10));

        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, this.height / 6 + 10, this.width, this.height , 0.5F, 0.0F);
        gridLayout.visitWidgets(this::addRenderableWidget);


    }

    @Override
    public void onClose() {
        CustomConfig.writeConfigFile("stellaris.json");
        CustomConfig.loadConfigFile();
        this.playToast(Component.literal("Config Saved"), Component.literal("The Stellaris config has been saved"));
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, -1);
    }

    public void addTypeWidgets(ConfigEntry<?> entry, GridLayout.RowHelper rowHelper, String entryName) {
        if(entry.getType() == Boolean.class) {
            Checkbox checkbox = Checkbox.builder(Component.literal(entry.value().toString()), this.font)
                    .selected((Boolean) entry.value())
                    .tooltip(Tooltip.create(Component.literal(entry.description()),null))
                    .onValueChange((checkbox1, aBoolean) -> CustomConfig.CONFIG.replace(entryName, new ConfigEntry<>(aBoolean, entry.description())))
                    .build();
            rowHelper.addChild(checkbox);

        } else if (entry.getType() == String.class ) {
            EditBox button = new EditBox(this.font, 50, 15, Component.literal(entry.value().toString()));
            button.setMaxLength(100);
            button.setTooltip(Tooltip.create(Component.literal(entry.description()),null));
            button.setValue(entry.value().toString());
            button.setResponder((string) -> CustomConfig.CONFIG.replace(entryName, new ConfigEntry<>(string, entry.description())));
            rowHelper.addChild(button);

        } else if (entry.getType() == Integer.class || entry.getType() == Double.class || entry.getType() == Float.class || entry.getType() == Long.class){
            EditBox button = new EditBox(this.font, 50, 15, Component.literal(entry.value().toString()));
            button.setMaxLength(100);
            button.setValue(entry.value().toString());
            button.setTooltip(Tooltip.create(Component.literal(entry.description()),null));

            button.setResponder((string) -> {
                int foo;
                try {
                    foo = Integer.parseInt(string);

                } catch (NumberFormatException e) {
                    foo = 0;
                }

                CustomConfig.CONFIG.replace(entryName, new ConfigEntry<>(foo, entry.description()));

            });
            rowHelper.addChild(button);

        } else {
            SpriteIconButton spriteIconButton = stellarisConfigButton(20);
            spriteIconButton.setTooltip(Tooltip.create(Component.literal("This config type is not supported. Use the manual config"), null));
            rowHelper.addChild(spriteIconButton);
        }
    }

    @Override
    public void removed() {
        this.minecraft.options.save();
    }

    public void playToast(Component title, Component description) {
        this.minecraft.getToastManager().addToast(new SystemToast(
                SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
                title,
                description
        ));

    }

    private SpriteIconButton stellarisConfigButton(int i) {
        return SpriteIconButton.builder(Component.literal("Config"), (button) -> {
            Path path = Path.of(Platform.getConfigFolder() + "/stellaris.json");
            Util.getPlatform().openUri(path.toUri());
        }, true).width(i).sprite(ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/item/engine_fan.png"), 16, 16).build();
    }
}
