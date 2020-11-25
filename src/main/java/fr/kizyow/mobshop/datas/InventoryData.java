package fr.kizyow.mobshop.datas;

import java.util.List;

public class InventoryData {

    private final String title;
    private final Integer size;
    private final List<ItemData> itemData;

    public InventoryData(String title, Integer size, List<ItemData> itemData) {
        this.title = title;
        this.size = size;
        this.itemData = itemData;
    }

    public String getTitle(){
        return title;
    }

    public Integer getSize(){
        return size;
    }

    public Integer getRow(){
        return size / 9;
    }

    public Integer getColumn(){
        return 9;
    }

    public List<ItemData> getItems(){
        return itemData;
    }

}
