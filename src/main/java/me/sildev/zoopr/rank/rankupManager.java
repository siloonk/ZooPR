package me.sildev.zoopr.rank;

import me.sildev.zoopr.ZooPR;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class rankupManager implements Listener {

    public static String[] ranks = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static NamespacedKey rankup = new NamespacedKey(ZooPR.getPlugin(), "rank");
    public static NamespacedKey rankupCost = new NamespacedKey(ZooPR.getPlugin(), "rankup-cost");

    public static NamespacedKey prestige = new NamespacedKey(ZooPR.getPlugin(), "prestige");
    public static NamespacedKey prestigeCost = new NamespacedKey(ZooPR.getPlugin(), "prestige-cost");
    public static NamespacedKey rebirthPoints = new NamespacedKey(ZooPR.getPlugin(), "rebirth-points");
    public static NamespacedKey prestigePoints = new NamespacedKey(ZooPR.getPlugin(), "prestige-points");

    public static final double StartRankupCost = 5000;
    public static final double rankupCostMultiplier = 1.25;
    private static File rebirthFile;
    public static FileConfiguration rebirthConfig;

    public rankupManager() {
        createRebirthConfig();
    }

    private void createRebirthConfig() {
        rebirthFile = new File(ZooPR.getPlugin().getDataFolder(), "rebirth.yml");
        if (!rebirthFile.exists()) {
            rebirthFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("rebirth.yml", false);
        }

        rebirthConfig = new YamlConfiguration();
        try {
            rebirthConfig.load(rebirthFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Integer getRebirthOfPlayer(UUID player) {
        if (!rebirthConfig.contains(String.valueOf(player)))
            return 0;
        return rebirthConfig.getInt(String.valueOf(player));
    }

    public static void storeRebirth(UUID player, int value) {
        rebirthConfig.set(String.valueOf(player), value);
        try {
            rebirthConfig.save(rebirthFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();

        //if (data.has(rankup)) return;

        data.set(rankup, PersistentDataType.INTEGER, 0);
        data.set(rankupCost, PersistentDataType.DOUBLE, StartRankupCost);
        data.set(prestige, PersistentDataType.INTEGER, 0);
        rebirthConfig.set(player.getUniqueId().toString(), 0);
        //data.set(rebirth, PersistentDataType.INTEGER, 0);
        data.set(rebirthPoints, PersistentDataType.DOUBLE, 0d);
        data.set(prestigePoints, PersistentDataType.DOUBLE, 0d);

        player.getInventory().clear();
    }
}
