package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Leaderboard.eco.SellBlocks;
import me.sildev.zoopr.utils.loops;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class LightningListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        // Check if the player is holding a diamond pickaxe
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) return;

        // Get the level of lucky
        int level = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LIGHTNING);
        if (!(level > 0)) return;

        Random rd = new Random();
        float num = rd.nextFloat() * 100;

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("LIGHTNING_CHANCE") / CustomEnchants.LIGHTNING.getMaxLevel() * level;

        if (!(num < chance)) return;
        ItemStack pickaxe = e.getPlayer().getInventory().getItemInMainHand();
        e.getBlock().getWorld().strikeLightningEffect(e.getBlock().getLocation());
        List<Location> blocks = loops.generateSphere(e.getBlock().getLocation(), 9, false);
        for (Location block : blocks) {
            int intNum = rd.nextInt(100);
            if (intNum < 75)
                SellBlocks.sellBlock(block.getBlock(), pickaxe);
            else
                e.getBlock().getWorld().createExplosion(block, 0);
        }
    }
}
