package me.sildev.zoopr.mines;

import me.sildev.zoopr.ZooPR;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MineManager {

    public static HashMap<String, Mine> mines = new HashMap<>();

    private File minesFile;
    private FileConfiguration minesConfig;

    public MineManager() {
        createConfig();
    }


    void createConfig() {
        minesFile = new File(ZooPR.getPlugin().getDataFolder(), "mines.yml");
        if (!minesFile.exists()) {
            minesFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("mines.yml", false);
        }

        minesConfig = new YamlConfiguration();
        try {
            minesConfig.load(minesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveMines() {
        minesConfig.createSection("mines", mines);
        try {
            minesConfig.save(minesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addMine(String name, Mine mine) {
        mines.put(name, mine);
    }

    public void loadMines() {
        if (minesConfig.getConfigurationSection("mines") == null) return;
        for (String name : minesConfig.getConfigurationSection("mines").getKeys(false)) {
            mines.put(name, (Mine) minesConfig.getConfigurationSection("mines").get(name));
        }
    }
}
