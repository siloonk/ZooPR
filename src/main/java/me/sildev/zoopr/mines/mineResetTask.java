package me.sildev.zoopr.mines;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class mineResetTask extends BukkitRunnable {


    int currX;
    Location minLoc;
    Location maxLoc;
    List<Material> blocks;

    public mineResetTask(Location minLoc, Location maxLoc, List<Material> blocks) {
        this.blocks = blocks;
        this.minLoc = minLoc;
        this.maxLoc = maxLoc;
        currX = minLoc.getBlockX();
    }

    @Override
    public void run() {
        Random rd = new Random();
        for (int y = minLoc.getBlockY(); y < maxLoc.getBlockY(); y++) {
            for (int z = minLoc.getBlockZ(); z < maxLoc.getBlockZ(); z++) {
                int index = rd.nextInt(blocks.size());
                if (minLoc.getWorld().getBlockAt(new Location(minLoc.getWorld(), currX, y, z)).getType() == Material.AIR)
                    minLoc.getWorld().getBlockAt(new Location(minLoc.getWorld(), currX, y, z)).setType(blocks.get(index));
            }
        }

        currX++;
        if (currX == maxLoc.getBlockX())
            cancel();
    }
}
