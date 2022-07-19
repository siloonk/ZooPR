package me.sildev.zoopr.Gang;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Gang implements ConfigurationSerializable {

    OfflinePlayer owner;
    List<OfflinePlayer> members = new ArrayList<OfflinePlayer>();
    String name;
    int level;
    double upgradeCost;

    public OfflinePlayer getOwner(){
        return this.owner;
    }

    public List<OfflinePlayer> getMembers() {
        return this.members;
    }

    public String getName() {
        return this.name;
    }

    public void setMembers(List<OfflinePlayer> members) {
        this.members = members;
        GangManager.updateConfig(this);
    }

    public void addMember(OfflinePlayer player) {
        this.members.add(player);
        GangManager.updateConfig(this);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
        GangManager.updateConfig(this);
    }


    public Gang(Player owner, List<OfflinePlayer> members, String name) {
        this.owner = owner;
        this.members =  members;
        this.name = name;
        this.level = 1;
        this.upgradeCost = 10000000d;
    }

    public Gang(Map<String, Object> map) {
        this.owner = Bukkit.getOfflinePlayer(UUID.fromString((String)map.get("owner")));
        List<String> tempMembers = (List<String>) map.get("members");
        for (String member : tempMembers)
            this.members.add(Bukkit.getOfflinePlayer(UUID.fromString(member)));
        this.name = (String) map.get("name");
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("owner", owner.getUniqueId().toString());
        List<String> tempMembers = new ArrayList<String>();
        for (OfflinePlayer member : members)
            tempMembers.add(member.getUniqueId().toString());
        map.put("members", tempMembers);
        map.put("name", name);
        return map;
    }
}
