package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.eco.SellBlocks;
import me.sildev.zoopr.utils.loops;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Random;

public class CubedListener implements Listener {

    int SIZE = 3;

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) {
            return;
        }

        // Check if the player has explosive;
        if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.CUBED)) {
            return;
        }


        int level = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.CUBED);

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("CUBED_CHANCE") / CustomEnchants.CUBED.getMaxLevel() * level;


        Random rd = new Random();
        float num = rd.nextFloat() * 100;
        if (!(num < chance)) {
            return;
        }
        if (!SellBlocks.isInRegionWhereCanMine(e.getBlock().getLocation())) {
            e.setCancelled(true);
            return;
        }

        List<Location> blocks = loops.generateCube(e.getBlock().getLocation(), SIZE, SIZE, false);
        for (Location block : blocks) {
            if (block.getBlock().getType() != Material.AIR)
                SellBlocks.sellBlock(block.getBlock(), player.getInventory().getItemInMainHand(), player);
        }
    }
}
