package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Pets.PetType;
import me.sildev.zoopr.Pets.petManager;
import me.sildev.zoopr.Leaderboard.eco.SellBlocks;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class noEnchantListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE){
            if (petManager.getActivePet(e.getPlayer()) != null && petManager.getActivePet(e.getPlayer()).getType() != PetType.BEACON)
                petManager.getActivePet(e.getPlayer()).usePet();
            SellBlocks.sellBlock(e.getBlock(), e.getPlayer().getInventory().getItemInMainHand());
        }
    }
}
