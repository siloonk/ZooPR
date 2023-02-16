package me.sildev.zoopr.essentials.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.utils.coloredString;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class onChat implements Listener {


    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player player = e.getPlayer();
        int prestige = player.getPersistentDataContainer().get(rankupManager.prestige, PersistentDataType.INTEGER);
        int rebirth = rankupManager.getRebirthOfPlayer(e.getPlayer().getUniqueId());
        int mineRank = player.getPersistentDataContainer().get(rankupManager.rankup, PersistentDataType.INTEGER);

        String mineRankName = rankupManager.ranks[mineRank];

        String groupname = "Bad";
        if (ZooPR.getPermissionsAPI() != null)
            groupname = ZooPR.getPermissionsAPI().getPrimaryGroup(e.getPlayer());


        String chatMessage = (coloredString.color("&8[&5R" + rebirth + "&8] [&d&lP" + prestige + "&8] [&f&l" + mineRankName + "&8] " + groupname + " " + player.getDisplayName() + " &7» "));
        Component message = Component.text(chatMessage).append(e.message());

        ZooPR.getPlugin().getServer().broadcast(message);
        e.setCancelled(true);

    }
}
