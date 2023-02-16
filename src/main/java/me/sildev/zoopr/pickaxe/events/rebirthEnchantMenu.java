package me.sildev.zoopr.pickaxe.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.pickaxe.UI.PickaxeMainMenu;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class rebirthEnchantMenu implements Listener {


    NamespacedKey levelkey = new NamespacedKey(ZooPR.getPlugin(), "level");

    String notEnoughTokensToBuyEnchant = Messages.get("notEnoughTokensToBuyEnchant");
    String successfullyUpgradedEnchant = Messages.get("successfullyUpgradedEnchant");
    String breachMaxLevel = Messages.get("breachMaxLevel");
    String dontMeetRequirement = Messages.get("dontMeetPickaxeLevelRequirement");

    void upgradeEnchantment(Enchantment ce, Player player, int levels) {
        int currentLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(ce);
        if (!(player.getInventory().getItemInMainHand().getEnchantmentLevel(ce) + levels < ce.getMaxLevel() + 1)) {
            player.sendMessage(breachMaxLevel);
            return;
        }

        double tokens = player.getPersistentDataContainer().get(rankupManager.rebirthPoints, PersistentDataType.DOUBLE);
        double price = EnchantPrices.getEnchantPrice(ce, currentLevel,currentLevel + levels);

        if (tokens < price) {
            player.sendMessage(notEnoughTokensToBuyEnchant);
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        double pickaxeLevel = item.getItemMeta().getPersistentDataContainer().get(levelkey, PersistentDataType.DOUBLE);
        double requiredPickaxeLevel = CustomEnchantConfigFiles.getEnchantmentLevelRequired(ce.getKey().toString().toUpperCase().replaceAll("MINECRAFT:", "") + "_REQUIRED");
        if (!(pickaxeLevel >= requiredPickaxeLevel)) {
            player.sendMessage(dontMeetRequirement);
            return;
        }

        player.getPersistentDataContainer().set(rankupManager.rebirthPoints, PersistentDataType.DOUBLE, tokens - price);
        item.removeEnchantment(ce);
        item.addUnsafeEnchantment(ce, currentLevel + levels);
        String message = successfullyUpgradedEnchant.replaceAll("%enchantment%", ce.getName());
        player.sendMessage(message);
        PickaxeMainMenu.resetItems(player.getOpenInventory().getTopInventory(), player);
    }


    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Rebirth Enchantments")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getSlot() == 20) {
                // Cubed Shit
                upgradeEnchantment(CustomEnchants.CUBED, player, 1);
            } else if (e.getSlot() == 21) {
                // Laser Shit
                upgradeEnchantment(CustomEnchants.LASER, player, 1);
            } else if (e.getSlot() == 23) {
                // Laser Shit
                upgradeEnchantment(CustomEnchants.LIGHTNING, player, 1);
            } else if (e.getSlot() == 24) {
                // Laser Shit
                upgradeEnchantment(CustomEnchants.DRILL, player, 1);
            } else if (e.getSlot() == 31) {
                PickaxeMainMenu.open(player);
            }
        }
    }
}
