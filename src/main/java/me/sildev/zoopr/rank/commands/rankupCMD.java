package me.sildev.zoopr.rank.commands;

import me.sildev.zoopr.Leaderboard.eco.EconomyManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class rankupCMD implements CommandExecutor {

    String onlyPlayersCanExecute = Messages.get("onlyPlayersCanExecuteThisCommand");
    String notEnoughMoneyToRankup = Messages.get("notEnoughToRankup");
    String alreadyAtZ = Messages.get("playerAlreadyAtZ");
    String successfullyRankedUp = Messages.get("successfullyRankedUp");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("rankup")) return false;
        if (!(sender instanceof Player)) { sender.sendMessage(onlyPlayersCanExecute); return true; }
        Player player = (Player) sender;



        PersistentDataContainer container = player.getPersistentDataContainer();
        double rankupCost = container.get(rankupManager.rankupCost, PersistentDataType.DOUBLE);
        int rank = container.get(rankupManager.rankup, PersistentDataType.INTEGER);
        double balance = EconomyManager.getMoneyOfUser(player);

        if (!(balance >= rankupCost)) {
            String message = notEnoughMoneyToRankup.replaceAll("%remaining%", formatNumber.coolFormat((rankupCost - balance), 0));
            player.sendMessage(message);
            return true;
        }
        EconomyManager.addMoneyToUser(player, -rankupCost);

        if (rank >= 25) {player.sendMessage(alreadyAtZ); return true; }

        container.set(rankupManager.rankup, PersistentDataType.INTEGER, rank + 1);
        container.set(rankupManager.rankupCost, PersistentDataType.DOUBLE, rankupCost * 1.5);
        String message = successfullyRankedUp.replaceAll("%rank%", rankupManager.ranks[rank + 1]);
        player.sendMessage(message);
        return true;
    }
}
