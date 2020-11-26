package fr.kizyow.mobshop.configurations;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.InventoryData;
import fr.kizyow.mobshop.datas.ItemData;
import fr.kizyow.mobshop.datas.SettingData;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CategoryConfig extends AbstractConfig {

    public CategoryConfig(Plugin plugin) {
        super(plugin, "category.yml");
    }

    public InventoryData getInventoryCategory() {

        ConfigurationSection inventorySection = getConfig().getConfigurationSection("category");
        String inventoryTitle = inventorySection.getString("title");
        Integer inventorySize = inventorySection.getInt("size");

        ConfigurationSection settingSection = inventorySection.getConfigurationSection("settings");

        String material = settingSection.getString("material");
        String title = settingSection.getString("title");
        List<String> lore = settingSection.getStringList("lore");

        SettingData settingData = new SettingData(material, title, lore, 0);

        ConfigurationSection itemSection = inventorySection.getConfigurationSection("items");
        List<ItemData> itemDataList = new ArrayList<>();

        for (String item : itemSection.getKeys(false)) {
            String itemMaterial = itemSection.getString(item + ".material");
            String action = itemSection.getString(item + ".action");
            Integer itemSlot = itemSection.getInt(item + ".slot");
            String itemTitle = itemSection.getString(item + ".title");
            List<String> itemLore = itemSection.getStringList(item + ".lore");

            ItemData itemData = new ItemData(itemMaterial, action, itemSlot, itemTitle, itemLore);
            itemDataList.add(itemData);

        }

        return new InventoryData(inventoryTitle, inventorySize, settingData, itemDataList);

    }

}