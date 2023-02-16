package me.sildev.zoopr.mines;

import me.sildev.zoopr.ZooPR;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mine implements ConfigurationSerializable {

    Location minLocation;
    Location maxLocation;

    List<Material> blocks;
    String name;
    long resetTimer; // In milliseconds

    public Mine(Location loc1, Location loc2, List<Material> blocks, String name, long resetTimer /* in seconds */) {
        minLocation = getMinLocation(loc1, loc2);
        maxLocation = getMaxLocation(loc1, loc2);
        this.name = name;
        this.blocks = blocks;
        this.resetTimer = resetTimer;

        new autoMineResetTask(this).runTaskTimer(ZooPR.getPlugin(), this.resetTimer * 20L, this.resetTimer * 20L);
    }

    public Mine(Map<String, Object> map) {
        this.minLocation = (Location) map.get("minLocation");
        this.maxLocation = (Location) map.get("maxLocation");
        List<String> bl = (List<String>) map.get("blocks");
        this.blocks = new ArrayList<>();
        for (String block : bl) {
            this.blocks.add(Material.matchMaterial(block));
        }
        this.name = (String) map.get("name");
        this.resetTimer = Long.parseLong(String.valueOf((int)map.get("resettimer")));

        new autoMineResetTask(this).runTaskTimer(ZooPR.getPlugin(), 0L, this.resetTimer * 20L);
    }

    public void reset() {
        new mineResetTask(minLocation, maxLocation, blocks).runTaskTimer(ZooPR.getPlugin(), 0L, 7L);
    }

    public void addBlock(Material block) {
        this.blocks.add(block);
    }

    public String getName() {
        return this.name;
    }

    private Location getMinLocation(Location loc1, Location loc2) {
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();

        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();

        int minX;
        int minY;
        int minZ;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);

        return new Location(loc1.getWorld(), minX, minY, minZ);
    }

    private Location getMaxLocation(Location loc1, Location loc2) {
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();

        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();

        int minX;
        int minY;
        int minZ;

        minX = Math.max(x1, x2);
        minY = Math.max(y1, y2);
        minZ = Math.max(z1, z2);

        return new Location(loc1.getWorld(), minX, minY, minZ);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String, Object> minesMap = new HashMap<String, Object>() {{
            put("minLocation", minLocation);
            put("maxLocation", maxLocation);
            List<String> bl = new ArrayList<>();
            for (Material block : blocks) {
                bl.add(block.toString());
            }
            put("blocks", bl);
            put("name", name);
            put("resettimer", resetTimer);
        }};
        return minesMap;
    }
}
