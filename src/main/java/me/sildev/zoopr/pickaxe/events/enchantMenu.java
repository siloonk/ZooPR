package me.sildev.zoopr.pickaxe.events;


import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.EnchantPrices;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.pickaxe.UI.PickaxeMainMenu;
import me.sildev.zoopr.utils.addLore;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class enchantMenu implements Listener {

    String notEnoughTokensToBuyEnchant = Messages.get("notEnoughTokensToBuyEnchant");
    String successfullyUpgradedEnchant = Messages.get("successfullyUpgradedEnchant");
    String breachMaxLevel = Messages.get("breachMaxLevel");

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE)
                PickaxeMainMenu.open(e.getPlayer());
        }
    }

    void upgradeEnchantment(Enchantment ce, Player player, int levels) {
        for (int i = 0; i < levels; i++) {
            double tokens = EconomyManager.getTokensOfUser(player);
            if (!(player.getInventory().getItemInMainHand().getEnchantmentLevel(ce) < ce.getMaxLevel())) { player.sendMessage(breachMaxLevel); return; }
            if (tokens < EnchantPrices.getPrice(ce)) { player.sendMessage(notEnoughTokensToBuyEnchant); return; }
            EconomyManager.addTokensToUser(player, -(double) EnchantPrices.getPrice(ce));
            ItemStack item = player.getInventory().getItemInMainHand();
            int level = item.getEnchantmentLevel(ce);
            item.removeEnchantment(ce);
            item.addUnsafeEnchantment(ce, level + 1);
            String message = successfullyUpgradedEnchant.replaceAll("%enchantment%", ce.getName());
            player.sendMessage(message);
            PickaxeMainMenu.resetItems(player.getOpenInventory().getTopInventory(), player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Enchantments")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == 21) {
                if (e.getAction() == InventoryAction.PICKUP_ALL) {
                    upgradeEnchantment(CustomEnchants.EXPLOSIVE, player, 1);
                } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                    upgradeEnchantment(CustomEnchants.EXPLOSIVE, player, 10);
                } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                    upgradeEnchantment(CustomEnchants.EXPLOSIVE, player, 100);
                }
            } else if (e.getSlot() == 22) {
                // Jackhammer
                if (e.getAction() == InventoryAction.PICKUP_ALL) {
                    upgradeEnchantment(CustomEnchants.JACKHAMMER, player, 1);
                } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                    upgradeEnchantment(CustomEnchants.JACKHAMMER, player, 10);
                } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                    upgradeEnchantment(CustomEnchants.JACKHAMMER, player, 100);
                }


            } else if (e.getSlot() == 23) {
                // Fortune shit

                if (e.getAction() == InventoryAction.PICKUP_ALL) {
                    upgradeEnchantment(CustomEnchants.FORTUNE, player, 1);
                } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                    upgradeEnchantment(CustomEnchants.FORTUNE, player, 10);
                } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                    upgradeEnchantment(CustomEnchants.FORTUNE, player, 100);
                }


            } else if (e.getSlot() == 24) {
                // Lucky Shit
                if (e.getAction() == InventoryAction.PICKUP_ALL) {
                    upgradeEnchantment(CustomEnchants.LUCKY, player, 1);
                } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                    upgradeEnchantment(CustomEnchants.LUCKY, player, 10);
                } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                    upgradeEnchantment(CustomEnchants.LUCKY, player, 100);
                }

            } else if (e.getSlot() == 25) {
                // Crate finder shit
                if (e.getAction() == InventoryAction.PICKUP_ALL) {
                    upgradeEnchantment(CustomEnchants.CRATE_FINDER, player, 1);
                } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                    upgradeEnchantment(CustomEnchants.CRATE_FINDER, player, 10);
                } else if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                    upgradeEnchantment(CustomEnchants.CRATE_FINDER, player, 100);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) return;

        addLore.addLore(e.getPlayer().getInventory().getItemInMainHand(), (Player) e.getPlayer());
    }
}
