package me.sildev.zoopr.pvemobs;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PveSpawnerCMD implements CommandExecutor {

    String usage = Messages.get("pvespawnerUsage");
    String noPerms = Messages.get("noPermission");
    String notAPlayer = Messages.get("playerIsNotOnline");
    String invalidSpawner = Messages.get("invalidSpawner");
    String notANumber = Messages.get("NotANumber");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("pvespawner")) return false;
        // /pvespawner give (player) (type) (amount)

        if (!sender.hasPermission("zoopr.pvespawner")) {
            sender.sendMessage(noPerms);
            return true;
        }

        if (!(args.length >= 4)) {
            sender.sendMessage(usage);
            return true;
        }

        if (!args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(usage);
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(notAPlayer);
            return true;
        }

        ItemStack spawner;
        try {
            spawner = PveMobManager.getSpawner(args[2]);
        } catch (NullPointerException e) {
            sender.sendMessage(invalidSpawner);
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(notANumber);
            return true;
        }


        spawner.setAmount(amount);
        target.getInventory().addItem(spawner);

        return true;
    }
}
