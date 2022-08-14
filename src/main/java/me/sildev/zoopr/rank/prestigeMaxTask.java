package me.sildev.zoopr.rank;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class prestigeMaxTask extends BukkitRunnable {

    Player player;
    PersistentDataContainer container;
    int initPrestige;


    public prestigeMaxTask(Player player, int initPrestige) {
        this.player = player;
        container = player.getPersistentDataContainer();
        this.initPrestige = initPrestige;
    }

    @Override
    public void run() {
        int rank = container.get(rankupManager.rankup, PersistentDataType.INTEGER);
        PersistentDataContainer container = player.getPersistentDataContainer();
        int prestige = container.get(rankupManager.prestige, PersistentDataType.INTEGER);
        boolean sentMessage = false;

        while (rank < 25) {
            double rankupCost = container.get(rankupManager.rankupCost, PersistentDataType.DOUBLE);
            double balance = EconomyManager.getMoneyOfUser(player);
            boolean hasEnough = true;

            if (!(balance >= rankupCost)) {
                hasEnough = false;
                if (!sentMessage)   {player.sendMessage(coloredString.color("&dzooPR &8| &7You have reached Prestige &d" + prestige + "&7 and rank &d" + rankupManager.ranks[rank])); sentMessage = true; }
                cancel();
            }

            if (hasEnough) {
                EconomyManager.addMoneyToUser(player, -rankupCost);
                container.set(rankupManager.rankup, PersistentDataType.INTEGER, rank + 1);
                container.set(rankupManager.rankupCost, PersistentDataType.DOUBLE, rankupCost * 1.5);
                rank = container.get(rankupManager.rankup, PersistentDataType.INTEGER);
            }
        }

        if (!(rank == 25)) {
           cancel();
        }
        double cost = container.get(rankupManager.prestigeCost, PersistentDataType.DOUBLE);
        double balance = EconomyManager.getMoneyOfUser(player);

        container = player.getPersistentDataContainer();
        boolean hasEnough = true;
        if (balance < cost) {
            player.sendMessage(coloredString.color("&dzooPR &8| &7You have reached Prestige &d" + prestige + "&7 and rank &d" + rankupManager.ranks[rank]));
            ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&8&m---------------------------------------------------"));
            ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color(""));
            ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&d      " + player.getName() + "&7 Has prestiged to prestige &d" +(prestige) + "&7 from prestige &d" + initPrestige));
            ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color(""));
            ZooPR.getPlugin().getServer().broadcastMessage(coloredString.color("&8&m---------------------------------------------------"));
            hasEnough = false;
            cancel();
        }
        if (hasEnough) {
            EconomyManager.addMoneyToUser(player, -cost);

            container.set(rankupManager.prestigeCost, PersistentDataType.DOUBLE, cost * rankupManager.prestigeCostMultiplier);

            container.set(rankupManager.rankup, PersistentDataType.INTEGER, 0);
            container.set(rankupManager.prestige, PersistentDataType.INTEGER, prestige + 1);
            double multiplier = (prestige + 1) * rankupManager.rankupCostMultiplier;
            double rankupCost = container.get(rankupManager.rankupCost, PersistentDataType.DOUBLE);
            container.set(rankupManager.rankupCost, PersistentDataType.DOUBLE, rankupManager.StartRankupCost * multiplier);
            container.set(rankupManager.rankupCost, PersistentDataType.DOUBLE, rankupManager.StartRankupCost * multiplier);
            double prestigePoints = container.get(rankupManager.prestigePoints, PersistentDataType.DOUBLE);
            container.set(rankupManager.prestigePoints, PersistentDataType.DOUBLE, prestigePoints + 1);
        }
    }
}
