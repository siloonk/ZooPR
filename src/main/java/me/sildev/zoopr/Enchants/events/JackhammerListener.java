package me.sildev.zoopr.Enchants.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.Tasks.JackhammerTask;
import me.sildev.zoopr.ZooPR;
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

public class JackhammerListener implements Listener {

    public int SIZE = 30;

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) {
            return;
        }

        // Check if the player has explosive;
        if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.JACKHAMMER)) {
            return;
        }

        int level = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.JACKHAMMER);

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("JACKHAMMER_CHANCE") / CustomEnchants.JACKHAMMER.getMaxLevel() * level;


        Random rd = new Random();
        float num = rd.nextFloat() * 100;
        if (!(num < chance)) {
            return;
        }

        new JackhammerTask(player, e.getBlock().getLocation(), SIZE, 1).runTaskTimer(ZooPR.getPlugin(), 0L, 0L);
    }
}
