package me.sildev.zoopr;

import me.sildev.zoopr.Enchants.CustomEnchantConfigFiles;
import me.sildev.zoopr.Enchants.CustomEnchants;
import me.sildev.zoopr.Enchants.EnchantPrices;
import me.sildev.zoopr.Enchants.events.*;
import me.sildev.zoopr.Gang.Gang;
import me.sildev.zoopr.Gang.GangManager;
import me.sildev.zoopr.Gang.commands.gangCMD;
import me.sildev.zoopr.Leaderboard.LeaderboardManager;
import me.sildev.zoopr.Leaderboard.baltopCMD;
import me.sildev.zoopr.Leaderboard.beacontopCMD;
import me.sildev.zoopr.Leaderboard.tokentopCMD;
import me.sildev.zoopr.bombs.bombCMD;
import me.sildev.zoopr.bombs.bombInteractListener;
import me.sildev.zoopr.eco.EconomyManager;
import me.sildev.zoopr.eco.SellBlocks;
import me.sildev.zoopr.eco.cmds.balCMD;
import me.sildev.zoopr.eco.cmds.payCMD;
import me.sildev.zoopr.essentials.commands.banCMD;
import me.sildev.zoopr.essentials.commands.vanishCMD;
import me.sildev.zoopr.pickaxe.events.enchantMenu;
import me.sildev.zoopr.pickaxe.givePickaxe;
import me.sildev.zoopr.pvemobs.PveMobManager;
import me.sildev.zoopr.pvemobs.PveSpawnerCMD;
import me.sildev.zoopr.rank.commands.prestigeCMD;
import me.sildev.zoopr.rank.commands.prestigeMax;
import me.sildev.zoopr.rank.commands.rankupCMD;
import me.sildev.zoopr.rank.rankupManager;
import me.sildev.zoopr.robots.*;
import me.sildev.zoopr.scoreboard.scoreboardListener;
import me.sildev.zoopr.utils.Messages;


import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZooPR extends JavaPlugin {

    private static ZooPR plugin;
    private static LeaderboardManager leaderboard;

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Gang.class);
        plugin = this;
        leaderboard = new LeaderboardManager();

        // Create instance of CustomEnchantConfigFiles, so it creates the config file!
        new CustomEnchantConfigFiles(this);
        new SellBlocks(this);
        new GangManager();
        new Messages();
        new EnchantPrices();

        registerCommands();
        registerEvents();
        CustomEnchants.register();



        this.getLogger().fine("ZooPR is Starting!");
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(ZooPR.getPlugin());
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

        // Enchantments
        pm.registerEvents(new ExplosiveListener(), this);
        pm.registerEvents(new JackhammerListener(), this);
        pm.registerEvents(new noEnchantListener(), this);
        pm.registerEvents(new LuckyListener(), this);
        pm.registerEvents(new LaserListener(), this);
        pm.registerEvents(new DrillListener(), this);
        pm.registerEvents(new LightningListener(), this);
    }

    public static ZooPR getPlugin() {
        return plugin;
    }
    public static LeaderboardManager getLeaderboard() {return leaderboard; }
}
