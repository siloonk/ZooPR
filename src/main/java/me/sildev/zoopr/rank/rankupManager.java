package me.sildev.zoopr.rank;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class rankupManager implements Listener {

    public static String[] ranks = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static NamespacedKey rankup = new NamespacedKey(ZooPR.getPlugin(), "rank");
    public static NamespacedKey rankupCost = new NamespacedKey(ZooPR.getPlugin(), "rankup-cost");

    public static NamespacedKey prestige = new NamespacedKey(ZooPR.getPlugin(), "prestige");
    public static NamespacedKey prestigeCost = new NamespacedKey(ZooPR.getPlugin(), "prestige-cost");
    public static NamespacedKey rebirth = new NamespacedKey(ZooPR.getPlugin(), "rebirth");
    public static NamespacedKey rebirthPoints = new NamespacedKey(ZooPR.getPlugin(), "rebirth-points");
    public static NamespacedKey prestigePoints = new NamespacedKey(ZooPR.getPlugin(), "prestige-points");

    public static final double StartRankupCost = 5000;
    public static final double rankupCostMultiplier = 1.5;

    public static final double StartPrestigeCost = 5000000;
    public static final double prestigeCostMultiplier = 2;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();

        //if (data.has(rankup)) return;

        data.set(rankup, PersistentDataType.INTEGER, 0);
        data.set(rankupCost, PersistentDataType.DOUBLE, StartRankupCost);
        data.set(prestige, PersistentDataType.INTEGER, 0);
        data.set(prestigeCost, PersistentDataType.DOUBLE, StartPrestigeCost);
        data.set(rebirth, PersistentDataType.INTEGER, 0);
        data.set(rebirthPoints, PersistentDataType.DOUBLE, 0d);
        data.set(prestigePoints, PersistentDataType.DOUBLE, 0d);
    }
}
