package me.sildev.zoopr.utils;

import org.bukkit.Material;

public class isArmor {

    public static boolean isArmor(Material type) {
        return type.toString().contains("_HELMET") || type.toString().contains("_CHESTPLATE") || type.toString().contains("_LEGGINGS") || type.toString().contains("_BOOTS");
    }

    public static boolean isSword(Material type) {
        return type.toString().contains("_SWORD");
    }

    public static boolean isAxe(Material type) {
        return type.toString().contains("_AXE");
    }
}
