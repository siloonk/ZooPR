package me.sildev.zoopr.shops;

import me.sildev.zoopr.ZooPR;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class shopCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("shop")) return false;
        if (!(sender instanceof Player)) return true;

        if (args.length == 0) {
            Inventory gui = ZooPR.getShopManager().loadIventory("regular");

            ((Player) sender).openInventory(gui);
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            ZooPR.getShopManager().reloadShops();
        }

        return true;
    }
}
