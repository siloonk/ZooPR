package me.sildev.zoopr.Enchants.pickaxeEnchants.events;

import me.sildev.zoopr.Pets.PetType;
import me.sildev.zoopr.Pets.petManager;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.SellBlocks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;

public class noEnchantListener implements Listener {

    public static NamespacedKey blocksBroken = new NamespacedKey(ZooPR.getPlugin(), "blocks-broken-player");

    File blocksBrokenFile;
    public static FileConfiguration blocksBrokenConfig;

    public noEnchantListener() {
        createBlocksBrokenConfig();
    }

    public void createBlocksBrokenConfig() {
        blocksBrokenFile = new File(ZooPR.getPlugin().getDataFolder(), "blocksBroken.yml");
        if (!blocksBrokenFile.exists()) {
            blocksBrokenFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("blocksBroken.yml", false);
        }

        blocksBrokenConfig = new YamlConfiguration();
        try {
            blocksBrokenConfig.load(blocksBrokenFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!SellBlocks.isInRegionWhereCanMine(e.getBlock().getLocation())) {
            e.setCancelled(true);
            return;
        }
        //PersistentDataContainer container = e.getPlayer().getPersistentDataContainer();
        if (!blocksBrokenConfig.contains(e.getPlayer().getUniqueId().toString()))
            blocksBrokenConfig.set(e.getPlayer().getUniqueId().toString(), 0);
        blocksBrokenConfig.set(e.getPlayer().getUniqueId().toString(), blocksBrokenConfig.getInt(e.getPlayer().getUniqueId().toString()) + 1);
        try {
            blocksBrokenConfig.save(blocksBrokenFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE){
            if (petManager.getActivePet(e.getPlayer()) != null && petManager.getActivePet(e.getPlayer()).getType() != PetType.BEACON)
                petManager.getActivePet(e.getPlayer()).usePet();
            SellBlocks.sellBlock(e.getBlock(), e.getPlayer().getInventory().getItemInMainHand(), e.getPlayer());
        }
    }
}
