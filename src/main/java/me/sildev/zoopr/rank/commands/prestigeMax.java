package me.sildev.zoopr.rank.commands;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.rank.prestigeMaxTask;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class prestigeMax implements CommandExecutor {

    String mustBeAPlayer = Messages.get("onlyPlayersCanExecuteThisCommand");

    public static List<BukkitTask> tasks = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!command.getName().equalsIgnoreCase("prestigemax")) {return false;}
        if (!(sender instanceof Player)) { sender.sendMessage(mustBeAPlayer); return true; }
        Player player = (Player) sender;

        maxPrestige(player);

        return true;
    }

    void maxPrestige(Player player) {
        int initPrestige = player.getPersistentDataContainer().get(rankupManager.prestige, PersistentDataType.INTEGER);
        tasks.add(new prestigeMaxTask(player, initPrestige).runTaskTimerAsynchronously(ZooPR.getPlugin(), 0L, 1L));
    }
}
