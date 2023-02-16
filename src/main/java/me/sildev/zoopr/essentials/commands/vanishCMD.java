package me.sildev.zoopr.essentials.commands;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class vanishCMD implements CommandExecutor {

    String onlyPlayersCanExecute = Messages.get("onlyPlayersCanExecuteThisCommand");
    String noPerms = Messages.get("noPermission");
    String vanishEnabled = Messages.get("vanishEnabled");
    String vanishDisabled = Messages.get("vanishDisabled");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("vanish")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(onlyPlayersCanExecute);
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("zoopr.vanish")) {
                player.sendMessage(noPerms);
                return true;
            }
            player.setInvisible(!player.isInvisible());

            if (player.isInvisible()) {
                player.sendMessage(vanishEnabled);
                player.setAllowFlight(true);
            } else {
                player.sendMessage(vanishDisabled);
                if (player.getGameMode() == GameMode.SURVIVAL)
                    player.setAllowFlight(false);
            }
            return true;

        }
        return false;
    }
}
