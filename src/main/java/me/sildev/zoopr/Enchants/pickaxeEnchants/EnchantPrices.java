package me.sildev.zoopr.Enchants.pickaxeEnchants;

import me.sildev.zoopr.Enchants.CustomEnchants;
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

        for (Enchantment ce : Enchantment.values()) {
            if (pricesConfig.contains(ce.getName())) {
                prices.put(ce, pricesConfig.getInt(ce.getName()));
            }
        }
    }

    public static double getEnchantPrice(Enchantment ce, int levelToGetTo) {
        double basePrice = getPrice(ce);
        double price = basePrice;

        int incrementType = (int) pricesConfig.get("increment_type");
        if (incrementType == 1) {
            // % increase
            double increase = (pricesConfig.getDouble(ce.getName() + "_increase") / 100) + 1;

            for (int i = 0; i < levelToGetTo; i++) {
                price *= increase;
            }

        } else if (incrementType == 0) {
           // Constant value
            double increase = (pricesConfig.getDouble(ce.getName() + "_increase"));

            for (int i = 0; i < levelToGetTo; i++) {
                price += increase;
            }
        }

        return price;
    }


    static HashMap<Enchantment, Integer> prices = new HashMap<Enchantment, Integer>();

    public static double getPrice(Enchantment ce) {
        return prices.get(ce);
    }
}
