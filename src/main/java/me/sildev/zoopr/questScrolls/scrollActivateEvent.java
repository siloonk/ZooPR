package me.sildev.zoopr.questScrolls;

import me.sildev.zoopr.utils.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class scrollActivateEvent implements Listener {

    String activatedQuest = Messages.get("activatedQuest");
    String alreadyActiveQuest = Messages.get("alreadyActiveQuest");

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        if (!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;

        if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(QuestScrollManager.QuestScrollItemType));

        PersistentDataContainer container = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();

        if (!container.has(QuestScrollManager.blocksRequired, PersistentDataType.INTEGER)) return;


        int blocksRequired = container.get(QuestScrollManager.blocksRequired, PersistentDataType.INTEGER);
        int tier = container.get(QuestScrollManager.questTier, PersistentDataType.INTEGER);

        PersistentDataContainer playerContainer = e.getPlayer().getPersistentDataContainer();

        if (playerContainer.has(QuestScrollManager.activeQuestTier, PersistentDataType.INTEGER)) {e.getPlayer().sendMessage(alreadyActiveQuest); return; }


        e.getPlayer().getPersistentDataContainer().set(QuestScrollManager.activeQuestRequired, PersistentDataType.INTEGER, blocksRequired);
        e.getPlayer().getPersistentDataContainer().set(QuestScrollManager.activeQuestTier, PersistentDataType.INTEGER, tier);
        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
        e.getPlayer().sendMessage(activatedQuest);
    }
}
