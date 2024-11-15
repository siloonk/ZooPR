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

public class baltopCMD implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("baltop"))
            return false;

        Map<UUID, Integer> leaderboard = ZooPR.getLeaderboard().getBlocksLeaderboard();

        AtomicInteger iteration = new AtomicInteger(1);
        sender.sendMessage(coloredString.color("&8&m----&r &dBal Top &8&m----&r"));
        sender.sendMessage("");
        leaderboard.forEach((key, value) -> {
            if (iteration.get() < 10) {
                sender.sendMessage(coloredString.color("&d" + iteration + " " + Bukkit.getOfflinePlayer(key).getName() + " $" + formatNumber.coolFormat(value, 0)));
                iteration.addAndGet(1);
            }else
                return;
        });
        sender.sendMessage(" ");
        sender.sendMessage(coloredString.color("&8&m----&r &dBal Top &8&m----&r"));

        return true;
    }
}
