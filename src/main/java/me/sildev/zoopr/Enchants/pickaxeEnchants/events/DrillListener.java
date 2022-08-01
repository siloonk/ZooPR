package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.eco.SellBlocks;
import me.sildev.zoopr.utils.loops;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class DrillListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        int level = item.getEnchantmentLevel(CustomEnchants.DRILL);

        if (!(level > 0))
            return;

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("DRILL_CHANCE") / CustomEnchants.DRILL.getMaxLevel() * level;


        Random rd = new Random();
        float num = rd.nextFloat() * 100;
        if (!(num < chance))
            return;

        List<Location> blocks = loops.generateSphere(e.getBlock().getLocation(), 5, false);
        for (Location block : blocks) {
            SellBlocks.sellBlock(block.getBlock(), item);
        }
    }
}
