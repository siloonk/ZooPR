package me.sildev.zoopr.pvemobs;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class cancelMobSpawning implements Listener {

    @EventHandler
    public void onEntitySpawnEvent(CreatureSpawnEvent event) {
        if (!(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            if (!event.getEntity().getType().equals(EntityType.DROPPED_ITEM) || event.getEntity().getType().equals(EntityType.PLAYER))
                event.setCancelled(true);
        }
    }

}
