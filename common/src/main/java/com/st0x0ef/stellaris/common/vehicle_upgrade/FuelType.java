package com.st0x0ef.stellaris.common.vehicle_upgrade;

import com.mojang.serialization.Codec;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public class FuelType {
    public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
    public static final Codec<Type.Radioactive> RADIOACTIVE_CODEC = StringRepresentable.fromEnum(Type.Radioactive::values);

    public static float getMegametersTraveled(int fuelQuantity, Item fuelItem) {
        Type type = Type.getTypeBasedOnItem(fuelItem);

        if (type != null && type != Type.RADIOACTIVE) {
            return switch (type) {
                case FUEL -> 19.22f * fuelQuantity; // Need 20mb to go on Moon, 2133mb to go on Venus, 2900mb to go on Mars and 4786mb to go on Mercury (approx)
                case HYDROGEN -> 21.36f * fuelQuantity; // Need 18mb to go on Moon, 1920mb to go on Venus, 2610mb to go on Mars and 4307mb to go on Mercury (approx)
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
        }

        Type.Radioactive radioactiveElement = Type.Radioactive.getTypeBasedOnItem(fuelItem);

        if (radioactiveElement != null && type == Type.RADIOACTIVE) {
            return switch (radioactiveElement) {
                case URANIUM -> 23.74f * fuelQuantity; // Need 16mb to go on Moon, 1728mb to go on Venus, 2349mb to go on Mars and 3876mb to go on Mercury (approx)
                case NEPTUNIUM -> 26.38f * fuelQuantity; // Need 15mb to go on Moon, 1555mb to go on Venus, 2114mb to go on Mars and 3488mb to go on Mercury (approx)
                case PLUTONIUM -> 29.3f * fuelQuantity; // Need 14mb to go on Moon, 1400mb to go on Venus, 1903mb to go on Mars and 3140mb to go on Mercury (approx)
            };
        }

        return 0.0f;
    }

    public static Item getFuelItem(Type type, Type.Radioactive radioactiveElement) {
        if (radioactiveElement == null) {
            return switch (type) {
                case Type.FUEL -> ItemsRegistry.FUEL_BUCKET.get();
                case Type.HYDROGEN -> ItemsRegistry.HYDROGEN_BUCKET.get();
                case RADIOACTIVE -> null;
            };
        } else {
            return switch (type) {
                case Type.FUEL -> ItemsRegistry.FUEL_BUCKET.get();
                case Type.HYDROGEN -> ItemsRegistry.HYDROGEN_BUCKET.get();
                case Type.RADIOACTIVE -> switch (radioactiveElement) {
                    case Type.Radioactive.URANIUM -> ItemsRegistry.URANIUM_INGOT.get();
                    case Type.Radioactive.NEPTUNIUM -> ItemsRegistry.NEPTUNIUM_INGOT.get();
                    case Type.Radioactive.PLUTONIUM -> ItemsRegistry.PLUTONIUM_INGOT.get();
                };
            };
        }
    }

    public static Item getItemBasedOnTypeName(String name) {
        if (name.equals(Type.FUEL.getSerializedName())) {
            return ItemsRegistry.FUEL_BUCKET.get();
        } else if (name.equals(Type.HYDROGEN.getSerializedName())) {
            return ItemsRegistry.HYDROGEN_BUCKET.get();
        } else if (name.equals(Type.Radioactive.URANIUM.getSerializedName())) {
            return ItemsRegistry.URANIUM_INGOT.get();
        } else if (name.equals(Type.Radioactive.NEPTUNIUM.getSerializedName())) {
            return ItemsRegistry.NEPTUNIUM_INGOT.get();
        } else if (name.equals(Type.Radioactive.PLUTONIUM.getSerializedName())) {
            return ItemsRegistry.PLUTONIUM_INGOT.get();
        }

        return null;
    }

    public static Item getItemBasedOnLoacation(ResourceLocation location) {
        return ItemsRegistry.ITEMS.getRegistrar().get(location);
    }

    public enum Type implements StringRepresentable {
        FUEL,
        HYDROGEN,
        RADIOACTIVE;

        public static Type getTypeBasedOnItem(Item item) {
            if (item == null) return null;
            if (item.getDefaultInstance().is(ItemsRegistry.FUEL_BUCKET.get())) {
                return FUEL;
            } else if (item.getDefaultInstance().is(ItemsRegistry.HYDROGEN_BUCKET.get())) {
                return HYDROGEN;
            }

            return null;
        }

        public static Type fromString(String name) {
            return switch (name) {
                case "fuel" -> FUEL;
                case "hydrogen" -> HYDROGEN;
                case "radioactive" -> RADIOACTIVE;
                default -> null;
            };
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }

        public enum Radioactive implements StringRepresentable {
            URANIUM,
            NEPTUNIUM,
            PLUTONIUM;

            public static Radioactive getTypeBasedOnItem(Item item) {
                if (item == null) return null;
                if (item.getDefaultInstance().is(ItemsRegistry.URANIUM_INGOT.get())) {
                    return URANIUM;
                } else if (item.getDefaultInstance().is(ItemsRegistry.NEPTUNIUM_INGOT.get())) {
                    return NEPTUNIUM;
                } else if (item.getDefaultInstance().is(ItemsRegistry.PLUTONIUM_INGOT.get())) {
                    return PLUTONIUM;
                }

                return null;
            }

            public static Radioactive fromString(String name) {
                return switch (name) {
                    case "uranium" -> URANIUM;
                    case "neptunium" -> NEPTUNIUM;
                    case "plutonium" -> PLUTONIUM;
                    default -> null;
                };
            }

            @Override
            public String getSerializedName() {
                return name().toLowerCase();
            }
        }
    }
}

