package me.sildev.zoopr.Enchants.pickaxeEnchants.Tasks;

import me.sildev.zoopr.Leaderboard.eco.SellBlocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class JackhammerTask extends BukkitRunnable {

    Player player;
    Location center;

    int size;
    int height;

    int currRow;

    ItemStack pickaxe;

    public JackhammerTask(Player player, Location center, int SIZE, int HEIGHT) {
        this.size = SIZE;
        this.height= HEIGHT;
        this.center = center;
        this.player = player;

        currRow = center.getBlockX() - SIZE;

        this.pickaxe = player.getInventory().getItemInMainHand();
    }

    @Override
    public void run() {

        int bz = center.getBlockZ();
        int by = center.getBlockY();

        for (int y = by; y > center.getBlockY() - height; y--) {
            for (int z = bz; z < center.getBlockZ() + size; z++) {
                Location l = new Location(center.getWorld(), currRow, y, z);
                if (l.getBlock().getType() != Material.AIR)
                    SellBlocks.sellBlock(l.getBlock(), this.pickaxe);
            }
        }


        for (int y = by; y > center.getBlockY() - height; y--) {
            for (int z = bz; z > center.getBlockZ() - size; z--) {
                Location l = new Location(center.getWorld(), currRow, y, z);
                if (l.getBlock().getType() != Material.AIR)
                    SellBlocks.sellBlock(l.getBlock(), this.pickaxe);
            }
        }

        currRow++;
        if (currRow >= center.getBlockX() + size)
            cancel();
    }
}
