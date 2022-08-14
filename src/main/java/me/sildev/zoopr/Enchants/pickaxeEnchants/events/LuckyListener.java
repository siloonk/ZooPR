package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Boosters.boosterManager;
import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Pets.PetType;
import me.sildev.zoopr.Pets.petManager;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.eco.SellBlocks;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class LuckyListener implements Listener {

    String luckyProc = Messages.get("luckyProc");

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        // Check if the player is holding a diamond pickaxe
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) return;

        // Get the level of lucky
        int level = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.LUCKY);
        if (!(level > 0)) return;

        Random rd = new Random();
        float num = rd.nextFloat() * 100;

        double chance = CustomEnchantConfigFiles.getEnchantmentChance("LUCKY_CHANCE") / CustomEnchants.LUCKY.getMaxLevel() * level;

        if (!(num < chance)) return;

        if (!SellBlocks.isInRegionWhereCanMine(e.getBlock().getLocation())) {
            e.setCancelled(true);
            return;
        }
        double amountMin = CustomEnchantConfigFiles.getEnchantmentAmount("LUCKY_AMOUNT_MIN");
        double amountMax = CustomEnchantConfigFiles.getEnchantmentAmount("LUCKY_AMOUNT_MAX");

        double amount = rd.nextFloat() * (amountMax - amountMin);
        double tokenMultiplier = (e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.TOKEN_MULTI)) * CustomEnchantConfigFiles.getEnchantmentAmount("TOKEN_MUTLI_MULTIPLIER");
        if (petManager.getActivePet(e.getPlayer()) != null && petManager.getActivePet(e.getPlayer()).getType().equals(PetType.TOKEN))
            tokenMultiplier += petManager.getActivePet(e.getPlayer()).getPetMultiplier();
        if (e.getPlayer().getPersistentDataContainer().has(boosterManager.tokenMultiplier))
            tokenMultiplier += e.getPlayer().getPersistentDataContainer().get(boosterManager.tokenMultiplier, PersistentDataType.DOUBLE);

        
        amount *= tokenMultiplier;
        EconomyManager.addTokensToUser(e.getPlayer(), amount);
        String message = luckyProc.replaceAll("%amount%", formatNumber.coolFormat(amount, 0));
        e.getPlayer().sendMessage(message);
    }
}
