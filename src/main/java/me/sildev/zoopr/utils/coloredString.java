package me.sildev.zoopr.utils;

import org.bukkit.ChatColor;

public class coloredString {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
