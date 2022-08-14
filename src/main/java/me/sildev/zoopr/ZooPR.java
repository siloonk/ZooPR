package me.sildev.zoopr;

import me.sildev.zoopr.Boosters.BoosterInteractEvent;
import me.sildev.zoopr.Boosters.boosterCommand;
import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.pickaxeEnchants.EnchantPrices;
import me.sildev.zoopr.Enchants.pickaxeEnchants.events.*;
import me.sildev.zoopr.Gang.Gang;
import me.sildev.zoopr.Gang.GangManager;
import me.sildev.zoopr.Gang.commands.gangCMD;
import me.sildev.zoopr.Leaderboard.LeaderboardManager;
import me.sildev.zoopr.Leaderboard.baltopCMD;
import me.sildev.zoopr.Leaderboard.beacontopCMD;
import me.sildev.zoopr.Leaderboard.tokentopCMD;
import me.sildev.zoopr.Luckyblock.LuckyBlockManager;
import me.sildev.zoopr.Luckyblock.giveLuckyblockEvent;
import me.sildev.zoopr.Luckyblock.luckyBlockInteractEvent;
import me.sildev.zoopr.Pets.Pet;
import me.sildev.zoopr.Pets.petCMD;
import me.sildev.zoopr.Pets.petManager;
import me.sildev.zoopr.Pets.petMenu;
import me.sildev.zoopr.armor.armorCeGuiClickEvent;
import me.sildev.zoopr.armor.ceCommand;
import me.sildev.zoopr.armor.swordCeGuiClickEvent;
import me.sildev.zoopr.bombs.bombCMD;
import me.sildev.zoopr.bombs.bombInteractListener;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.eco.SellBlocks;
import me.sildev.zoopr.eco.cmds.balCMD;
import me.sildev.zoopr.eco.cmds.ecoCMD;
import me.sildev.zoopr.eco.cmds.payCMD;
import me.sildev.zoopr.essentials.MineLocationManager;
import me.sildev.zoopr.essentials.commands.banCMD;
import me.sildev.zoopr.mines.commands.mineCMD;
import me.sildev.zoopr.essentials.commands.setMineLocation;
import me.sildev.zoopr.essentials.commands.vanishCMD;
import me.sildev.zoopr.essentials.ui.mainMineMenu;
import me.sildev.zoopr.essentials.ui.subMineMenu;
import me.sildev.zoopr.mines.Mine;
import me.sildev.zoopr.mines.MineManager;
import me.sildev.zoopr.mines.commands.resetMine;
import me.sildev.zoopr.playtime.playtimeManager;
import me.sildev.zoopr.pickaxe.events.enchantMenu;
import me.sildev.zoopr.pickaxe.givePickaxe;
import me.sildev.zoopr.pouches.openPouchListener;
import me.sildev.zoopr.pouches.pouchCMD;
import me.sildev.zoopr.pouches.pouchManager;
import me.sildev.zoopr.pvemobs.PveMobManager;
import me.sildev.zoopr.pvemobs.PveSpawnerCMD;
import me.sildev.zoopr.questScrolls.QuestScrollManager;
import me.sildev.zoopr.questScrolls.questCMD;
import me.sildev.zoopr.questScrolls.scrollActivateEvent;
import me.sildev.zoopr.questScrolls.scrollProgressEvent;
import me.sildev.zoopr.rank.commands.prestigeCMD;
import me.sildev.zoopr.rank.commands.prestigeMax;
import me.sildev.zoopr.rank.commands.rankupCMD;
import me.sildev.zoopr.rank.commands.rebirthCMD;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.robots.*;
import me.sildev.zoopr.scoreboard.scoreboardListener;
import me.sildev.zoopr.shops.ShopManager;
import me.sildev.zoopr.shops.customShopGuiClickEvent;
import me.sildev.zoopr.shops.shopCMD;
import me.sildev.zoopr.utils.Messages;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;

public final class ZooPR extends JavaPlugin {

    private static ZooPR plugin;
    private static ShopManager shopManager;
    private static petManager petmanager;
    private static LeaderboardManager leaderboard;
    private static MineManager mineManager;

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Gang.class);
        ConfigurationSerialization.registerClass(Pet.class);
        ConfigurationSerialization.registerClass(Mine.class);
        plugin = this;
        petmanager = new petManager();
        leaderboard = new LeaderboardManager();
        shopManager = new ShopManager();
        mineManager = new MineManager();
        mineManager.loadMines();


        // Create instance of CustomEnchantConfigFiles, so it creates the config file!
        new Messages();
        new CustomEnchantConfigFiles(this);
        new SellBlocks(this);
        new GangManager();
        new EnchantPrices();
        new pouchManager();
        new QuestScrollManager();
        new LuckyBlockManager();
        new MineLocationManager();

        registerCommands();
        registerEvents();
        CustomEnchants.register();

        this.getLogger().fine("ZooPR is Starting!");
    }

    @Override
    public void onDisable() {
        for (BukkitTask task : prestigeMax.tasks) {
            task.cancel();
            System.out.println("Canceled Task: #" + task.getTaskId());
        }
        mineManager.saveMines();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            try {
                petManager.savePets(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.getLogger().fine("ZooPR is Shutting Down!");
    }

    void registerCommands() {
        getCommand("bal").setExecutor(new balCMD());
        getCommand("pay").setExecutor(new payCMD());
        getCommand("vanish").setExecutor(new vanishCMD());
        getCommand("ban").setExecutor(new banCMD());
        getCommand("rankup").setExecutor(new rankupCMD());
        getCommand("prestige").setExecutor(new prestigeCMD());
        getCommand("gang").setExecutor(new gangCMD());
        getCommand("prestigemax").setExecutor(new prestigeMax());
        getCommand("robot").setExecutor(new RobotCMD());
        getCommand("pvespawner").setExecutor(new PveSpawnerCMD());
        getCommand("bomb").setExecutor(new bombCMD());
        getCommand("baltop").setExecutor(new baltopCMD());
        getCommand("beacontop").setExecutor(new beacontopCMD());
        getCommand("tokentop").setExecutor(new tokentopCMD());
        getCommand("pouch").setExecutor(new pouchCMD());
        getCommand("booster").setExecutor(new boosterCommand());
        getCommand("ce").setExecutor(new ceCommand());
        getCommand("rebirth").setExecutor(new rebirthCMD());
        getCommand("pet").setExecutor(new petCMD());
        getCommand("quest").setExecutor(new questCMD());
        getCommand("shop").setExecutor(new shopCMD());
        getCommand("eco").setExecutor(new ecoCMD());
        getCommand("mine").setExecutor(new mineCMD());
        getCommand("setminelocation").setExecutor(new setMineLocation());
        getCommand("resetmine").setExecutor(new resetMine());
    }

    void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new EconomyManager(this), this);
        pm.registerEvents(new givePickaxe(), this);
        pm.registerEvents(new enchantMenu(), this);
        pm.registerEvents(new rankupManager(), this);
        pm.registerEvents(new scoreboardListener(), this);
        pm.registerEvents(new RobotManager(), this);
        pm.registerEvents(new RobotDamage(), this);
        pm.registerEvents(new openRobotMenu(), this);
        pm.registerEvents(new RobotGUI(), this);
        pm.registerEvents(new PveMobManager(), this);
        pm.registerEvents(new bombInteractListener(), this);
        pm.registerEvents(new openPouchListener(), this);
        pm.registerEvents(new playtimeManager(), this);
        pm.registerEvents(new BoosterInteractEvent(), this);
        pm.registerEvents(new armorCeGuiClickEvent(), this);
        pm.registerEvents(new swordCeGuiClickEvent(), this);
        pm.registerEvents(new petMenu(), this);
        pm.registerEvents(new petManager(), this);
        pm.registerEvents(new scrollActivateEvent(), this);
        pm.registerEvents(new scrollProgressEvent(), this);
        pm.registerEvents(new giveLuckyblockEvent(), this);
        pm.registerEvents(new luckyBlockInteractEvent(), this);
        pm.registerEvents(new customShopGuiClickEvent(), this);
        pm.registerEvents(new mainMineMenu(), this);
        pm.registerEvents(new subMineMenu(), this);



        // Enchantments
        pm.registerEvents(new ExplosiveListener(), this);
        pm.registerEvents(new JackhammerListener(), this);
        pm.registerEvents(new noEnchantListener(), this);
        pm.registerEvents(new LuckyListener(), this);
        pm.registerEvents(new LaserListener(), this);
        pm.registerEvents(new DrillListener(), this);
        pm.registerEvents(new LightningListener(), this);
        pm.registerEvents(new PouchFinderListener(), this);
        pm.registerEvents(new CubedListener(), this);
    }

    public static ZooPR getPlugin() {
        return plugin;
    }
    public static LeaderboardManager getLeaderboard() {return leaderboard; }
    public static petManager getPetManager() {
        return petmanager;
    }
    public static ShopManager getShopManager() {return shopManager;}
    public static MineManager getMineManager() {return mineManager;}
}
