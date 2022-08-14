package me.sildev.zoopr.Enchants.pickaxeEnchants.Tasks;

import me.sildev.zoopr.eco.SellBlocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LaserTask extends BukkitRunnable {

    int SIZE;
    Player player;
    Location loc;
    int currHeight;

    ItemStack pickaxe;

    public LaserTask(Player player, int SIZE, Location loc) {
        this.SIZE = SIZE;
        this.player = player;
        this.loc = loc;
        currHeight = loc.getBlockY();

        this.pickaxe = player.getInventory().getItemInMainHand();
    }

    @Override
    public void run() {
        List<Location> blocks = new ArrayList<Location>();

        int bx = loc.getBlockX() - SIZE;
        int by = loc.getBlockY() - SIZE;
        int bz = loc.getBlockZ() - SIZE;

        // X-axis
        for (int x = bx; x < loc.getBlockX() + SIZE; x++) {
            Location l = new Location(loc.getWorld(), x, currHeight, loc.getBlockZ());
            if (l.getBlock().getType() != Material.AIR)
                SellBlocks.sellBlock(l.getBlock(), this.pickaxe, player);
            /*for (int y = by; y < loc.getBlockY() + SIZE; y++) {
                Location l = new Location(loc.getWorld(), x, y, loc.getBlockZ());
                if (l.getBlock().getType() != Material.AIR)
                    blocks.add(l);
            }*/
        }
        // Z-axis
        for (int z = bz; z < loc.getBlockZ() + SIZE; z++) {
            Location l = new Location(loc.getWorld(), loc.getBlockX(), currHeight, z);
            if (l.getBlock().getType() != Material.AIR)
                SellBlocks.sellBlock(l.getBlock(), this.pickaxe, player);

            /*for (int y = by; y < loc.getBlockY() + SIZE; y++) {
                Location l = new Location(loc.getWorld(), loc.getBlockX(), y, z);
                if (l.getBlock().getType() != Material.AIR)
                    blocks.add(l);
            }*/
        }
        currHeight--;
        if (currHeight <= loc.getBlockY() - SIZE)
            cancel();
    }
}
