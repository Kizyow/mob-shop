package fr.kizyow.mobshop.configurations;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.InventoryData;
import fr.kizyow.mobshop.datas.ItemData;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ConfirmConfig extends AbstractConfig {

    public ConfirmConfig(Plugin plugin){
        super(plugin, "confirm.yml");
    }

    public InventoryData getInventoryConfirm(){

        ConfigurationSection inventorySection = getConfig().getConfigurationSection("confirm");
        String inventoryTitle = inventorySection.getString("title");
        Integer inventorySize = inventorySection.getInt("size");

        ConfigurationSection itemSection = inventorySection.getConfigurationSection("items");
        List<ItemData> itemDataList = new ArrayList<>();

        for(String item : itemSection.getKeys(false)){
            String itemMaterial = itemSection.getString(item + ".material");
            String action = itemSection.getString(item + ".action");
            Integer itemSlot = itemSection.getInt(item + ".slot");
            String itemTitle = itemSection.getString(item + ".title");
            List<String> itemLore = itemSection.getStringList(item + ".lore");

            ItemData itemData = new ItemData(itemMaterial, action, itemSlot, itemTitle, itemLore);
            itemDataList.add(itemData);

        }

        return new InventoryData(inventoryTitle, inventorySize, null, itemDataList);

    }

}