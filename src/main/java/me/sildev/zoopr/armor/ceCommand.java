package me.sildev.zoopr.armor;

import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.isArmor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ceCommand implements CommandExecutor {


    String onlyPlayersCanExecuteThisCommand = Messages.get("onlyPlayersCanExecuteThisCommand");
    String notAnArmorPiece = Messages.get("notAnArmorPiece");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("ce")) return false;

        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(onlyPlayersCanExecuteThisCommand);
            return true;
        }
        Player player = (Player) sender;

        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the piece the player is holding is armor
        if (!(isArmor.isArmor(item.getType()) || isArmor.isSword(item.getType()) || isArmor.isAxe(item.getType()))) {
            player.sendMessage(notAnArmorPiece);
            return true;
        }

        if (isArmor.isArmor(item.getType()))
            armorCeGUI.open(player, item);

        if (isArmor.isSword(item.getType()))
            swordCeGUI.open(player, item);

        if (isArmor.isAxe(item.getType()))
            swordCeGUI.open(player, item);


        return true;
    }
}
