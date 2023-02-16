package me.sildev.zoopr.Leaderboard;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.sildev.zoopr.Enchants.pickaxeEnchants.events.noEnchantListener;
import me.sildev.zoopr.Gang.Gang;
import me.sildev.zoopr.Leaderboard.tasks.getAllPlayersTask;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.coloredString;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class LeaderboardManager {

    public HashMap<UUID, Integer> rebirthToSort = new HashMap<>();
    public HashMap<UUID, Integer> blockToSort = new HashMap<>();
    public HashMap<Gang, Integer> gangsToSort = new HashMap<>();

    List<ArmorStand> rebirthArmorStands = new ArrayList<>();
    Location rebirthArmorStandBaseLocation;

    long resetTimer = 300; // in seconds

    String[] numberCharacters = new String[] {
            "➊",
            "➋",
            "➌",
            "➍",
            "➎",
            "➏",
            "➐",
            "➑",
            "➒",
            "➓"
    };


    public LeaderboardManager() {
        new getAllPlayersTask(this).runTaskTimerAsynchronously(ZooPR.getPlugin(), 0L, 12000L /* 10 minutes */);

        startHoloRefreshTimer();
    }

    void startHoloRefreshTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println(Arrays.toString(blockToSort.values().toArray()));
                // Rebirth
                if (DHAPI.getHologram("rebirth") != null) {
                    Hologram holo = DHAPI.getHologram("rebirth");
                    Map<UUID, Integer> map = getRebirthLeaderBoard();
                    AtomicInteger index = new AtomicInteger(1);
                    map.forEach((player, value) -> {
                        if (index.get() > 10)
                            return;
                        ZooPR.getPlugin().getServer().getConsoleSender().sendMessage(player + " -> " + value);

                        DHAPI.setHologramLine(holo.getPages().get(0), index.get() + 1, coloredString.color("&d&l" + numberCharacters[index.get() - 1] + "&5&l " + Bukkit.getOfflinePlayer(player).getName() + " ➼ &f&l&o" + rankupManager.getRebirthOfPlayer(player) + " Rebirths"));
                        index.addAndGet(1);
                    });
                }

                if (DHAPI.getHologram("gang") != null) {
                    Hologram holo = DHAPI.getHologram("gang");
                    Map<Gang, Integer> map = getGangsLeaderboard();
                    AtomicInteger index = new AtomicInteger(1);
                    map.forEach((player, value) -> {
                        if (index.get() > 10)
                            return;
                        ZooPR.getPlugin().getServer().getConsoleSender().sendMessage(player + " -> " + value);

                        DHAPI.setHologramLine(holo.getPages().get(0), index.get() + 1, coloredString.color("&d&l" + numberCharacters[index.get() - 1] + "&5&l " + player.getName() + " ➼ &f&l&oLevel " + value));
                        index.addAndGet(1);
                    });
                }

                if (DHAPI.getHologram("blocks") != null) {
                    Hologram holo = DHAPI.getHologram("blocks");
                    Map<UUID, Integer> map = getBlocksLeaderboard();
                    AtomicInteger index = new AtomicInteger(1);
                    map.forEach((player, value) -> {
                        if (index.get() > 10)
                            return;
                        ZooPR.getPlugin().getServer().getConsoleSender().sendMessage(player + " -> " + value);

                        DHAPI.setHologramLine(holo.getPages().get(0), index.get() + 1, coloredString.color("&d&l" + numberCharacters[index.get() - 1] + "&5&l " + Bukkit.getOfflinePlayer(player).getName() + " ➼ &f&l&o" + formatNumber.coolFormat(noEnchantListener.blocksBrokenConfig.getInt(player.toString()), 0) + " blocks"));
                        index.addAndGet(1);
                    });
                }

            }
        }.runTaskTimer(ZooPR.getPlugin(), 0, resetTimer * 20);
    }

    

    public Map<UUID, Integer> getRebirthLeaderBoard() {
        Map<UUID, Integer> sorted = rebirthToSort.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }

    public Map<Gang, Integer> getGangsLeaderboard() {
        Map<Gang, Integer> sorted = gangsToSort.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }

    public Map<UUID, Integer> getBlocksLeaderboard() {
        Map<UUID, Integer> sorted = blockToSort.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }
}
