package me.sildev.zoopr.mines.ui;

import me.sildev.zoopr.essentials.MineLocationManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import me.sildev.zoopr.utils.guiExtension;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class mainMineMenu implements Listener {

    String notHighEnoughRankYet = Messages.get("notHighEnoughRankYet");

    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 5*9, "Mines");

        ItemStack purpleGlass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemStack magentaGlass = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack endCrystal = new ItemStack(Material.END_CRYSTAL);
        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        // Black stained glass pane
        for (int i = 0; i < gui.getSize() - 1; i++) {
            gui.setItem(i, blackGlass);
        }

        // Magenta Glass
        gui.setItem(0, magentaGlass);
        gui.setItem(8, magentaGlass);
        gui.setItem(36, magentaGlass);
        gui.setItem(44, magentaGlass);

        // End Crystal
        gui.setItem(1, endCrystal);
        gui.setItem(7, endCrystal);
        gui.setItem(9, endCrystal);
        gui.setItem(17, endCrystal);
        gui.setItem(18, endCrystal);
        gui.setItem(26, endCrystal);
        gui.setItem(27, endCrystal);
        gui.setItem(35, endCrystal);
        gui.setItem(37, endCrystal);
        gui.setItem(43, endCrystal);

        // Nether Star
        gui.setItem(4, netherStar);
        gui.setItem(40, netherStar);

        // Mine Blocks
        guiExtension.addRenamedItemWithLore(gui, 19, Material.ORANGE_CONCRETE, "&0&l[&d&ki &5&l&oSTARTER MINE &d&ki&0&L]", new String[] {
                "&7Click to enter the Starter mine!"
        });

        guiExtension.addRenamedItemWithLore(gui, 20, Material.MAGENTA_CONCRETE, "&0&l[&d&ki &5&l&oPRESTIGE 1 &d&ki&0&L]", new String[] {
                "&7Click to enter the &dP1&7 mine!"
        });
        guiExtension.addRenamedItemWithLore(gui, 21, Material.LIGHT_BLUE_CONCRETE, "&0&l[&d&ki &5&l&oPRESTIGE 10 &d&ki&0&L]", new String[] {
                "&7Click to enter the &dP10&7 mine!"
        });
        guiExtension.addRenamedItemWithLore(gui, 22, Material.YELLOW_CONCRETE, "&0&l[&d&ki &5&l&oPRESTIGE 50 &d&ki&0&L]", new String[] {
                "&7Click to enter the &dP50&7 mine!"
        });
        guiExtension.addRenamedItemWithLore(gui, 23, Material.LIME_CONCRETE, "&0&l[&d&ki &5&l&oPRESTIGE 100 &d&ki&0&L]", new String[] {
                "&7Click to enter the &dP100&7 mine!"
        });
        guiExtension.addRenamedItemWithLore(gui, 24, Material.PINK_CONCRETE, "&0&l[&d&ki &5&l&oPRESTIGE 250 &d&ki&0&L]", new String[] {
                "&7Click to enter the &dP250&7 mine!"
        });
        guiExtension.addRenamedItemWithLore(gui, 25, Material.GRAY_CONCRETE, "&0&l[&d&ki &5&l&oPRESTIGE 500 &d&ki&0&L]", new String[] {
                "&7Click to enter the &dP500&7 mine!"
        });

        player.openInventory(gui);
    }

    private int getPrestige(Player player) {
        return player.getPersistentDataContainer().get(rankupManager.prestige, PersistentDataType.INTEGER);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        if (!title.equalsIgnoreCase("Mines")) return;
        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getItem(e.getSlot()) == null) return;

        int prestige = getPrestige(player);
        String message = coloredString.color("&5&l&oZOOPR ✦ &7You are not prestige &d%prestige%&7 yet!");

        if (e.getSlot() == 19) {
            player.teleport(MineLocationManager.getLocation("STARTER"));
        }

        if (e.getSlot() == 20) {
            if (prestige >= 1) player.teleport(MineLocationManager.getLocation("P1"));
            else player.sendMessage(message.replaceAll("%prestige%", "1"));
        }

        if (e.getSlot() == 21) {
            if (prestige >= 10) player.teleport(MineLocationManager.getLocation("P10"));
            else player.sendMessage(message.replaceAll("%prestige%", "10"));
        }

        if (e.getSlot() == 22) {
            if (prestige >= 50) player.teleport(MineLocationManager.getLocation("P50"));
            else player.sendMessage(message.replaceAll("%prestige%", "50"));
        }
        if (e.getSlot() == 23) {
            if (prestige >= 100) player.teleport(MineLocationManager.getLocation("P100"));
            else player.sendMessage(message.replaceAll("%prestige%", "100"));
        }
        if (e.getSlot() == 24) {
            if (prestige >= 250) player.teleport(MineLocationManager.getLocation("P250"));
            else player.sendMessage(message.replaceAll("%prestige%", "250"));
        }
        if (e.getSlot() == 25) {
            if (prestige >= 500) player.teleport(MineLocationManager.getLocation("P500"));
            else player.sendMessage(message.replaceAll("%prestige%", "500"));
        }
    }
}
