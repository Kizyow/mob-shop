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
    private final ItemStack item;
    private final Integer id;

    private final ShopManager shopManager;
    private final InventoryData inventoryData;

    public ConfirmInventory(Plugin plugin, ItemStack item, Integer id) {
        this.plugin = plugin;
        this.item = item;
        this.id = id;
        this.shopManager = plugin.getShopManager();
        this.inventoryData = plugin.getConfirmConfig().getInventoryConfirm();
    }

    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .manager(plugin.getInventoryManager())
                .provider(new Provider(plugin, item, id, inventoryData, shopManager))
                .size(inventoryData.getRow(), inventoryData.getColumn())
                .title(inventoryData.getTitle())
                .build();
    }

    static class Provider implements InventoryProvider {

        private final Plugin plugin;
        private final ItemStack item;
        private final Integer id;
        private final List<ItemData> itemDataList;
        private final List<DecorativeData> decorativeData;
        private final ShopManager shopManager;

        public Provider(Plugin plugin, ItemStack item, Integer id, InventoryData inventoryData, ShopManager shopManager) {
            this.plugin = plugin;
            this.item = item;
            this.id = id;
            this.itemDataList = inventoryData.getItems();
            this.decorativeData = inventoryData.getDecorativeData();
            this.shopManager = shopManager;
        }

        @Override
        public void init(Player player, InventoryContents contents) {

            for (ItemData itemData : itemDataList) {

                ActionData actionData = itemData.getAction();
                MobData mobData = shopManager.getMobDataMap().get(id);

                OfflinePlayer author = Bukkit.getOfflinePlayer(mobData.getUUID());
                double price = mobData.getPrice();
                EntityType entityType = mobData.getEntityType();

                ItemStack itemStack = itemData.getItem(entityType);
                ItemConverter.replaceShopTag(itemStack, author.getName(), price, mobData.getTimeLeft());

                if (actionData == ActionData.PREDEFINE) {

                    itemStack = itemData.getPredefinedItem(item, entityType);
                    ItemConverter.replaceShopTag(itemStack, author.getName(), price, mobData.getTimeLeft());

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.empty(itemStack));

                } else if (actionData == ActionData.CONFIRM) {

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> shopManager.buyMob(player, id, entityType)));

                } else if (actionData == ActionData.REFUSE) {

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> {
                                ShopInventory shopInventory = new ShopInventory(plugin, entityType);
                                shopInventory.getInventory().open(player);
                            }));

                } else if (actionData == ActionData.CLOSE) {

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> player.closeInventory()));

                } else {
                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.empty(itemStack));

                }

            }

            for (DecorativeData decorative : decorativeData) {

                ItemStack itemStack = decorative.getItem();

                for (Integer slot : decorative.getSlots()) {

                    int row = slot / 9;
                    int column = slot % 9;

                    contents.set(row, column, ClickableItem.empty(itemStack));

                }

            }

        }

        @Override
        public void update(Player player, InventoryContents contents) {

        }

    }

}
