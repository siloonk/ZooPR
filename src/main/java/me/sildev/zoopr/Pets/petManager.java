package me.sildev.zoopr.Pets;

import me.sildev.zoopr.ZooPR;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class petManager implements Listener {

    private static HashMap<OfflinePlayer, List<Pet>> pets = new HashMap<>();
    private static HashMap<Player, Pet> activePets = new HashMap<>();

    private static File petsConfigFile;
    private static FileConfiguration petsConfig;

    public petManager() {
        createPetConfig();
    }

    void createPetConfig() {
        petsConfigFile = new File(ZooPR.getPlugin().getDataFolder(), "pets.yml");
        if (!petsConfigFile.exists()) {
            petsConfigFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("pets.yml", false);
        }

        petsConfig = new YamlConfiguration();
        try {
            petsConfig.load(petsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Pet getActivePet(Player player) {
        return activePets.getOrDefault(player, null);
    }

    public static void savePets() throws IOException {
        pets.forEach((key, value) -> {
            petsConfig.set(key.getUniqueId().toString(), value);
        });
        petsConfig.save(petsConfigFile);
    }

    public static void savePets(Player player) throws IOException {
        if (!petsConfig.contains(player.getUniqueId().toString())) return;
        petsConfig.set(player.getUniqueId().toString(), pets.get(player));
        petsConfig.save(petsConfigFile);
    }

    public static void savePets(OfflinePlayer player) throws IOException {
        petsConfig.set(player.getUniqueId().toString(), pets.get(player));
        petsConfig.save(petsConfigFile);
    }

    public static void loadPets() {
        System.out.println(Bukkit.getOfflinePlayers());
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (petsConfig.contains(player.getUniqueId().toString()))
                pets.put(player, (List<Pet>) petsConfig.get(player.getUniqueId().toString()));
        }
    }

    public static void loadPets(OfflinePlayer player) {
        if (petsConfig.contains(player.getUniqueId().toString()))
            pets.put(player, (List<Pet>) petsConfig.get(player.getUniqueId().toString()));
    }


    public static double getSetting(String setting) {
        return petsConfig.getDouble(setting + "_setting");
    }

    public static void addPet(Player player, Pet pet) {
        if (!pets.containsKey(player))
            pets.put(Bukkit.getOfflinePlayer(player.getName()), new ArrayList<>());
        List<Pet> playerPets = pets.get(Bukkit.getOfflinePlayer(player.getName()));
        playerPets.add(pet);
        pets.put(Bukkit.getOfflinePlayer(player.getName()), playerPets);

        try {
            savePets();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Set active pet of player
    public static void setActivePet(Player player, Pet pet) {
        activePets.put(player, pet);
    }

    // remove active pet of player
    public static void removeActivePet(Player player) {
        activePets.remove(player);
    }

    public static List<Pet> getPetsOfPlayer(Player player) {
        List<Pet> playerPets = new ArrayList<>();
        pets.forEach((p, pet) -> {
            if (p.equals(player)) {
                playerPets.addAll(pets.get(p));
            }
        });
        return playerPets;
    }


    @EventHandler
    void playerJoin(PlayerJoinEvent e) {
        loadPets(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        try {
            savePets(e.getPlayer());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
