package me.sildev.zoopr.armor;

import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.utils.guiExtension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class swordCeGUI {

    public static void open(Player player, ItemStack item) {
        Inventory gui = Bukkit.createInventory(null, 5*9, "Weapon Enchants");

        for (int i  = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        gui.setItem(13, item);

        guiExtension.addRenamedItemWithLore(gui, 30, Material.BOOK, "&7Sharpness", new String[] {
                "",
                "&f&lDescription &7&oDeal more damage!",
                "",
                "&7Cost " + EnchantPrices.getEnchantPrice(Enchantment.DAMAGE_ALL, item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) , item.getEnchantmentLevel(Enchantment.DAMAGE_ALL)),
                "&7Level " + item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + " / 7"
        });


        guiExtension.addRenamedItemWithLore(gui, 31, Material.BOOK, "&7BeaconMaster", new String[] {
                "",
                "&f&lDescription &7&oGives you more beacons when killing mobs!",
                "",
                "&7Cost " + EnchantPrices.getEnchantPrice(CustomEnchants.BEACONMASTER, item.getEnchantmentLevel(CustomEnchants.BEACONMASTER) ,item.getEnchantmentLevel(CustomEnchants.BEACONMASTER)),
                "&7Level " + item.getEnchantmentLevel(CustomEnchants.BEACONMASTER) + " / " + CustomEnchants.BEACONMASTER.getMaxLevel()
        });

        guiExtension.addRenamedItemWithLore(gui, 32, Material.BOOK, "&7Unbreaking", new String[] {
                "",
                "&f&lDescription &7&oLet's your armor last longer!",
                "",
                "&7Cost " + EnchantPrices.getEnchantPrice(Enchantment.DURABILITY, item.getEnchantmentLevel(Enchantment.DURABILITY) ,item.getEnchantmentLevel(Enchantment.DURABILITY)),
                "&7Level " + item.getEnchantmentLevel(Enchantment.DURABILITY) + " / 10"
        });

        player.openInventory(gui);
    }

    public static void resetItems(Inventory gui, Player player, ItemStack item) {
        gui.setItem(13, item);

        guiExtension.addRenamedItemWithLore(gui, 30, Material.BOOK, "&7Sharpness", new String[] {
                "",
                "&f&lDescription &7&oDeal more damage!",
                "",
                "&7Cost " + EnchantPrices.getEnchantPrice(Enchantment.DAMAGE_ALL, item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) ,item.getEnchantmentLevel(Enchantment.DAMAGE_ALL)),
                "&7Level " + item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + " / 10"
        });


        guiExtension.addRenamedItemWithLore(gui, 31, Material.BOOK, "&7BeaconMaster", new String[] {
                "",
                "&f&lDescription &7&oGives you more beacons when killing mobs!",
                "",
                "&7Cost " + EnchantPrices.getEnchantPrice(CustomEnchants.BEACONMASTER, item.getEnchantmentLevel(CustomEnchants.BEACONMASTER) ,item.getEnchantmentLevel(CustomEnchants.BEACONMASTER)),
                "&7Level " + item.getEnchantmentLevel(CustomEnchants.BEACONMASTER) + " / " + CustomEnchants.BEACONMASTER.getMaxLevel()
        });

        guiExtension.addRenamedItemWithLore(gui, 32, Material.BOOK, "&7Unbreaking", new String[] {
                "",
                "&f&lDescription &7&oLet's your armor last longer!",
                "",
                "&7Cost " + EnchantPrices.getEnchantPrice(Enchantment.DURABILITY, item.getEnchantmentLevel(Enchantment.DURABILITY) , item.getEnchantmentLevel(Enchantment.DURABILITY)),
                "&7Level " + item.getEnchantmentLevel(Enchantment.DURABILITY) + " / 10"
        });
    }
}
