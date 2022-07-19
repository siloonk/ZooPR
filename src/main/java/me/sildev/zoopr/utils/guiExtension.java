package me.sildev.zoopr.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class guiExtension {

    public static void addRenamedItem(Inventory gui, int slot, Material type, String name) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(coloredString.color(name));
        item.setItemMeta(meta);
        gui.setItem(slot, item);
    }

    public static void addRenamedItemWithLore(Inventory gui, int slot, Material type, String name, String[] lore) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(coloredString.color(name));

        List<String> l = new ArrayList<>();
        for (String line : lore) {
            l.add(coloredString.color(line));
        }

        meta.setLore(l);
        item.setItemMeta(meta);
        gui.setItem(slot, item);
    }
}
