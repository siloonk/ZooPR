package me.sildev.zoopr.shops;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class customShopGuiClickEvent implements Listener {

    String notEnoughMoney = Messages.get("notEnoughMoneyToBuyThisItem");

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        String title = e.getView().getTitle();
        String guiName = title.split(" ")[0];

        if (!ZooPR.getShopManager().doesShopExist(guiName)) return;
        e.setCancelled(true);

        ItemStack background = ZooPR.getShopManager().getBackGroundItem(guiName);
        if ((e.getInventory().getItem(e.getSlot()) == null)) return;
        if (e.getInventory().getItem(e.getSlot()).getType().equals(background.getType())) return;

        // It's a sellable item
        int cost = ZooPR.getShopManager().getCost(guiName, e.getInventory().getItem(e.getSlot()).getType());

        String currency = ZooPR.getShopManager().getCurrency(guiName);
        double balance = 0;

        if (currency.equalsIgnoreCase("MONEY"))
            balance = EconomyManager.getMoneyOfUser(player.getUniqueId());
        else if (currency.equalsIgnoreCase("TOKENS"))
            balance = EconomyManager.getTokensOfUser(player.getUniqueId());
        else if (currency.equalsIgnoreCase("BEACONS"))
            balance = EconomyManager.getBeaconsOfUser(player.getUniqueId());
        else if (currency.equalsIgnoreCase("REBIRTH-POINTS"))
            balance = player.getPersistentDataContainer().get(rankupManager.rebirthPoints, PersistentDataType.DOUBLE);
        else if (currency.equalsIgnoreCase("PRESTIGE-POINTS"))
            balance = player.getPersistentDataContainer().get(rankupManager.prestigePoints, PersistentDataType.DOUBLE);

        if (balance >= cost) {
            player.getInventory().addItem(new ItemStack(e.getInventory().getItem(e.getSlot()).getType()));
            removeMoney(player, currency, cost);
        } else {
            player.sendMessage(notEnoughMoney.replaceAll("%currency%", currency));
        }

    }

    void removeMoney(Player player, String currency, double amount) {
        if (currency.equalsIgnoreCase("MONEY"))
            EconomyManager.addMoneyToUser(player, -amount);
        else if (currency.equalsIgnoreCase("TOKENS"))
            EconomyManager.addTokensToUser(player, -amount);
        else if (currency.equalsIgnoreCase("BEACONS"))
            EconomyManager.addBeaconsToUser(player, -amount);
        else if (currency.equalsIgnoreCase("REBIRTH-POINTS"))
            player.getPersistentDataContainer().set(rankupManager.rebirthPoints, PersistentDataType.DOUBLE, player.getPersistentDataContainer().get(rankupManager.rebirthPoints, PersistentDataType.DOUBLE) - amount);
        else if (currency.equalsIgnoreCase("PRESTIGE-POINTS"))
            player.getPersistentDataContainer().set(rankupManager.prestigePoints, PersistentDataType.DOUBLE, player.getPersistentDataContainer().get(rankupManager.prestigePoints, PersistentDataType.DOUBLE) - amount);
    }
}