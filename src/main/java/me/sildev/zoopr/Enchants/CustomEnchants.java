package me.sildev.zoopr.Enchants;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomEnchants {

    public static final Enchantment EXPLOSIVE = new EnchantmentWrapper("explosive", "Explosive", 5000);
    public static final Enchantment JACKHAMMER = new EnchantmentWrapper("jackhammer", "Jackhammer", 5000);
    public static final Enchantment LUCKY = new EnchantmentWrapper("lucky", "Lucky", 5000);
    public static final Enchantment CRATE_FINDER = new EnchantmentWrapper("crate_finder", "Crate Finder", 10000);
    public static final Enchantment FORTUNE = new EnchantmentWrapper("fortune", "Fortune", 1000);
    public static final Enchantment DRILL = new EnchantmentWrapper("drill", "Drill", 10);
    public static final Enchantment LASER = new EnchantmentWrapper("laser", "Laser", 5000);
    public static final Enchantment LIGHTNING = new EnchantmentWrapper("lightning", "Lightning", 10);
    public static final Enchantment BEACONMASTER = new EnchantmentWrapper("beaconmaster", "BeaconMaster", 10);
    public static final Enchantment MERCHANT = new EnchantmentWrapper("merchant", "Merchant", 2500);
    public static final Enchantment TOKEN_MULTI = new EnchantmentWrapper("token_multi", "Token Multi", 2500);
    public static final Enchantment POUCH_FINDER = new EnchantmentWrapper("pouch_finder", "Pouch Finder", 10000);
    public static final Enchantment CUBED = new EnchantmentWrapper("cubed", "Cubed", 10);


    public static List<Enchantment> Enchantments = new ArrayList<Enchantment>() {{
        add(EXPLOSIVE);
        add(JACKHAMMER);
        add(LUCKY);
        add(CRATE_FINDER);
        add(FORTUNE);
        add(DRILL);
        add(LASER);
        add(LIGHTNING);
        add(BEACONMASTER);
        add(MERCHANT);
        add(MERCHANT);
        add(TOKEN_MULTI);
        add(POUCH_FINDER);
        add(CUBED);
    }};

    public static void register() {
        for (Enchantment ce : Enchantments) {
            boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(ce);

            if (!registered)
                registerEnchantment(ce);
        }
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
    }
}
