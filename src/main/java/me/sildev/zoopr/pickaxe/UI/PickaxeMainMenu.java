package me.sildev.zoopr.pickaxe.UI;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.formatNumber;
import me.sildev.zoopr.utils.guiExtension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PickaxeMainMenu {

    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 6 * 9, "Enchantments");

        resetItems(gui, player);

        player.openInventory(gui);

    }

    public static void resetItems(Inventory gui, Player player) {

        ItemStack whiteGlass = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack magentaGlass = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        ItemStack endCrystal = new ItemStack(Material.END_CRYSTAL);
        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);


        // White glass
        gui.setItem(0, whiteGlass);
        gui.setItem(1, whiteGlass);
        gui.setItem(2, whiteGlass);
        gui.setItem(3, whiteGlass);
        gui.setItem(4, whiteGlass);
        gui.setItem(5, whiteGlass);
        gui.setItem(6, whiteGlass);
        gui.setItem(7, whiteGlass);
        gui.setItem(8, whiteGlass);
        gui.setItem(9, whiteGlass);
        gui.setItem(17, whiteGlass);
        gui.setItem(40, whiteGlass);
        gui.setItem(45, whiteGlass);
        gui.setItem(53, whiteGlass);

        // Black glass
        gui.setItem(10, blackGlass);
        gui.setItem(11, blackGlass);
        gui.setItem(15, blackGlass);
        gui.setItem(16, blackGlass);
        gui.setItem(18, blackGlass);
        gui.setItem(26, blackGlass);
        gui.setItem(36, blackGlass);
        gui.setItem(44, blackGlass);
        gui.setItem(46, blackGlass);
        gui.setItem(52, blackGlass);

        // Magenta Glass
        gui.setItem(4, magentaGlass);
        gui.setItem(12, magentaGlass);
        gui.setItem(14, magentaGlass);
        gui.setItem(19, magentaGlass);
        gui.setItem(20, magentaGlass);
        gui.setItem(24, magentaGlass);
        gui.setItem(25, magentaGlass);
        gui.setItem(27, magentaGlass);
        gui.setItem(35, magentaGlass);
        gui.setItem(37, magentaGlass);
        gui.setItem(43, magentaGlass);
        gui.setItem(47, magentaGlass);
        gui.setItem(48, magentaGlass);
        gui.setItem(50, magentaGlass);
        gui.setItem(51, magentaGlass);

        // End crystal
        gui.setItem(21, endCrystal);
        gui.setItem(23, endCrystal);
        gui.setItem(38, endCrystal);
        gui.setItem(42, endCrystal);
        gui.setItem(49, endCrystal);

        gui.setItem(13, player.getInventory().getItemInMainHand());
        guiExtension.addRenamedItemWithLore(gui, 22, Material.MAGMA_CREAM, "&0&l[&d&ki &5&lTOKENS &d&ki&0&l]", new String[]{"", " &d&l| &7" + formatNumber.coolFormat(EconomyManager.getTokensOfUser(player), 0)});

        int level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE);
        guiExtension.addRenamedItemWithLore(gui, 28, Material.BOOK, "&5&l✬ &nExplosive&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&oexplode surrounding blocks",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("EXPLOSIVE_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.EXPLOSIVE, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.EXPLOSIVE, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.EXPLOSIVE, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE) + 100), 0) + " Tokens",

                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER);
        guiExtension.addRenamedItemWithLore(gui, 29, Material.BOOK, "&5&l✬ &nJackhammer&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&obreak the entire layer",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("JACKHAMMER_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.JACKHAMMER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.JACKHAMMER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.JACKHAMMER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER) + 100), 0) + " Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE);
        guiExtension.addRenamedItemWithLore(gui, 30, Material.BOOK, "&5&l✬ &nFortune&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oGive more blocks while mining",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("FORTUNE_REQUIRED")),
                        "",
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.FORTUNE, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.FORTUNE, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.FORTUNE, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE) + 100), 0) + " Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY);
        guiExtension.addRenamedItemWithLore(gui, 32, Material.BOOK, "&5&l✬ &nLucky&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&ogive tokens to the player",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LUCKY_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LUCKY, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LUCKY, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.LUCKY, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY) + 100), 0) + " Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER);
        guiExtension.addRenamedItemWithLore(gui, 33, Material.BOOK, "&5&l✬ &nCrate Finder&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&ogive a key to the player",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("CRATE_FINDER_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.CRATE_FINDER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.CRATE_FINDER, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.CRATE_FINDER, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER) + 100), 0) + " Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.POUCH_FINDER);
        guiExtension.addRenamedItemWithLore(gui, 34, Material.BOOK, "&5&l✬ &nPouch Finder&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.POUCH_FINDER) + "&7&l)",
                new String[]{"",
                        "&d&l&oDescription &5&oHas a random chance to",
                        "&5&ogive you a pouch!",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("POUCH_FINDER_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.POUCH_FINDER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.POUCH_FINDER)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.POUCH_FINDER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.POUCH_FINDER) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.POUCH_FINDER, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.POUCH_FINDER) + 100), 0) + " Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.TOKEN_MULTI);
        guiExtension.addRenamedItemWithLore(gui, 39, Material.BOOK, "&5&l✬ &nToken Multi&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.TOKEN_MULTI) + "&7&l)",
                new String[]{"",
                        "&d&lDescription &5&oIncreases your token multiplier!",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("TOKEN_MULTI_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.TOKEN_MULTI, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.TOKEN_MULTI)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.TOKEN_MULTI, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.TOKEN_MULTI) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.TOKEN_MULTI, level,player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.TOKEN_MULTI) + 100), 0) + " Tokens",
                });

        level = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.MERCHANT);
        guiExtension.addRenamedItemWithLore(gui, 41, Material.BOOK, "&5&l✬ &nMerchant&r &7&l(&d&oLevel: " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.MERCHANT) + "&7&l)",
                new String[]{"",
                        "&d&lDescription &5&oIncreases your money multiplier!",
                        "",
                        //"&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("MERCHANT_REQUIRED")),
                        "&5&l➥ &f&lCost 1x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.MERCHANT, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.MERCHANT)), 0) + " Tokens",
                        "&5&l➥ &f&lCost 10x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.MERCHANT, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.MERCHANT) + 10), 0) + " Tokens",
                        "&5&l➥ &f&lCost 100x: " + formatNumber.coolFormat(EnchantPrices.getEnchantPrice(CustomEnchants.MERCHANT, level, player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.MERCHANT) + 100), 0) + " Tokens",
                });

        guiExtension.addRenamedItemWithLore(gui, 31, Material.NETHER_STAR, "&5&l۞ &5&lRebirth Enchantments",
                new String[]{
                        "&d&l⬤ &7&oClick to go to Rebirth Enchantments"
                });
    }
}
