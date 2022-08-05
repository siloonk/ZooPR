package me.sildev.zoopr.eco;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.sildev.zoopr.Boosters.boosterManager;
import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Pets.PetType;
import me.sildev.zoopr.Pets.petManager;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.playtime.playtimeManager;
import me.sildev.zoopr.pickaxe.events.addExpToPickaxe;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.MillisecondsToTime;
import me.sildev.zoopr.utils.addLore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class SellBlocks {

    static String boosterEndedMessage = Messages.get("boosterEnded");

    static String receivedBeacons = Messages.get("receivedBeacons");

    public static FileConfiguration blockValueConfig;
    static File blockValueFile;


    static ZooPR instance;

    public SellBlocks(ZooPR plugin) {
        instance = plugin;

        createBlockValuesConfig();
    }

    private static void createBlockValuesConfig() {
        blockValueFile = new File(instance.getDataFolder(), "blocks.yml");
        if (!blockValueFile.exists()) {
            blockValueFile.getParentFile().mkdirs();
            instance.saveResource("blocks.yml", false);
        }

        blockValueConfig = new YamlConfiguration();
        try {
            blockValueConfig.load(blockValueFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    static double getValue(String path) {
        if (!blockValueConfig.contains(path)) {
            return -1;
        }
        return blockValueConfig.getDouble(path);

    }

    static List<String> getRegionsToStopMining() {
        return (List<String>) blockValueConfig.getList("mine-regions");
    }


    private static Map<String, ProtectedRegion> getRegionAtPlayer(Location loc) {
        WorldGuardPlugin wg = WorldGuardPlugin.inst();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get((World) loc.getWorld());
        regions.getRegions();
        return regions.getRegions();
    }

    private static boolean isInRegionWhereCanMine(Location loc) {
        Map<String, ProtectedRegion> regions = getRegionAtPlayer(loc);
        for (String r : getRegionsToStopMining()) {
            if (regions.containsKey(r)) {
                return true;
            }
        }
        return false;
    }

    public static void sellBlock(Block block, ItemStack pickaxe) {
        Player player = Bukkit.getPlayer(UUID.fromString(pickaxe.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(ZooPR.getPlugin(), "owner"), PersistentDataType.STRING)));

        if (!isInRegionWhereCanMine(block.getLocation())) {
            return;
        }
        Material blockType = block.getType();
        if (blockType == Material.BEACON) {
            Random rd = new Random();
            int amount = rd.nextInt(4) + 1;
            EconomyManager.addBeaconsToUser(player,amount);
            player.sendMessage(receivedBeacons.replaceAll("%amount%", String.valueOf(amount)));
        }
        double value = getValue(blockType.name());
        if (value == -1) {
            return;
        }

        addExpToPickaxe.addEXPToPickaxe(player);
        player.getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);

        // Token booster
        if (player.getPersistentDataContainer().has(boosterManager.tokenMultiplier)) {
            long timeLeft = player.getPersistentDataContainer().get(boosterManager.tokenMultiplierLength, PersistentDataType.LONG) - playtimeManager.getPlaytime(player);
            System.out.println(MillisecondsToTime.getTime(playtimeManager.getPlaytime(player)));
            System.out.println(timeLeft + " Tokens");

            if (timeLeft <= 0) {
                player.getPersistentDataContainer().remove(boosterManager.tokenMultiplier);
                player.getPersistentDataContainer().remove(boosterManager.tokenMultiplierLength);
                player.sendMessage(boosterEndedMessage.replaceAll("%boostertype%", "Token"));
            }
        }

        // Money booster
        if (player.getPersistentDataContainer().has(boosterManager.moneyMultiplier)) {
            long timeLeft = player.getPersistentDataContainer().get(boosterManager.moneyMultiplierLength, PersistentDataType.LONG) - playtimeManager.getPlaytime(player);
            System.out.println(timeLeft + " Money");
            if (timeLeft <= 0) {
                player.getPersistentDataContainer().remove(boosterManager.moneyMultiplier);
                player.getPersistentDataContainer().remove(boosterManager.moneyMultiplierLength);
                player.sendMessage(boosterEndedMessage.replaceAll("%boostertype%", "Money"));
            }
        }
        double tokenMultiplier = 1;
        tokenMultiplier += (player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.TOKEN_MULTI) * CustomEnchantConfigFiles.getEnchantmentAmount("TOKEN_MULTI_MULTIPLIER"));
        if (petManager.getActivePet(player) != null && petManager.getActivePet(player).getType().equals(PetType.TOKEN))
            tokenMultiplier += petManager.getActivePet(player).getPetMultiplier();
        if (player.getPersistentDataContainer().has(boosterManager.tokenMultiplier)) {
            tokenMultiplier += player.getPersistentDataContainer().get(boosterManager.tokenMultiplier, PersistentDataType.DOUBLE);
        }

        EconomyManager.addTokensToUser(player, 1 * tokenMultiplier);


        double multiplier = ((player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE) * CustomEnchantConfigFiles.getEnchantmentAmount("FORTUNE_MULTIPLIER")) + 1);
        multiplier += ((player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.MERCHANT) * CustomEnchantConfigFiles.getEnchantmentAmount("MERCHANT_MULTIPLIER")));
        if (player.getPersistentDataContainer().has(boosterManager.moneyMultiplier))
            multiplier += player.getPersistentDataContainer().get(boosterManager.moneyMultiplier, PersistentDataType.DOUBLE);

        if (petManager.getActivePet(player) != null && petManager.getActivePet(player).getType().equals(PetType.MONEY))
            multiplier += petManager.getActivePet(player).getPetMultiplier();

        value *= multiplier;
        EconomyManager.addMoneyToUser(player, value);
        ItemMeta meta = pickaxe.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE)) {
            double blocksBroken = container.get(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE);
            container.set(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE, blocksBroken + 1);
            if ((blocksBroken + 1) % 100 == 0) {
                addLore.addLore(pickaxe, player);
            }
            pickaxe.setItemMeta(meta);
        }
    }

    public static void sellBlocknoPickaxe(Block block, Player player) {
        if (!isInRegionWhereCanMine(block.getLocation())) {
            return;
        }
        Material blockType = block.getType();
        if (blockType == Material.BEACON) {
            Random rd = new Random();
            int amount = rd.nextInt(4) + 1;
            EconomyManager.addBeaconsToUser(player,amount);
            player.sendMessage(receivedBeacons.replaceAll("%amount%", String.valueOf(amount)));
        }
        double value = getValue(blockType.name());
        if (value == -1) {
            return;
        }
        addExpToPickaxe.addEXPToPickaxe(player);
        // Token booster
        if (player.getPersistentDataContainer().has(boosterManager.tokenMultiplier)) {
            long timeLeft = player.getPersistentDataContainer().get(boosterManager.tokenMultiplierLength, PersistentDataType.LONG) - playtimeManager.getPlaytime(player);

            if (timeLeft <= 0) {
                player.getPersistentDataContainer().remove(boosterManager.tokenMultiplier);
                player.getPersistentDataContainer().remove(boosterManager.tokenMultiplierLength);
                player.sendMessage(boosterEndedMessage.replaceAll("%boostertype%", "Token"));
            }
        }

        // Money booster
        if (player.getPersistentDataContainer().has(boosterManager.moneyMultiplier)) {
            long timeLeft = player.getPersistentDataContainer().get(boosterManager.moneyMultiplierLength, PersistentDataType.LONG) - playtimeManager.getPlaytime(player);
            if (timeLeft <= 0) {
                player.getPersistentDataContainer().remove(boosterManager.moneyMultiplier);
                player.getPersistentDataContainer().remove(boosterManager.moneyMultiplierLength);
                player.sendMessage(boosterEndedMessage.replaceAll("%boostertype%", "Money"));
            }
        }

        double tokenMultiplier = 1;
        if (player.getPersistentDataContainer().has(boosterManager.tokenMultiplier))
            tokenMultiplier += player.getPersistentDataContainer().get(boosterManager.tokenMultiplier, PersistentDataType.DOUBLE);

        EconomyManager.addTokensToUser(player, 1 * tokenMultiplier);
        double multiplier = ((player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE) * CustomEnchantConfigFiles.getEnchantmentAmount("FORTUNE_MULTIPLIER")) + 1);
        if (player.getPersistentDataContainer().has(boosterManager.moneyMultiplier))
            multiplier += player.getPersistentDataContainer().get(boosterManager.moneyMultiplier, PersistentDataType.DOUBLE);

        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE) > 0) {
            value = value * multiplier;
        }
        EconomyManager.addMoneyToUser(player, value);

        player.getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);
    }


}
