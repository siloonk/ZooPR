package me.sildev.zoopr.utils;

import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.robots.RobotManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
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
            lore.add(meta.getLore().get(1));
        } else {
            lore.add("");
            lore.add(coloredString.color("&f&l↪ &5Owned by: &d" + player.getName()));
        }
        lore.add(coloredString.color("&5&l|----------------------------"));

        for (Enchantment ce : pickaxe.getEnchantments().keySet()) {
            if (!isRebirthEnchantment(ce))
                lore.add(coloredString.color("&f&l❖ &5&o" + getEnchantmentName.getEnchantmentName(ce) + ": &d" + pickaxe.getEnchantmentLevel(ce)));
        }
        boolean hasFoundRebirthCe = false;

        for (Enchantment ce : pickaxe.getEnchantments().keySet()) {
            if (isRebirthEnchantment(ce)) {
                if (!hasFoundRebirthCe) {
                    lore.add(coloredString.color("&5&l|----------------------------"));
                    hasFoundRebirthCe = true;
                }
                lore.add(coloredString.color("&f&l✡ &5&o" + getEnchantmentName.getEnchantmentName(ce) + ": &d" + pickaxe.getEnchantmentLevel(ce)));
            }
        }
        lore.add(coloredString.color("&5&l|----------------------------"));

        lore.add(coloredString.color("&f&l⛏ &5&oBlocks Broken: &d " + formatNumber.coolFormat(meta.getPersistentDataContainer().get(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE), 0)));
        lore.add(coloredString.color( "&f&ki &5&oLevel: &d&o" + Math.round(meta.getPersistentDataContainer().get(new NamespacedKey(ZooPR.getPlugin(), "level"), PersistentDataType.DOUBLE)) + "/100 &f&ki"));
        double progress = (meta.getPersistentDataContainer().get(new NamespacedKey(ZooPR.getPlugin(), "level-exp"), PersistentDataType.DOUBLE) / meta.getPersistentDataContainer().get(new NamespacedKey(ZooPR.getPlugin(), "level-exp-requirement"), PersistentDataType.DOUBLE));
        StringBuilder progressBar = new StringBuilder("&7[");
        for (int i = 0; i < 10; i++) {
            if (progress * 10 >= i)
                progressBar.append("&5■");
            else
                progressBar.append("&8■");
        }
        progressBar.append("&7]");
        lore.add(coloredString.color(progressBar.toString()));
        lore.add(coloredString.color("&5&l|----------------------------"));
        meta.setLore(lore);
        player.getInventory().getItemInMainHand().setItemMeta(meta);
    }

    public static void addWeaponLore(ItemStack weapon, Player player) {
        ItemMeta meta = weapon.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<>();
        for (Enchantment ce : weapon.getEnchantments().keySet()) {
            lore.add(ChatColor.GRAY + getEnchantmentName.getEnchantmentName(ce) + " " + weapon.getEnchantmentLevel(ce));
        }
        meta.setLore(lore);
        player.getInventory().getItemInMainHand().setItemMeta(meta);
    }


    public static ItemStack addRobotLore(ItemStack robot) {
        ItemStack item = robot;
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<String>();

        PersistentDataContainer container = meta.getPersistentDataContainer();

        lore.add("");
        lore.add(coloredString.color("&5&l➥ &5&lOwned by: &f&l" + Bukkit.getOfflinePlayer(UUID.fromString(container.get(RobotManager.owner, PersistentDataType.STRING))).getName()));
        lore.add(coloredString.color("&5&l➥ &5&lLevel: &f&l" + container.get(RobotManager.level, PersistentDataType.INTEGER)));
        lore.add(" ");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static boolean isRebirthEnchantment(Enchantment ce) {
        return (ce.equals(CustomEnchants.DRILL) || ce.equals(CustomEnchants.LASER) || ce.equals(CustomEnchants.LIGHTNING) || ce.equals(CustomEnchants.CUBED));
    }
}
