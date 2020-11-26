package fr.kizyow.mobshop.inventories;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.*;
import fr.kizyow.mobshop.managers.ShopManager;
import fr.kizyow.mobshop.utils.ItemConverter;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfirmInventory {

    private final Plugin plugin;
    private final Integer id;

    private final ShopManager shopManager;
    private final InventoryData inventoryData;

    public ConfirmInventory(Plugin plugin, Integer id){
        this.plugin = plugin;
        this.id = id;
        this.shopManager = plugin.getShopManager();
        this.inventoryData = plugin.getConfirmConfig().getInventoryConfirm();

    }

    public SmartInventory getInventory(){
        return SmartInventory.builder()
                .manager(plugin.getInventoryManager())
                .provider(new Provider(plugin, id, inventoryData.getItems(), shopManager))
                .size(inventoryData.getRow(), inventoryData.getColumn())
                .title(inventoryData.getTitle())
                .build();
    }

    static class Provider implements InventoryProvider {

        private final Plugin plugin;
        private final Integer id;
        private final List<ItemData> itemDataList;
        private final ShopManager shopManager;

        public Provider(Plugin plugin, Integer id, List<ItemData> itemDataList, ShopManager shopManager){
            this.plugin = plugin;
            this.id = id;
            this.itemDataList = itemDataList;
            this.shopManager = shopManager;
        }

        @Override
        public void init(Player player, InventoryContents contents){

            for(ItemData itemData : itemDataList){

                ActionData actionData = itemData.getAction();
                MobData mobData = shopManager.getMobDataMap().get(id);

                OfflinePlayer author = Bukkit.getOfflinePlayer(mobData.getUUID());
                double price = mobData.getPrice();
                EntityType entityType = mobData.getEntityType();

                ItemStack itemStack = itemData.getItem(entityType);
                ItemConverter.replaceShopTag(itemStack, author.getName(), price, mobData.getTimeLeft());

                if(actionData == ActionData.CONFIRM){

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> {
                                shopManager.buyMob(player, id, entityType);
                            }));

                } else if(actionData == ActionData.REFUSE){

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> {
                                ShopInventory shopInventory = new ShopInventory(plugin, entityType);
                                shopInventory.getInventory().open(player);
                            }));

                } else if(actionData == ActionData.CLOSE){

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> player.closeInventory()));

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
