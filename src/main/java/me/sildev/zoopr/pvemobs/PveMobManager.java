package me.sildev.zoopr.pvemobs;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;


public class PveMobManager implements Listener {

    public static NamespacedKey spawnerType = new NamespacedKey(ZooPR.getPlugin(), "spawner-type");


    String receivedBeacons = Messages.get("receivedBeacons");


    public static ItemStack getSpawner(String type) {
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        ItemMeta meta = spawner.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();


        if (type.equalsIgnoreCase("zombie") || type.equalsIgnoreCase("skeleton") || type.equalsIgnoreCase("spider") || type.equalsIgnoreCase("wither_skeleton"))
            container.set(spawnerType, PersistentDataType.STRING, type.toUpperCase());
        else
            return null;

        meta.setDisplayName(coloredString.color("&d" + type.toLowerCase() + "&d Spawner"));
        spawner.setItemMeta(meta);
        return spawner;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        PersistentDataContainer itemContainer = item.getItemMeta().getPersistentDataContainer();

        if (!itemContainer.has(spawnerType, PersistentDataType.STRING))
            return;

        CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
        spawner.setSpawnCount(3);

        Block block = e.getBlock();
        TileState state = (TileState) block.getState();
        PersistentDataContainer blockContainer = state.getPersistentDataContainer();
        blockContainer.set(spawnerType, PersistentDataType.STRING, itemContainer.get(spawnerType, PersistentDataType.STRING));
        state.update();
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent e) {
        Block block = e.getSpawner().getBlock();
        TileState state = (TileState) block.getState();
        PersistentDataContainer container = state.getPersistentDataContainer();
        if (!container.has(spawnerType, PersistentDataType.STRING))
            return;

        CreatureSpawner spawner = e.getSpawner();

        String type = container.get(spawnerType, PersistentDataType.STRING);

        Entity entity = e.getEntity();

        if (type.equalsIgnoreCase("zombie")) {
            Location loc = e.getLocation();
            e.getEntity().remove();

            Zombie zombie = (Zombie) e.getLocation().getWorld().spawnEntity(loc, EntityType.ZOMBIE);
            zombie.setMaxHealth(25);
            zombie.setHealth(zombie.getHealth());
            zombie.setCustomName(coloredString.color("&d" + type.toLowerCase() + " &c❤ &7" + zombie.getHealth()));
            zombie.setCustomNameVisible(true);
            zombie.setShouldBurnInDay(false);
        }

        if (type.equalsIgnoreCase("skeleton")) {
            Location loc = e.getLocation();
            e.getEntity().remove();

            Skeleton zombie = (Skeleton) e.getLocation().getWorld().spawnEntity(loc, EntityType.SKELETON);
            zombie.setMaxHealth(25);
            zombie.setHealth(zombie.getHealth());
            zombie.setCustomName(coloredString.color("&d" + type.toLowerCase() + " &c❤ &7" + zombie.getHealth()));
            zombie.setCustomNameVisible(true);
            zombie.setShouldBurnInDay(false);
        }

        if (type.equalsIgnoreCase("spider")) {
            Location loc = e.getLocation();
            e.getEntity().remove();

            Spider zombie = (Spider) e.getLocation().getWorld().spawnEntity(loc, EntityType.SPIDER);
            zombie.setMaxHealth(25);
            zombie.setHealth(zombie.getHealth());
            zombie.setCustomName(coloredString.color("&d" + type.toLowerCase() + " &c❤ &7" + zombie.getHealth()));
            zombie.setCustomNameVisible(true);
        }

        if (type.equalsIgnoreCase("wither_skeleton")) {
            Location loc = e.getLocation();
            e.getEntity().remove();

            WitherSkeleton zombie = (WitherSkeleton) e.getLocation().getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
            zombie.setHealth(zombie.getHealth());
            zombie.setCustomName(coloredString.color("&d" + type.toLowerCase() + " &c❤ &7" + zombie.getHealth()));
            zombie.setCustomNameVisible(true);
            zombie.setShouldBurnInDay(false);
        }
    }

    @EventHandler
    public void onMobKill(EntityDamageByEntityEvent e) {
        Entity ent = e.getEntity();
        if (!(ent.getType() == EntityType.SKELETON || ent.getType() == EntityType.SPIDER || ent.getType() == EntityType.WITHER_SKELETON || ent.getType() == EntityType.ZOMBIE))
            return;

        LivingEntity entity = (LivingEntity) e.getEntity();
        Entity attacker = e.getDamager();

        if (entity.getHealth() <= e.getDamage()) {
            if (!(attacker instanceof Player))
                return;

            Player player = (Player) attacker;
            if (e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.SKELETON || e.getEntityType() == EntityType.WITHER_SKELETON || e.getEntityType() == EntityType.SPIDER) {
                Random rd = new Random();
                int amountOfBeacons = rd.nextInt(10);
                if (amountOfBeacons == 0) return;
                EconomyManager.addBeaconsToUser(player, amountOfBeacons);
                String message = receivedBeacons.replaceAll("%amount%", String.valueOf(amountOfBeacons));
                player.sendMessage(message);
                return;
            }
        }
        if (entity.getType() == EntityType.SKELETON || entity.getType() == EntityType.SPIDER || entity.getType() == EntityType.WITHER_SKELETON || entity.getType() == EntityType.ZOMBIE)
            entity.setCustomName(coloredString.color("&d" + entity.getType().toString().toLowerCase() + " &c❤ &7" + Math.round(entity.getHealth() - e.getDamage())));
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        e.getDrops().clear();
        e.setDroppedExp(0);
    }

}