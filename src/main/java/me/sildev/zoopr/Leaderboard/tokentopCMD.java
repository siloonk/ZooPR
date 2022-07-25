package me.sildev.zoopr.Leaderboard;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class tokentopCMD implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("tokentop"))
            return false;

        Map<UUID, Double> leaderboard = ZooPR.getLeaderboard().getMoneyLeaderboard();

        AtomicInteger iteration = new AtomicInteger(1);
        sender.sendMessage(coloredString.color("&8&m----&r &dToken Top &8&m----&r"));
        sender.sendMessage("");
        leaderboard.forEach((key, value) -> {
            if (iteration.get() < 10) {
                sender.sendMessage(coloredString.color("&d" + iteration + " " + Bukkit.getOfflinePlayer(key).getName() + " " + formatNumber.coolFormat(value, 0) + " Tokens"));
                iteration.addAndGet(1);
            }else
                return;
        });
        sender.sendMessage(" ");
        sender.sendMessage(coloredString.color("&8&m----&r &dToken Top &8&m----&r"));

        return true;
    }
}
