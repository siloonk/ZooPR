package me.sildev.zoopr.rank.commands;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class rebirthCMD implements CommandExecutor {

    String notPres1000Yet = Messages.get("notPres1000Yet");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("rebirth")) return false;
        if (!(sender instanceof Player)) {sender.sendMessage("Only players can execute this command!"); return true;}
        Player player = (Player) sender;

        PersistentDataContainer container = player.getPersistentDataContainer();
        int prestige = container.get(rankupManager.prestige, PersistentDataType.INTEGER);
        int rebirth = rankupManager.getRebirthOfPlayer(player.getUniqueId());
        if (!(prestige >= 1000)) {
            String message = notPres1000Yet.replaceAll("%prestige%", String.valueOf(prestige));
            player.sendMessage(message);
            return true;
        }
        EconomyManager.setMoneyOfUser(player, 0d);

        container.set(rankupManager.rankup, PersistentDataType.INTEGER, 0);
        container.set(rankupManager.prestige, PersistentDataType.INTEGER, 0);
        container.set(rankupManager.rebirthPoints, PersistentDataType.DOUBLE, container.get(rankupManager.rebirthPoints, PersistentDataType.DOUBLE) + 1);
        container.set(rankupManager.rankupCost, PersistentDataType.DOUBLE, rankupManager.StartRankupCost);
        rankupManager.storeRebirth(player.getUniqueId(), rankupManager.getRebirthOfPlayer(player.getUniqueId()) + 1);

        // Sending the message
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&8&m---------------------------------------------------"));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color(""));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&d      " + player.getName() + "&7 Has rebirthed to rebirth &d" +(rebirth + 1)));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color(""));
        ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&8&m---------------------------------------------------"));

        return true;
    }
}
