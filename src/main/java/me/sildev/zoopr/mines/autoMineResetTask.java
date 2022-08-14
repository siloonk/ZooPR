package me.sildev.zoopr.mines;

import me.sildev.zoopr.ZooPR;
import org.bukkit.scheduler.BukkitRunnable;

public class autoMineResetTask extends BukkitRunnable {

    Mine mine;

    public autoMineResetTask(Mine mine) {
        this.mine = mine;
    }

    @Override
    public void run() {
        new mineResetTask(mine.minLocation, mine.maxLocation, mine.blocks).runTaskTimer(ZooPR.getPlugin(), 0L, 7L);
        System.out.println("test");
    }
}
