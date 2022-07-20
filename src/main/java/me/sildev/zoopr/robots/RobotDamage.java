package me.sildev.zoopr.robots;

import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RobotDamage implements Listener {

    String cantDamageRobot = Messages.get("CantDamageRobot");

    @EventHandler
    public void onInteract(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof ArmorStand))
            return;

        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();
        if (!container.has(RobotManager.owner, PersistentDataType.STRING))
            return;

        e.getDamager().sendMessage(cantDamageRobot);
        e.setCancelled(true);
    }
}
