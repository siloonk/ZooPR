package me.sildev.zoopr.pouches;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.pouches.tasks.openPouchTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class openPouchListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!(e.getAction().isRightClick())) return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != pouchManager.pouchItemType) return;

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (!item.getItemMeta().getPersistentDataContainer().has(pouchManager.min, PersistentDataType.INTEGER)) return;
        if (!item.getItemMeta().getPersistentDataContainer().has(pouchManager.max, PersistentDataType.INTEGER)) return;

        // It is a pouch!
        int min = item.getItemMeta().getPersistentDataContainer().get(pouchManager.min, PersistentDataType.INTEGER);
        int max = item.getItemMeta().getPersistentDataContainer().get(pouchManager.max, PersistentDataType.INTEGER);
        String type = item.getItemMeta().getPersistentDataContainer().get(pouchManager.pouchType, PersistentDataType.STRING);

        int amountToGive = ThreadLocalRandom.current().nextInt(min, max + 1);

        new openPouchTask(e.getPlayer(), amountToGive, type).runTaskTimerAsynchronously(ZooPR.getPlugin(), 0L, 10L);
        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);

        e.setCancelled(true);

    }
}
