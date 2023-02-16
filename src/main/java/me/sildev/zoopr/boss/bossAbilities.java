package me.sildev.zoopr.boss;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class bossAbilities implements Listener {


    @EventHandler
    public void onBossDeathEvent(EntityDeathEvent e) {
        if (ZooPR.getBossManager().bossEntity == null)
            return;

        if (!e.getEntity().equals(ZooPR.getBossManager().bossEntity))
            return;

        Player closestPlayer = null;
        // get closest player
        for (Player P : Bukkit.getOnlinePlayers()) {
            if (P.getWorld().equals(e.getEntity().getWorld())) {
                if (closestPlayer != null) {
                    if (P.getLocation().distance(e.getEntity().getLocation()) < closestPlayer.getLocation().distance(e.getEntity().getLocation())) {
                        closestPlayer = P;
                    }
                } else
                    closestPlayer = P;
            }
        }

        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "cc give p boss 1 " + closestPlayer.getName());
    }

    @EventHandler
    public void onBossDamageTakeEvent(EntityDamageByEntityEvent e) {
        LivingEntity damager = (LivingEntity) e.getDamager();
        LivingEntity victim = (LivingEntity) e.getEntity();

        if (ZooPR.getBossManager().bossEntity == null)
            return;

        if (!ZooPR.getBossManager().bossEntity.equals(victim))
            return;
        ZooPR.getBossManager().updateDisplayName();

        // Ability Procs
        List<String> abilities = ZooPR.getBossManager().bossConfig.getStringList("boss-abilities");
        if (abilities.toArray().length == 0)
            return;

        Random rd = new Random();
        if (rd.nextInt(100) > 20)
            return;

        int abilityIndex = rd.nextInt(abilities.size());
        System.out.println(abilities.get(abilityIndex));
        if (abilities.get(abilityIndex).equalsIgnoreCase("disarm")) {
            if (!(damager instanceof Player))
                return;

            Player player = (Player) damager;
            ItemStack chestplate = player.getInventory().getChestplate();
            if (chestplate == null)
                chestplate = player.getInventory().getItemInMainHand();

            player.getWorld().dropItem(player.getLocation(), chestplate);
            player.getInventory().remove(chestplate);

            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 10, 1);
            player.sendMessage(coloredString.color("&5&l&oZOOPR ✦ &7The boss used &dDisarm&7!"));
        }
        if (abilities.get(abilityIndex).equalsIgnoreCase("launch")) {
            Collection<Player> players = victim.getWorld().getNearbyPlayers(victim.getLocation(), 10);
            players.forEach((p) -> {
                p.spawnParticle(Particle.CLOUD, p.getLocation(), 20);
                Vector force = p.getLocation().toVector().subtract(victim.getLocation().toVector());
                force.normalize();
                force.add(new Vector(0, 0.3, 0));
                p.setVelocity(force.multiply(4));
                p.sendMessage(coloredString.color("&5&l&oZOOPR ✦ &7The boss used &dLaunch&7!"));
            });
        }
        if (abilities.get(abilityIndex).equalsIgnoreCase("nausea")) {
            victim.getWorld().getNearbyPlayers(victim.getLocation(), 7).forEach((player) -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 5));
                player.spawnParticle(Particle.ASH, player.getLocation(), 50);
                player.sendMessage(coloredString.color("&5&l&oZOOPR ✦ &7The boss used &dNausea&7!"));
            });
        }

        if (abilities.get(abilityIndex).equalsIgnoreCase("stun")) {
            victim.getWorld().getNearbyPlayers(victim.getLocation(), 5).forEach((player) -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 255));
                player.sendMessage(coloredString.color("&5&l&oZOOPR ✦ &7The boss used &dNausea&7!"));
            });
        }

        if (abilities.get(abilityIndex).equalsIgnoreCase("laser")) {
            victim.getWorld().getNearbyPlayers(victim.getLocation(), 10).forEach((player) -> {
                double distance = victim.getEyeLocation().distance(player.getEyeLocation());
                Vector start = victim.getEyeLocation().toVector();
                Vector end = player.getEyeLocation().toVector();
                Vector vec = end.clone().subtract(start).normalize().multiply(0.5);

                double length = 0;

                for (; length < distance; start.add(vec)) {
                    player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, start.getX(), start.getY(), start.getZ(), 20);
                    length += 0.5;
                }

                player.setFireTicks(300);
                player.sendMessage(coloredString.color("&5&l&oZOOPR ✦ &7The boss used &dLaser&7!"));
            });
        }

    }
}
