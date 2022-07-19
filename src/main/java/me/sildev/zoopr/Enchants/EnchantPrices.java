package me.sildev.zoopr.Enchants;

import me.sildev.zoopr.ZooPR;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class EnchantPrices {

    static FileConfiguration pricesConfig;
    static File pricesConfigFile;

    public EnchantPrices() {
        createPricesConfig();
    }

    private static void createPricesConfig() {
        pricesConfigFile = new File(ZooPR.getPlugin().getDataFolder(), "prices.yml");
        if (!pricesConfigFile.exists()) {
            pricesConfigFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("prices.yml", false);
        }

        pricesConfig = new YamlConfiguration();
        try {
            pricesConfig.load(pricesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        loadValues();
    }

    static void loadValues() {
        for (Enchantment ce : CustomEnchants.Enchantments) {
            prices.put(ce, pricesConfig.getInt(ce.getName()));
        }
    }

    static HashMap<Enchantment, Integer> prices = new HashMap<Enchantment, Integer>();

    public static int getPrice(Enchantment ce) {
        return prices.get(ce);
    }
}
