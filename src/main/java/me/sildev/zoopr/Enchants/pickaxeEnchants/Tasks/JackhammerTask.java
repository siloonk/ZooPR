package me.sildev.zoopr.Enchants.pickaxeEnchants.Tasks;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.SellBlocks;
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

    ItemStack pickaxe;

    public JackhammerTask(Player player, Location center, int SIZE, int HEIGHT) {
        this.size = SIZE;
        this.height= HEIGHT;
        this.center = center;
        this.player = player;

        this.pickaxe = player.getInventory().getItemInMainHand();
    }

    @Override
    public void run() {

        int bz = center.getBlockZ();
        int by = center.getBlockY();
        int bx = center.getBlockX();

        for (int x = bx - size; x < center.getBlockX() + size; x++) {
            for (int y = by; y > center.getBlockY() - height; y--) {
                for (int z = bz - size; z < center.getBlockZ() + size; z++) {
                    Location l = new Location(center.getWorld(), x, y, z);
                    if (l.getBlock().getType() != Material.AIR) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SellBlocks.sellBlock(l.getBlock(), pickaxe, player);
                            }
                        }.runTask(ZooPR.getPlugin());
                    }
                }
            }
        }
    }
}
