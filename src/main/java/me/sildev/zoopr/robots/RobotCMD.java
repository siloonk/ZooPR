package me.sildev.zoopr.robots;

import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.addLore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class RobotCMD implements CommandExecutor {

    String robotUsage = Messages.get("robotUsage");
    String notAPlayer = Messages.get("playerIsNotOnline");
    String notANumber = Messages.get("NotANumber");
    String noPerms = Messages.get("noPermission");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("robot"))
            return false;
        // /robot give (player) (level) (type) (amount)


        if (!sender.hasPermission("zoopr.robot.give")) {
            sender.sendMessage(noPerms);
            return true;
        }

        // Check if the amount of argument matches up
        if (!(args.length >= 5)) {
            sender.sendMessage(robotUsage);
            return true;
        }

        // Check if the given argument is give
        if (!args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(robotUsage);
            return true;
        }

        // Check if the given argument is a player
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(notAPlayer);
            return true;
        }

        int level;
        int amount;

        try {
            level = Integer.parseInt(args[2]);
            amount = Integer.parseInt(args[4]);
        } catch (Exception e) {
            sender.sendMessage(notANumber);
            return true;
        }

        String type = args[3];
        if (!(type.equals("TOKENS") || type.equals("MONEY")))
            return true;

        StringBuilder typeString = new StringBuilder(type.toLowerCase());
        String character = String.valueOf(typeString.charAt(0));
        typeString.setCharAt(0, character.toUpperCase().toCharArray()[0]);
        ItemStack robotItem = new ItemStack(RobotManager.RobotType);
        ItemMeta meta = robotItem.getItemMeta();
        meta.setDisplayName(RobotManager.RobotItemName.replaceAll("%type%", typeString.toString()));

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(RobotManager.level, PersistentDataType.INTEGER, level);
        container.set(RobotManager.owner, PersistentDataType.STRING, target.getUniqueId().toString());
        container.set(RobotManager.type, PersistentDataType.STRING, type);
        robotItem.setItemMeta(meta);
        robotItem = addLore.addRobotLore(robotItem);
        robotItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        robotItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        for (int i = 0; i < amount; i++) {
            target.getInventory().addItem(robotItem);
        }

        return true;
    }
}
