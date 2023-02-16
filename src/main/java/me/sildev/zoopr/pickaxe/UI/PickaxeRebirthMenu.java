package me.sildev.zoopr.pickaxe.UI;

import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.formatNumber;
import me.sildev.zoopr.utils.guiExtension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PickaxeRebirthMenu {

    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 5 * 9, "Rebirth Enchantments");

        refreshItems(gui, player);

        player.openInventory(gui);
    }


    public static void refreshItems(Inventory gui, Player player) {

        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack magentaGlass = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        ItemStack whiteGlass = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack endCrystal = new ItemStack(Material.END_CRYSTAL);

        // black glass
        gui.setItem(0, blackGlass);
        gui.setItem(1, blackGlass);
        gui.setItem(7, blackGlass);
        gui.setItem(8, blackGlass);
        gui.setItem(9, blackGlass);
        gui.setItem(17, blackGlass);
        gui.setItem(27, blackGlass);
        gui.setItem(35, blackGlass);
        gui.setItem(36, blackGlass);
        gui.setItem(37, blackGlass);
        gui.setItem(43, blackGlass);
        gui.setItem(44, blackGlass);


        // Magenta Glass
        gui.setItem(2, magentaGlass);
        gui.setItem(3, magentaGlass);
        gui.setItem(5, magentaGlass);
        gui.setItem(6, magentaGlass);
        gui.setItem(10, magentaGlass);
        gui.setItem(16, magentaGlass);
        gui.setItem(19, magentaGlass);
        gui.setItem(25, magentaGlass);
        gui.setItem(28, magentaGlass);
        gui.setItem(34, magentaGlass);
        gui.setItem(38, magentaGlass);
        gui.setItem(39, magentaGlass);
        gui.setItem(41, magentaGlass);
        gui.setItem(42, magentaGlass);

        // White Glass
        gui.setItem(11, whiteGlass);
        gui.setItem(12, whiteGlass);
        gui.setItem(14, whiteGlass);
        gui.setItem(15, whiteGlass);
        gui.setItem(29, whiteGlass);
        gui.setItem(30, whiteGlass);
        gui.setItem(32, whiteGlass);
        gui.setItem(33, whiteGlass);

        // end crystals
        gui.setItem(4, endCrystal);
        gui.setItem(18, endCrystal);
        gui.setItem(26, endCrystal);
        gui.setItem(40, endCrystal);

        guiExtension.addRenamedItemWithLore(gui, 31, Material.NETHER_STAR, "&5&l۞ &5&lNormal Enchantments",
                new String[]{
                        "&d&l⬤ &7&oClick to go to Normal Enchantments"
                }
        );


        gui.setItem(13, player.getInventory().getItemInMainHand());
        double rebirthTokens = player.getPersistentDataContainer().get(rankupManager.rebirthPoints, PersistentDataType.DOUBLE);
        guiExtension.addRenamedItemWithLore(gui, 22, Material.MAGMA_CREAM, "&dRebirth Tokens", new String[]{"", " &d&l| &7" + formatNumber.coolFormat(rebirthTokens, 0)});

        int level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CUBED);
        guiExtension.addRenamedItemWithLore(gui, 20, Material.WRITABLE_BOOK, "&5&l✬ &nCubed&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CUBED) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&odestroy a 6x6x6 cube!",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("CUBED_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.CUBED, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CUBED)), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.CUBED, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CUBED) + 10), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.CUBED, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CUBED) + 100), 0) + " Rebirth Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LASER);
        guiExtension.addRenamedItemWithLore(gui, 21, Material.WRITABLE_BOOK, "&5&l✬ &nLaser&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LASER) + "&7&l)",
                new String[]{"",
                        "&d&lDescription &5&oHas a random chance to",
                        "&5&obreak blocks in all directions!",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LASER_REQUIRED")),
                        "",
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LASER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LASER)), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LASER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LASER) + 10), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LASER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LASER) + 100), 0) + " Rebirth Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING);
        guiExtension.addRenamedItemWithLore(gui, 23, Material.WRITABLE_BOOK, "&5&l✬ &nLightning&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&ostrike lightning and break a lot of blocks!",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LIGHTNING_REQUIRED")),
                        "",
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LIGHTNING, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING)), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LIGHTNING, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING) + 10), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LIGHTNING, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING) + 100), 0) + " Rebirth Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.DRILL);
        guiExtension.addRenamedItemWithLore(gui, 24, Material.WRITABLE_BOOK, "&5&l✬ &nDrill&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.DRILL) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&obreak a ton of blocks!",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("DRILL_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.DRILL,level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.DRILL)), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.DRILL, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.DRILL) + 10), 0) + " Rebirth Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.DRILL, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.DRILL) + 100), 0) + " Rebirth Tokens",
                });
    }
}
