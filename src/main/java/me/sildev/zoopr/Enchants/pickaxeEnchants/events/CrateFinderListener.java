package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.SellBlocks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class CrateFinderListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) {
            return;
        }

        // Check if the player has Cubed;
        if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.CRATE_FINDER)) {
            return;
        }


        int level = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CRATE_FINDER);

        Random rd = new Random();
        float num = rd.nextFloat() * 100;
        double chance = CustomEnchantConfigFiles.getEnchantmentChance("CRATE_FINDER_CHANCE") / CustomEnchants.CRATE_FINDER.getMaxLevel() * level;

        if (!(num < chance)) return;

        if (!SellBlocks.isInRegionWhereCanMine(e.getBlock().getLocation())) {
            e.setCancelled(true);
            return;
        }

        String command = CustomEnchantConfigFiles.getEnchantmentCommand("CRATE_FINDER_COMMAND");
        ZooPR.getPlugin().getServer().dispatchCommand(ZooPR.getPlugin().getServer().getConsoleSender(), command);

    }
}
