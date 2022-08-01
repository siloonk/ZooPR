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
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ExplosiveListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) {
            return;
        }

        // Check if the player has explosive;
        if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.EXPLOSIVE)) {
            return;
        }

        int level = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.EXPLOSIVE);

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("EXPLOSIVE_CHANCE") / CustomEnchants.EXPLOSIVE.getMaxLevel() * level;


        Random rd = new Random();
        float num = rd.nextFloat() * 100;
        if (!(num < chance)) {
            return;
        }
        // Create explosion;
        List<Location> blocks = loops.generateCube(e.getBlock().getLocation(), 2, 2, false);
        ItemStack pickaxe = player.getInventory().getItemInMainHand();

        for (Location block : blocks) {
            int intNum = rd.nextInt(100);
            if (intNum < 75)
                SellBlocks.sellBlock(block.getBlock(), pickaxe);
        }
        e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), 0);
    }
}
