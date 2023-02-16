package me.sildev.zoopr.Gang.commands;

import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.Gang.Gang;
import me.sildev.zoopr.Gang.GangManager;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class gangCMD implements CommandExecutor {

    String usageCreate = Messages.get("usageCreate");
    String usageInvite = Messages.get("usageInvite");
    String usageAccept = Messages.get("usageAccept");
    String usageLeave = Messages.get("usageLeave");
    String createGang = Messages.get("createGang");
    String needAGang = Messages.get("needAGang");
    String notInAGang = Messages.get("notInAGang");
    String unableToInviteSelf = Messages.get("unableToInviteSelf");
    String notInvited = Messages.get("notInvited");
    String alreadyInAGang = Messages.get("alreadyInAGang");
    String cantLeaveOwnGang = Messages.get("cantLeaveOwnGang");
    String sucessullyLeftGang = Messages.get("successfullyLeftGang");
    String succesfullyInvited = Messages.get("successfullyInvited");
    String haveBeenInvited = Messages.get("haveBeenInvited");
    String failedToCreateAGang = Messages.get("failedToCreateAGang");
    String playerJoinedTheGang = Messages.get("playerJoinedGang");

    static NamespacedKey gang = new NamespacedKey(ZooPR.getPlugin(), "gang");
    static NamespacedKey invited = new NamespacedKey(ZooPR.getPlugin(), "invitation");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("gang")) return false;
        if (!(sender instanceof Player)) { sender.sendMessage("Only players can execute this command!"); return true; }
        Player player = (Player) sender;
        if (!(args.length >= 1)) {
            PersistentDataContainer container = player.getPersistentDataContainer();
            if (!container.has(gang, PersistentDataType.STRING)) {player.sendMessage(needAGang);return true;}
            String g =  container.get(gang, PersistentDataType.STRING);

            Gang gangOBJ = GangManager.loadGang(g);

            StringBuilder builder = new StringBuilder();
            for (OfflinePlayer member : gangOBJ.getMembers()) {
                builder.append(member.getName() + ", ");
            }

            player.sendMessage(coloredString.color("&8&m-----&r " + "&d"+ g + " &8&m-----&r"));
            player.sendMessage("");
            player.sendMessage(coloredString.color(" &8- &dOwner &7" + gangOBJ.getOwner().getName()));
            player.sendMessage(coloredString.color(" &8- &dMembers &7" + builder.toString()));
            player.sendMessage(coloredString.color(" &8- &dLevel &7" + gangOBJ.getLevel()));
            player.sendMessage("");
            player.sendMessage(coloredString.color("&8&m-----&r " + "&d"+ g + " &8&m-----&r"));
            return true;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (!(args.length > 1)) { player.sendMessage(usageCreate); return true; }
            boolean hasCreated = GangManager.createGang(player, args[1]);
            String message = createGang.replaceAll("%name%", args[1]);
            if (hasCreated) { sender.sendMessage(message); }
            else sender.sendMessage(coloredString.color(failedToCreateAGang));
            return true;
        }

        if (args[0].equalsIgnoreCase("invite")) {
            if (!(args.length > 1)) { player.sendMessage(usageInvite); return true; }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) { player.sendMessage(usageInvite); return true; }
            if (target.equals(player)) {player.sendMessage(unableToInviteSelf);}
            PersistentDataContainer targetContainer = target.getPersistentDataContainer();
            PersistentDataContainer playerContainer = player.getPersistentDataContainer();
            if (!playerContainer.has(gang, PersistentDataType.STRING)) { player.sendMessage(needAGang); return true; }
            String g = playerContainer.get(gang, PersistentDataType.STRING);

            targetContainer.set(invited, PersistentDataType.STRING, g);
            String message = succesfullyInvited.replaceAll("%target%", target.getName());
            player.sendMessage(message);
            message = haveBeenInvited.replaceAll("%name%", g);
            target.sendMessage(message);
        }

        if (args[0].equalsIgnoreCase("accept")) {
            if (!(args.length > 0)) {player.sendMessage(usageAccept);}
            PersistentDataContainer container = player.getPersistentDataContainer();
            if (!container.has(invited, PersistentDataType.STRING)) { player.sendMessage(notInvited); return true; }
            if (container.has(gang, PersistentDataType.STRING)) {player.sendMessage(alreadyInAGang);return true;}
            container.set(gang, PersistentDataType.STRING, container.get(invited, PersistentDataType.STRING));
            Gang g = GangManager.loadGang(container.get(invited, PersistentDataType.STRING));
            g.addMember(player);
            for (OfflinePlayer member : g.getMembers()) {
                if (member.isOnline()) {
                    Player mem = Bukkit.getPlayer(member.getName());
                    String message = playerJoinedTheGang.replaceAll("%target%", player.getName());
                    mem.sendMessage(message);
                }
            }
            container.remove(invited);
            container.set(gang, PersistentDataType.STRING, g.getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("leave")) {
            if (!(args.length > 0)) {player.sendMessage(usageLeave);}
            PersistentDataContainer container = player.getPersistentDataContainer();
            if (!container.has(gang, PersistentDataType.STRING)) { player.sendMessage(notInAGang); return true; }
            String g = container.get(gang, PersistentDataType.STRING);
            Gang gangOBJ = GangManager.loadGang(g);

            if (gangOBJ.getOwner().equals(player)) { player.sendMessage(cantLeaveOwnGang); return true; }
            List<OfflinePlayer> members = gangOBJ.getMembers();
            members.remove(player);
            gangOBJ.setMembers(members);
            container.remove(gang);
            player.sendMessage(sucessullyLeftGang);
            return true;
        }

        if (args[0].equalsIgnoreCase("reset")) {
            player.getPersistentDataContainer().remove(gang);
        }

        return true;
    }
}
