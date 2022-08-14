package me.sildev.zoopr.scoreboard;

import me.sildev.zoopr.ZooPR;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.HashMap;
import java.util.UUID;

public class scoreboardListener implements Listener {

    HashMap<UUID, Integer> ids = new HashMap<UUID, Integer>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ZooPR.getPlugin(), new scoreboardResetTask(e.getPlayer()), 0L, 10L);
        ids.put(e.getPlayer().getUniqueId(), taskID);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (ids.containsKey(e.getPlayer().getUniqueId())) {
            int taskID = ids.get(e.getPlayer().getUniqueId());
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }
}
