package me.sildev.zoopr.pouches;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class pouchCMD implements CommandExecutor {

    String pouchUsage = Messages.get("pouchUsage");
    String noPerms = Messages.get("noPermission");
    String playerIsNotOnline = Messages.get("playerIsNotOnline");
    String notANumber = Messages.get("NotANumber");
    String somethingWentWrong = Messages.get("somethingWentWrongWhileGivingPouch");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("pouch")) return false;


        // Check the arguments of the command!

        // /pouch reload
        if (args.length < 4) {
            if (args.length != 1) { sender.sendMessage(pouchUsage); return true; }
            if (!sender.hasPermission("zoopr.pouch.reload")) {sender.sendMessage(noPerms); return true; }
            pouchManager.reloadPouchesConfig();
            sender.sendMessage("Pouches have been reloaded!");
            return true;
        }

        // /pouch give (player) (type) (tier)
        if (!args[0].equalsIgnoreCase("give")) {sender.sendMessage(pouchUsage); return true; }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) { sender.sendMessage(playerIsNotOnline); return true; }

        String type = args[2];

        int tier = 0;
        try {
            tier = Integer.parseInt(args[3]);
        } catch (Exception e) {
            sender.sendMessage(notANumber);
            return true;
        }
        ItemStack pouch = pouchManager.generatePouch(tier, type);
        if (pouch == null) { sender.sendMessage(somethingWentWrong); return true; }

        target.getInventory().addItem(pouch);

        return true;
    }
}
