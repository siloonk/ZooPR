package me.sildev.zoopr.Luckyblock;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


import java.util.ArrayList;
import java.util.List;

public class LuckyBlockManager {

    public static Material luckyBlockitemType = Material.SPONGE;
    public static String luckyBlockItemName = coloredString.color("&f&ki&d LuckyEgg &7(Right click) &f&ki");
    public static List<String> luckyBlockLore = new ArrayList<String>() {{
        add(coloredString.color("&7Right click to get a reward!"));
    }};

    public static NamespacedKey luckyblockItemConfirmation = new NamespacedKey(ZooPR.getPlugin(), "luckyblock-confirm");

    public static ItemStack generateLucky() {
        ItemStack luckyBlock = new ItemStack(luckyBlockitemType);
        ItemMeta meta = luckyBlock.getItemMeta();
        meta.setDisplayName(luckyBlockItemName);
        meta.setLore(luckyBlockLore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(luckyblockItemConfirmation, PersistentDataType.INTEGER, 1);
        luckyBlock.setItemMeta(meta);

        return luckyBlock;
    }
}
