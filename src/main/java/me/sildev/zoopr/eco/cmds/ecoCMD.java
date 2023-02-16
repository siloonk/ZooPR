package me.sildev.zoopr.eco.cmds;

import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.formatNumber;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ecoCMD implements CommandExecutor {


    String ecoUsage = Messages.get("ecoUsage");
    String noPerms = Messages.get("noPermission");
    String notAPlayer = Messages.get("playerIsNotOnline");
    String receivedMoney = Messages.get("receivedCurrency");
    String gaveMoney = Messages.get("gaveMoney");
    String notANumber = Messages.get("NotANumber");
    String currencyTakenFromAccount = Messages.get("currencyTakenFromAccount");
    String currencySetOfAccount = Messages.get("currencySetOfAccount");
    String setCurrencyOfOther = Messages.get("setCurrencyOfOther");
    String takeCurrencyOfOther = Messages.get("takeCurrencyOfOther");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("eco")) return false;

        if (args.length == 0) {
            sender.sendMessage(ecoUsage);
            return true;
        }

        // Eco give (player) (money/tokens) (amount)
        // Eco take (player) (money/tokens) (amount)
        // Eco set (Player) (money/tokens) (amount)

        if (args.length != 4) {
            sender.sendMessage(ecoUsage);
            return true;
        }

        if (!sender.hasPermission("zoopr.eco")) {
            sender.sendMessage(noPerms);
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(notAPlayer);
            return true;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(args[3]);
        } catch (Exception e) {
            sender.sendMessage(notANumber);
            return true;
        }



        if (args[0].equalsIgnoreCase("give")) {
            if (args[2].equalsIgnoreCase("money"))
                EconomyManager.addMoneyToUser(target, amount);
            if (args[2].equalsIgnoreCase("tokens"))
                EconomyManager.addTokensToUser(target, amount);
            if (args[2].equalsIgnoreCase("beacons"))
                EconomyManager.addBeaconsToUser(target, amount);
            String currency = args[2];


            if (!(currency.equalsIgnoreCase("money") || currency.equalsIgnoreCase("tokens") || currency.equalsIgnoreCase("beacons"))) {
                sender.sendMessage(ecoUsage);
                return true;
            }


            sender.sendMessage(gaveMoney
                    .replaceAll("%target%", target.getName())
                    .replaceAll("%amount%", formatNumber.coolFormat(amount, 0))
                    .replaceAll("%currency%", currency.toLowerCase()));
            target.sendMessage(receivedMoney
                    .replaceAll("%amount%", formatNumber.coolFormat(amount, 0))
                    .replaceAll("%currency%", currency.toLowerCase()));
            return true;
        }

        if (args[0].equalsIgnoreCase("take")) {
            if (args[2].equalsIgnoreCase("money"))
                EconomyManager.addMoneyToUser(target, -amount);
            if (args[2].equalsIgnoreCase("tokens"))
                EconomyManager.addTokensToUser(target, -amount);
            if (args[2].equalsIgnoreCase("beacons"))
                EconomyManager.addBeaconsToUser(target, -amount);

            String currency = args[2];


            if (!(currency.equalsIgnoreCase("money") || currency.equalsIgnoreCase("tokens") || currency.equalsIgnoreCase("beacons"))) {
                sender.sendMessage(ecoUsage);
                return true;
            }


            sender.sendMessage(takeCurrencyOfOther
                    .replaceAll("%target%", target.getName())
                    .replaceAll("%amount%", formatNumber.coolFormat(amount, 0))
                    .replaceAll("%currency%", currency.toLowerCase()));
            target.sendMessage(currencyTakenFromAccount
                    .replaceAll("%amount%", formatNumber.coolFormat(amount, 0))
                    .replaceAll("%currency%", currency.toLowerCase()));
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args[2].equalsIgnoreCase("money"))
                EconomyManager.setMoneyOfUser(target, amount);
            if (args[2].equalsIgnoreCase("tokens"))
                EconomyManager.setTokensOfUser(target, amount);
            if (args[2].equalsIgnoreCase("beacons"))
                EconomyManager.setBeaconsOfUser(target, amount);

            String currency = args[2];


            if (!(currency.equalsIgnoreCase("money") || currency.equalsIgnoreCase("tokens") || currency.equalsIgnoreCase("beacons"))) {
                sender.sendMessage(ecoUsage);
                return true;
            }


            sender.sendMessage(setCurrencyOfOther
                    .replaceAll("%target%", target.getName())
                    .replaceAll("%amount%", formatNumber.coolFormat(amount, 0))
                    .replaceAll("%currency%", currency.toLowerCase()));
            target.sendMessage(currencySetOfAccount
                    .replaceAll("%amount%", formatNumber.coolFormat(amount, 0))
                    .replaceAll("%currency%", currency.toLowerCase()));
            return true;
        }

        sender.sendMessage(ecoUsage);
        return true;
    }
}
