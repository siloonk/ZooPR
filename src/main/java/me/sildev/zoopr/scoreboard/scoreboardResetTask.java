package me.sildev.zoopr.scoreboard;

import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.coloredString;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.*;

public class scoreboardResetTask implements Runnable {

    Player player;
    public scoreboardResetTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        int rank = player.getPersistentDataContainer().get(rankupManager.rankup, PersistentDataType.INTEGER);
        int prestige = player.getPersistentDataContainer().get(rankupManager.prestige, PersistentDataType.INTEGER);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective scoreboard = board.registerNewObjective("stats", "dummy");
        scoreboard.setDisplayName(coloredString.color("&dZooPR &8| &fSI"));
        scoreboard.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score emptyLine1 = scoreboard.getScore("");
        emptyLine1.setScore(15);
        Score dottedLine1 = scoreboard.getScore(coloredString.color("&7&m-----------------"));
        dottedLine1.setScore(14);
        Score playerLine = scoreboard.getScore(coloredString.color("&f - &5Player &d" + player.getName()));
        playerLine.setScore(13);
        Score balanceLine = scoreboard.getScore(coloredString.color("&f  - &5Balance &d$" + formatNumber.coolFormat(EconomyManager.getMoneyOfUser(player), 0)));
        balanceLine.setScore(12);
        Score tokensLine = scoreboard.getScore(coloredString.color("&f  - &5Tokens &d" + formatNumber.coolFormat(EconomyManager.getTokensOfUser(player), 0)));
        tokensLine.setScore(11);
        Score beaconsLine = scoreboard.getScore(coloredString.color("&f  - &5Beacons &d" + formatNumber.coolFormat(EconomyManager.getBeaconsOfUser(player), 0)));
        beaconsLine.setScore(10);
        Score rankLine = scoreboard.getScore(coloredString.color("&f  - &5Rank &d" + rankupManager.ranks[rank]));
        rankLine.setScore(9);
        Score prestigeLine = scoreboard.getScore(coloredString.color("&f  - &5Prestige &d" + prestige));
        prestigeLine.setScore(8);
        Score emptyLine2 = scoreboard.getScore(coloredString.color("&7"));
        emptyLine2.setScore(7);
        Score serverLine = scoreboard.getScore(coloredString.color("&f - &5Server &dZooPR"));
        serverLine.setScore(6);
        Score TPSLine = scoreboard.getScore(coloredString.color("&f  - &5TPS &d" + Math.round(Bukkit.getServer().getTPS()[0])));
        TPSLine.setScore(5);
        Score onlinePlayersLine = scoreboard.getScore(coloredString.color("&f  - &5Players &d" + Bukkit.getOnlinePlayers().size()));
        onlinePlayersLine.setScore(4);
        Score joinsLine = scoreboard.getScore(coloredString.color("&f  - &5Joins &d" + Bukkit.getServer().getOfflinePlayers().length));
        joinsLine.setScore(3);
        Score dottedLine2 = scoreboard.getScore(coloredString.color("&7&m------------------&r"));
        dottedLine2.setScore(2);
        Score ipLine = scoreboard.getScore(coloredString.color("&7&oZooPR.minehut.gg"));
        ipLine.setScore(1);

        player.setScoreboard(board);

    }
}
