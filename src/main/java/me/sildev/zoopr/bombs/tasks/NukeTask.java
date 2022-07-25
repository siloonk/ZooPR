package me.sildev.zoopr.bombs.tasks;

import me.sildev.zoopr.eco.SellBlocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class NukeTask extends BukkitRunnable {

    Player player;
    int tier;
    Location center;
    int radius;

    int currRow;

    public NukeTask(Player player, int tier, Location center) {
        this.player = player;
        this.tier = tier;
        this.center = center;

        radius = tier * 3;
        currRow = center.getBlockX() - radius;
    }

    @Override
    public void run() {

        int bx = center.getBlockX();
        int by = center.getBlockY();
        int bz = center.getBlockZ();

        for(int y = by - radius; y <= by + radius; y++) {
            for(int z = bz - radius; z <= bz + radius; z++) {

                double distance = ((bx-currRow) * (bx-currRow) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));

                if(distance < radius * radius && (distance < ((radius - 1) * (radius - 1)))) {
                    Location l = new Location(center.getWorld(), currRow, y, z);
                    if (l.getBlock().getType() != Material.AIR)
                        SellBlocks.sellBlocknoPickaxe(l.getBlock(), player);
                }
            }
        }


        currRow++;
        if (!(currRow <= bx + radius)) {
            cancel();
        }
    }
}
