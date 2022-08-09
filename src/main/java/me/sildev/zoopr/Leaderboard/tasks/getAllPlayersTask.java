package me.sildev.zoopr.Leaderboard.tasks;

import me.sildev.zoopr.Leaderboard.LeaderboardManager;
import me.sildev.zoopr.Leaderboard.eco.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class getAllPlayersTask extends BukkitRunnable {

    LeaderboardManager manager;

    public getAllPlayersTask(LeaderboardManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            manager.moneyToSort.put(player.getUniqueId(), EconomyManager.getMoneyOfUser(player.getUniqueId()));
            manager.tokensToSort.put(player.getUniqueId(), EconomyManager.getMoneyOfUser(player.getUniqueId()));
            manager.beaconsToSort.put(player.getUniqueId(), EconomyManager.getMoneyOfUser(player.getUniqueId()));
        }
    }
}
