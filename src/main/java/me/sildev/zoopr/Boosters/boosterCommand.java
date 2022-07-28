package me.sildev.zoopr.Boosters;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class boosterCommand implements CommandExecutor {

    String noPerms = Messages.get("noPermission");
    String usage = Messages.get("boosterUsage");
    String notAPlayer = Messages.get("playerIsNotOnline");
    String invalidNumbers = Messages.get("NotANumber");
    String receivedBooster = Messages.get("receivedBooster");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("booster")) return false;

        // Check for permissions;
        if (!sender.hasPermission("zoopr.booster")) {
            sender.sendMessage(noPerms);
            return true;
        }


        // /booster give (player) (multiplier) (lengthNumber) (Minutes/Hours) (Type)
        if (!(args.length >= 6)){
            sender.sendMessage(usage);
            return true;
        }

        // check first argument
        if (!args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(usage);
            return true;
        }

        // Check if its a player that was given
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(notAPlayer);
            return true;
        }

        // Check if the 3rd 4th arguments are numbers
        double multiplier;
        int length;

        try {
            multiplier = Double.parseDouble(args[2]);
            length = Integer.parseInt(args[3]);
        } catch (Exception e) {
            sender.sendMessage(invalidNumbers);
            return true;
        }

        if (args[4].equalsIgnoreCase("minutes")) {
            length = length * 60 * 1000;
        } else if (args[4].equalsIgnoreCase("hours")) {
            length = length * 60 * 60 * 1000;
        }

        String type = args[5];
        if (!(type.equalsIgnoreCase("tokens") || type.equalsIgnoreCase("money"))) {
            sender.sendMessage(usage);
            return true;
        }

        // get the booster item
        ItemStack booster = boosterManager.generateBooster(multiplier, length, type.toUpperCase());
        target.getInventory().addItem(booster);

        // Send message to target
        String message = receivedBooster.replaceAll("%boostertype%", type.toLowerCase());
        target.sendMessage(message);
        return true;
    }
}
