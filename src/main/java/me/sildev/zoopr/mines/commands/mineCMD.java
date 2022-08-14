package me.sildev.zoopr.mines.commands;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.essentials.ui.mainMineMenu;
import me.sildev.zoopr.mines.Mine;
import me.sildev.zoopr.mines.MineManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class mineCMD implements CommandExecutor {

    String onlyPlayersCanExecuteThisCommand = Messages.get("onlyPlayersCanExecuteThisCommand");
    String noPerms = Messages.get("noPermission");
    String notANumber = Messages.get("NotANumber");


    Location pos1;
    Location pos2;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("mine"))
            return false;

        if (!(sender instanceof Player)) {
            sender.sendMessage(onlyPlayersCanExecuteThisCommand);
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            mainMineMenu.open((Player) sender);
            return true;
        }

        // mine pos1
        // mine pos2
        // mine create name resetDelay (in seconds)
        if (!sender.hasPermission("zoopr.createmine")) {
            sender.sendMessage(noPerms);
            return true;
        }


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("pos1")) {
                pos1 = player.getLocation();
                player.sendMessage(coloredString.color("&dZooPR &8| &7 pos1 has been set to " + pos1.getBlockX() + " " + pos1.getBlockY() + " " + pos1.getBlockZ() + "!"));
                return true;
            } else if (args[0].equalsIgnoreCase("pos2")) {
                pos2 = player.getLocation();
                player.sendMessage(coloredString.color("&dZooPR &8| &7 pos2 has been set to " + pos2.getBlockX() + " " + pos2.getBlockY() + " " + pos2.getBlockZ() + "!"));
                return true;
            }
        } else if (args.length >= 3) {
            if (pos1 != null && pos2 != null) {
                if (MineManager.mines.containsKey(args[1])) return true;
                if (!player.getInventory().getItemInMainHand().getType().isBlock()) return true;
                long resetTimer = 0;
                try {
                    resetTimer = Long.parseLong(args[2]);
                } catch (Exception e) {
                    sender.sendMessage(notANumber);
                    return true;
                }

                List<Material> blocks = new ArrayList<Material>() {{
                    add(player.getInventory().getItemInMainHand().getType());
                }};
                Mine mine = new Mine(pos1, pos2, blocks, args[1], resetTimer);
                ZooPR.getMineManager().addMine(args[1], mine);
                ZooPR.getMineManager().saveMines();
            }
        }

        return true;
    }
}
