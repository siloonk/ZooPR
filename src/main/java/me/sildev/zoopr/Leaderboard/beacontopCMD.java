package me.sildev.zoopr.Leaderboard;

import me.sildev.zoopr.Gang.Gang;
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

public class beacontopCMD implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("beacontop"))
            return false;

        Map<Gang, Integer> leaderboard = ZooPR.getLeaderboard().getGangsLeaderboard();

        AtomicInteger iteration = new AtomicInteger(1);
        sender.sendMessage(coloredString.color("&8&m----&r &dGang Top &8&m----&r"));
        sender.sendMessage("");
        leaderboard.forEach((key, value) -> {
            if (iteration.get() < 10) {
                sender.sendMessage(coloredString.color("&d" + iteration + " " + key.getName() + " " + formatNumber.coolFormat(value, 0) + " level"));
                iteration.addAndGet(1);
            }else
                return;
        });
        sender.sendMessage(" ");
        sender.sendMessage(coloredString.color("&8&m----&r &dGang Top &8&m----&r"));

        return true;
    }
}
