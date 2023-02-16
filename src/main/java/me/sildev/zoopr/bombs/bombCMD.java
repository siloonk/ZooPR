package me.sildev.zoopr.bombs;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class bombCMD implements CommandExecutor {

    String usage = Messages.get("bombUsage");
    String noPerms = Messages.get("noPermission");
    String playerNotOnline = Messages.get("playerIsNotOnline");
    String invalidBombType = Messages.get("invalidBombType");
    String notANumber = Messages.get("NotANumber");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("bomb"))
            return false;

        // /bomb give (player) (type) (tier) (amount)

        // Check if the correct amount of arguments have been given!
        if (args.length < 5) {
            sender.sendMessage(usage);
            return true;
        }

        if (!sender.hasPermission("zoopr.bomb")) {
            sender.sendMessage(noPerms);
            return true;
        }

        if (!args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(usage);
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(playerNotOnline);
            return true;
        }

        String type = args[2].toUpperCase();
        if (!(type.equals("MOAB") || type.equals("NUKE") || type.equals("BLACKHOLE"))) {
            sender.sendMessage(invalidBombType);
            return true;
        }

        int tier;
        int amount;
        try {
            tier = Integer.parseInt(args[3]);
            amount = Integer.parseInt(args[4]);
        } catch (Exception e) {
            sender.sendMessage(notANumber);
            return true;
        }

        ItemStack bomb = bombManager.getBomb(type, tier);
        bomb.setAmount(amount);
        target.getInventory().addItem(bomb);

        return true;
    }
}
