package me.sildev.zoopr.armor;

import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.Messages;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class armorCeGuiClickEvent implements Listener {

    String notEnoughTokensToBuyEnchant = Messages.get("notEnoughBeaconsToBuyEnchant");
    String successfullyUpgradedEnchant = Messages.get("successfullyUpgradedEnchant");
    String breachMaxLevel = Messages.get("breachMaxLevel");
    String dontMeetRequirement = Messages.get("dontMeetPickaxeLevelRequirement");


    NamespacedKey levelkey = new NamespacedKey(ZooPR.getPlugin(), "level");

    void upgradeEnchantment(Enchantment ce, Player player, int level) {
        int currentLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(ce);
        if (!(player.getInventory().getItemInMainHand().getEnchantmentLevel(ce) + level < 10 + 1)) { player.sendMessage(breachMaxLevel); return; }

        double tokens = EconomyManager.getBeaconsOfUser(player);
        double price = EnchantPrices.getEnchantPrice(ce, currentLevel,currentLevel + level);

        if (tokens < price) { player.sendMessage(notEnoughTokensToBuyEnchant); return; }

        ItemStack item = player.getInventory().getItemInMainHand();

        EconomyManager.addBeaconsToUser(player, -price);
        item.removeEnchantment(ce);
        item.addUnsafeEnchantment(ce, currentLevel + level);
        String message = successfullyUpgradedEnchant.replaceAll("%enchantment%", ce.getKey().toString().replaceAll("minecraft:", ""));
        player.sendMessage(message);
        armorCeGUI.resetItems(player.getOpenInventory().getTopInventory(), player, item);

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("Armor Enchants")) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getSlot() == 30) {
            // Protection
            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                upgradeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, player, 1);
            } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                upgradeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, player, 10);
            } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                upgradeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, player, 100);
            }
        }

        else if (e.getSlot() == 31) {
            // Thorns
            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                upgradeEnchantment(Enchantment.THORNS, player, 1);
            } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                upgradeEnchantment(Enchantment.THORNS, player, 10);
            } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                upgradeEnchantment(Enchantment.THORNS, player, 100);
            }
        }
        else if (e.getSlot() == 32) {
            // Unbreaking
            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                upgradeEnchantment(Enchantment.DURABILITY, player, 1);
            } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                upgradeEnchantment(Enchantment.DURABILITY, player, 10);
            } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                upgradeEnchantment(Enchantment.DURABILITY, player, 100);
            }
        }
        e.setCancelled(true);
    }
}
