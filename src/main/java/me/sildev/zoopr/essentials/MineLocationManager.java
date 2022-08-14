package me.sildev.zoopr.essentials;

import me.sildev.zoopr.ZooPR;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MineLocationManager {

    private static File mineLocationFile;
    private static FileConfiguration mineLocations;

    public MineLocationManager() {
        createMineLocations();
    }

    public void createMineLocations() {
        mineLocationFile = new File(ZooPR.getPlugin().getDataFolder(), "minelocations.yml");
        if (!mineLocationFile.exists()) {
            mineLocationFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("minelocations.yml", false);
        }

        mineLocations = new YamlConfiguration();
        try {
            mineLocations.load(mineLocationFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void storeLocation(String mine, Location loc) {
        mineLocations.set(mine, loc);
        try {
            mineLocations.save(mineLocationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Location getLocation(String mine) {
        if (!mineLocations.contains(mine)) return null;
        Location loc = (Location) mineLocations.getLocation(mine);
        return loc;
    }
}
