package me.sildev.zoopr.bombs;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.bombs.tasks.NukeTask;
import me.sildev.zoopr.bombs.tasks.blackHoleTask;
import me.sildev.zoopr.eco.SellBlocks;
import me.sildev.zoopr.utils.loops;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.N;

import java.util.List;
import java.util.UUID;


public class bombInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();

            if (!container.has(bombManager.type))
                return;

            // It is a bomb
            Location loc = e.getPlayer().getLocation();
            TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);

            PersistentDataContainer tntContainer = tnt.getPersistentDataContainer();
            tntContainer.set(bombManager.type, PersistentDataType.STRING, container.get(bombManager.type, PersistentDataType.STRING));
            tntContainer.set(bombManager.tier, PersistentDataType.INTEGER, container.get(bombManager.tier, PersistentDataType.INTEGER));
            tntContainer.set(new NamespacedKey(ZooPR.getPlugin(), "owner"), PersistentDataType.STRING, e.getPlayer().getUniqueId().toString());

            Player p = e.getPlayer();
            Vector vector = new Vector(loc.getDirection().getX(), loc.getDirection().getY(), loc.getDirection().getZ());
            vector.normalize();
            tnt.setVelocity(vector);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (!(e.getEntityType() == EntityType.PRIMED_TNT))
            return;

        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();
        if (!container.has(bombManager.type, PersistentDataType.STRING))
            return;

        String type = container.get(bombManager.type, PersistentDataType.STRING);
        int tier = container.get(bombManager.tier, PersistentDataType.INTEGER);
        Player player = Bukkit.getPlayer(UUID.fromString(container.get(new NamespacedKey(ZooPR.getPlugin(), "owner"), PersistentDataType.STRING)));

        Location loc = e.getLocation();
        if (type.equals("MOAB")) {
            List<Location> blocks = loops.generateSphere(loc, tier * 2, false);
            for (Location block : blocks) {
                SellBlocks.sellBlocknoPickaxe(block.getBlock(), player);
            }
        }

        if (type.equals("NUKE")) {
            new NukeTask(player, tier, loc).runTaskTimer(ZooPR.getPlugin(), 0L, 2L);
        }

        if (type.equals("BLACKHOLE")) {
            new blackHoleTask(player, loc, tier).runTaskTimer(ZooPR.getPlugin(), 0L, 20L);
        }

        e.setCancelled(true);
    }
}
