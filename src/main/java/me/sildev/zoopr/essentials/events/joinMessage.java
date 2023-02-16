package me.sildev.zoopr.essentials.events;

import me.sildev.zoopr.utils.coloredString;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class joinMessage implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(coloredString.color("&7[&a+&7] &5" + e.getPlayer().getName()));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(coloredString.color("&7[&c-&7] &5" + e.getPlayer().getName()));
    }
}
