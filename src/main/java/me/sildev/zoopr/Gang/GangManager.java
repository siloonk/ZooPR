package me.sildev.zoopr.Gang;

import me.sildev.zoopr.ZooPR;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GangManager {

    static FileConfiguration gangs;
    static File gangsFile;
    static ZooPR instance = ZooPR.getPlugin();

    public static List<String> gangNames = new ArrayList<>();

    public static NamespacedKey gang = new NamespacedKey(ZooPR.getPlugin(), "gang");

    public GangManager() {

        createGangsConfig();
        gangNames = gangs.getStringList("gangNames");
    }

    public static boolean createGang(Player owner, String name) {
        if (gangs.contains("gangs." + name)) {return false;}
        PersistentDataContainer container = owner.getPersistentDataContainer();
        if (container.has(gang, PersistentDataType.STRING)) {return false;}

        List<OfflinePlayer> members = new ArrayList<OfflinePlayer>();
        members.add(owner);
        Gang g = new Gang(owner, members, name);
        gangNames.add(g.getName());
        gangs.set("gangNames", gangNames);
        gangs.set("gangs." + name, g);
        try {
            gangs.save(gangsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        container.set(gang, PersistentDataType.STRING, name);
        return true;
    }

    public static Gang loadGang(String name) {
        if (!gangs.contains("gangs." + name)) { return null; }
        return (Gang) gangs.get("gangs." + name);
    }

    public static void updateConfig(Gang gang) {
        gangs.set("gangs." + gang.getName(), gang);
        try {
            gangs.save(gangsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createGangsConfig() {
        gangsFile = new File(instance.getDataFolder(), "gangs.yml");
        if (!gangsFile.exists()) {
            gangsFile.getParentFile().mkdirs();
            instance.saveResource("gangs.yml", false);
        }

        gangs = new YamlConfiguration();
        try {
            gangs.load(gangsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
