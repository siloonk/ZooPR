package me.sildev.zoopr.Leaderboard;

import me.sildev.zoopr.Leaderboard.tasks.getAllPlayersTask;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class LeaderboardManager {

    public HashMap<UUID, Double> moneyToSort = new HashMap<>();
    public HashMap<UUID, Double> tokensToSort = new HashMap<>();
    public HashMap<UUID, Double> beaconsToSort = new HashMap<>();

    public LeaderboardManager() {
        new getAllPlayersTask(this).runTaskAsynchronously(ZooPR.getPlugin());
    }


    public Map<UUID, Double> getMoneyLeaderboard() {
        Map<UUID, Double> sorted = moneyToSort.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }

    public Map<UUID, Double> getBeaconLeaderboard() {
        Map<UUID, Double> sorted = beaconsToSort.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }

    public Map<UUID, Double> getTokenLeaderboard() {
        Map<UUID, Double> sorted = tokensToSort.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }
}
