package me.sildev.zoopr.Luckyblock;

import me.sildev.zoopr.ZooPR;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class luckyBlockInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.hasItemMeta()) return;

        if (item.getType() != LuckyBlockManager.luckyBlockitemType) return;

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (!container.has(LuckyBlockManager.luckyblockItemConfirmation)) return;

        HashMap<String, List<String>> loot = LuckyBlockManager.getLoot();
        Random rd = new Random();
        int chance = rd.nextInt(100);
        List<String> chosenLoot;
        if (chance < 2) {
            chosenLoot = loot.get("rare");
        } else {
            chosenLoot = loot.get("common");
        }

        int index = rd.nextInt(chosenLoot.size());
        ZooPR.getPlugin().getServer().dispatchCommand(ZooPR.getPlugin().getServer().getConsoleSender(), chosenLoot.get(index));
    }
}
