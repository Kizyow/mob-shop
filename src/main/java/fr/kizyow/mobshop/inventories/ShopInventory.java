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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopInventory {

    private final Plugin plugin;
    private final ShopManager shopManager;
    private final InventoryData inventoryData;

    public ShopInventory(Plugin plugin){
        this.plugin = plugin;
        this.shopManager = plugin.getShopManager();
        this.inventoryData = plugin.getShopConfig().getInventoryShop();

    }

    public SmartInventory getInventory(){
        return SmartInventory.builder()
                .manager(plugin.getInventoryManager())
                .provider(new Provider(inventoryData.getSettingData(), inventoryData.getItems(), shopManager))
                .size(inventoryData.getRow(), inventoryData.getColumn())
                .title(inventoryData.getTitle())
                .build();
    }

    static class Provider implements InventoryProvider {

        private final SettingData settingData;
        private final List<ItemData> itemDataList;
        private final ShopManager shopManager;

        public Provider(SettingData settingData, List<ItemData> itemDataList, ShopManager shopManager){
            this.settingData = settingData;
            this.itemDataList = itemDataList;
            this.shopManager = shopManager;
        }

        @Override
        public void init(Player player, InventoryContents contents){

            Pagination pagination = contents.pagination();
            ClickableItem[] items = new ClickableItem[shopManager.getMobDataMap().keySet().size()];

            for(int id = 0; id < items.length; id++){

                MobData mobData = shopManager.getMobDataMap().get(id);
                OfflinePlayer author = Bukkit.getOfflinePlayer(mobData.getUUID());
                double price = mobData.getPrice();
                EntityType entityType = mobData.getEntityType();
                settingData.getLore().add("&8ID: " + id);

                ItemStack itemStack = ItemConverter.getItem(settingData.getMaterial(), settingData.getTitle(), settingData.getLore(), entityType);
                ItemConverter.replaceShopTag(itemStack, author.getName(), price);

                ClickableItem item = ClickableItem.of(itemStack, event -> {
                    shopManager.buyItem(itemStack, entityType, player);
                });

                items[id] = item;

            }

            pagination.setItems(items);
            pagination.setItemsPerPage(settingData.getItemPerPage());

            for(ItemData itemData : itemDataList){

                ActionData actionData = itemData.getAction();
                ItemStack itemStack = itemData.getItem();

                if(actionData == ActionData.NEXT_PAGE){

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> contents.inventory().open(player, pagination.previous().getPage())));

                } else if(actionData == ActionData.PREVIOUS_PAGE){

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> contents.inventory().open(player, pagination.next().getPage())));

                } else {
                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.empty(itemStack));

                }

            }

        }

        @Override
        public void update(Player player, InventoryContents contents){

        }

    }

}
