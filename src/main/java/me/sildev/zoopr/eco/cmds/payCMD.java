package me.sildev.zoopr.eco.cmds;

import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class payCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String notANumber = Messages.get("NotANumber");
        String playerIsNotOnline = Messages.get("playerIsNotOnline");
        String insufficientFunds = Messages.get("insufficientFunds");
        String youPaid = Messages.get("youPaid");
        String youGotPaid = Messages.get("youGotPaid");
        String onlyPlayersCanExecuteThisCommand = Messages.get("onlyPlayersCanExecuteThisCommand");
        String usage = Messages.get("payUsage");

        if (command.getName().equalsIgnoreCase("pay")) {
            if (args.length >= 2) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(playerIsNotOnline);
                        return true;
                    }
                    double playerBalance = EconomyManager.getMoneyOfUser(player);
                    double targetBalance = EconomyManager.getMoneyOfUser(target);
                    double amount = 0d;
                    try {
                        amount = Double.parseDouble(args[1]);
                    } catch (Exception e) {
                        player.sendMessage(notANumber);
                        return true;
                    }
                    if (amount > 0) {
                        if (playerBalance <= amount) {
                            player.sendMessage(insufficientFunds);
                            return true;
                        }

                        EconomyManager.addMoneyToUser(player, -amount);
                        EconomyManager.addMoneyToUser(target, amount);
                        String message = youPaid.replaceAll("%amount%", formatNumber.coolFormat(amount, 0));
                        message = message.replaceAll("%target%", target.getName());
                        player.sendMessage(message);
                        message = youGotPaid.replaceAll("%amount%", formatNumber.coolFormat(amount, 0));
                        message = message.replaceAll("%target%", player.getName());
                        target.sendMessage(message);
                        return true;
                    }
                } else {
                    sender.sendMessage(onlyPlayersCanExecuteThisCommand);
                    return true;
                }
            } else {
                sender.sendMessage(usage);
                return true;
            }
        }

        return false;
    }
}
