package fr.kizyow.mobshop.configurations;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.DecorativeData;
import fr.kizyow.mobshop.datas.InventoryData;
import fr.kizyow.mobshop.datas.ItemData;
import fr.kizyow.mobshop.datas.SettingData;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ShopConfig extends AbstractConfig {

    public ShopConfig(Plugin plugin) {
        super(plugin, "shop.yml");
    }

    public InventoryData getInventoryShop() {

        ConfigurationSection inventorySection = getConfig().getConfigurationSection("shop");
        String inventoryTitle = inventorySection.getString("title");
        Integer inventorySize = inventorySection.getInt("size");

        ConfigurationSection settingSection = inventorySection.getConfigurationSection("settings");

        String material = settingSection.getString("material");
        String title = settingSection.getString("title");
        List<String> lore = settingSection.getStringList("lore");
        Integer itemPerPage = settingSection.getInt("item-per-page");

        SettingData settingData = new SettingData(material, title, lore, itemPerPage);

        ConfigurationSection itemSection = inventorySection.getConfigurationSection("items");
        List<ItemData> itemDataList = new ArrayList<>();

        if (itemSection != null) {
            for (String item : itemSection.getKeys(false)) {
                String itemMaterial = itemSection.getString(item + ".material");
                String action = itemSection.getString(item + ".action");
                Integer itemSlot = itemSection.getInt(item + ".slot");
                String itemTitle = itemSection.getString(item + ".title");
                List<String> itemLore = itemSection.getStringList(item + ".lore");

                ItemData itemData = new ItemData(itemMaterial, action, itemSlot, itemTitle, itemLore);
                itemDataList.add(itemData);

            }
        }

        ConfigurationSection decorativeSection = inventorySection.getConfigurationSection("decorative");
        List<DecorativeData> decorativeDataList = new ArrayList<>();

        if (decorativeSection != null) {
            for (String item : decorativeSection.getKeys(false)) {
                String itemMaterial = decorativeSection.getString(item + ".material");
                String itemTitle = decorativeSection.getString(item + ".title");
                List<String> itemLore = decorativeSection.getStringList(item + ".lore");
                List<Integer> itemSlots = decorativeSection.getIntegerList(item + ".slots");

                DecorativeData decorativeData = new DecorativeData(itemMaterial, itemTitle, itemLore, itemSlots);
                decorativeDataList.add(decorativeData);

            }
        }

        return new InventoryData(inventoryTitle, inventorySize, settingData, itemDataList, decorativeDataList);

    }

}