package me.sildev.zoopr.Boosters;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.MillisecondsToTime;
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

public class boosterManager {


    // Namespaces
    public static NamespacedKey multiplier = new NamespacedKey(ZooPR.getPlugin(), "booster-multiplier");
    public static NamespacedKey type = new NamespacedKey(ZooPR.getPlugin(), "booster-type");
    public static NamespacedKey length = new NamespacedKey(ZooPR.getPlugin(), "booster-length");

    public static NamespacedKey moneyMultiplier = new NamespacedKey(ZooPR.getPlugin(), "player-money-multiplier");
    public static NamespacedKey tokenMultiplier = new NamespacedKey(ZooPR.getPlugin(), "player-token-multiplier");
    public static NamespacedKey moneyMultiplierLength = new NamespacedKey(ZooPR.getPlugin(), "player-money-multiplier-length");
    public static NamespacedKey tokenMultiplierLength = new NamespacedKey(ZooPR.getPlugin(), "player-tokens-multiplier-length");

    static Material boosterItemType = Material.NETHER_STAR;
    static String boosterName = "&0&l[ &5&l✥ &d&l&n%type%&d&l Booster &5&l✥ &0&l]";


    public static ItemStack generateBooster(double multiplier, double length, String type) {
        if (!(type.equalsIgnoreCase("tokens") || type.equalsIgnoreCase("money"))) return null;

        ItemStack booster = new ItemStack(boosterItemType);

        ItemMeta meta = booster.getItemMeta();
        meta.setDisplayName(coloredString.color(boosterName.replaceAll("%type%", type)));
        List<String> lore = new ArrayList<String>() {{
            add("");
            add(coloredString.color("&5&l➥ &d&l&nMultiplier:&d&l " + multiplier + "x"));
            add(coloredString.color("&5&l➥ &d&l&nDuration:&d&l " + MillisecondsToTime.getTime((long)length)));
        }};
        meta.setLore(lore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(boosterManager.multiplier, PersistentDataType.DOUBLE, multiplier);
        container.set(boosterManager.type, PersistentDataType.STRING, type);
        container.set(boosterManager.length, PersistentDataType.DOUBLE, length);

        booster.setItemMeta(meta);
        booster.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        booster.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return booster;
    }
}
