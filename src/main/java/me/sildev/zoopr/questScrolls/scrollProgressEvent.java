package me.sildev.zoopr.questScrolls;

import me.sildev.zoopr.eco.SellBlocks;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class scrollProgressEvent implements Listener {

    @EventHandler
    public void onScrollProgressEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        PersistentDataContainer playerContainer = player.getPersistentDataContainer();

        if (!SellBlocks.isInRegionWhereCanMine(e.getBlock().getLocation())) {
            e.setCancelled(true);
            return;
        }

        if (!playerContainer.has(QuestScrollManager.activeQuestTier, PersistentDataType.INTEGER)) return;

        playerContainer.set(QuestScrollManager.activeQuestRequired, PersistentDataType.INTEGER, playerContainer.get(QuestScrollManager.activeQuestRequired, PersistentDataType.INTEGER) - 1);

        int tier = playerContainer.get(QuestScrollManager.activeQuestTier, PersistentDataType.INTEGER);
        int required =playerContainer.get(QuestScrollManager.activeQuestRequired, PersistentDataType.INTEGER);

        player.sendActionBar(coloredString.color("&5Quest: &d" +(tier * QuestScrollManager.startBlocks -  required )+ "/" + tier * QuestScrollManager.startBlocks));

        if (playerContainer.get(QuestScrollManager.activeQuestRequired, PersistentDataType.INTEGER) <= 0) {
            playerContainer.remove(QuestScrollManager.activeQuestRequired);
            playerContainer.remove(QuestScrollManager.activeQuestTier);

            QuestScrollManager.getLoot(player, tier);
        }
    }
}
