package com.st0x0ef.stellaris.common.utils;

import com.mojang.serialization.Codec;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.entities.vehicles.LanderEntity;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.EntityData;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.common.registry.StatsRegistry;
import net.minecraft.client.player.LocalPlayer;
import com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType;
import dev.architectury.utils.GameInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;

public class Utils {

    /** Transfert the rocket inventory inside the Lander inventory */
    public static void transfertInventory(RocketEntity rocket, LanderEntity lander) {
        Container rocketContainer = rocket.getInventory();
        Container landerContainer = lander.getInventory();

        for (int i = 2; i < lander.getInventory().getContainerSize() - 1; i++) {
            landerContainer.setItem(i, rocketContainer.getItem(i));
        }

        ItemStack rocketStack = new ItemStack(ItemsRegistry.ROCKET.get());
        rocketStack.set(DataComponentsRegistry.ROCKET_COMPONENT.get(), rocket.getRocketComponent());
        landerContainer.setItem(14, rocketStack);
    }

    /** Should be call after teleporting the player */
    public static LanderEntity createLanderFromRocket(Entity player, RocketEntity rocket, int yPos, Level destination) {
        LanderEntity lander = new LanderEntity(destination);
        lander.setPos(player.getX(), yPos, player.getZ());
        transfertInventory(rocket, lander);

        rocket.discard();

        return lander;
    }

    /** Teleport an entity to the planet wanted */
    public static void teleportEntity(Entity entity, Planet destination) {
        if (entity.level().isClientSide()) return;
        entity.setNoGravity(false);

        TeleportUtil.teleportToPlanet(entity, getPlanetLevel(destination), 600);
    }

    /** To use with the planetSelection menu */
    public static void changeDimension(Player player, Planet destination) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getVehicle() instanceof RocketEntity rocket) {
                serverPlayer.stopRiding();
                serverPlayer.closeContainer();

                ItemStack fuelType = rocket.getInventory().getItem(10);
                if (fuelType.isEmpty()) {
                    fuelType = ItemsRegistry.FUEL_BUCKET.get().getDefaultInstance();
                }

                if (!serverPlayer.isCreative() && !serverPlayer.isSpectator()) {
                    int fuelConsumption = Math.round(FuelType.getFuelNeededToGoOnPlanet(PlanetUtil.getPlanet(serverPlayer.level().dimension().location()), destination, fuelType.getItem()));
                    rocket.FUEL -= fuelConsumption;
                    rocket.syncRocketData(serverPlayer);
                }

                LanderEntity lander = createLanderFromRocket(serverPlayer, rocket, 600, getPlanetLevel(destination));
                teleportEntity(serverPlayer, destination);
                player.awardStat(StatsRegistry.SPACE_TRAVEL.get(), Utils.distanceToPlanet(PlanetUtil.getPlanet(player.level().dimension().location()), destination));

                serverPlayer.level().addFreshEntity(lander);

                while (!serverPlayer.startRiding(lander, true)) {
                    // Wait until the player starts riding the lander
                }

                serverPlayer.sendSystemMessage(Component.translatable("message.stellaris.lander"));
            } else {
                serverPlayer.closeContainer();
                teleportEntity(serverPlayer, destination);
            }
        }
    }

    public static void changeDimensionForPlayers(List<Entity> entities, Planet destination) {
        RocketEntity rocket = (RocketEntity) entities.getFirst().getVehicle();

        for (Entity entity : entities) {
            if (entity.level().isClientSide()) return;

            Entity vehicle = entity.getVehicle();
            if (vehicle instanceof RocketEntity playerRocket) {
                entity.stopRiding();
                rocket = playerRocket;
                teleportEntity(entity, destination);

                if(entity instanceof Player player) {

                    player.awardStat(StatsRegistry.SPACE_TRAVEL.get(), Utils.distanceToPlanet(PlanetUtil.getPlanet(player.level().dimension().location()), destination));

                    player.closeContainer();
                    player.getEntityData().set(EntityData.DATA_PLANET_MENU_OPEN, false);
                }
            }
        }
        LanderEntity lander = createLanderFromRocket(entities.getFirst(), rocket, 600, getPlanetLevel(destination));
        entities.getFirst().level().addFreshEntity(lander);

        for (Entity entity : entities) {
            entity.startRiding(lander, true);
            entity.sendSystemMessage(Component.translatable("message.stellaris.lander"));
        }
    }

    public static int distanceToPlanet(Planet actual, Planet destination) {
        return Mth.abs(actual.distanceFromEarth() - destination.distanceFromEarth());
    }

    public static double changeLastDigitToEven(double number) {
        String numberStr = Double.toString(number);
        int decimalIndex = numberStr.indexOf('.');

        if (decimalIndex != -1) {
            String beforeDecimal = numberStr.substring(0, decimalIndex + 1);
            String afterDecimal = numberStr.substring(decimalIndex + 1);

            char lastChar = afterDecimal.charAt(afterDecimal.length() - 1);

            if ((lastChar - '0') % 2 != 0) {
                afterDecimal = afterDecimal.substring(0, afterDecimal.length() - 1) + '2';
            }

            numberStr = beforeDecimal + afterDecimal;
        }

        return Double.parseDouble(numberStr);
    }

    /** COLOR!!! */
    public static int getColorHexCode(String colorName) {
        // Custom Colour Hex Code Support
        if (colorName.startsWith("#")) {
            try {
                return Integer.parseInt(colorName.substring(1), 16);
            } catch (NumberFormatException e) {
                return 0xFFFFFF; // Return white if invalid hex format
            }
        }

        return switch (colorName.toLowerCase()) {
            case "black" -> 0x000000;
            case "red" -> 0xFF0000;
            case "green" -> 0x008000;
            case "blue" -> 0x0000FF;
            case "yellow" -> 0xFFFF00;
            case "cyan" -> 0x00FFFF;
            case "magenta" -> 0xFF00FF;
            case "gray" -> 0x808080;
            case "maroon" -> 0x800000;
            case "olive" -> 0x808000;
            case "purple" -> 0x800080;
            case "teal" -> 0x008080;
            case "navy" -> 0x000080;
            case "orange" -> 0xFFA500;
            case "brown" -> 0xA52A2A;
            case "lime" -> 0x00FF00;
            case "pink" -> 0xFFC0CB;
            case "coral" -> 0xFF7F50;
            case "gold" -> 0xFFD700;
            case "silver" -> 0xC0C0C0;
            case "beige" -> 0xF5F5DC;
            case "lavender" -> 0xE6E6FA;
            case "turquoise" -> 0x40E0D0;
            case "salmon" -> 0xFA8072;
            case "khaki" -> 0xF0E68C;
            case "darkred" -> 0x8B0000;
            case "dark_red" -> 0x8B0000;
            case "rainbow" -> generateRandomHexColor();
            default -> 0xFFFFFF;
        };
    }

    public static int generateRandomHexColor() {
        Random random = new Random();
        return random.nextInt(0xFFFFFF + 1);
    }

    public static String betterIntToString(int i) {
        if (i == 0) return "0";

        return (i % 1000) + "K";
    }

    /** gui convenience feature */
    public static Component getMessageComponent(String text, String color) {
        return Component.literal(text).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(getColorHexCode(color))));
    }

    public static Component getMessageComponent(String text, int color) {
        return Component.literal(text).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));
    }

    public static Component getMessageComponent(String text) {
        return Component.literal(text).setStyle(Style.EMPTY);
    }

    /** dimension util */
    public static int getPlayerCountInDimension(MinecraftServer server, ResourceLocation dimensionKey) {
        ServerLevel dimension = server.getLevel(Utils.getPlanetLevel(dimensionKey));
        if (dimension == null) {
            return 0;
        }
        return dimension.players().size();
    }

    /** codec */
    public static <T extends Enum<T>> Codec<T> EnumCodec(Class<T> e) {
        Function<String, T> stringToEnum = s -> Enum.valueOf(e, s.toUpperCase(Locale.ROOT));

        Function<T, String> enumToString = Enum::name;

        return Codec.STRING.xmap(stringToEnum, enumToString);
    }


    /**
     * @param MCG Minecraft Gravity Unit (blocks/t²)
     * @return m/s²
     */
    public static float MCGToMPS2(float MCG){
        return 122.583125f*MCG;
    }

    /**
     * @param MPS2 m/s²
     * @return Minecraft Gravity Unit (blocks/t²)
     */
    public static double MPS2ToMCG(float MPS2) {
        if (MPS2>0) return Math.floor(0.00816d * MPS2 * 100000) / 100000;
        else if (MPS2<0) return Math.ceil(0.00816d * MPS2 * 100000) / 100000;
        else return 0;
    }

    public static void disableFlyAntiCheat(Player player, boolean condition) {
        if (player instanceof ServerPlayer) {
            if (condition) {
                ((ServerPlayer) player).connection.aboveGroundTickCount = 0;
            }
        }
    }

    public static boolean isLivingInJetSuit(LivingEntity entity) {
        return isLivingInArmor(entity, EquipmentSlot.FEET, ItemsRegistry.JETSUIT_BOOTS.get()) && isLivingInArmor(entity, EquipmentSlot.HEAD, ItemsRegistry.JETSUIT_HELMET.get()) && isLivingInArmor(entity, EquipmentSlot.CHEST, ItemsRegistry.JETSUIT_SUIT.get()) && isLivingInArmor(entity, EquipmentSlot.LEGS, ItemsRegistry.JETSUIT_LEGGINGS.get());
    }

    public static boolean isLivingInSpaceSuit(LivingEntity entity) {
        return isLivingInArmor(entity, EquipmentSlot.FEET, ItemsRegistry.SPACESUIT_BOOTS.get()) && isLivingInArmor(entity, EquipmentSlot.LEGS, ItemsRegistry.SPACESUIT_LEGGINGS.get()) && isLivingInArmor(entity, EquipmentSlot.CHEST, ItemsRegistry.SPACESUIT_SUIT.get()) && isLivingInArmor(entity, EquipmentSlot.HEAD, ItemsRegistry.SPACESUIT_HELMET.get());
    }


    public static boolean isLivingInArmor(LivingEntity entity, EquipmentSlot slot, Item item) {
        return entity.getItemBySlot(slot).getItem() == item;
    }

    public static ResourceKey<Level> getPlanetLevel(ResourceLocation planet) {
        return ResourceKey.create(ResourceKey.createRegistryKey(planet), planet);
    }

    public static ServerLevel getPlanetLevel(Planet planet) {
        for (ServerLevel level : GameInstance.getServer().getAllLevels()) {
            if (level.dimension().location().equals(planet.dimension())) {
                return level;
            }
        }
        return null;
    }

    public static boolean entityHasBlockAbove(LivingEntity entity, @Nullable BlockPos pos, @Nullable Integer recusion) {

        if(pos == null) pos = entity.blockPosition();
        if(recusion == null) recusion = 0;

        if(recusion > 10) return false;

        if(entity.level().getBlockState(pos).is(BlockTags.AIR)) {
            recusion += 1;
            return !entityHasBlockAbove(entity, pos.above(), recusion);
        }

        return false;
    }
}
