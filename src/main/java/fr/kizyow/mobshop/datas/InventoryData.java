package fr.kizyow.mobshop.datas;

import java.util.List;

public class InventoryData {

    private final String title;
    private final Integer size;
    private final SettingData settingData;
    private final List<ItemData> itemData;
    private final List<DecorativeData> decorativeData;

    public InventoryData(String title, Integer size, SettingData settingData, List<ItemData> itemData, List<DecorativeData> decorativeData) {
        this.title = title;
        this.size = size;
        this.settingData = settingData;
        this.itemData = itemData;
        this.decorativeData = decorativeData;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getRow() {
        return size / 9;
    }

    public Integer getColumn() {
        return 9;
    }

    public SettingData getSettingData() {
        return settingData;
    }

    public List<ItemData> getItems() {
        return itemData;
    }

    public List<DecorativeData> getDecorativeData() {
        return decorativeData;
    }

}
