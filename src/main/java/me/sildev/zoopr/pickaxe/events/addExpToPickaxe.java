package me.sildev.zoopr.pickaxe.events;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.addLore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class addExpToPickaxe {

    static NamespacedKey levelExp = new NamespacedKey(ZooPR.getPlugin(), "level-exp");
    static NamespacedKey levelExpRequirement = new NamespacedKey(ZooPR.getPlugin(), "level-exp-requirement");
    static NamespacedKey level = new NamespacedKey(ZooPR.getPlugin(), "level");

    public static void addEXPToPickaxe(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (!(item.getType() == Material.DIAMOND_PICKAXE))
            return;

        PersistentDataContainer data = meta.getPersistentDataContainer();

        double value = 0;

        if (data.has(levelExp)) {
            value = data.get(levelExp, PersistentDataType.DOUBLE);
        } else {
            data.set(levelExp, PersistentDataType.DOUBLE, 0d);
            data.set(levelExpRequirement, PersistentDataType.DOUBLE, 500d);
            data.set(level, PersistentDataType.DOUBLE, 0d);
        }

        data.set(levelExp, PersistentDataType.DOUBLE ,value + 1);

        if (data.get(levelExpRequirement, PersistentDataType.DOUBLE) - data.get(levelExp, PersistentDataType.DOUBLE) <= 0)  {
            data.set(levelExp, PersistentDataType.DOUBLE, 0d);
            data.set(level, PersistentDataType.DOUBLE, data.get(level, PersistentDataType.DOUBLE) + 1);
            data.set(levelExpRequirement, PersistentDataType.DOUBLE, data.get(levelExpRequirement, PersistentDataType.DOUBLE) * 1.3);
            player.sendMessage("Your pickaxe has leveled up!");
            addLore.addLore(player.getInventory().getItemInMainHand(), player);
        }
        item.setItemMeta(meta);
    }
}
