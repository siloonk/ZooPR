package me.sildev.zoopr.eco.cmds;

import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class balCMD implements CommandExecutor {

    String balanceSelf = Messages.get("balanceSelf");
    String balanceOther = Messages.get("balanceOther");
    String onlyPlayersCanExecuteThisCommand = Messages.get("onlyPlayersCanExecuteThisCommand");
    String playerIsNotOnline = Messages.get("playerIsNotOnline");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("bal")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Double money = EconomyManager.getMoneyOfUser(player);
                    String message = balanceSelf.replaceAll("%amount%", formatNumber.coolFormat(money, 0));
                    player.sendMessage(message);
                    return true;
                } else {
                    sender.sendMessage(onlyPlayersCanExecuteThisCommand);
                    return true;
                }
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    Double money = EconomyManager.getMoneyOfUser(target);
                    String message = balanceOther.replaceAll("%amount%", formatNumber.coolFormat(money, 0));
                    target.sendMessage(message);
                    return true;
                } else {
                    sender.sendMessage(playerIsNotOnline);
                    return true;
                }
            }
        }
        return false;
    }
}
