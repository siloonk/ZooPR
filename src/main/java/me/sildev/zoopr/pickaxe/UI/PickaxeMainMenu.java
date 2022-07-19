package me.sildev.zoopr.pickaxe.UI;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.EnchantPrices;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.formatNumber;
import me.sildev.zoopr.utils.guiExtension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PickaxeMainMenu {

    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 6 * 9, "Enchantments");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        gui.setItem(4, player.getInventory().getItemInMainHand());
        guiExtension.addRenamedItemWithLore(gui, 19, Material.MAGMA_CREAM, "&dTokens", new String[]{"", " &d&l| &7" + formatNumber.coolFormat(EconomyManager.getTokensOfUser(player), 0)});

        guiExtension.addRenamedItemWithLore(gui, 21, Material.BOOK, "&7Explosive",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&oexplode surrounding blocks",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("EXPLOSIVE_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.EXPLOSIVE) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE)

                });

        guiExtension.addRenamedItemWithLore(gui, 22, Material.BOOK, "&7Jackhammer",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&obreak the entire layer",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("JACKHAMMER_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.JACKHAMMER) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER)
                });

        guiExtension.addRenamedItemWithLore(gui, 23, Material.BOOK, "&7Fortune",
                new String[]{"",
                        "&f&lDescription &7&oGive more blocks while mining",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("FORTUNE_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(Enchantment.LOOT_BONUS_BLOCKS) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)
                });

        guiExtension.addRenamedItemWithLore(gui, 24, Material.BOOK, "&7Lucky",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&ogive tokens to the player",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LUCKY_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.LUCKY) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY)
                });

        guiExtension.addRenamedItemWithLore(gui, 25, Material.BOOK, "&7Crate Finder",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&ogive a key to the player",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("CRATE_FINDER_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.CRATE_FINDER) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER)
                });

        guiExtension.addRenamedItemWithLore(gui, 30, Material.BOOK, "&7Drill",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&obreak a ton of blocks!",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("DRILL_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.DRILL) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.DRILL)
                });

        guiExtension.addRenamedItemWithLore(gui, 31, Material.BOOK, "&7Lightning",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&ostrike lightning and break a lot of blocks!",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LIGHTNING_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.LIGHTNING) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING)
                });

        guiExtension.addRenamedItemWithLore(gui, 32, Material.BOOK, "&7Laser",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&obreak blocks in all directions!",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LASER_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.LASER) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LASER)
                });

        player.openInventory(gui);

    }

    public static void resetItems(Inventory gui, Player player) {
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        gui.setItem(4, player.getInventory().getItemInMainHand());
        guiExtension.addRenamedItemWithLore(gui, 19, Material.MAGMA_CREAM, "&dTokens", new String[]{"", " &d&l| &7" + EconomyManager.getTokensOfUser(player)});

        guiExtension.addRenamedItemWithLore(gui, 21, Material.BOOK, "&7Explosive",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&oexplode surrounding blocks",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("EXPLOSIVE_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.EXPLOSIVE) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE)

                });

        guiExtension.addRenamedItemWithLore(gui, 22, Material.BOOK, "&7Jackhammer",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&obreak the entire layer",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("JACKHAMMER_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.JACKHAMMER) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER)
                });

        guiExtension.addRenamedItemWithLore(gui, 23, Material.BOOK, "&7Fortune",
                new String[]{"",
                        "&f&lDescription &7&oGive more blocks while mining",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("FORTUNE_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(Enchantment.LOOT_BONUS_BLOCKS) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)
                });

        guiExtension.addRenamedItemWithLore(gui, 24, Material.BOOK, "&7Lucky",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&ogive tokens to the player",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LUCKY_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.LUCKY) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY)
                });

        guiExtension.addRenamedItemWithLore(gui, 25, Material.BOOK, "&7Crate Finder",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&ogive a key to the player",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("CRATE_FINDER_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.CRATE_FINDER) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER)
                });

        guiExtension.addRenamedItemWithLore(gui, 30, Material.BOOK, "&7Drill",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&obreak a ton of blocks!",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("DRILL_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.DRILL) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.DRILL)
                });

        guiExtension.addRenamedItemWithLore(gui, 31, Material.BOOK, "&7Lightning",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&ostrike lightning and break a lot of blocks!",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LIGHTNING_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.LIGHTNING) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING)
                });

        guiExtension.addRenamedItemWithLore(gui, 32, Material.BOOK, "&7Laser",
                new String[]{"",
                        "&f&lDescription &7&oHas a random chance to",
                        "&7&obreak blocks in all directions!",
                        "",
                        "&7Required pickaxe level " + Math.round(CustomEnchantConfigFiles.getEnchantmentLevelRequired("LASER_REQUIRED")),
                        "",
                        "&7Cost: " + EnchantPrices.getPrice(CustomEnchants.LASER) + " Tokens",
                        "&7Level " + player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LASER)
                });
    }
}
