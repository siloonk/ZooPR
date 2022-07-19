package me.sildev.zoopr.eco;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.pickaxe.events.addExpToPickaxe;
import me.sildev.zoopr.utils.addLore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;

public class SellBlocks {

    static FileConfiguration blockValueConfig;
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

    public static void sellBlock(Block block, Player player) {
        Material blockType = block.getType();
        double value = getValue(blockType.name());
        if (value == -1) {
            return;
        }
        addExpToPickaxe.addEXPToPickaxe(player);
        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) > 0) {
            value = value * ((player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchants.FORTUNE) * CustomEnchantConfigFiles.getEnchantmentAmount("FORTUNE_MULTIPLIER")) + 1);
        }
        EconomyManager.addMoneyToUser(player, value);
        EconomyManager.addTokensToUser(player, 1d);
        player.getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE)) {
            double blocksBroken = container.get(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE);
            container.set(new NamespacedKey(ZooPR.getPlugin(), "blocks-broken"), PersistentDataType.DOUBLE, blocksBroken + 1);
            if ((blocksBroken + 1) % 100 == 0) {
                addLore.addLore(item, player);
            }
            item.setItemMeta(meta);
        }
    }


}
