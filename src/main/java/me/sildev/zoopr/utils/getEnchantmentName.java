package me.sildev.zoopr.utils;

import me.sildev.zoopr.Enchants.EnchantmentWrapper;
import org.bukkit.enchantments.Enchantment;

public class getEnchantmentName {

    public static String getEnchantmentName(Enchantment ce) {
        if (ce.equals(Enchantment.DIG_SPEED)) {
            return "Efficiency";
        }
        else if (ce.equals(Enchantment.LOOT_BONUS_BLOCKS)) {
            return "Fortune";
        } else if (ce.equals(Enchantment.DAMAGE_ALL)) {
            return "Sharpness";
        } else if (ce.equals(Enchantment.DURABILITY)) {
            return "Unbreaking";
        } else {
            return ce.getName();
        }
    }
}
