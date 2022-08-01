package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.eco.SellBlocks;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class noEnchantListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE)
            SellBlocks.sellBlock(e.getBlock(), e.getPlayer().getInventory().getItemInMainHand());
    }
}
