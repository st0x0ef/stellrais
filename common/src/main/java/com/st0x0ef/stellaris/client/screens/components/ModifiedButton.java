package com.st0x0ef.stellaris.client.screens.components;

import com.st0x0ef.stellaris.client.screens.PlanetSelectionScreen;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ModifiedButton extends TexturedButton{
    private final ColorTypes colorType;

    private final boolean rocketCondition;
    private final ButtonTypes type;
    private final List<String> list;

    /** USE ROW 0 FOR (NO ROW SYSTEM), USE ROW 1 FOR (CATEGORIES, PLANETS), USE ROW 2 FOR (ORBITS), USE ROW 3 FOR (SPACE STATIONS) */
    public final int row;

    // Category for if we are visible
    public Predicate<Integer> isVisible = i -> false;

    public ModifiedButton(int xIn, int yIn, int row, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, boolean rocketCondition, ButtonTypes type, List<String> list, ResourceLocation buttonTexture, ColorTypes colorType, int textureWidth, int textureHeight, Button.OnPress onPressIn, Component title) {
        this(xIn, yIn, row, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, rocketCondition, type, list, buttonTexture, colorType, textureWidth, textureHeight, onPressIn, DEFAULT_NARRATION, title);
    }

    public ModifiedButton(int xIn, int yIn, int row, int widthIn, int heightIn, int xTexStart, int yTexStart, int p_i244513_7_, boolean rocketCondition, ButtonTypes type, List<String> list, ResourceLocation buttonTexture, ColorTypes colorType, int textureWidth, int textureHeight, Button.OnPress p_i244513_11_, CreateNarration p_i244513_12_, Component p_i244513_13_) {
        super(xIn, yIn, row, heightIn, p_i244513_13_, p_i244513_11_, p_i244513_12_);
        this.tex(buttonTexture, buttonTexture);
        this.size(textureWidth, textureHeight);
        this.setUVs(xTexStart, yTexStart);
        setYShift(p_i244513_7_);
        this.colorType = colorType;
        this.rocketCondition = rocketCondition;
        this.type = type;
        this.list = list;
        this.row = row;
    }

    public void setPosition(int xIn, int yIn) {
        this.setX(xIn);
        this.setY(yIn);
    }

    /** IF YOU WANT NO ONE RETURN (NULL) */
    public enum ButtonTypes {

        /** IF YOU USE THIS PUT 1 STRING IN THE LIST (CATEGORY) */
        MILKY_WAY_CATEGORY,

        /** IF YOU USE THIS PUT 2 STRINGS IN THE LIST (CATEGORY, PROVIDED) */
        SOLAR_SYSTEM_CATEGORY,

        /** IF YOU USE THIS PUT 4 STRINGS IN THE LIST (TYPE, GRAVITY, OXYGEN, TEMPERATURE) */
        PLANET_CATEGORY,

        /** IF YOU USE THIS PUT 4 STRINGS IN THE LIST (TYPE, GRAVITY, OXYGEN, TEMPERATURE) */
        PLANET_SPACE_STATION_CATEGORY
    }

    public void renderButton(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();

        super.renderWidget(graphics, mouseX, mouseY, partialTicks);
        /** TYPE SYSTEM */
        if (mc.screen instanceof PlanetSelectionScreen) {
            this.milkyWayCategoryManager(mc, graphics, mouseX, mouseY);
            this.solarSystemToolTip(mc, graphics, mouseX, mouseY, partialTicks);
            this.planetToolTip(mc, graphics, mouseX, mouseY, partialTicks);
            this.spaceStationToolTip(mc, graphics, mouseX, mouseY, partialTicks);
        }
    }

    /** MILKY WAY TYPE MANAGER */
    private void milkyWayCategoryManager(Minecraft minecraft, GuiGraphics graphics, int mouseX, int mouseY) {
        if (this.isHovered && this.type == ButtonTypes.MILKY_WAY_CATEGORY) {
            Screen screen = minecraft.screen;

            List<Component> list = new ArrayList<>();

            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.CATEGORY_TEXT.getString() + ": §b" + this.list.getFirst()));
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.TYPE_TEXT.getString() + ": §3" + ScreenHelper.PlanetScreenHelper.SOLAR_SYSTEM_TEXT.getString()));

            graphics.renderComponentTooltip(Minecraft.getInstance().font, list, mouseX, mouseY);
        }
    }

    /** SOLAR SYSTEM TYPE MANAGER */
    private void solarSystemToolTip(Minecraft minecraft, GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (this.isHovered && this.type == ButtonTypes.SOLAR_SYSTEM_CATEGORY) {
            Screen screen = minecraft.screen;

            List<FormattedCharSequence> list = new ArrayList<>();

            String condition = this.rocketCondition ? "a" : "c";

            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.CATEGORY_TEXT.getString() + ": §" + condition + this.list.get(0)).getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.PROVIDED_TEXT.getString() + ": §b" + this.list.get(1)).getVisualOrderText());

            screen.setTooltipForNextRenderPass(list);
            screen.renderWithTooltip(graphics, mouseX, mouseY, partialTick);
        }
    }

    /** PLANET SYSTEM TYPE MANAGER */
    private void planetToolTip(Minecraft minecraft, GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (this.isHovered && this.type == ButtonTypes.PLANET_CATEGORY) {
            Screen screen = minecraft.screen;

            List<FormattedCharSequence> list = new ArrayList<>();

            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.TYPE_TEXT.getString() + ": §3" + this.list.get(0)).getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.GRAVITY_TEXT.getString() + ": §3" + this.list.get(1)).getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.OXYGEN_TEXT.getString() + ": §" + this.list.get(2)).getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.TEMPERATURE_TEXT.getString() + ": §" + this.list.get(3)).getVisualOrderText());

            screen.setTooltipForNextRenderPass(list);
            screen.renderWithTooltip(graphics, mouseX, mouseY, partialTick);
        }
    }

    /** PLANET SPACE STATION SYSTEM TYPE MANAGER */
    private void spaceStationToolTip(Minecraft minecraft, GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (this.isHovered && this.type == ButtonTypes.PLANET_SPACE_STATION_CATEGORY) {
            PlanetSelectionScreen screen = (PlanetSelectionScreen) minecraft.screen;

            List<FormattedCharSequence> list = new ArrayList<>();

            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.ITEM_REQUIREMENT_TEXT.getString()).getVisualOrderText());

            //TODO NEED A REWORK WITH THE CRAFTING RECPIES
            /*
            for (IngredientStack ingredientStack : screen.recipe.getIngredientStacks()) {
                boolean check = screen.getSpaceStationItemCheck(ingredientStack);
                Component component = Arrays.stream(ingredientStack.getIngredient().getItems()).findFirst().map(ItemStack::getHoverName).orElse(Component.empty());

                list.add(Component.literal("§8[§6" + ingredientStack.getCount() + "§8]" + (check ? "§a" : "§c") + " " + component.getString() + (ingredientStack.getCount() > 1 ? "'s" : "")));
            }*/

            list.add(Component.literal("§c----------------").getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.TYPE_TEXT.getString() + ": §3" + this.list.get(0)).getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.GRAVITY_TEXT.getString() + ": §3" + this.list.get(1)).getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.OXYGEN_TEXT.getString() + ": §" + this.list.get(2)).getVisualOrderText());
            list.add(Component.literal("§9" + ScreenHelper.PlanetScreenHelper.TEMPERATURE_TEXT.getString() + ": §" + this.list.get(3)).getVisualOrderText());

            screen.setTooltipForNextRenderPass(list);
            screen.renderWithTooltip(graphics, mouseX, mouseY, partialTick);
        }
    }

    /** TYPE TEXTURE MANAGER */
    @Override
    protected Vec3 getTypeColor() {
        if (this.isHovered) {
            return ColorTypes.WHITE.getColor();
        }
        else if (this.type == ButtonTypes.SOLAR_SYSTEM_CATEGORY) {
            if (!this.rocketCondition) {
                return ColorTypes.RED.getColor();
            }
        }
        else if (this.type == ButtonTypes.PLANET_SPACE_STATION_CATEGORY) {
            if (true) { //TODO ADD SPACE STATION CONDITION
                return ColorTypes.RED.getColor();
            }
        }

        return this.colorType.getColor();
    }

}
