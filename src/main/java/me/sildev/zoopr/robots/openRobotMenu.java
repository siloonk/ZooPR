package me.sildev.zoopr.robots;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class openRobotMenu implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();
        PersistentDataContainer container = entity.getPersistentDataContainer();
        if (!container.has(RobotManager.owner, PersistentDataType.STRING))
            return;

        if (!container.get(RobotManager.owner, PersistentDataType.STRING).equals(player.getUniqueId().toString()))
            return;

        RobotGUI.open(entity, player);

        e.setCancelled(true);
    }
}
