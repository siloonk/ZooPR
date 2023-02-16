package me.sildev.zoopr.boss;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class BossManager {

    File bossConfigFile;
    FileConfiguration bossConfig;

    IronGolem bossEntity;
    Location spawnLocation;

    ItemStack droppedItem;

    public BossManager() {
        createBossConfig();
        new spawnBossTask(bossConfig.getLocation("boss-location")).runTaskTimer(ZooPR.getPlugin(), 0L, 36000L);
    }

    public void setLocation(Location loc) {
        spawnLocation = loc;
    }


    private void createBossConfig() {
        bossConfigFile = new File(ZooPR.getPlugin().getDataFolder(), "boss.yml");
        if (!bossConfigFile.exists()) {
            bossConfigFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("boss.yml", false);
        }

        bossConfig = new YamlConfiguration();
        try {
            bossConfig.load(bossConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void spawnBoss(Location loc) {
        IronGolem golem = (IronGolem) loc.getWorld().spawnEntity(loc, EntityType.IRON_GOLEM);
        HashMap<String, Object> bossData = loadBossData();
        golem.setMaxHealth((Double) bossData.get("health"));
        golem.setHealth(golem.getMaxHealth());
        golem.setCustomName(((String) bossData.get("name")).replaceAll("%health%", String.valueOf(golem.getHealth())));
        golem.setCustomNameVisible(true);
        golem.getPersistentDataContainer().set(new NamespacedKey(ZooPR.getPlugin(), "confirm-boss"), PersistentDataType.INTEGER, 1);
        bossEntity = golem;
    }

    public void updateDisplayName() {
        HashMap<String, Object> bossData = loadBossData();
        bossEntity.setCustomName(((String) bossData.get("name")).replaceAll("%health%", String.valueOf(bossEntity.getHealth())));
    }

    public List<String> getAbilities() {
        HashMap<String, Object> bossData = loadBossData();
        return (List<String>) bossData.get("abilities");
    }

    // Load all data related to the boss except for it's entity-displayname and it's chat-displayname
    public HashMap<String, Object> loadBossData() {
        HashMap<String, Object> map = new HashMap<>();

        List<String> abilties = bossConfig.getStringList("boss-abilties");
        map.put("abilities", abilties);

        double health = bossConfig.getDouble("boss-health");
        map.put("health", health);

        String name = coloredString.color(bossConfig.getString("entity-displayName"));
        map.put("name", name);

        map.put("spawnLocation", spawnLocation);

        map.put("droppedItem", droppedItem);

        return map;
    }
}
