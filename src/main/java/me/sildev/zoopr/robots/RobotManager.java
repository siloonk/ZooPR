package me.sildev.zoopr.robots;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;
import java.util.UUID;

public class RobotManager implements Listener {

    public static int maxLevel = 100;
    public static String RobotItemName = coloredString.color("&f&ki&d Miner Robot &f&ki");
    public static NamespacedKey level = new NamespacedKey(ZooPR.getPlugin(), "robot-level");
    public static  NamespacedKey owner = new NamespacedKey(ZooPR.getPlugin(), "robot-owner");
    public static NamespacedKey earnings = new NamespacedKey(ZooPR.getPlugin(), "robot-earnings");
    public static NamespacedKey value = new NamespacedKey(ZooPR.getPlugin(), "robot-value");
    public static NamespacedKey type = new NamespacedKey(ZooPR.getPlugin(), "robot-type");
    public static NamespacedKey lastOpened = new NamespacedKey(ZooPR.getPlugin(), "robot-last-opened");
    public static NamespacedKey upgradeCost = new NamespacedKey(ZooPR.getPlugin(), "robot-upgrade-cost");

    public static Material RobotType = Material.ARMOR_STAND;

    public static int ratePerLevel = 5;
    public static int baseRate = 10;
    public static double costMultiplier = 1.5;

    double upgradeCostStart = 50;

    // Messages
    String notYourRobot = Messages.get("NotYourRobot");
    String placedYourRobot = Messages.get("placedRobot");
    String robotNameString = Messages.get("robotName");


    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item.getType() != RobotType)
                return;

            if (!item.getItemMeta().getDisplayName().equals(RobotItemName))
                return;

                // Item is an actual robot!
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            System.out.println(container.getKeys());

            if (!container.has(level, PersistentDataType.INTEGER))
                return;
            int robotLevel = container.get(level, PersistentDataType.INTEGER);

            if (!container.has(owner, PersistentDataType.STRING))
                return;

            OfflinePlayer robotOwner = Bukkit.getOfflinePlayer(UUID.fromString(container.get(owner, PersistentDataType.STRING)));
            if (!player.equals(robotOwner)) {
                player.sendMessage(notYourRobot);
                return;
            }

            if (!container.has(type, PersistentDataType.STRING))
                return;

            String Type = container.get(type, PersistentDataType.STRING);

            World world = player.getWorld();

            Location loc = new Location(world, e.getClickedBlock().getLocation().getBlockX(), e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ()).add(0.5, 1, 0.5);
            ArmorStand armorStand = (ArmorStand) world.spawn(loc, ArmorStand.class);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            ItemStack customItem = new ItemStack(Material.DIAMOND_PICKAXE);
            customItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            armorStand.setItem(EquipmentSlot.HAND, customItem);
            armorStand.setCustomNameVisible(true);
            String name = robotNameString.replaceAll("%owner%", robotOwner.getName());
            armorStand.setCustomName(name);
            armorStand.setBasePlate(false);
            armorStand.setArms(true);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta m = (SkullMeta) head.getItemMeta();
            m.setOwner(player.getName());
            head.setItemMeta(m);
            armorStand.setHelmet(head);
            armorStand.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            armorStand.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            armorStand.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            PersistentDataContainer asContainer = armorStand.getPersistentDataContainer();
            asContainer.set(level, PersistentDataType.INTEGER, robotLevel);
            asContainer.set(owner, PersistentDataType.STRING, robotOwner.getUniqueId().toString());
            asContainer.set(type, PersistentDataType.STRING, Type);
            asContainer.set(earnings, PersistentDataType.DOUBLE, 0d);
            asContainer.set(value, PersistentDataType.DOUBLE, 0d);
            if (!container.has(upgradeCost, PersistentDataType.DOUBLE))
                asContainer.set(upgradeCost, PersistentDataType.DOUBLE, upgradeCostStart);
            else asContainer.set(upgradeCost, PersistentDataType.DOUBLE, container.get(upgradeCost, PersistentDataType.DOUBLE));

            Date date = new Date();
            asContainer.set(lastOpened, PersistentDataType.LONG, date.getTime());

            int amount = player.getInventory().getItemInMainHand().getAmount();

            player.getInventory().getItemInMainHand().setAmount(amount -1);
            player.sendMessage(placedYourRobot);
        }
    }
}
