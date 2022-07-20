package me.sildev.zoopr.robots;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.*;
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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;
import java.util.UUID;

public class RobotManager implements Listener {

    public static int maxLevel = 100;
    public static String RobotItemName = coloredString.color("&f&ki&r&d Miner Robot &f&ki&r");
    public static NamespacedKey level = new NamespacedKey(ZooPR.getPlugin(), "robot-level");
    public static  NamespacedKey owner = new NamespacedKey(ZooPR.getPlugin(), "robot-owner");
    public static NamespacedKey earnings = new NamespacedKey(ZooPR.getPlugin(), "robot-earnings");
    public static NamespacedKey value = new NamespacedKey(ZooPR.getPlugin(), "robot-value");
    public static NamespacedKey type = new NamespacedKey(ZooPR.getPlugin(), "robot-type");
    public static NamespacedKey lastOpened = new NamespacedKey(ZooPR.getPlugin(), "robot-last-opened");

    Material RobotType = Material.ZOMBIE_SPAWN_EGG;


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

            ArmorStand armorStand = (ArmorStand) world.spawn(e.getClickedBlock().getLocation().add(0d, 1d,0d), ArmorStand.class);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            ItemStack customItem = new ItemStack(Material.DIAMOND_PICKAXE);
            customItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            armorStand.setItem(EquipmentSlot.HAND, customItem);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(coloredString.color(robotNameString));
            armorStand.setBasePlate(false);
            PersistentDataContainer asContainer = armorStand.getPersistentDataContainer();
            asContainer.set(level, PersistentDataType.INTEGER, robotLevel);
            asContainer.set(owner, PersistentDataType.STRING, robotOwner.getUniqueId().toString());
            asContainer.set(type, PersistentDataType.STRING, Type);
            Date date = new Date();
            asContainer.set(lastOpened, PersistentDataType.LONG, date.getTime());
            player.sendMessage(placedYourRobot);
        }
    }
}
