package me.sildev.zoopr.pouches.tasks;

import me.sildev.zoopr.Leaderboard.eco.EconomyManager;
import me.sildev.zoopr.utils.Messages;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class openPouchTask extends BukkitRunnable {


    String receivedValue = Messages.get("receivedPouchValue");

    int decidedValue;
    Player player;
    int iterationsNeeded;
    String type;
    StringBuilder originalName;
    int offset;

    StringBuilder titleMessage;

    public openPouchTask(Player player, int decidedValue, String type) {
        this.player = player;
        this.decidedValue = decidedValue;
        this.type = type;

        this.iterationsNeeded = String.valueOf(decidedValue).length();
        if (type.equalsIgnoreCase("money")) {
            titleMessage = new StringBuilder("$");
        } else {
            titleMessage = new StringBuilder();
        }

        for (int i = 0; i < iterationsNeeded; i++) {
            titleMessage.append("#");
        }
        if (type.equalsIgnoreCase("tokens")) {
            titleMessage.append(" Tokens");
        }
        offset = 0;
        if (type.equalsIgnoreCase("money")) {
            offset = 1;
            iterationsNeeded++;
        }

        originalName = new StringBuilder(String.valueOf(decidedValue));
    }



    @Override
    public void run() {
        player.sendTitle(coloredString.color("&d" + type), titleMessage.toString());
        try {
            if (type.equalsIgnoreCase("money")) {
                titleMessage.setCharAt(offset, originalName.charAt(offset - 1));
            } else
                titleMessage.setCharAt(offset, originalName.charAt(offset));
        } catch (Exception e) {
            if (type.equalsIgnoreCase("money")) {
                EconomyManager.addMoneyToUser(player, (double) decidedValue);
            } else  {
                EconomyManager.addTokensToUser(player, (double) decidedValue);
            }
            cancel();
        }
        offset++;
    }
}
