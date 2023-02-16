package me.sildev.zoopr.boss;

import me.sildev.zoopr.ZooPR;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class bossSpawnCMD implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("spawnboss")) return false;

        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;

        if (!player.hasPermission("zoopr.spawnboss"))
            return true;

        Location loc = player.getLocation();
        ZooPR.getBossManager().spawnBoss(loc);

        return true;
    }
}
