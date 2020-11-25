package fr.kizyow.mobshop.datas;

import java.util.List;

public class SettingData {

    private final String material;
    private final String title;
    private final List<String> lore;
    private final Integer itemPerPage;

    public SettingData(String material, String title, List<String> lore, Integer itemPerPage){
        this.material = material;
        this.title = title;
        this.lore = lore;
        this.itemPerPage = itemPerPage;
    }

    public String getMaterial(){
        return material;
    }

    public String getTitle(){
        return title;
    }

    public List<String> getLore(){
        return lore;
    }

    public Integer getItemPerPage(){
        return itemPerPage;
    }

}
