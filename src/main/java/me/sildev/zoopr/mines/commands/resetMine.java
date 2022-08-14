package me.sildev.zoopr.mines.commands;

import me.sildev.zoopr.mines.MineManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class resetMine implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("resetmine")) return false;

        String mineName = args[0];
        if (MineManager.mines.containsKey(args[0])) {
            MineManager.mines.get(args[0]).reset();
        }

        return true;
    }
}
