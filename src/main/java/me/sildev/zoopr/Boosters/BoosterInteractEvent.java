package me.sildev.zoopr.Boosters;

import me.sildev.zoopr.playtime.playtimeManager;
import me.sildev.zoopr.utils.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class BoosterInteractEvent implements Listener {

    String boosterAlreadyActive = Messages.get("alreadyABoosterActive");
    String activatedBooster = Messages.get("activatedBooster");


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (!item.hasItemMeta()) return;
        if (item.getType() != boosterManager.boosterItemType) return;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(boosterManager.multiplier)) return;
        if (!container.has(boosterManager.type)) return;
        if (!container.has(boosterManager.length)) return;

        // It's a booster
        double multiplier = container.get(boosterManager.multiplier, PersistentDataType.DOUBLE);
        String type = container.get(boosterManager.type, PersistentDataType.STRING);
        double length = container.get(boosterManager.length, PersistentDataType.DOUBLE);

        PersistentDataContainer playerContainer = e.getPlayer().getPersistentDataContainer();

        if (type.equalsIgnoreCase("money")) {
            if (playerContainer.has(boosterManager.moneyMultiplier)) {e.getPlayer().sendMessage(boosterAlreadyActive); return; }

            long playerLength = (long)length + playtimeManager.getPlaytime(e.getPlayer());
            playerContainer.set(boosterManager.moneyMultiplier, PersistentDataType.DOUBLE, multiplier);
            playerContainer.set(boosterManager.moneyMultiplierLength, PersistentDataType.LONG, playerLength);
            e.getPlayer().sendMessage(activatedBooster);
            e.getPlayer().getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
            return;
        }

        if (type.equalsIgnoreCase("tokens")) {
            if (playerContainer.has(boosterManager.tokenMultiplier)) { e.getPlayer().sendMessage(boosterAlreadyActive); return; }
            long playerLength = (long) length + playtimeManager.getPlaytime(e.getPlayer());

            playerContainer.set(boosterManager.tokenMultiplier, PersistentDataType.DOUBLE, multiplier);
            playerContainer.set(boosterManager.tokenMultiplierLength, PersistentDataType.LONG, playerLength);
            e.getPlayer().sendMessage(activatedBooster);
            e.getPlayer().getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
        } else {
            e.getPlayer().sendMessage("Something wen't wrong, please dm horo or sil asap!");
        }
    }
}
