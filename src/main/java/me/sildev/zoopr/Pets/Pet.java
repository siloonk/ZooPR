package me.sildev.zoopr.Pets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Getter @Setter
public class Pet implements ConfigurationSerializable {

    PetType type;
    PetTier tier;

    OfflinePlayer owner;

    int level;
    double exp;
    double expRequired;


    public Pet(OfflinePlayer owner, PetTier tier, PetType type) {
        this.type = type;
        this.owner = owner;
        this.tier = tier;

        this.level = 1;
        this.exp = 0d;
        this.expRequired = petManager.getSetting("startExp");
    }

    public Pet(OfflinePlayer owner, PetTier tier, PetType type, int level, double exp, double expRequired) {
        this.type = type;
        this.owner = owner;
        this.tier =tier;

        this.level = level;
        this.exp = exp;
        this.expRequired = expRequired;
    }

    public Pet(Map<String, Object> map) {
        this.owner = Bukkit.getPlayer(UUID.fromString((String) map.get("owner")));
        this.type = PetType.valueOf((String) map.get("type"));
        this.tier = PetTier.valueOf((String) map.get("tier"));
        this.level = (int) map.get("level");
        this.exp = (double) map.get("exp");
        this.expRequired = (double) map.get("expRequired");
    }

    public void usePet() {
        Random rd = new Random();
        this.exp += rd.nextInt(5);
        if (this.exp >= this.expRequired) {
            this.exp = 0d;
            this.expRequired *= 1.08;
            this.level += 1;
            if (this.owner.isOnline()) {
                Player player = (Player) this.owner;
                player.sendMessage("Pet has leveled up!");
            }
        }
    }

    public double getPetMultiplier() {
        double baseMulti = this.tier.getMultiplier();
        baseMulti += (0.01 * this.level);
        return baseMulti;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner", owner.getUniqueId().toString());
        StringBuilder tierBuilder = new StringBuilder(tier.toString());
        tierBuilder.setCharAt(0, ' ');
        tierBuilder.setCharAt(1, ' ');

        map.put("type", type.toString());
        map.put("tier", tierBuilder.toString().replaceAll("  ", ""));
        map.put("level", level);
        map.put("exp", exp);
        map.put("expRequired", expRequired);
        return map;
    }

    public PetType getType() {
        return this.type;
    }
}
