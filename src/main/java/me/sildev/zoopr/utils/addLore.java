package me.sildev.zoopr.utils;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.robots.RobotManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class addLore {

    public static void addLore(ItemStack pickaxe, Player player) {
        ItemMeta meta = pickaxe.getItemMeta();

        List<String> lore = new ArrayList<String>();

        if (meta.getLore() != null) {
            lore.add(meta.getLore().get(0));
            lore.add(ChatColor.GRAY + "Blocksbroken " + formatNumber.coolFormat(meta.getPersistentDataContainer().get(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE), 0));
            lore.add(meta.getLore().get(2));
        } else {
            lore.add("");
            lore.add(ChatColor.GRAY + "Blocksbroken: 0");
            lore.add(ChatColor.GRAY + "Owner: " + player.getName());
        }
        lore.add("");

        for (Enchantment ce : pickaxe.getEnchantments().keySet()) {
            lore.add(ChatColor.GRAY + getEnchantmentName.getEnchantmentName(ce) + " " + pickaxe.getEnchantmentLevel(ce));
        }


        lore.add(" ");
        lore.add(ChatColor.GRAY + "Level " + Math.round(meta.getPersistentDataContainer().get(new NamespacedKey(ZooPR.getPlugin(), "level"), PersistentDataType.DOUBLE)));
        meta.setLore(lore);
        player.getInventory().getItemInMainHand().setItemMeta(meta);
    }


    public static ItemStack addRobotLore(ItemStack robot) {
        ItemStack item = robot;
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<String>();

        PersistentDataContainer container = meta.getPersistentDataContainer();

        lore.add("");
        lore.add(coloredString.color("&dOwner &f" + Bukkit.getOfflinePlayer(UUID.fromString(container.get(RobotManager.owner, PersistentDataType.STRING))).getName()));
        lore.add(coloredString.color("&dLevel &f" + container.get(RobotManager.level, PersistentDataType.INTEGER)));
        lore.add(coloredString.color("&dType &f" + container.get(RobotManager.type, PersistentDataType.STRING)));
        lore.add(" ");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
