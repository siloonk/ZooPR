package me.sildev.zoopr.Luckyblock;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class luckyblockGiveCMD implements CommandExecutor {

    String noPerms = Messages.get("noPermission");
    String notAPlayer = Messages.get("playerIsNotOnline");
    String giveluckyblockUsage = Messages.get("giveluckyblockUsage");
    String notANumber = Messages.get("NotANumber");
    String givenLuckyBlocks = Messages.get("givenLuckyBlock");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("giveluckyblock")) return false;

        // Check if the player has permisisons to execute this command
        if (!sender.hasPermission("zoopr.giveluckyblock")) {
            sender.sendMessage(noPerms);
        }

        if (!(args.length >= 2)) {
            sender.sendMessage(giveluckyblockUsage);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(notAPlayer);
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (Exception e) {
            sender.sendMessage(notANumber);
            return true;
        }

        ItemStack luckyblock = LuckyBlockManager.generateLuckyBlock();
        luckyblock.setAmount(amount);
        target.getInventory().addItem(luckyblock);
        target.sendMessage(givenLuckyBlocks.replaceAll("%amount%", String.valueOf(amount)));
        return true;
    }
}
