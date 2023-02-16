package me.sildev.zoopr.shops;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopManager {

    File shopsConfigFile;
    FileConfiguration shopsConfig;

    public ShopManager() {
        createShopsConfig();
    }

    void createShopsConfig() {
        shopsConfigFile = new File(ZooPR.getPlugin().getDataFolder(), "shops.yml");
        if (!shopsConfigFile.exists()) {
            shopsConfigFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("shops.yml", false);
        }

        shopsConfig = new YamlConfiguration();
        try {
            shopsConfig.load(shopsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void reloadShops() {
        try {
            shopsConfig.load(shopsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Inventory loadIventory(String inventoryName) {
        int rows = shopsConfig.getInt(inventoryName + ".rows");
        Inventory gui = Bukkit.createInventory(null, rows * 9, inventoryName + " Shop");

        ItemStack background = new ItemStack(Material.matchMaterial(shopsConfig.getString(inventoryName + ".background")));
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, background);
        }

        List<String> sellables = (List<String>) shopsConfig.getList(inventoryName + ".sellables");
        // Where do i start with the sellable items
        int offset = 10;

        // Where do i end with the sellable items
        int limit = 44;
        for (int i = 0; i < sellables.size(); i++) {
            // Letting you go down a row
            if ((i + offset + 1) % 9 == 0)
                offset += 2;

            String item = sellables.get(i);
            List<String> Configlore = (List<String>) shopsConfig.getList(inventoryName + ".lores");
            List<String> lore = new ArrayList<>();
            for (String line : Configlore) {
                lore.add(coloredString.color(line.replaceAll("%cost%", formatNumber.coolFormat(shopsConfig.getInt(inventoryName + ".values." + item), 0)).replaceAll("%itemtype%", item)));
            }
            ItemStack sellableitem = new ItemStack(Material.matchMaterial(item));
            ItemMeta meta = sellableitem.getItemMeta();
            meta.setLore(lore);
            meta.setDisplayName(coloredString.color(shopsConfig.getString(inventoryName + ".names." + item)));
            sellableitem.setItemMeta(meta);
            gui.setItem(i + offset, sellableitem);
        }
        return gui;
    }

    public ItemStack getBackGroundItem(String inventoryName) {
        String itemName = shopsConfig.getString(inventoryName + ".background");
        return new ItemStack(Material.matchMaterial(itemName));
    }

    public int getCost(String inventoryName, Material itemType) {
        int cost  = shopsConfig.getInt(inventoryName + ".values." + itemType.toString());
        return cost;
    }

    public boolean doesShopExist(String inventoryName) {
        return shopsConfig.contains(inventoryName);
    }

    public String getCurrency(String inventoryName) {
        return shopsConfig.getString(inventoryName + ".currency");
    }
}
