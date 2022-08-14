package me.sildev.zoopr.essentials.ui;

import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
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
        Inventory gui = Bukkit.createInventory(null, 6*9, "Mines");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        int offset = 10;
        for (int i = 0; i < 26; i++) {
            if ((offset + i + 1) % 9 == 0)
                offset += 2;
            ItemStack item = new ItemStack(Material.STONE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(coloredString.color("&d&l&ki &5&lMine " + rankupManager.ranks[i] + " &d&l&ki"));
            int rank = i;
            int playerMine = player.getPersistentDataContainer().get(rankupManager.rankup, PersistentDataType.INTEGER);
            ArrayList<String> lore = new ArrayList<String>() {{
                add(coloredString.color("&7&oClick To teleport to mine " + rankupManager.ranks[rank]));
                add(coloredString.color(""));
                if (playerMine >= rank){
                    add(coloredString.color("&a&l&ki &f&lUNLOCKED &a&l&ki"));
                } else {
                    add(coloredString.color("&c&l&ki &4&lLOCKED &c&l&ki"));
                }
            }};
            meta.setLore(lore);
            item.setItemMeta(meta);

            gui.setItem(offset + i, item);

            player.openInventory(gui);
        }

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        if (!title.equalsIgnoreCase("Mines")) return;
        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getItem(e.getSlot()) == null) return;


        if (e.getInventory().getItem(e.getSlot()).getType() == Material.BLACK_STAINED_GLASS_PANE || e.getInventory().getItem(e.getSlot()).getType() == Material.AIR) return;

        String mineName = ChatColor.stripColor(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()).split(" ")[2];
        List<String> mineNames = Arrays.asList(rankupManager.ranks);
        int index = mineNames.indexOf(mineName);

        int playerMine = player.getPersistentDataContainer().get(rankupManager.rankup, PersistentDataType.INTEGER);

        if (playerMine >= index)
            subMineMenu.open(player, index);
        else
            player.sendMessage(notHighEnoughRankYet.replaceAll("%rank%", rankupManager.ranks[index]));

    }
}
