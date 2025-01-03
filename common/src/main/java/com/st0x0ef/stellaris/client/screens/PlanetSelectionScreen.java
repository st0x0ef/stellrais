package com.st0x0ef.stellaris.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.client.screens.components.*;
import com.st0x0ef.stellaris.client.screens.helper.ScreenHelper;
import com.st0x0ef.stellaris.client.screens.info.CelestialBody;
import com.st0x0ef.stellaris.client.screens.info.MoonInfo;
import com.st0x0ef.stellaris.client.screens.info.PlanetInfo;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data.recipes.SpaceStationRecipe;
import com.st0x0ef.stellaris.common.data.recipes.SpaceStationRecipesManager;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import com.st0x0ef.stellaris.common.menus.PlanetSelectionMenu;
import com.st0x0ef.stellaris.common.network.packets.TeleportEntityToPlanetPacket;
import com.st0x0ef.stellaris.common.registry.EntityData;
import com.st0x0ef.stellaris.common.registry.TranslatableRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import com.st0x0ef.stellaris.common.utils.Utils;
import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Environment(EnvType.CLIENT)
public class PlanetSelectionScreen extends AbstractContainerScreen<PlanetSelectionMenu> {

    public static final ResourceLocation HIGHLIGHTER_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/planet_highlighter.png");
    public static final ResourceLocation BLACK_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/black.png");

    public static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/planet_selection.png");
    public static final ResourceLocation SCROLLER_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/scroller.png");

    public static final ResourceLocation SMALL_BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/small_button.png");
    public static final ResourceLocation BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/button.png");
    public static final ResourceLocation LARGE_BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/large_button.png");
    public static final ResourceLocation LAUNCH_BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button.png");
    public static final ResourceLocation NEXT_BUTTON = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/next_button.png");
    public static final ResourceLocation BACK_BUTTON = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/back_button.png");


    public static final ResourceLocation SMALL_MENU_LIST = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/planet_menu.png");
    public static final ResourceLocation LARGE_MENU_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/large_planet_menu.png");
    public static final ResourceLocation LARGE_MENU_TEXTURE_RED = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/large_planet_menu_red.png");

    public static final List<CelestialBody> STARS = new ArrayList<>();
    public static final List<PlanetInfo> PLANETS = new ArrayList<>();
    public static final List<MoonInfo> MOONS = new ArrayList<>();

    public static final Component temperature = Component.translatable("text.stellaris.planetscreen.temperature");
    public static final Component gravity = Component.translatable("text.stellaris.planetscreen.gravity");
    public static final Component launch = Component.translatable("text.stellaris.planetscreen.launch");
    public static final Component oxygen = Component.translatable("text.stellaris.planetscreen.oxygen");
    public static final Component system = Component.translatable("text.stellaris.planetscreen.system");
    public static final Component error_message = Component.translatable("text.stellaris.planetscreen.error_message");


    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final long UPDATE_INTERVAL = 1L;

    private boolean isLaunching = false;
    private boolean showLargeMenu = false;
    private boolean showHelpMenu = true;

    LaunchButton launchButton;

    private double offsetX = 0;
    private double offsetY = 0;

    private double lastMouseX;
    private double lastMouseY;
    private boolean dragging = false;

    public boolean isPausePressed = false;
    private boolean isShiftPressed = false;
    private boolean isWheelButtonDown = false;
    public boolean isPlanetScreenOpened;

    private double zoomLevel = 1.0;
    private GLFWScrollCallback prevScrollCallback;

    public ArrayList<ArrayList<TexturedButton>> planetsButton = new ArrayList<>();
    public int currentPage = 0;

    private final List<InvisibleButton> planetButtons = new ArrayList<>();
    private final List<InvisibleButton> moonButtons = new ArrayList<>();

    public ArrayList<SpaceStationRecipesManager.SpaceStationRecipeState> spaceStationRecipeStates = new ArrayList<>();

    public PlanetSelectionScreen(PlanetSelectionMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        this.imageWidth = 1200;
        this.imageHeight = 1600;
        this.inventoryLabelY = this.imageHeight - 110;
        initializeLaunchButton();

    }

    @Override
    protected void init() {
        super.init();
        getMenu().freeze_gui = false;
        centerSun();
        initSpaceStationRecipes();
        isPlanetScreenOpened = true;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        long windowHandle = Minecraft.getInstance().getWindow().getWindow();
        prevScrollCallback = GLFW.glfwSetScrollCallback(windowHandle, this::onMouseScroll);
        initPlanetList();
        initializeAllButtons();

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBg(graphics, partialTicks, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTicks);

        if (!isPausePressed) {
            updatePlanets();
        }
        if (focusedBody != null) {
            centerOnBody(focusedBody);
        }
        if (!showLargeMenu) {
            launchButton.visible = false;
        }

        renderHelp(graphics);
        drawOrbits();

        renderBodiesAndPlanets(graphics);
        renderHighlighter(graphics, mouseX, mouseY);

        if (hoveredBody != null) {
            updateHighlighterPosition(graphics, hoveredBody);
        }
        if (focusedBody != null) {
            updateHighlighterPosition(graphics, focusedBody);
        }

        renderLargeMenu(graphics);
        renderPlanetList(currentPage);

        this.renderTooltip(graphics, mouseX, mouseY);
    }


    public boolean canLaunch(Planet planet) {
        if (this.getMenu().getForceCanGoTo()) return true;

        Player player = this.getPlayer();
        if (player == null) {
            return false;
        }
        Entity vehicle = player.getVehicle();
        if (vehicle instanceof RocketEntity rocket) {
            if (PlanetUtil.isPlanet(player.level().dimension().location())) {
                return rocket.canGoTo(PlanetUtil.getPlanet(player.level().dimension().location()), planet);
            }
            return rocket.canGoTo(PlanetUtil.getPlanet(Level.OVERWORLD.location()), planet);
        }
        return false;
    }


    private void initializeAllButtons() {
        initializePlanetButtons();
        initializeMoonButtons();
        initializeLaunchButton();
    }

    private void initializePlanetButtons() {
        planetButtons.clear();
        for (PlanetInfo planet : PLANETS) {
            int planetWidth = (int) (planet.width * zoomLevel);
            int planetHeight = (int) (planet.height * zoomLevel);
            float planetX = (float) ((planet.orbitCenter.x + offsetX + planet.orbitRadius * Math.cos(planet.currentAngle) - (double) planetWidth / 2) * zoomLevel);
            float planetY = (float) ((planet.orbitCenter.y + offsetY + planet.orbitRadius * Math.sin(planet.currentAngle) - (double) planetHeight / 2) * zoomLevel);

            InvisibleButton button = new InvisibleButton(
                    (int) planetX, (int) planetY, planetWidth + 2, planetHeight + 2,
                    Component.literal(planet.name),
                    (btn) -> onPlanetButtonClick(planet),
                    () -> hoveredBody = planet
            );

            planetButtons.add(button);
            addRenderableWidget(button);
        }
    }

    private void initializeMoonButtons() {
        moonButtons.clear();
        for (MoonInfo moon : MOONS) {
            int moonWidth = (int) (moon.width * zoomLevel);
            int moonHeight = (int) (moon.height * zoomLevel);
            float moonX = (float) ((moon.orbitCenter.x + offsetX + moon.orbitRadius * Math.cos(moon.currentAngle) - (double) moonWidth / 2) * zoomLevel);
            float moonY = (float) ((moon.orbitCenter.y + offsetY + moon.orbitRadius * Math.sin(moon.currentAngle) - (double) moonHeight / 2) * zoomLevel);

            InvisibleButton button = new InvisibleButton(
                    (int) moonX, (int) moonY, moonWidth, moonHeight,
                    Component.literal(moon.name),
                    (btn) -> onMoonButtonClick(moon),
                    () -> hoveredBody = moon
            );
            moonButtons.add(button);
            addRenderableWidget(button);
        }
    }

    private void initializeLaunchButton() {
        int buttonWidth = 74;
        int buttonHeight = 20;
        int buttonX = (this.width - buttonWidth) / 4 + 100;
        int buttonY = (this.height - buttonHeight) / 4;

        launchButton = new LaunchButton(buttonX, buttonY, buttonWidth, buttonHeight, Component.literal("Launch"), (btn) -> onLaunchButtonClick());

        this.addRenderableWidget(launchButton);

        launchButton.visible = false;
    }

    public static CelestialBody focusedBody = null;
    public static CelestialBody hoveredBody = null;

    private void onPlanetButtonClick(PlanetInfo planet) {
        if (!showLargeMenu) {
            focusedBody = planet;
            centerOnBody(planet);
            showLargeMenu = true;
        }
    }

    private void onMoonButtonClick(MoonInfo moon) {
        if (!showLargeMenu && moon.clickable) {
            focusedBody = moon;
            centerOnBody(moon);
            showLargeMenu = true;
        }
    }

    private void onLaunchButtonClick() {
        if (focusedBody != null && focusedBody.dimension != null) {
            if (canLaunch(PlanetUtil.getPlanet(focusedBody.dimension))) {

                if(focusedBody.spaceStation) {
                    //Add Tooltips
                } else {
                    tpToFocusedPlanet();
                }

            } else {
                if (PlanetUtil.getPlanet(focusedBody.dimension).name().equals("Earth")) {
                    tpToFocusedPlanet();
                }
            }
            showLargeMenu = false;
        }
    }




    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        graphics.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    public void renderBodiesAndPlanets(GuiGraphics graphics) {
        renderStars(graphics);
        renderPlanets(graphics);
        renderMoons(graphics);
        initializePlanetButtons();
        initializeMoonButtons();
    }

    private void renderHelp(GuiGraphics graphics) {
        if(showHelpMenu) {
            graphics.drawCenteredString(this.font, Component.translatable("text.stellaris.planetscreen.press_space"), this.width/2, this.height - 20 , 16777212);
            graphics.drawCenteredString(this.font, Component.translatable("text.stellaris.planetscreen.arrows"), this.width/2, this.height - 10 , 16777212);

        }

    }

    private void renderStars(GuiGraphics graphics) {
        Font font = Minecraft.getInstance().font;
        for (CelestialBody star : STARS) {
            float bodyX = (float) ((star.x + offsetX) * zoomLevel - (star.width / 2) * zoomLevel);
            float bodyY = (float) ((star.y + offsetY) * zoomLevel - (star.height / 2) * zoomLevel);

            int bodyWidth = (int) (star.width * zoomLevel);
            int bodyHeight = (int) (star.height * zoomLevel);

            graphics.blit(star.texture, (int) bodyX, (int) bodyY, 0, 0, bodyWidth, bodyHeight, bodyWidth, bodyHeight);

            int nameWidth = font.width(star.name);
            graphics.drawString(font, star.translatable, (int) (bodyX + (float) bodyWidth / 2 - (float) nameWidth / 2), (int) (bodyY + bodyHeight), 0xFFFFFF);
        }
    }

    private void renderPlanets(GuiGraphics graphics) {
        Font font = Minecraft.getInstance().font;
        for (PlanetInfo planet : PLANETS) {
            CelestialBody orbitCenter = planet.orbitCenter;

            float orbitCenterX = (float) ((orbitCenter.x + offsetX) * zoomLevel);
            float orbitCenterY = (float) ((orbitCenter.y + offsetY) * zoomLevel);

            float planetX = (float) (orbitCenterX + planet.orbitRadius * zoomLevel * Math.cos(planet.currentAngle) - planet.width / 2 * zoomLevel);
            float planetY = (float) (orbitCenterY + planet.orbitRadius * zoomLevel * Math.sin(planet.currentAngle) - planet.height / 2 * zoomLevel);

            int planetWidth = (int) (planet.width * zoomLevel);
            int planetHeight = (int) (planet.height * zoomLevel);

            ScreenHelper.drawTexturewithRotation(graphics, planet.texture, (int) planetX, (int) planetY, 0, 0, planetWidth, planetHeight, planetWidth, planetHeight, (float) planet.currentAngle);

            int nameWidth = font.width(planet.name);
            graphics.drawString(font, planet.name, (int) (planetX + (float) planetWidth / 2 - (float) nameWidth / 2), (int) (planetY + planetHeight), 0xFFFFFF);
        }
    }

    private void renderMoons(GuiGraphics graphics) {
        for (MoonInfo moon : MOONS) {
            float moonX = (float) ((moon.x + offsetX) * zoomLevel - (moon.width / 2) * zoomLevel);
            float moonY = (float) ((moon.y + offsetY) * zoomLevel - (moon.height / 2) * zoomLevel);

            int moonWidth = (int) (moon.width * zoomLevel);
            int moonHeight = (int) (moon.height * zoomLevel);

            ScreenHelper.drawTexturewithRotation(graphics, moon.texture, (int) moonX, (int) moonY, 0, 0, moonWidth, moonHeight, moonWidth, moonHeight, (float) moon.currentAngle);
        }
    }



    private int currentHighlighterFrame = 0;
    private final int totalHighlighterFrames = 30;

    private void renderHighlighter(GuiGraphics graphics, int mouseX, int mouseY) {
        if (!showLargeMenu && (hoveredBody != null || focusedBody != null)) {
            CelestialBody bodyToHighlight = hoveredBody != null ? hoveredBody : focusedBody;
            renderBodyDescription(graphics, bodyToHighlight, mouseX, mouseY);
            renderHighlightFrame(graphics, bodyToHighlight);
        }
    }

    private void renderBodyDescription(GuiGraphics graphics, CelestialBody bodyToHighlight, int mouseX, int mouseY) {
        List<Component> bodyDescription = new ArrayList<>();
        bodyDescription.add(Utils.getMessageComponent("§f" + bodyToHighlight.translatable.getString()));
        if (!isShiftPressed) {
            bodyDescription.add(Utils.getMessageComponent("§8" + TranslatableRegistry.HOLD_SHIFT.getString()));
        } else {
            addDetailedDescription(bodyDescription, bodyToHighlight);
        }
        if (isPausePressed) {
            graphics.renderComponentTooltip(this.font, bodyDescription, mouseX, mouseY);
        }
    }

    private void addDetailedDescription(List<Component> bodyDescription, CelestialBody bodyToHighlight) {
        bodyDescription.add(Utils.getMessageComponent("§f￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣"));
        Planet planet = PlanetUtil.getPlanet(bodyToHighlight.dimension);
        if (planet == null) {
            bodyDescription.add(Utils.getMessageComponent(error_message.getString(), "Red"));
        } else {
            bodyDescription.add(Utils.getMessageComponent(temperature.getString() + " : " + planet.temperature() + "°C"));
            bodyDescription.add(Utils.getMessageComponent(gravity.getString() + " : " + planet.gravity() + "m/s"));
            bodyDescription.add(Utils.getMessageComponent(oxygen.getString() + " : " + planet.oxygen()));
            bodyDescription.add(Utils.getMessageComponent(system.getString() + " : " + Component.translatable(planet.system()).getString()));

            if (bodyToHighlight instanceof PlanetInfo && getMoonsCount((PlanetInfo) bodyToHighlight) > 0) {
                bodyDescription.add(Utils.getMessageComponent(""));
                bodyDescription.add(Utils.getMessageComponent(TranslatableRegistry.MOONS.getString() + " : " + getMoonsCount((PlanetInfo) bodyToHighlight)));
            }

            if (getPlayer().getServer() != null) {
                bodyDescription.add(Utils.getMessageComponent(""));
                bodyDescription.add(Utils.getMessageComponent(TranslatableRegistry.PLAYERS.getString() + " : " + Utils.getPlayerCountInDimension(getPlayer().getServer(), bodyToHighlight.dimension)));
            }
        }
    }

    private void renderHighlightFrame(GuiGraphics graphics, CelestialBody bodyToHighlight) {
        int highlightWidth = (int) (bodyToHighlight.width * zoomLevel);
        int highlightHeight = (int) (bodyToHighlight.height * zoomLevel);
        float highlightX = (float) ((bodyToHighlight.x + offsetX) * zoomLevel - (double) highlightWidth / 2);
        float highlightY = (float) ((bodyToHighlight.y + offsetY) * zoomLevel - (double) highlightHeight / 2);

        currentHighlighterFrame = (currentHighlighterFrame + 1) % totalHighlighterFrames;

        int frameY = currentHighlighterFrame * highlightHeight;

        float currentAngle = bodyToHighlight instanceof PlanetInfo ? (float) ((PlanetInfo) bodyToHighlight).currentAngle : (float) ((MoonInfo) bodyToHighlight).currentAngle;

        ScreenHelper.drawTexturewithRotation(graphics, HIGHLIGHTER_TEXTURE, (int) highlightX, (int) highlightY, 0, frameY, highlightWidth, highlightHeight, highlightWidth, totalHighlighterFrames * highlightHeight, currentAngle);
    }


    private void renderLargeMenu(GuiGraphics graphics) {
        if (showLargeMenu) {
            getMenu().freeze_gui = false;
            ResourceLocation CELESTIAL_BODY_TEXTURE = focusedBody.texture;

            Component CELESTIAL_BODY_NAME = focusedBody.translatable;

            Planet planet = PlanetUtil.getPlanet(focusedBody.dimension);

            if (planet == null) return;

            float CELESTIAL_BODY_TEMPERATURE = planet.temperature();
            float CELESTIAL_BODY_GRAVITY = planet.gravity();
            boolean CELESTIAL_BODY_OXYGEN = planet.oxygen();
            String CELESTIAL_BODY_SYSTEM = planet.system();

            Component systemTranslatable;

            Component temperatureV;
            Component gravityV;
            Component oxygenV;
            Component systemV;

            int oxygenColor;
            int temperatureColor;

            if (CELESTIAL_BODY_SYSTEM == null) {
                systemV = Component.literal(system + " : null");
            } else {
                systemTranslatable = Component.translatable(CELESTIAL_BODY_SYSTEM);
                systemV = Component.literal(system.getString() + " : " + systemTranslatable.getString());
            }

            temperatureV = Component.literal(temperature.getString() + " : " + planet.temperature() + "°C");

            oxygenV = Component.literal(oxygen.getString() + " : " + CELESTIAL_BODY_OXYGEN);

            gravityV = Component.literal(gravity.getString() + " : " + CELESTIAL_BODY_GRAVITY + "m/s");

            if (CELESTIAL_BODY_OXYGEN) {
                oxygenColor = Utils.getColorHexCode("Lime");
            } else {
                oxygenColor = Utils.getColorHexCode("Red");
            }

            if (CELESTIAL_BODY_TEMPERATURE >= 100) {
                temperatureColor = Utils.getColorHexCode("DarkRed");
            } else if (CELESTIAL_BODY_TEMPERATURE >= 0){
                temperatureColor = Utils.getColorHexCode("Lime");
            } else if (CELESTIAL_BODY_TEMPERATURE >= -100) {
                temperatureColor = Utils.getColorHexCode("Cyan");
            } else {
                temperatureColor = Utils.getColorHexCode("Blue");
            }

            int menuWidth = 215;
            int menuHeight = 177;

            int buttonWidth = 74;
            int buttonHeight = 20;

            int centerX = (this.width - menuWidth) / 2;
            int centerY = (this.height - menuHeight) / 2;

            int buttonX = centerX + buttonWidth / 2 - buttonWidth / 3 - buttonWidth / 15;
            int buttonY = centerY + buttonHeight / 2 + 1;

            int textX = buttonX + buttonWidth / 4 - 20;

            if(focusedBody.spaceStation) {
                launchButton.setTooltip(Tooltip.create(SpaceStationRecipesManager.getTotalTooltip(spaceStationRecipeStates)));
            }

            launchButton.visible = true;
            launchButton.setPosition(buttonX, buttonY);


            float alpha = 0.5f;

            RenderSystem.disableBlend();

            graphics.drawString(font, launch, buttonX + buttonWidth / 4, buttonY + buttonHeight / 4 + 1, 0xFFFFFF);
            graphics.drawString(font, CELESTIAL_BODY_NAME, textX, buttonY + buttonHeight / 4 + 37, 0xFFFFFF, true);

            graphics.drawString(font, "￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣", textX, buttonY + buttonHeight / 4 + 50, 0xFFFFFF, true);

            graphics.drawString(font, temperatureV, textX, buttonY + buttonHeight / 4 + 60, temperatureColor, true);
            graphics.drawString(font, oxygenV, textX, buttonY + buttonHeight / 4 + 90, oxygenColor, true);
            graphics.drawString(font, systemV, textX, buttonY + buttonHeight / 4 + 105, 0xFFFFFF, true);

            if (focusedBody instanceof PlanetInfo && getMoonsCount((PlanetInfo) focusedBody) > 0) {
                graphics.drawString(font, TranslatableRegistry.MOONS.getString() + " : " + getMoonsCount((PlanetInfo) focusedBody), textX, buttonY + buttonHeight / 4 + 120, 0xD3D3D3, true);
            }

            if (getPlayer().getServer() != null) {
                graphics.drawString(font, TranslatableRegistry.PLAYERS.getString() + " : " + Utils.getPlayerCountInDimension(getPlayer().getServer(), focusedBody.dimension), textX, buttonY + buttonHeight / 4 + 135, 0xFFFFFF, false);
            }

            if (canLaunch(planet)) {
                graphics.drawString(font, gravityV, textX, buttonY + buttonHeight / 4 + 75, Utils.getColorHexCode("White"), true);

                RenderSystem.enableBlend();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);

                RenderSystem.setShaderTexture(0, LARGE_MENU_TEXTURE);
                graphics.blit(LARGE_MENU_TEXTURE, centerX, centerY, 0, 0, menuWidth, menuHeight, menuWidth, menuHeight);
                launchButton.setButtonTexture(
                        ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button.png"),
                        ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button_hovered.png")
                );
            } else {
                if (Objects.equals(focusedBody.name, "Earth")) {
                    graphics.drawString(font, gravityV, textX, buttonY + buttonHeight / 4 + 75, Utils.getColorHexCode("White"), true);

                    RenderSystem.enableBlend();
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);

                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);

                    RenderSystem.setShaderTexture(0, LARGE_MENU_TEXTURE);
                    launchButton.setButtonTexture(
                            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button.png"),
                            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button_hovered.png")
                    );
                    graphics.blit(LARGE_MENU_TEXTURE, centerX, centerY, 0, 0, menuWidth, menuHeight, menuWidth, menuHeight);
                } else {
                    graphics.drawString(font, gravityV, textX, buttonY + buttonHeight / 4 + 75, Utils.getColorHexCode("Orange"), true);

                    RenderSystem.enableBlend();
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);

                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);

                    RenderSystem.setShaderTexture(0, LARGE_MENU_TEXTURE_RED);
                    graphics.blit(LARGE_MENU_TEXTURE_RED, centerX, centerY, 0, 0, menuWidth, menuHeight, menuWidth, menuHeight);
                    launchButton.setButtonTexture(
                            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/no_launch_button.png"),
                            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/no_launch_button_hovered.png")
                    );
                }
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            RenderSystem.setShaderTexture(0, BLACK_TEXTURE);
            graphics.blit(BLACK_TEXTURE, centerX + menuWidth - 64, centerY + menuHeight / 2 - 32, 0, 0, 48, 48, 48, 48);

            RenderSystem.setShaderTexture(0, CELESTIAL_BODY_TEXTURE);
            if (focusedBody.width != focusedBody.height) {
                if (Math.min((int) focusedBody.width, (int) focusedBody.height) == focusedBody.width) {
                    float rate = focusedBody.height / focusedBody.width;
                    graphics.blit(CELESTIAL_BODY_TEXTURE, centerX + menuWidth - 46, centerY + menuHeight / 2 - 14, 0, 0, 12, (int) (12 * rate), 12, (int) (12 * rate));
                } else {
                    float rate = focusedBody.width / focusedBody.height;
                    graphics.blit(CELESTIAL_BODY_TEXTURE, centerX + menuWidth - 46, centerY + menuHeight / 2 - 14, 0, 0, (int) (12 * rate), 12, (int) (12 * rate), 12);
                }
            } else {
                graphics.blit(CELESTIAL_BODY_TEXTURE, centerX + menuWidth - 46, centerY + menuHeight / 2 - 14, 0, 0, 12, 12, 12, 12);
            }

            RenderSystem.disableBlend();
        } else {
            launchButton.visible = false;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_Z) {
            if (focusedBody != null) {
                if (canLaunch(PlanetUtil.getPlanet(focusedBody.dimension))) {
                    tpToFocusedPlanet();
                }
            }
        }
        else if (keyCode == GLFW.GLFW_KEY_H) {
            showHelpMenu = !showHelpMenu;
        } else if (keyCode == GLFW.GLFW_KEY_SPACE) {
            isPausePressed = !isPausePressed;
        } else if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            isShiftPressed = true;
        } else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
            if (focusedBody instanceof CelestialBody) {
                focusedBody = getNextBodyByDistance(focusedBody);
                centerOnBody(focusedBody);
            }
        } else if (keyCode == GLFW.GLFW_KEY_LEFT) {
            if (focusedBody instanceof CelestialBody) {
                focusedBody = getPreviousBodyByDistance(focusedBody);
                centerOnBody(focusedBody);
            }
        } else if (keyCode == GLFW.GLFW_KEY_DOWN) {
            if (focusedBody instanceof PlanetInfo) {
                var moonToBeFocused = getMoonsByDistance((PlanetInfo) focusedBody);

                if(moonToBeFocused.clickable) {
                    focusedBody = moonToBeFocused;
                    centerOnBody(focusedBody);
                }
            }
        } else if (keyCode == GLFW.GLFW_KEY_UP) {
            if (focusedBody instanceof MoonInfo) {
                focusedBody = ((MoonInfo) focusedBody).orbitCenter;
                centerOnBody(focusedBody);
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private CelestialBody getNextBodyByDistance(CelestialBody currentBody) {
        switch (currentBody) {
            case null -> {
                return null;
            }
            case PlanetInfo planetInfo -> {
                List<PlanetInfo> bodies = new ArrayList<>(PLANETS);

                bodies.sort(Comparator.comparingDouble(b -> b.orbitRadius));

                for (int i = 0; i < bodies.size(); i++) {
                    if (bodies.get(i) == currentBody) {
                        for (int j = i + 1; j < bodies.size(); j++) {
                            if (bodies.get(j).orbitCenter == ((PlanetInfo) currentBody).orbitCenter) {
                                return bodies.get(j);
                            }
                        }
                    }
                }
            }
            case MoonInfo moonInfo -> {
                List<MoonInfo> bodies = new ArrayList<>(MOONS);

                bodies.sort(Comparator.comparingDouble(b -> b.orbitRadius));

                for (int i = 0; i < bodies.size(); i++) {
                    if (bodies.get(i) == currentBody) {
                        for (int j = i + 1; j < bodies.size(); j++) {
                            if (bodies.get(j).orbitCenter == ((MoonInfo) currentBody).orbitCenter) {
                                return bodies.get(j);
                            }
                        }
                    }
                }
            }
            default -> {
            }
        }

        return currentBody;
    }

    private CelestialBody getPreviousBodyByDistance(CelestialBody currentBody) {
        switch (currentBody) {
            case null -> {
                return null;
            }
            case PlanetInfo planetInfo -> {
                List<PlanetInfo> bodies = new ArrayList<>(PLANETS);

                bodies.sort(Comparator.comparingDouble(b -> b.orbitRadius));

                for (int i = bodies.size() - 1; i >= 0; i--) {
                    if (bodies.get(i) == currentBody) {
                        for (int j = i - 1; j >= 0; j--) {
                            if (bodies.get(j).orbitCenter == ((PlanetInfo) currentBody).orbitCenter) {
                                return bodies.get(j);
                            }
                        }
                    }
                }
            }
            case MoonInfo moonInfo -> {
                List<MoonInfo> bodies = new ArrayList<>(MOONS);

                bodies.sort(Comparator.comparingDouble(b -> b.orbitRadius));

                for (int i = bodies.size() - 1; i >= 0; i--) {
                    if (bodies.get(i) == currentBody) {
                        for (int j = i - 1; j >= 0; j--) {
                            if (bodies.get(j).orbitCenter == ((MoonInfo) currentBody).orbitCenter) {
                                return bodies.get(j);
                            }
                        }
                    }
                }
            }
            default -> {
            }
        }

        return currentBody;
    }

    private CelestialBody getMoonsByDistance(PlanetInfo currentBody) {
        MoonInfo smallestOrbitMoon = null;
        for (MoonInfo moon : MOONS) {
            if (moon.orbitCenter.equals(currentBody)) {
                if (smallestOrbitMoon == null || moon.orbitRadius < smallestOrbitMoon.orbitRadius) {
                    smallestOrbitMoon = moon;
                }
            }
        }
        return smallestOrbitMoon != null ? smallestOrbitMoon : currentBody;
    }

    private int getMoonsCount(PlanetInfo currentBody) {
        int i = 0;

        for (MoonInfo moon : MOONS) {
            if (moon.orbitCenter.equals(currentBody)) {
                i++;
            }
        }

        return i;
    }

    public void tpToFocusedPlanet() {
        if (focusedBody != null) {

            NetworkManager.sendToServer(new TeleportEntityToPlanetPacket(focusedBody.dimension));
            long windowHandle = Minecraft.getInstance().getWindow().getWindow();
            prevScrollCallback = GLFW.glfwSetScrollCallback(windowHandle, Minecraft.getInstance().mouseHandler::onScroll);
        } else {
            Stellaris.LOG.error("Focused body is null");
        }
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            isShiftPressed = false;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    double angle;

    private void updatePlanets() {
        long time = Util.getMillis();
        if (!getMenu().freeze_gui || !isPausePressed) {
            for (PlanetInfo planet : PLANETS) {
                planet.updateAngle(time);
                planet.updatePosition();

                this.angle = planet.updateAngle(time);
            }
            for (MoonInfo moon : MOONS) {
                moon.updateAngle(time);
                moon.updatePosition();

                this.angle = moon.updateAngle(time);
            }
        }
    }

    public void drawOrbits() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();

        for (PlanetInfo planet : PLANETS) {
            CelestialBody orbitCenter = planet.orbitCenter;

            float orbitCenterX = (float) ((orbitCenter.x + offsetX) * zoomLevel);
            float orbitCenterY = (float) ((orbitCenter.y + offsetY) * zoomLevel);

            renderOrbits(tesselator, orbitCenterX, orbitCenterY, planet.orbitRadius * zoomLevel, 75, orbitCenter.orbitColor, 1.0F);
        }

        for (MoonInfo moon : MOONS) {
            CelestialBody orbitCenter = moon.orbitCenter;

            float orbitCenterX = (float) ((orbitCenter.x + offsetX) * zoomLevel);
            float orbitCenterY = (float) ((orbitCenter.y + offsetY) * zoomLevel);

            renderOrbits(tesselator, orbitCenterX, orbitCenterY, moon.orbitRadius * zoomLevel, 75, 0x888888, 0.5F);
        }

        RenderSystem.disableBlend();
    }

    public static void renderOrbits(Tesselator tesselator, double centerX, double centerY, double radius, int sides, int color, float alphaL) {
        float red = ((color >> 16) & 0xFF) / 255.0F;
        float green = ((color >> 8) & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        float angleStep = (float) (2.0 * Math.PI / sides);

        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i < sides; i++) {
            float currentAngle = i * angleStep;
            float nextAngle = currentAngle + angleStep;

            float vertex1X = (float) (centerX + radius * Math.cos(currentAngle));
            float vertex1Y = (float) (centerY + radius * Math.sin(currentAngle));
            float vertex2X = (float) (centerX + radius * Math.cos(nextAngle));
            float vertex2Y = (float) (centerY + radius * Math.sin(nextAngle));

            bufferBuilder.addVertex(vertex1X, vertex1Y, 0).setColor(red, green, blue, alphaL);
            bufferBuilder.addVertex(vertex2X, vertex2Y, 0).setColor(red, green, blue, alphaL);
        }

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    }

    private void centerSun() {
        float centerX = width / 2.0f;
        float centerY = height / 2.0f;
        CelestialBody sun = findByNameStar("stellaris:sun");
        if (sun != null) {
            sun.setPosition(centerX, centerY);
        } else {
            Stellaris.LOG.error("stellaris:sun is null");
        }
        offsetX = 0;
        offsetY = 0;
    }


    public static CelestialBody findByNameStar(String id) {
        for (CelestialBody body : PlanetSelectionScreen.STARS) {
            if (body.getId().equals(id)) {
                return body;
            }
        }
        Stellaris.LOG.warn("Star not found : {}", id);
        return null;
    }

    public static PlanetInfo findByNamePlanet(String id) {
        for (PlanetInfo body : PlanetSelectionScreen.PLANETS) {
            if (body.getId().equals(id)) {
                return body;
            }
        }
        return null;
    }

    public static MoonInfo findByNameMoon(String id) {
        for (MoonInfo body : PlanetSelectionScreen.MOONS) {
            if (body.getId().equals(id)) {
                return body;
            }
        }
        return null;
    }

    public void centerOnBody(CelestialBody body) {
        if (isLaunching) {
            zoomLevel = 1.6;
        } else {
            zoomLevel = 1.0;
        }
        offsetX = ((body.x - width / 2.0)) * -1;
        offsetY = ((body.y - height / 2.0)) * -1;
    }

    private void onMouseScroll(long window, double scrollX, double scrollY) {
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];

        GLFW.glfwGetCursorPos(window, mouseX, mouseY);

        if (this.minecraft != null && this.minecraft.player != null) {
            if (this.minecraft.screen != null) {
                handleHotbarScroll(scrollY);
            }

            if (this.minecraft.screen instanceof CreativeModeInventoryScreen creativeScreen) {
                if (creativeScreen.mouseScrolled(mouseX[0], mouseY[0], scrollX, scrollY)) {
                    return;
                }
            }

            if (this.minecraft.screen instanceof PlanetSelectionScreen) {
                if (scrollY != 0) {
                    int i = 0;
                    while (i < 5) {
                        zoomLevel += scrollY * 0.004;
                        i++;
                    }
                    zoomLevel = Math.max(0.02, Math.min(zoomLevel, 2.0));
                }
            }
        }
    }

    public void initPlanetList() {
        for (int i = 0; i < PlanetSelectionScreen.PLANETS.size(); i++) {
            PlanetInfo planet = PlanetSelectionScreen.PLANETS.get(i);
            int x = 2;
            int buttonWidth = 74;
            int buttonHeight = 20;

            TexturedButton focusButton = new TexturedButton(x + 5, 20, buttonWidth, buttonHeight, planet.translatable, (btn) -> {
                focusedBody = planet;
                centerOnBody(planet);
                isPausePressed = true;
            })
                    .tex(
                            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button.png"),
                            ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button_hovered.png")
                    );

            focusButton.visible = false;
            addButtonToList(focusButton);
            this.addRenderableWidget(focusButton);


            for (MoonInfo moon : MOONS) {
                if (moon.orbitCenter == planet) {
                    TexturedButton moonButton = new TexturedButton(x + 5, 20, buttonWidth, buttonHeight, moon.translatable, (btn) -> {
                        focusedBody = moon;
                        centerOnBody(moon);
                        isPausePressed = true;
                    })
                            .tex(
                                    ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button.png"),
                                    ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/gui/util/buttons/launch_button_hovered.png")
                            );
                    addButtonToList(moonButton);
                    moonButton.visible = false;
                    this.addRenderableWidget(moonButton);

                }
            }
        }

        //Add back and next button
        TexturedButton backButton = new TexturedButton(5, 5, 15, 15, (btn) -> {
            if(currentPage != 0) {
                currentPage--;
            }
        })
                .tex(BACK_BUTTON, BACK_BUTTON);
        backButton.setPosition(10, (height / 2) - 177 / 2 + 52);
        this.addRenderableWidget(backButton);

        TexturedButton nextButton = new TexturedButton(5, 5, 15, 15, (btn) -> {
            if(currentPage < planetsButton.size() - 1) {
                currentPage++;
            }
        })
                .tex(NEXT_BUTTON, NEXT_BUTTON);
        nextButton.setPosition(30, (height / 2) - 177 / 2 + 52);
        this.addRenderableWidget(nextButton);

    }

    public void addButtonToList(TexturedButton button){
        if (planetsButton.isEmpty()) {
            ArrayList<TexturedButton> list = new ArrayList<>();
            list.add(button);
            planetsButton.add(list);
            return;
        }

        for (ArrayList<TexturedButton> buttons : planetsButton) {
            if(buttons.size() < 5){
                buttons.add(button);
                break;
            } else if (buttons.size() == 5) {
                if (planetsButton.indexOf(buttons) + 1 >= planetsButton.size()) {
                    ArrayList<TexturedButton> list = new ArrayList<>();
                    list.add(button);
                    planetsButton.add(list);
                    break;
                }
            }
        }
    }

    public void renderPlanetList(int page) {
        ScreenHelper.drawTexture(0, (height / 2) - 177 / 2, 105, 177, PlanetSelectionScreen.SMALL_MENU_LIST, true);

        AtomicInteger number = new AtomicInteger(0);

        for (TexturedButton buttons : this.planetsButton.get(page)) {
            buttons.setY((height / 2) - 177 / 2 + 47 + (number.getAndAdd(1) * 23));
            buttons.visible = true;
        }

        for (int i = 0; i < planetsButton.size(); i++) {
            if (i != page) {
                for (TexturedButton buttons : this.planetsButton.get(i)) {
                    buttons.visible = false;
                }
            }
        }
    }

    private boolean handleHotbarScroll(double scrollY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            int currentSlot = this.minecraft.player.getInventory().selected;
            int newSlot = currentSlot - (int) scrollY;

            newSlot = (newSlot + 9) % 9;

            if (newSlot != currentSlot) {
                this.minecraft.player.getInventory().selected = newSlot;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            isWheelButtonDown = true;
        }
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT || button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            dragging = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            if (showLargeMenu) {
                if (launchButton.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
                showLargeMenu = false;
                return true;
            } else {
                focusedBody = null;
                hoveredBody = null;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT || button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            dragging = false;
        }
        if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            isWheelButtonDown = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            if (isWheelButtonDown) {
                double rotationSpeed = 0.005;
                double deltaAngle = deltaX * rotationSpeed;

                for (PlanetInfo planet : PLANETS) {
                    planet.currentAngle += deltaAngle;
                    planet.updatePosition();
                }
                for (MoonInfo moon : MOONS) {
                    moon.currentAngle += deltaAngle;
                    moon.updatePosition();
                }

            } else {
                offsetX += Utils.changeLastDigitToEven((mouseX - lastMouseX) / zoomLevel);
                offsetY += Utils.changeLastDigitToEven((mouseY - lastMouseY) / zoomLevel);
                lastMouseX = mouseX;
                lastMouseY = mouseY;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    private void updateHighlighterPosition(GuiGraphics graphics, CelestialBody body) {
        int highlightWidth = (int) (body.width * zoomLevel);
        int highlightHeight = (int) (body.height * zoomLevel);
        float highlightX = (float) ((body.x + offsetX) * zoomLevel - (double) highlightWidth / 2);
        float highlightY = (float) ((body.y + offsetY) * zoomLevel - (double) highlightHeight / 2);

        currentHighlighterFrame = (currentHighlighterFrame + 1) % totalHighlighterFrames;

        int frameY = currentHighlighterFrame * highlightHeight;

        float currentAngle = body instanceof PlanetInfo ? (float) ((PlanetInfo) body).currentAngle : (float) ((MoonInfo) body).currentAngle;

        ScreenHelper.drawTexturewithRotation(graphics, HIGHLIGHTER_TEXTURE, (int) highlightX, (int) highlightY, 0, frameY, highlightWidth, highlightHeight, highlightWidth, totalHighlighterFrames * highlightHeight, currentAngle);
    }


    @Override
    public PlanetSelectionMenu getMenu() {
        return this.menu;
    }

    @Override
    public void onClose() {
        if(getPlayer().getEntityData().get(EntityData.DATA_PLANET_MENU_OPEN)) {
            return;
        }
        long windowHandle = Minecraft.getInstance().getWindow().getWindow();
        prevScrollCallback = GLFW.glfwSetScrollCallback(windowHandle, Minecraft.getInstance().mouseHandler::onScroll);

        super.onClose();
    }

    /** Space Station **/

    //We only check one time if the player have the recipes because normally he can't get item during the screen
    private void initSpaceStationRecipes() {
        for (SpaceStationRecipe recipe : SpaceStationRecipesManager.SPACE_STATION_RECIPES) {
            spaceStationRecipeStates.add(recipe.fromRecipe(this.getPlayer()));
        }
    }


    public Player getPlayer() {
        return menu.getPlayer();
    }

    public ModifiedButton addButton(int x, int y, int row, int width, int height, boolean rocketCondition,
                                    ModifiedButton.ButtonTypes type, List<String> list, ResourceLocation buttonTexture,
                                    ModifiedButton.ColorTypes colorType, Component title, Button.OnPress onPress) {
        return this.addRenderableWidget(new ModifiedButton(x, y, row, width, height, 0, 0, 0,
                rocketCondition, type, list, buttonTexture, colorType, width, height, onPress, title));
    }

}
