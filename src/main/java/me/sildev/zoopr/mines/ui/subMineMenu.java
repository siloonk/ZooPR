package me.sildev.zoopr.mines.ui;

import me.sildev.zoopr.essentials.MineLocationManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class subMineMenu implements Listener {

    public static void open(Player player, int mine) {
        String mineName = rankupManager.ranks[mine];
        Inventory gui = Bukkit.createInventory(null, 3 * 9, "Mine " + mineName);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        int currMine = 1;
        int offset = 11;
        for (int i = currMine; i < 4; i++) {
            ItemStack item = new ItemStack(Material.STONE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(coloredString.color("&d&l&ki &5&lMine " + rankupManager.ranks[mine] + i + " &d&l&ki"));
            int currMineForLore = i;
            List<String> lore = new ArrayList<String>() {{
                add(coloredString.color("&7&oClick To teleport to mine " + rankupManager.ranks[mine] + currMineForLore));
            }};
            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.setItem(offset + i, item);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        if (!title.contains("Mine ")) return;
        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getItem(e.getSlot()) == null) return;

        if (e.getInventory().getItem(e.getSlot()).getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        String itemName = ChatColor.stripColor(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
        String itemNumber = itemName.split(title.split(" ")[1])[1].replaceAll(" i", "");

        String mineName = title.split(" ")[1] + itemNumber;
        Location loc = MineLocationManager.getLocation(mineName);
        if (loc != null) {
            player.teleport(loc);
        } else {
            player.sendMessage(coloredString.color("&dZooPR &8| &7This mine location has not yet been defined please tell horo or sil about this ASAP!"));
        }
    }
}
