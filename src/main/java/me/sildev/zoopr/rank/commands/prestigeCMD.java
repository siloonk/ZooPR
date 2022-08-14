package me.sildev.zoopr.rank.commands;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class prestigeCMD implements CommandExecutor {

    String notZYet = Messages.get("notRankZYet");
    String notEnoughmoney = Messages.get("notEnoughMoneyToPrestige");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("prestige")) return false;
        if (!(sender instanceof Player)) {sender.sendMessage("Only players can execute this command!"); return true;}
        Player player = (Player) sender;

        PersistentDataContainer container = player.getPersistentDataContainer();
        int rank = container.get(rankupManager.rankup, PersistentDataType.INTEGER);
        int prestige = container.get(rankupManager.prestige, PersistentDataType.INTEGER);
        if (!(rank == 25)) {
            String message = notZYet.replaceAll("%rank%", rankupManager.ranks[rank]);
            player.sendMessage(message);
            return true;
        }
        double cost = container.get(rankupManager.prestigeCost, PersistentDataType.DOUBLE);
        double balance = EconomyManager.getMoneyOfUser(player);

        String message = notEnoughmoney.replaceAll("%remaining%", formatNumber.coolFormat(cost-balance, 0));
        if (balance < cost) {player.sendMessage(message); return true; }
        EconomyManager.addMoneyToUser(player, -cost);
        container.set(rankupManager.prestigeCost, PersistentDataType.DOUBLE, cost * rankupManager.prestigeCostMultiplier);

        container.set(rankupManager.rankup, PersistentDataType.INTEGER, 0);
        container.set(rankupManager.prestige, PersistentDataType.INTEGER, prestige + 1);
        double multiplier = (prestige + 1) * rankupManager.rankupCostMultiplier;
        double rankupCost = container.get(rankupManager.rankupCost, PersistentDataType.DOUBLE);
        container.set(rankupManager.rankupCost, PersistentDataType.DOUBLE, rankupManager.StartRankupCost * multiplier);
        double prestigePoints = container.get(rankupManager.prestigePoints, PersistentDataType.DOUBLE);
        container.set(rankupManager.prestigePoints, PersistentDataType.DOUBLE, prestigePoints + 1);

        // Sending the message
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&8&m---------------------------------------------------"));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color(""));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&d      " + player.getName() + "&7 Has prestiged to prestige &d" +(prestige + 1)));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color(""));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&8&m---------------------------------------------------"));

        return true;
    }
}
