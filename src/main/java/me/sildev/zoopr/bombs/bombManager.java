package me.sildev.zoopr.bombs;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;


public class bombManager {

    public static NamespacedKey tier = new NamespacedKey(ZooPR.getPlugin(), "bomb-tier");
    public static NamespacedKey type = new NamespacedKey(ZooPR.getPlugin(), "bomb-type");

    public static ItemStack getBomb(String Type, int bombTier) {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(type, PersistentDataType.STRING, Type.toUpperCase());
        container.set(tier, PersistentDataType.INTEGER, bombTier);

        meta.setDisplayName(coloredString.color("&0&l[ &5☢ &5&l&n" + Type.toUpperCase() + "&5&l Tier: " + bombTier + " &5☢ &0&l]"));

        List<String> lore = new ArrayList<String>() {{
            add(coloredString.color("&d&o➥ Right click to throw!"));
        }};

        meta.setLore(lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return item;
    }
}
