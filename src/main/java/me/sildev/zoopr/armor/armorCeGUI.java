package me.sildev.zoopr.armor;

import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.utils.guiExtension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class armorCeGUI {

    public static void open(Player player, ItemStack item) {
        Inventory gui = Bukkit.createInventory(null, 5*9, "Armor Enchants");

        for (int i  = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        gui.setItem(13, item);

        resetItems(gui, player, item);

        player.openInventory(gui);
    }

    public static void resetItems(Inventory gui, Player player, ItemStack item) {
        gui.setItem(13, item);

        guiExtension.addRenamedItemWithLore(gui, 30, Material.BOOK, "&8&l[ &5&ki &5&lProtection &5&ki &8&l]", new String[] {
                "",
                "&5&lDescription &7&oTake less damage when you get hit!",
                "",
                "&d&l➥ &5&lCost: &d" + EnchantPrices.getEnchantPrice(Enchantment.PROTECTION_ENVIRONMENTAL, item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) , item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL)),
                "&d&l➥ &5&lLevel: &d" + item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + " / 10"
        });


        guiExtension.addRenamedItemWithLore(gui, 31, Material.BOOK, "&8&l[ &5&ki &5&lThorns &5&ki &8&l]", new String[] {
                "",
                "&5&lDescription &7&oHas a chance to deal damage to the entity that hit you!",
                "",
                "&d&l➥ &5&lCost: &d" + EnchantPrices.getEnchantPrice(Enchantment.THORNS, item.getEnchantmentLevel(Enchantment.THORNS) , item.getEnchantmentLevel(Enchantment.THORNS)),
                "&7Level " + item.getEnchantmentLevel(Enchantment.THORNS) + " / 10"
        });

        guiExtension.addRenamedItemWithLore(gui, 32, Material.BOOK, "&8&l[ &5&ki &5&lUnbreaking &5&ki &8&l]", new String[] {
                "",
                "&5&lDescription &7&oLet's your armor last longer!",
                "",
                "&d&l➥ &5&lCost: &d" + EnchantPrices.getEnchantPrice(Enchantment.DURABILITY, item.getEnchantmentLevel(Enchantment.DURABILITY) , item.getEnchantmentLevel(Enchantment.DURABILITY)),
                "&d&l➥ &5&lLevel: &d" + item.getEnchantmentLevel(Enchantment.DURABILITY) + " / 10"
        });
    }
}
