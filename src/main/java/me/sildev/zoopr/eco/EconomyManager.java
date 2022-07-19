package me.sildev.zoopr.eco;

import me.sildev.zoopr.ZooPR;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class EconomyManager implements Listener {

    static FileConfiguration money;
    static File moneyConfigFile;

    static FileConfiguration tokens;
    static File tokensConfigFile;

    static ZooPR instance;

    public EconomyManager(ZooPR plugin) {
        instance = plugin;


        createMoneyConfig();
        createTokenConfig();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        addUser(e.getPlayer());
    }

    // Config initializers.
    private static void createMoneyConfig() {
        moneyConfigFile = new File(instance.getDataFolder(), "money.yml");
        if (!moneyConfigFile.exists()) {
            moneyConfigFile.getParentFile().mkdirs();
            instance.saveResource("money.yml", false);
        }

        money = new YamlConfiguration();
        try {
            money.load(moneyConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    private static void createTokenConfig() {
        tokensConfigFile = new File(instance.getDataFolder(), "tokens.yml");
        if (!tokensConfigFile.exists()) {
            instance.saveResource("tokens.yml", false);
        }

        tokens = new YamlConfiguration();
        try {
            tokens.load(tokensConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    // Adders and getters and setters for player money;

    static void addUser(Player player) {
        if (!money.contains("balance." + player.getUniqueId())) {
            money.set("balance." + player.getUniqueId(), 0);
            try {
                money.save(moneyConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!tokens.contains("balance." + player.getUniqueId())) {
            System.out.println(money.getDouble("balance." + player.getUniqueId()));
            tokens.set("balance." + player.getUniqueId(), 0);
            try {
                tokens.save(tokensConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Double getMoneyOfUser(Player player) {
        return money.getDouble("balance." + player.getUniqueId());
    }

    public static void setMoneyOfUser(Player player, Double amount) {
        money.set("balance." + player.getUniqueId(), amount);
        try {
            money.save(moneyConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addMoneyToUser(Player player, Double amount) {
        Double currMoney = money.getDouble("balance." + player.getUniqueId());
        money.set("balance." + player.getUniqueId(), currMoney + amount);
        try {
            money.save(moneyConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Double getTokensOfUser(Player player) {
        return tokens.getDouble("balance." + player.getUniqueId());
    }

    public static void setTokensOfUser(Player player, Double amount) {
        tokens.set("balance." + player.getUniqueId(), amount);
        try {
            tokens.save(tokensConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTokensToUser(Player player, Double amount) {
        Double currTokens = tokens.getDouble("balance." + player.getUniqueId());
        tokens.set("balance." + player.getUniqueId(), currTokens + amount);
        try {
            tokens.save(tokensConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
