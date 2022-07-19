package me.sildev.zoopr.utils;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class loops {

    public static List<Location> generateSphere(Location centerBlock, int radius, boolean hollow) {
        if (centerBlock == null) {
            return new ArrayList<>();
        }

        List<Location> circleBlocks = new ArrayList<Location>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {

                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));

                    if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {

                        Location l = new Location(centerBlock.getWorld(), x, y, z);
                        if (l.getBlock().getType() != Material.AIR)
                            circleBlocks.add(l);

                    }

                }
            }
        }

        return circleBlocks;
    }

    public static List<Location> generateCube(Location center, int size, int height, boolean isTop) {
        if (center == null) {
            return new ArrayList<>();
        }
        List<Location> blocks = new ArrayList<Location>();

        int bx = center.getBlockX() - size;
        int bz = center.getBlockZ() - size;
        int by = center.getBlockY() + height;

        if (isTop) by = center.getBlockY();

        // Get all blocks
        for (int x = bx; x < center.getBlockX() + size; x++) {
            for (int y = by; y > center.getBlockY() - height; y--) {
                for (int z = bz; z < center.getBlockZ() + size; z++) {
                    Location l = new Location(center.getWorld(), x, y, z);
                    if (l.getBlock().getType() != Material.AIR)
                        blocks.add(l);
                }
            }
        }

        return blocks;
    }

    public static List<Location> getMultipleDirection(Location center, int size, int height) {
        List<Location> blocks = new ArrayList<Location>();

        int bx = center.getBlockX() - size;
        int by = center.getBlockY() - height;
        int bz = center.getBlockZ() - size;

        // X-axis
        for (int x = bx; x < center.getBlockX() + size; x++)
            for (int y = by; y < center.getBlockY() + height; y++) {
                Location l = new Location(center.getWorld(), x, y, center.getBlockZ());
                if (l.getBlock().getType() != Material.AIR)
                    blocks.add(l);
            }

        // Z-axis
        for (int z = bz; z < center.getBlockX() + size; z++)
            for (int y = by; y < center.getBlockY() + height; y++) {
                Location l = new Location(center.getWorld(), center.getBlockX(), y, z);
                if (l.getBlock().getType() != Material.AIR)
                    blocks.add(l);
            }



        return blocks;
    }
}
