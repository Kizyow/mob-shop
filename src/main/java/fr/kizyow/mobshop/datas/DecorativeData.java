package fr.kizyow.mobshop.datas;

import fr.kizyow.mobshop.utils.ItemConverter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DecorativeData {

    private final String stringMaterial;
    private final String title;
    private final List<String> lore;
    private final List<Integer> slots;

    public DecorativeData(String stringMaterial, String title, List<String> lore, List<Integer> slots) {
        this.stringMaterial = stringMaterial;
        this.title = title;
        this.lore = lore;
        this.slots = slots;
    }

    public Material getMaterial() {
        return Material.valueOf(stringMaterial);
    }


    public String getTitle() {
        return title;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public ItemStack getItem() {
        return ItemConverter.getItem(stringMaterial, title, lore, null, false);
    }

}
