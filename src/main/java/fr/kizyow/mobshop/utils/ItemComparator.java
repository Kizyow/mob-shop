package fr.kizyow.mobshop.utils;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.MobData;
import fr.minuskube.inv.ClickableItem;

import java.util.Comparator;
import java.util.List;

public class ItemComparator implements Comparator<ClickableItem> {

    @Override
    public int compare(ClickableItem clickableItem1, ClickableItem clickableItem2){


        List<String> lore1 = clickableItem1.getItem().getItemMeta().getLore();
        String idRaw1 = lore1.get(lore1.size() - 1);
        Integer id1 = Integer.valueOf(idRaw1.split(" ")[1]);

        List<String> lore2 = clickableItem2.getItem().getItemMeta().getLore();
        String idRaw2 = lore2.get(lore1.size() - 1);
        Integer id2 = Integer.valueOf(idRaw2.split(" ")[1]);

        MobData mobData1 = Plugin.getInstance().getShopManager().getMobDataMap().get(id1);
        MobData mobData2 = Plugin.getInstance().getShopManager().getMobDataMap().get(id2);

        return Double.compare(mobData1.getPrice(), mobData2.getPrice());
    }

}
