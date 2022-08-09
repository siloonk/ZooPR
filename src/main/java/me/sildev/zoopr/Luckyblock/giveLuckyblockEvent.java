package me.sildev.zoopr.Luckyblock;

import me.sildev.zoopr.Leaderboard.eco.SellBlocks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class giveLuckyblockEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) return;
        if (SellBlocks.isInRegionWhereCanMine(e.getBlock().getLocation())) {

            Random rd = new Random();

            int chance = rd.nextInt(1000);
            if (chance < 9) {
                ItemStack luckyBlock = LuckyBlockManager.generateLuckyBlock();
                player.getInventory().addItem(luckyBlock);
            }
        }
    }
}
