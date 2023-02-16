package me.sildev.zoopr.robots;

import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;
import java.util.UUID;

public class RobotGUI implements Listener {

    static Entity as;

    String insufficientfunds = Messages.get("insufficientFunds");

    public static void open(Entity armorStand, Player player) {
        as = armorStand;
        PersistentDataContainer container = armorStand.getPersistentDataContainer();
        int level = container.get(RobotManager.level, PersistentDataType.INTEGER);
        String owner = container.get(RobotManager.owner, PersistentDataType.STRING);
        double earnings = container.get(RobotManager.earnings, PersistentDataType.DOUBLE);
        double value = container.get(RobotManager.value, PersistentDataType.DOUBLE);
        String type = container.get(RobotManager.type, PersistentDataType.STRING);
        long lastOpened = container.get(RobotManager.lastOpened, PersistentDataType.LONG);
        double upgradeCost = container.get(RobotManager.upgradeCost, PersistentDataType.DOUBLE);

        Date date = new Date();
        long currTime = date.getTime();
        long elapsedTime = (currTime - lastOpened) / 1000; // In seconds
        value = value + elapsedTime * (level * RobotManager.ratePerLevel + RobotManager.baseRate);
        container.set(RobotManager.value, PersistentDataType.DOUBLE, value);




        Inventory gui = Bukkit.createInventory(null, 6* 9, "Robot Menu");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }


        guiExtension.addRenamedItemWithLore(gui,13, Material.ARMOR_STAND, "&0&l[ &d&ki &5&lMiner Robot &d&ki &0&l]", new String[]{
                "",
                coloredString.color("&5&l • &d&lStatistics"),
                coloredString.color(" &d➥ &5&lOwner &d" + Bukkit.getPlayer(UUID.fromString(owner)).getName()),
                coloredString.color(" &d➥ &5&lLevel &d" + level),
                coloredString.color(" &d➥ &5&lEarnings &d" + formatNumber.coolFormat(earnings, 0)),
                coloredString.color(" &d➥ &5&lValue &d" + formatNumber.coolFormat(value, 0)),
                coloredString.color(" &d➥ &5&lType&d " + type),
                coloredString.color(" &d➥ &5&lEarn Rate &d" + (level * RobotManager.ratePerLevel + RobotManager.baseRate)),
                " "
        });

        guiExtension.addRenamedItemWithLore(gui, 29, Material.OAK_SIGN, "&0&l[ &5&ki &d&lClaim Rewards &5&ki &0&l]", new String[] {
                "",
                coloredString.color(" &d➥ &5&lValue &d" + formatNumber.coolFormat(value, 0))
        });

        guiExtension.addRenamedItemWithLore(gui, 31, Material.OAK_SIGN, "&0&l[ &5&ki &d&lChange Direction &7(&d" + armorStand.getFacing() + "&7) &5&ki &0&l]", new String[] {
        });

        guiExtension.addRenamedItemWithLore(gui, 33, Material.OAK_SIGN, "&0&l[ &5&ki &d&lUpgrade Robot &5&ki &0&l]", new String[] {
                "",
                coloredString.color(" &d➥ &5&lLevel &d" + level),
                coloredString.color(" &d➥ &5&lCost &d" + upgradeCost + " beacons"),
                ""
        });

        guiExtension.addRenamedItemWithLore(gui, gui.getSize() - 1, Material.BARRIER, "&5&lPICKUP ROBOT", new String[]{
                "",
                coloredString.color("&c&lWARNING! &7&oBy picking up the robot, you will lose the earnings")
        });


        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("Robot Menu"))
            return;

        Player player = (Player) e.getWhoClicked();
        Entity armorStand = as;

        PersistentDataContainer container = armorStand.getPersistentDataContainer();
        int level = container.get(RobotManager.level, PersistentDataType.INTEGER);
        String owner = container.get(RobotManager.owner, PersistentDataType.STRING);
        double earnings = container.get(RobotManager.earnings, PersistentDataType.DOUBLE);
        double value = container.get(RobotManager.value, PersistentDataType.DOUBLE);
        String type = container.get(RobotManager.type, PersistentDataType.STRING);
        long lastOpened = container.get(RobotManager.lastOpened, PersistentDataType.LONG);
        double upgradeCost = container.get(RobotManager.upgradeCost, PersistentDataType.DOUBLE);

        Date date = new Date();
        long currTime = date.getTime();
        long elapsedTime = (currTime - lastOpened) / 1000; // In seconds

        if (e.getSlot() == 29) {
            if (type.equals("TOKENS")) {
                EconomyManager.addTokensToUser(player, value);
                container.set(RobotManager.value, PersistentDataType.DOUBLE, 0d);
                container.set(RobotManager.lastOpened, PersistentDataType.LONG, date.getTime());
                container.set(RobotManager.earnings, PersistentDataType.DOUBLE, earnings + value);
                open(armorStand, player);
            }
            if (type.equals("MONEY")) {
                EconomyManager.addMoneyToUser(player, value);
                container.set(RobotManager.value, PersistentDataType.DOUBLE, 0d);
                container.set(RobotManager.lastOpened, PersistentDataType.LONG, date.getTime());
                container.set(RobotManager.earnings, PersistentDataType.DOUBLE, earnings + value);
                open(armorStand, player);
            }
        }

        if (e.getSlot() == 31) {
            armorStand.setRotation(armorStand.getLocation().getYaw() + 90, armorStand.getLocation().getPitch());
            open(armorStand, player);
        }

        if (e.getSlot() == 33) {

            double beacons = EconomyManager.getBeaconsOfUser(player);
            if (beacons < upgradeCost) {
                player.sendMessage(insufficientfunds);
                e.setCancelled(true);
                return;
            }
            EconomyManager.addBeaconsToUser(player, -upgradeCost);
            container.set(RobotManager.upgradeCost, PersistentDataType.DOUBLE, upgradeCost * RobotManager.costMultiplier);
            container.set(RobotManager.level, PersistentDataType.INTEGER, level + 1);
            open(armorStand, player);

            // TODO: Add upgrading here!
        }


        if (e.getSlot() == 53) {
            ItemStack robotItem = new ItemStack(RobotManager.RobotType);
            ItemMeta meta = robotItem.getItemMeta();
            StringBuilder typeString = new StringBuilder(type.toLowerCase());
            String character = String.valueOf(typeString.charAt(0));
            typeString.setCharAt(0, character.toUpperCase().toCharArray()[0]);
            meta.setDisplayName(RobotManager.RobotItemName.replaceAll("%type%", typeString.toString()));
            PersistentDataContainer c = meta.getPersistentDataContainer();
            c.set(RobotManager.level, PersistentDataType.INTEGER, level);
            c.set(RobotManager.owner, PersistentDataType.STRING, owner);
            c.set(RobotManager.type, PersistentDataType.STRING, type);
            c.set(RobotManager.upgradeCost, PersistentDataType.DOUBLE, upgradeCost);
            robotItem.setItemMeta(meta);
            robotItem = addLore.addRobotLore(robotItem);
            armorStand.remove();
            player.closeInventory();
            robotItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            robotItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            player.getInventory().addItem(robotItem);
        }

        e.setCancelled(true);
    }
}
