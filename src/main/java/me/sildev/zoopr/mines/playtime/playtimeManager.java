package me.sildev.zoopr.mines.playtime;

import me.sildev.zoopr.ZooPR;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;

public class playtimeManager implements Listener {

    public static NamespacedKey playtime = new NamespacedKey(ZooPR.getPlugin(), "player-playtime");
    public static NamespacedKey timeBetween = new NamespacedKey(ZooPR.getPlugin(), "player-time-between");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Date date = new Date();
        PersistentDataContainer container = e.getPlayer().getPersistentDataContainer();
        container.set(timeBetween, PersistentDataType.LONG, date.getTime());
        if (container.has(playtime, PersistentDataType.LONG)) return;

        container.set(playtime, PersistentDataType.LONG, 0L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        System.out.println(getPlaytime(e.getPlayer()));
        e.getPlayer().getPersistentDataContainer().set(playtime, PersistentDataType.LONG, getPlaytime(e.getPlayer()));
        e.getPlayer().getPersistentDataContainer().remove(timeBetween);
    }

    public static long getPlaytime(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();

        // Get the current stored playtime;
        long pt = container.get(playtime, PersistentDataType.LONG);

        // Get the time between!
        Date date = new Date();
        long tb = date.getTime() - container.get(timeBetween, PersistentDataType.LONG);

        long actualPlaytime = pt + tb;

        return actualPlaytime;
    }
}
