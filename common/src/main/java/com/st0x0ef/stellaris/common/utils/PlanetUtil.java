package com.st0x0ef.stellaris.common.utils;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.data.planets.StellarisData;
import com.st0x0ef.stellaris.common.menus.MilkyWayMenu;
import com.st0x0ef.stellaris.common.menus.PlanetSelectionMenu;
import com.st0x0ef.stellaris.common.menus.TabletMenu;
import com.st0x0ef.stellaris.common.menus.WaitMenu;
import com.st0x0ef.stellaris.common.oxygen.GlobalOxygenManager;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PlanetUtil {
    public static Planet getPlanet(ResourceLocation level) {
        AtomicReference<Planet> p = new AtomicReference<>();
        StellarisData.getPlanets().forEach(planet -> {if (planet.dimension().equals(level)) p.set(planet);});
        return p.get();
    }

    public static boolean isPlanet(ResourceLocation level) {
        AtomicBoolean isPlanet = new AtomicBoolean(false);
        StellarisData.getPlanets().forEach(planet -> {if (planet.dimension().equals(level)) isPlanet.set(true);});
        return isPlanet.get();
    }

    public static boolean hasOxygen(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return hasOxygenAt(serverLevel, null);
        }

        return false;
    }

    public static boolean hasOxygenAt(ServerLevel level, @Nullable BlockPos pos) {
        if (isPlanet(level.dimension().location())) {
            if (!getPlanet(level.dimension().location()).oxygen()) {
                if (pos == null){
                    return false;
                }

                return GlobalOxygenManager.getInstance().getOrCreateDimensionManager(level).hasOxygenAt(pos);
            }
        }

        return true;
    }

    /** Get the resourcelocation of the planet bar set in the Planet file */
    public static ResourceLocation getPlanetBar(ResourceLocation level) {
        if (isPlanet(level)) {
            return getPlanet(level).textures().planet_bar();
        }
        return ResourceLocation.fromNamespaceAndPath(Stellaris.MODID, "textures/planet_bar/earth_planet_bar.png");
    }

    public static int openPlanetSelectionMenu(Player player, boolean forceCanGoTo) {
        ExtendedMenuProvider provider = new ExtendedMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buffer) {
                buffer.writeBoolean(forceCanGoTo);
            }

            @Override
            public Component getDisplayName() {
                return Component.literal("Planets");
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                return PlanetSelectionMenu.create(syncId, inv, buffer.writeBoolean(forceCanGoTo));
            }
        };

        if (player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, provider);
            return 1;
        }

        return 0;
    }

    public static int openWaitMenu(Player player, String playerChoosing) {
        System.out.println("eee");
        ExtendedMenuProvider provider = new ExtendedMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buffer) {
                buffer.writeUtf(playerChoosing);
            }

            @Override
            public Component getDisplayName() {
                return Component.literal("Waiting");
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                return WaitMenu.create(syncId, inv, buffer.writeUtf(playerChoosing));
            }
        };

        if (player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, provider);
            return 1;
        }

        return 0;
    }

    public static int openTabletMenu(Player player) {
        ExtendedMenuProvider provider = new ExtendedMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buffer) {}

            @Override
            public Component getDisplayName() {
                return Component.literal("Tablet");
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                return TabletMenu.create(syncId, inv, buffer);
            }
        };

        if (player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, provider);
            return 1;
        }

        return 0;
    }

    public static int openMilkyWayMenu(Player player) {
        ExtendedMenuProvider provider = new ExtendedMenuProvider() {

            @Override
            public void saveExtraData(FriendlyByteBuf buf) {

            }

            @Override
            public Component getDisplayName() {
                return Component.literal("MilkyWay");
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                return MilkyWayMenu.create(syncId, inv, buffer);
            }
        };

        if (player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, provider);
            return 1;
        }

        return 0;
    }
}
