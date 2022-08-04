package me.sildev.zoopr.Pets;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class petCMD implements CommandExecutor {

    String onlyPlayersCanDoThis = Messages.get("onlyPlayersCanExecuteThisCommand");
    String petUsage = Messages.get("petUsage");
    String noPerms = Messages.get("noPermission");
    String notAPlayer = Messages.get("playerIsNotOnline");
    String notAValidType = Messages.get("NotAValidType");
    String notAValidTier = Messages.get("NotAValidTier");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("pet")) return false;
        if (args.length == 0) {
            if (!(sender instanceof Player)) { sender.sendMessage(onlyPlayersCanDoThis); return true; }
            petMenu menu = new petMenu();
            menu.open((Player) sender);
            return true;
        }

        // /pet give (player) (type) (tier)
        if (args.length == 4) {
            if (!args[0].equalsIgnoreCase("give")) {
                sender.sendMessage(petUsage);
                return true;
            }

            if (!sender.hasPermission("zoopr.givepet")) {
                sender.sendMessage(noPerms);
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(notAPlayer);
                return true;
            }

            PetType type = null;
            try {
                type = PetType.valueOf(args[2]);
            } catch (Exception e) {
                sender.sendMessage(notAValidType);
                return true;
            }
            PetTier tier = null;
            try {
                tier = PetTier.valueOf(args[3].toUpperCase());
            } catch (Exception e) {
                sender.sendMessage(notAValidTier);
                return true;
            }
            Pet pet = new Pet(target, tier, type);
            petManager.addPet(target, pet);
            sender.sendMessage("Added pet to " + target.getName());
            target.sendMessage("Received pet!!");
            return true;
        } else {
            sender.sendMessage(petUsage);
            return true;
        }
    }
}
