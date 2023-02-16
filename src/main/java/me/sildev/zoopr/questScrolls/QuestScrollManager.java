package me.sildev.zoopr.questScrolls;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestScrollManager {

    static File questScrollConfigFile;
    static FileConfiguration questScrollConfig;

    public static NamespacedKey blocksRequired = new NamespacedKey(ZooPR.getPlugin(), "quest-blocks-required");
    public static NamespacedKey questTier = new NamespacedKey(ZooPR.getPlugin(), "quest-tier");
    public static NamespacedKey activeQuestRequired = new NamespacedKey(ZooPR.getPlugin(), "quest-active-required");
    public static NamespacedKey activeQuestTier = new NamespacedKey(ZooPR.getPlugin(), "quest-active-tier");

    public static final int startBlocks = 5000;

    public static Material QuestScrollItemType = Material.PAPER;
    private static String QuestScrollName = coloredString.color("&0&l[ &5&l⛏ &d&l&nQuest Scroll&r &7(&d&oLevel 1&7) &5&l⛏ &0&l]");
    private static List<String> lore = new ArrayList<String>() {{
        add("");
        add(coloredString.color("&5&l➥ &7&oMine 15000 blocks to unlock!"));
        add(coloredString.color("&5&l➥ &7&oRight Click to Activate!"));
    }};


    public QuestScrollManager() {
        createQuestScrollConfig();
    }

    public static ItemStack generateQuestScroll(int tier) {
        ItemStack scroll = new ItemStack(QuestScrollItemType);
        if (tier < 0 || tier > 3) return null;
        ItemMeta meta = scroll.getItemMeta();
        meta.setDisplayName(QuestScrollName.replaceAll("%level%", String.valueOf(tier)));
        List<String> actualLore = new ArrayList<>();
        for (String line : lore) {
            actualLore.add(line.replaceAll("%amount-of-blocks%", String.valueOf(startBlocks * tier)));
        }
        meta.setLore(actualLore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(blocksRequired, PersistentDataType.INTEGER, startBlocks * tier);
        container.set(questTier, PersistentDataType.INTEGER, tier);

        scroll.setItemMeta(meta);

        return scroll;
    }

    public static void getLoot(Player player, int tier) {
        List<String> commands = (List<String>) questScrollConfig.getList("scroll" + tier);

        for (String command : commands) {
            int index = commands.indexOf(command);
            commands.set(index, command.replaceAll("%player%", player.getName()));
        }

        Random rd = new Random();
        int value = rd.nextInt(commands.size());
        ZooPR.getPlugin().getServer().dispatchCommand(ZooPR.getPlugin().getServer().getConsoleSender(), commands.get(value));
    }




    private static void createQuestScrollConfig() {
        questScrollConfigFile = new File(ZooPR.getPlugin().getDataFolder(), "scrolls.yml");
        if (!questScrollConfigFile.exists()) {
            questScrollConfigFile.getParentFile().mkdirs();
            ZooPR.getPlugin().saveResource("scrolls.yml", false);
        }

        questScrollConfig = new YamlConfiguration();
        try {
            questScrollConfig.load(questScrollConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
