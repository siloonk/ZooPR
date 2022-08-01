package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.pickaxeEnchants.Tasks.LaserTask;
import me.sildev.zoopr.ZooPR;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LaserListener implements Listener {

    final int SIZE = 20;

    @EventHandler
    public void onLaserProc(BlockBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        int level = item.getEnchantmentLevel(CustomEnchants.LASER);

        if (!(level > 0))
            return;

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("LASER_CHANCE") / CustomEnchants.LASER.getMaxLevel() * level;


        Random rd = new Random();
        float num = rd.nextFloat() * 100;
        if (!(num < chance))
            return;

        /*List<Location> blocks = loops.getMultipleDirection(e.getBlock().getLocation(), SIZE, SIZE);
        for (Location block : blocks) {
            if (block.getBlockX() == e.getBlock().getLocation().getBlockX() || block.getBlockZ() == e.getBlock().getLocation().getBlockZ())
                SellBlocks.sellBlock(block.getBlock(), player);
        }*/

        System.out.println("LASER");
        new LaserTask(player, SIZE, e.getBlock().getLocation()).runTaskTimer(ZooPR.getPlugin(), 0L, 5L);
        //Bukkit.getScheduler().scheduleSyncRepeatingTask(ZooPR.getPlugin(), new LaserTask(player, SIZE, e.getBlock().getLocation()), 0L, 20L);
    }
}
