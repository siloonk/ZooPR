package me.sildev.zoopr.pickaxe;

import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.addLore;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class givePickaxe implements Listener {

    @EventHandler
    public void givePickaxe(PlayerJoinEvent e) {
        if (!e.getPlayer().getInventory().contains(Material.DIAMOND_PICKAXE)) {

            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);

            ItemMeta meta = item.getItemMeta();

            // Store pickaxe levels!
            PersistentDataContainer data = meta.getPersistentDataContainer();
            if (!data.has(new NamespacedKey(ZooPR.getPlugin(), "level-exp"), PersistentDataType.DOUBLE)) {
                data.set(new NamespacedKey(ZooPR.getPlugin(), "level-exp"), PersistentDataType.DOUBLE, 0d);
            }

            if (!data.has(new NamespacedKey(ZooPR.getPlugin(), "level-exp-requirement"), PersistentDataType.DOUBLE)) {
                data.set(new NamespacedKey(ZooPR.getPlugin(), "level-exp-requirement"), PersistentDataType.DOUBLE, 500d);
            }

            if (!data.has(new NamespacedKey(ZooPR.getPlugin(), "level"), PersistentDataType.DOUBLE)) {
                data.set(new NamespacedKey(ZooPR.getPlugin(), "level"), PersistentDataType.DOUBLE, 1d);
            }

            if (!data.has(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE)) {
                data.set(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE, 0d);
            }

            // Add display name and set some attributes
            meta.setDisplayName(coloredString.color("&dZoo Keeper"));
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);

            // Add custom enchants to the item
            item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 25);
            for (Enchantment ce : CustomEnchants.Enchantments) {
                item.addUnsafeEnchantment(ce, ce.getMaxLevel());
            }

            // Give the item to the player
            e.getPlayer().getInventory().setItemInMainHand(item);
            addLore.addLore(item, e.getPlayer());
        }
    }
}
