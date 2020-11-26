package fr.kizyow.mobshop.datas;

import fr.kizyow.mobshop.utils.ItemConverter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemData {

    private final String stringMaterial;
    private final String action;
    private final Integer slot;
    private final String title;
    private final List<String> lore;

    public ItemData(String stringMaterial, String action, Integer slot, String title, List<String> lore){
        this.stringMaterial = stringMaterial;
        this.action = action;
        this.slot = slot;
        this.title = title;
        this.lore = lore;
    }

    public Material getMaterial(){
        return Material.valueOf(stringMaterial);
    }

    public ActionData getAction(){
        return ActionData.getAction(action);
    }

    public Integer getSlot(){
        return slot;
    }

    public Integer getRow(){
        return slot / 9;
    }

    public Integer getColumn(){
        return slot % 9;
    }

    public String getTitle(){
        return title;
    }

    public List<String> getLore(){
        return lore;
    }

    public ItemStack getItem(EntityType entityType){
        return ItemConverter.getItem(stringMaterial, title, lore, entityType, false);
    }

    public ItemStack getItem(){
        return getItem(null);
    }

    public ItemStack getPredefinedItem(ItemStack itemStack, EntityType entityType){
        return ItemConverter.getPredefinedItem(itemStack, title, lore, entityType);
    }

}
