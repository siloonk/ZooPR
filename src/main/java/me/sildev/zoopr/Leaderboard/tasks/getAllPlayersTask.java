package me.sildev.zoopr.Leaderboard.tasks;

import me.sildev.zoopr.Enchants.pickaxeEnchants.events.noEnchantListener;
import me.sildev.zoopr.Gang.Gang;
import me.sildev.zoopr.Gang.GangManager;
import me.sildev.zoopr.Leaderboard.LeaderboardManager;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.rank.rankupManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class getAllPlayersTask extends BukkitRunnable {

    LeaderboardManager manager;

    public getAllPlayersTask(LeaderboardManager manager) {
        this.manager = manager;
    }

    List<Gang> gangs = new ArrayList<>();

    @Override
    public void run() {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {

            manager.rebirthToSort.put(player.getUniqueId(), rankupManager.getRebirthOfPlayer(player.getUniqueId()));
            manager.blockToSort.put(player.getUniqueId(), noEnchantListener.blocksBrokenConfig.getInt(player.getUniqueId().toString()));
        }

        for (String gangName : GangManager.gangNames) {
            Gang g = GangManager.loadGang(gangName);
            gangs.add(g);
        }

        for (Gang gang : gangs) {
            manager.gangsToSort.put(gang, gang.getLevel());
            System.out.println(gang.getName());
        }
    }
}
