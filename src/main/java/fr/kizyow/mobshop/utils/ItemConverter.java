package fr.kizyow.mobshop.utils;

import fr.kizyow.mobshop.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ItemConverter {

    public static ItemStack getItem(String strMaterial, String title, List<String> lore, EntityType entityType){

        ItemStack itemStack = customHead(entityType, strMaterial);
        if(itemStack == null){
            Material material = Material.valueOf(strMaterial);
            itemStack = new ItemStack(material);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        List<String> loreColored = lore.stream()
                .map(l -> ChatColor.translateAlternateColorCodes('&', l))
                .collect(Collectors.toList());
        itemMeta.setLore(loreColored);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    private static ItemStack customHead(EntityType entityType, String material){

        String data = "";

        if(material.equalsIgnoreCase("<entity_head_shop>")){
            data = Plugin.getInstance().getMobShopConfig().getShopEntitiesHead().get(entityType);
        } else if(material.equalsIgnoreCase("<entity_head_butcher>")){
            data = Plugin.getInstance().getMobShopConfig().getButcherEntitiesHead().get(entityType);
        } else if(material.startsWith("hdb-")){
            data = material;
        } else {
            return null;
        }

        String id = material.substring(4);
        ItemStack itemStack = Plugin.getInstance().getHeadDatabaseAPI().getItemHead(id);

        if(itemStack == null){
            itemStack = new ItemStack(Material.ZOMBIE_HEAD);
        }

        return itemStack;
    }

}
