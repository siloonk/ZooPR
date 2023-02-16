package me.sildev.zoopr.essentials.commands;

import me.sildev.zoopr.essentials.MineLocationManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class setMineLocation implements CommandExecutor {

    String onlyPlayersCanExecuteThisCommand = Messages.get("onlyPlayersCanExecuteThisCommand");
    String noPerms = Messages.get("noPermission");
    String setMineLocationusage = Messages.get("setMineLocationUsage");


    public setMineLocation() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("setminelocation"))
            return false;

        if (!(sender instanceof Player)) {
            sender.sendMessage(onlyPlayersCanExecuteThisCommand);
            return true;
        }

        if (!sender.hasPermission("zoopr.setminelocation")) {
            sender.sendMessage(noPerms);
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(setMineLocationusage);
            return true;
        }

        String mine = args[0];
        List<String> ranks = Arrays.asList(rankupManager.ranks);

        MineLocationManager.storeLocation(mine.toUpperCase(), ((Player) sender).getLocation());
        return true;
    }
}
