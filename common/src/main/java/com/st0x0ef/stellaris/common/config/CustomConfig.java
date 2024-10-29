package com.st0x0ef.stellaris.common.config;

import com.google.gson.JsonObject;
import com.st0x0ef.stellaris.Stellaris;
import dev.architectury.platform.Platform;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CustomConfig {

    public static Map<String, ConfigEntry<?>> CONFIG = new HashMap<>();

    public static void init() {
        loadConfigFile();
        addEntries();
        writeConfigFile("stellaris.json");
    }


    public static void addEntries() {
        addEntry("planetScreenGravityColor", new ConfigEntry<>("White", ""));
//
//        addEntry("uraniumBurnTime", new ConfigEntry<>(/*coalBurnTime*5 */8000, "Burn time for uranium ingot in Radioactive Generator"));
//        addEntry("plutoniumBurnTime", new ConfigEntry<>(/*uraniumBurnTime*1.5 */12000, "Burn time for plutonium ingot in Radioactive Generator"));
//        addEntry("neptuniumBurnTime", new ConfigEntry<>(/*uraniumBurnTime*2 */16000, "Burn time for neptunium ingot in Radioactive Generator"));

//        addEntry("customSky", new ConfigEntry<>(true, "Render custom sky on planet"));
    }

    public static void addEntry(String name, ConfigEntry<?> entry) {
        CONFIG.merge(name, entry, (a, b) -> new ConfigEntry<>(a.value(), b.description()));
    }

    public static void writeConfigFile(String path) {
        try {
            FileWriter file = new FileWriter(Platform.getConfigFolder() + "/" + path);
            file.write(Stellaris.GSON.toJson(CONFIG));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfigFile() {

        String path = Platform.getConfigFolder() + "/stellaris.json";

        String jsonString;
        try {
            jsonString = readFileAsString(path);
            JsonObject jsonObject = Stellaris.GSON.fromJson(jsonString, JsonObject.class);

            jsonObject.getAsJsonObject().entrySet().forEach(entry -> {
                ConfigEntry<?> obj2 = Stellaris.GSON.fromJson(entry.getValue(), ConfigEntry.class);
                CONFIG.put(entry.getKey(), obj2);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static Object getValue(String key) {
        return CONFIG.get(key).value();
    }
}