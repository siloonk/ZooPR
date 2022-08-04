package me.sildev.zoopr.Pets;

import me.sildev.zoopr.ZooPR;
import me.sildev.zoopr.utils.coloredString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class petMenu implements Listener {

    ItemStack moneySkull;
    ItemStack tokenSkull;
    ItemStack beaconSkull;

    public petMenu() {
        createSkulls();
    }

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 6 * 9, "Pets");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
        List<Pet> pets = petManager.getPetsOfPlayer(player);

        Pet activePet = petManager.getActivePet(player);
        if (activePet == null)
            gui.setItem(13, new ItemStack(Material.BARRIER));
        else {
            gui.setItem(13, generatePetItem(activePet));
        }


        int offset = 19;
        for (int i = 0; i < pets.size(); i++) {
            if ((i + offset + 1) % 9 == 0)
                offset += 2;
            Pet pet = pets.get(i);
            ItemStack PetItem = generatePetItem(pet, i);
            gui.setItem(i + offset, PetItem);
        }

        player.openInventory(gui);
    }

    public ItemStack generatePetItem(Pet pet, int listIndex) {
        ItemStack PetItem = tokenSkull;
        if (pet.getType().equals(PetType.MONEY))
            PetItem = moneySkull;
        else if (pet.getType().equals(PetType.BEACON))
            PetItem = beaconSkull;

        ItemMeta meta = PetItem.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(ZooPR.getPlugin(), "listIndex"), PersistentDataType.INTEGER, listIndex);
        meta.setDisplayName(coloredString.color(pet.getTier().getColor() + "" + pet.getType().toString() + " Pet"));
        String type = pet.getType().toString();
        List<String> lore = new ArrayList<String>() {{
            add("");
            add(coloredString.color("&f&lDescription &7&oThis pet will help you gain more"));
            add(coloredString.color("&7&o%type% while mining!").replaceAll("%type%", type));
            add("");
            add(coloredString.color("&dStatistics"));
            add(coloredString.color(" &5Level &d" + pet.getLevel()));
            add(coloredString.color(" &5Exp &d" + pet.getExp() + " / " + pet.getExpRequired()));
            add(coloredString.color(" &5Multiplier &d" +  (float) pet.getPetMultiplier() + "x"));
            add("");
            add(pet.getTier().toString());
            add("");
            add(coloredString.color("&7(Click to activate)"));
        }};
        meta.setLore(lore);
        PetItem.setItemMeta(meta);

        return PetItem;
    }

    public ItemStack generatePetItem(Pet pet) {
        ItemStack PetItem = tokenSkull;
        if (pet.getType().equals(PetType.MONEY))
            PetItem = moneySkull;
        else if (pet.getType().equals(PetType.BEACON))
            PetItem = beaconSkull;

        ItemMeta meta = PetItem.getItemMeta();
        meta.setDisplayName(coloredString.color(pet.getTier().getColor() + "" + pet.getType().toString() + " Pet"));
        String type = pet.getType().toString();
        List<String> lore = new ArrayList<String>() {{
            add("");
            add(coloredString.color("&f&lDescription &7&oThis pet will help you gain more"));
            add(coloredString.color("&7&o%type% while mining!").replaceAll("%type%", type));
            add("");
            add(coloredString.color("&dStatistics"));
            add(coloredString.color(" &5Level &d" + pet.getLevel()));
            add(coloredString.color(" &5Exp &d" + pet.getExp() + " / " + pet.getExpRequired()));
            add(coloredString.color(" &5Multiplier &d" +  (float) pet.getPetMultiplier() + "x"));
            add("");
            add(pet.getTier().toString());
            add("");
            add(coloredString.color("&7(Click to activate)"));
        }};
        meta.setLore(lore);
        PetItem.setItemMeta(meta);

        return PetItem;
    }

    public void createSkulls() {
        beaconSkull  = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) beaconSkull.getItemMeta();
        meta.setOwner("Horowicz");
        beaconSkull.setItemMeta(meta);

        tokenSkull = new ItemStack(Material.PLAYER_HEAD);
        meta = (SkullMeta) tokenSkull.getItemMeta();
        meta.setOwner("siloonk");
        tokenSkull.setItemMeta(meta);

        moneySkull = new ItemStack(Material.PLAYER_HEAD);
        meta = (SkullMeta) moneySkull.getItemMeta();
        meta.setOwner("535g");
        moneySkull.setItemMeta(meta);
    }



    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("Pets")) return;
        if (e.getClickedInventory().getItem(e.getSlot()).getType() != Material.BLACK_STAINED_GLASS_PANE || e.getClickedInventory().getItem(e.getSlot()).getType() != Material.BARRIER) {
            PersistentDataContainer container = e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getPersistentDataContainer();
            if (!container.has(new NamespacedKey(ZooPR.getPlugin(), "listIndex"), PersistentDataType.INTEGER)) return;
            // its not an item we wish to set as the pet!
            int index = container.get(new NamespacedKey(ZooPR.getPlugin(), "listIndex"), PersistentDataType.INTEGER);
            List<Pet> petList = petManager.getPetsOfPlayer((Player) e.getWhoClicked());
            Pet pet = petList.get(index);
            petManager.setActivePet((Player) e.getWhoClicked(), pet);
            open((Player) e.getWhoClicked());
        }
        e.setCancelled(true);
    }
}
