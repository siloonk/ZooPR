package me.sildev.zoopr.essentials.commands;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;

public class banCMD implements CommandExecutor {

    String usage = Messages.get("banUsage");
    String noPerms = Messages.get("noPermission");
    String notExist = Messages.get("playerDoesNotExist");
    String successfullyBanned = Messages.get("successfullyBannedPlayer");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("ban")) {
            return false;
        }

        // Check if the sender gave the right arguments!
        if (!(args.length >= 3)) {
            sender.sendMessage(usage);
            return true;
        }

        // Check if the sender has permissions to using this command!
        if (!sender.hasPermission("zoopr.ban")) {
            sender.sendMessage(noPerms);
            return true;
        }

        // Check if the given player exists
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage(notExist);
            return true;
        }

        // Set the time
        int time = Integer.parseInt(args[1]);
        if (args[2].equalsIgnoreCase("day")) {
            time = time * 60 * 60 * 24 * 1000;
        } else if (args[2].equalsIgnoreCase("month")) {
            time = time * 60 * 60 * 24 * 30 * 1000;
        } else if (args[2].equalsIgnoreCase("year")) {
            time = time * 60 * 60 * 24 * 365 * 1000;
        } else {
            sender.sendMessage(usage);
            return true;
        }

        Date banTime = new Date();
        long banTimeInMillis = banTime.getTime();

        banTime.setTime(banTimeInMillis + time);

        // Set the reason!
        String[] reasonArray = Arrays.copyOfRange(args, 3, args.length);
        String reason = "";
        for (String word : reasonArray) {
            reason += word + " ";
        }


        // Ban the player
        if (target.isOnline())
            ((Player)target).kickPlayer(reason);
        target.banPlayer(reason, banTime, sender.getName(), true);
        String message = successfullyBanned.replaceAll("%target%", target.getName());
        sender.sendMessage(message);
        return true;
    }
}
