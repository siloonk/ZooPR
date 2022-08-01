package me.sildev.zoopr.pouches;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class pouchManager {

    static NamespacedKey min = new NamespacedKey(ZooPR.getPlugin(), "pouch-min");
    static NamespacedKey max = new NamespacedKey(ZooPR.getPlugin(), "pouch-max");
    static NamespacedKey pouchType = new NamespacedKey(ZooPR.getPlugin(), "pouch-type");

    static FileConfiguration pouchesConfig;
    static File pouchesConfigFile;

    public static Material pouchItemType;
    public static int maxPouchLevel;
    static String pouchName;

    public pouchManager() {
        createPouchesConfig();
        pouchItemType = Material.matchMaterial(pouchesConfig.getString("POUCH_ITEM"));
        pouchName = coloredString.color(pouchesConfig.getString("POUCH_NAME"));
        maxPouchLevel = pouchesConfig.getInt("HIGHEST_TIER");
    }


    public static ItemStack generatePouch(int tier, String type) {
        if (!(type.equalsIgnoreCase("tokens") || type.equalsIgnoreCase("money"))) return null;
        if (!pouchesConfig.contains("Pouches." + "TIER" + tier + "." + type.toUpperCase() + ".MIN")) return null;

        int minValue = pouchesConfig.getInt("Pouches." + "TIER" + tier + type.toUpperCase() + ".MIN");
        int maxValue = pouchesConfig.getInt("Pouches." + "TIER" + tier + "." + type.toUpperCase() + ".MAX");

        ItemStack pouch = new ItemStack(pouchItemType);
        ItemMeta meta = pouch.getItemMeta();
        String name = pouchName.replaceAll("%tier%", String.valueOf(tier));
        name = name.replaceAll("%type%", type.toUpperCase());

        meta.setDisplayName(name);
        List<String> lore = new ArrayList<String>() {{
            add(coloredString.color("&7" + type.toLowerCase() + " pouch, right click to use!"));
        }};
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(min, PersistentDataType.INTEGER, minValue);
        container.set(max, PersistentDataType.INTEGER, maxValue);
        container.set(pouchType, PersistentDataType.STRING, type.toUpperCase());
        pouch.setItemMeta(meta);

        return pouch;
    }


    private static void createPouchesConfig() {
        pouchesConfigFile = new File(ZooPR.getPlugin().getDataFolder(), "pouches.yml");
        if (!pouchesConfigFile.exists()) {
            pouchesConfigFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("pouches.yml", false);
        }

        pouchesConfig = new YamlConfiguration();
        try {
            pouchesConfig.load(pouchesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void reloadPouchesConfig() {
        try {
            pouchesConfig.load(pouchesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
