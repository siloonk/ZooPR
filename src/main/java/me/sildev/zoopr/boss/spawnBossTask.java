package me.sildev.zoopr.boss;

import me.sildev.zoopr.ZooPR;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class spawnBossTask extends BukkitRunnable {

    Location spawnLocation;

    public spawnBossTask(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    @Override
    public void run() {
        ZooPR.getBossManager().spawnBoss(new Location(Bukkit.getWorld("PveMap"), 128, 20, 122));
    }
}
