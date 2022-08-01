package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.pouches.pouchManager;
import me.sildev.zoopr.utils.Messages;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PouchFinderListener implements Listener {

    String receivedPouchMessage = Messages.get("pouchProc");

    @EventHandler
    public void onPouchFinderProcEvent(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) return;
        if (!(e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.POUCH_FINDER) > 0)) return;

        int level = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.POUCH_FINDER);

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("POUCH_FINDER_CHANCE") / CustomEnchants.POUCH_FINDER.getMaxLevel() * level;

        Random rd = new Random();
        float num = rd.nextFloat() * 100;
        if (!(num < chance)) {
            return;
        }

        int tier = rd.nextInt(pouchManager.maxPouchLevel) + 1;
        int type = rd.nextInt(100);
        String pouchType;
        if (type < 50)
            pouchType = "MONEY";
        else
            pouchType = "TOKENS";


        ItemStack pouch = pouchManager.generatePouch(tier, pouchType);
        e.getPlayer().getInventory().addItem(pouch);

        String message = receivedPouchMessage.replaceAll("%pouch%", pouchType.toLowerCase()).replaceAll("%tier%", tier + "");
        e.getPlayer().sendMessage(message);
    }
}
