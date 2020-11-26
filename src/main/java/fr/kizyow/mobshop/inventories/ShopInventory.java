package fr.kizyow.mobshop.inventories;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.*;
import fr.kizyow.mobshop.managers.ShopManager;
import fr.kizyow.mobshop.utils.ItemConverter;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopInventory {

    private final Plugin plugin;
    private final EntityType entityType;
    private final ShopManager shopManager;
    private final InventoryData inventoryData;

    public ShopInventory(Plugin plugin, EntityType entityType){
        this.plugin = plugin;
        this.entityType = entityType;
        this.shopManager = plugin.getShopManager();
        this.inventoryData = plugin.getShopConfig().getInventoryShop();

    }

    public SmartInventory getInventory(){
        return SmartInventory.builder()
                .manager(plugin.getInventoryManager())
                .provider(new Provider(inventoryData.getSettingData(), entityType, inventoryData.getItems(), shopManager))
                .size(inventoryData.getRow(), inventoryData.getColumn())
                .title(inventoryData.getTitle())
                .build();
    }

    static class Provider implements InventoryProvider {

        private final SettingData settingData;
        private final EntityType entityType;
        private final List<ItemData> itemDataList;
        private final ShopManager shopManager;

        public Provider(SettingData settingData, EntityType entityType, List<ItemData> itemDataList, ShopManager shopManager){
            this.settingData = settingData;
            this.entityType = entityType;
            this.itemDataList = itemDataList;
            this.shopManager = shopManager;
        }

        @Override
        public void init(Player player, InventoryContents contents){

            Pagination pagination = contents.pagination();
            List<ClickableItem> itemList = new ArrayList<>();

            for(Map.Entry<Integer, MobData> entry : shopManager.getMobDataMap().entrySet()){

                Integer id = entry.getKey();
                MobData mobData = entry.getValue();

                EntityType mobType = mobData.getEntityType();

                if(entityType == mobType){
                    OfflinePlayer author = Bukkit.getOfflinePlayer(mobData.getUUID());
                    double price = mobData.getPrice();
                    List<String> loreClone = new ArrayList<>(settingData.getLore());
                    loreClone.add(ChatColor.DARK_GRAY + "ID: " + id);

                    ItemStack itemStack = ItemConverter.getItem(settingData.getMaterial(), settingData.getTitle(), loreClone, entityType);
                    ItemConverter.replaceShopTag(itemStack, author.getName(), price);

                    ClickableItem item = ClickableItem.of(itemStack, event -> {
                        shopManager.confirmItem(itemStack, player, entityType);
                    });

                    itemList.add(item);

                }

            }

            ClickableItem[] items = itemList.toArray(new ClickableItem[0]);

            pagination.setItems(items);
            pagination.setItemsPerPage(settingData.getItemPerPage());

            for(ItemData itemData : itemDataList){

                ActionData actionData = itemData.getAction();
                ItemStack itemStack = itemData.getItem();

                if(actionData == ActionData.PREVIOUS_PAGE){

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> contents.inventory().open(player, pagination.previous().getPage())));

                } else if(actionData == ActionData.NEXT_PAGE){

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> contents.inventory().open(player, pagination.next().getPage())));

                } else if(actionData == ActionData.CLOSE){

                contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                        event -> player.closeInventory()));

                } else {
                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.empty(itemStack));

                }

            }

            for(ClickableItem item : pagination.getPageItems()){
                contents.add(item);
            }

        }

        @Override
        public void update(Player player, InventoryContents contents){

        }

    }

}
