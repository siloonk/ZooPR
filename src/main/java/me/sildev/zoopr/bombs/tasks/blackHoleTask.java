package me.sildev.zoopr.bombs.tasks;

import me.sildev.zoopr.eco.SellBlocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class blackHoleTask extends BukkitRunnable {

    int currX;
    int currY;
    int currZ;


    Player player;
    Location loc;
    int limit;
    int cubeSize;


    public blackHoleTask(Player player, Location loc, int tier) {
        this.player = player;
        this.loc = loc;

        this.currX = loc.getBlockX();
        this.currY = loc.getBlockY();
        this.currZ = loc.getBlockZ();

        this.limit = tier * 3;
        this.cubeSize = 1;

    }

    @Override
    public void run() {
        //(x == currX - cubeSize || x == currX + cubeSize - 1) &&
        // && (z == currZ - cubeSize || z == currZ + cubeSize - 1)
        // (y == currY - cubeSize || y == currY + cubeSize - 1)
        for (int x = currX - cubeSize; x < currX + cubeSize; x++){
            for (int y = currY - cubeSize; y < currY + cubeSize; y++) {
                for (int z = currZ - cubeSize; z < currZ + cubeSize; z++) {
                    if (y != currY - cubeSize && y != currY + cubeSize)
                        if ((x >= currX - cubeSize && z == currZ - cubeSize) || (x >= currX - cubeSize && z == currZ + cubeSize - 1) || (x == currX + cubeSize - 1 && z >= currZ - cubeSize)) {
                            Location l = new Location(loc.getWorld(), x, y, z);
                            if (SellBlocks.blockValueConfig.contains(l.getBlock().getType().toString()))
                                SellBlocks.sellBlocknoPickaxe(l.getBlock(), player);
                        }
                    else {
                        Location l = new Location(loc.getWorld(), x, y, z);
                        if (SellBlocks.blockValueConfig.contains(l.getBlock().getType().toString()))
                            SellBlocks.sellBlocknoPickaxe(l.getBlock(), player);
                    }
                }
            }
        }

        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 1);
        cubeSize++;
        if (cubeSize >= limit)
            cancel();
    }



}
