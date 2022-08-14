package me.sildev.zoopr.armor;

import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.addLore;
import me.sildev.zoopr.utils.isArmor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class swordCeGuiClickEvent implements Listener {

    String notEnoughTokensToBuyEnchant = Messages.get("notEnoughBeaconsToBuyEnchant");
    String successfullyUpgradedEnchant = Messages.get("successfullyUpgradedEnchant");
    String breachMaxLevel = Messages.get("breachMaxLevel");

    void upgradeEnchantment(Enchantment ce, Player player, int level) {
        if (!(isArmor.isSword(player.getInventory().getItemInMainHand().getType()) || isArmor.isAxe(player.getInventory().getItemInMainHand().getType())))
            return;
        int currentLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(ce);
        if (!(player.getInventory().getItemInMainHand().getEnchantmentLevel(ce) + level < 10 + 1)) { player.sendMessage(breachMaxLevel); return; }

        double tokens = EconomyManager.getBeaconsOfUser(player);
        double price = EnchantPrices.getEnchantPrice(ce, currentLevel + level);

        if (tokens < price) { player.sendMessage(notEnoughTokensToBuyEnchant); return; }

        ItemStack item = player.getInventory().getItemInMainHand();

        EconomyManager.addBeaconsToUser(player, -price);
        item.removeEnchantment(ce);
        item.addUnsafeEnchantment(ce, currentLevel + level);
        String message = successfullyUpgradedEnchant.replaceAll("%enchantment%", ce.getKey().toString().replaceAll("minecraft:", ""));
        addLore.addWeaponLore(player.getInventory().getItemInMainHand(), player);
        player.sendMessage(message);
        swordCeGUI.resetItems(player.getOpenInventory().getTopInventory(), player, item);
    }


    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("Weapon Enchants")) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getSlot() == 30)
            upgradeEnchantment(Enchantment.DAMAGE_ALL, player, 1);

        else if (e.getSlot() == 31)
            upgradeEnchantment(CustomEnchants.BEACONMASTER, player, 1);

        else if (e.getSlot() == 32)
            upgradeEnchantment(Enchantment.DURABILITY, player, 1);

        e.setCancelled(true);
    }


}
