package me.sildev.zoopr.Luckyblock;

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


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LuckyBlockManager {

    public static Material luckyBlockitemType = Material.SPONGE;
    public static String luckyBlockItemName = coloredString.color("&f&ki&d Luckyblock &7(Right click) &f&ki");
    public static List<String> luckyBlockLore = new ArrayList<>();
    public static NamespacedKey luckyblockItemConfirmation = new NamespacedKey(ZooPR.getPlugin(), "luckyblock-confirm");

    private static File luckyblockLootFile;
    private static FileConfiguration luckyblockLoot;


    public LuckyBlockManager() {

        createLuckyBlockLootConfig();
        for (String s : (List<String>)luckyblockLoot.getList("lore")) {
            luckyBlockLore.add(coloredString.color(s));
        }
    }

    public void createLuckyBlockLootConfig() {
        luckyblockLootFile = new File(ZooPR.getPlugin().getDataFolder(), "luckyblock.yml");
        if (!luckyblockLootFile.exists()) {
            luckyblockLootFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("luckyblock.yml", false);
        }

        luckyblockLoot = new YamlConfiguration();
        try {
            luckyblockLoot.load(luckyblockLootFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static ItemStack generateLuckyBlock() {
        ItemStack luckyBlock = new ItemStack(luckyBlockitemType);
        ItemMeta meta = luckyBlock.getItemMeta();
        meta.setDisplayName(luckyBlockItemName);
        meta.setLore(luckyBlockLore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(luckyblockItemConfirmation, PersistentDataType.INTEGER, 1);
        luckyBlock.setItemMeta(meta);

        return luckyBlock;
    }

    public static HashMap<String, List<String>> getLoot() {
        HashMap<String, List<String>> loot = new HashMap<>();
        List<String> commonLoot = (List<String>) luckyblockLoot.getList("common");
        List<String> rareLoot = (List<String>) luckyblockLoot.getList("rare");

        loot.put("common", commonLoot);
        loot.put("rare", rareLoot);

        return loot;
    }
}
