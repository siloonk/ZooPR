package me.sildev.zoopr.questScrolls;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class questCMD implements CommandExecutor {


    String noPerms = Messages.get("noPermission");
    String questUsage = Messages.get("questUsage");
    String playerNotOnline = Messages.get("playerIsNotOnline");
    String notANumber = Messages.get("NotANumber");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("quest")) return false;

        if (!sender.hasPermission("zoopr.quest")) {
            sender.sendMessage(noPerms);
            return true;
        }

        // /quest give (player) (integer)
        if (args.length < 3) {
            sender.sendMessage(questUsage);
            return true;
        }

        if (!args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(questUsage);
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(playerNotOnline);
            return true;
        }

        int tier = 0;
        try {
            tier = Integer.parseInt(args[2]);
        } catch (Exception e) {
            sender.sendMessage(notANumber);
            return true;
        }

        ItemStack scroll = QuestScrollManager.generateQuestScroll(tier);
        target.getInventory().addItem(scroll);

        return true;
    }
}
