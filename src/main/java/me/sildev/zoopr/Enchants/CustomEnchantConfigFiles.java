package me.sildev.zoopr.Enchants;

import me.sildev.zoopr.ZooPR;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomEnchantConfigFiles {

    static FileConfiguration enchantsChances;
    static File enchantsChancesConfigFile;

    private static ZooPR instance;

    public CustomEnchantConfigFiles(ZooPR plugin) {
        instance = plugin;
        createEnchantsConfig();
    }


    private static void createEnchantsConfig() {
        enchantsChancesConfigFile = new File(instance.getDataFolder(), "enchants.yml");
        if (!enchantsChancesConfigFile.exists()) {
            enchantsChancesConfigFile.getParentFile().mkdirs();
            instance.saveResource("enchants.yml", false);
        }

        enchantsChances = new YamlConfiguration();
        try {
            enchantsChances.load(enchantsChancesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static double getEnchantmentChance(String path) {
        return enchantsChances.getDouble(path.toUpperCase());
    }

    public static double getEnchantmentAmount(String path) {return enchantsChances.getDouble(path.toUpperCase()); }

    public static double getEnchantmentLevelRequired(String path) {return enchantsChances.getInt(path.toUpperCase()); }

}
