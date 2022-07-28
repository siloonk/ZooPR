package me.sildev.zoopr.utils;

import me.sildev.zoopr.ZooPR;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Messages {

    static FileConfiguration messages;
    static File messagesFile;

    public Messages() {
        createMessagesConfig();
    }

    public static String get(String messagePath) {
        return coloredString.color((String) messages.get(messagePath));
    }


    private static void createMessagesConfig() {
        messagesFile = new File(ZooPR.getPlugin().getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("messages.yml", false);
        }

        messages = new YamlConfiguration();
        try {
            messages.load(messagesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
