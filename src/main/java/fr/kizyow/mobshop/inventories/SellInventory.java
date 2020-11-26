package fr.kizyow.mobshop.inventories;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.ActionData;
import fr.kizyow.mobshop.datas.InventoryData;
import fr.kizyow.mobshop.datas.ItemData;
import fr.kizyow.mobshop.managers.ShopManager;
import fr.kizyow.mobshop.utils.ItemConverter;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SellInventory {

    private final Plugin plugin;
    private final ShopManager shopManager;
    private final InventoryData inventoryData;
    private final Entity entity;

    public SellInventory(Plugin plugin, Entity entity) {
        this.plugin = plugin;
        this.shopManager = plugin.getShopManager();
        this.entity = entity;

        boolean canButchered = plugin.getMobShopConfig().getButcherEntities().stream().anyMatch(type -> type == entity.getType());
        if (canButchered) {
            this.inventoryData = plugin.getSellConfig().getInventoryButcheryAndShop();
        } else {
            this.inventoryData = plugin.getSellConfig().getInventoryShopOnly();
        }

    }

    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .manager(plugin.getInventoryManager())
                .provider(new Provider(inventoryData.getItems(), shopManager, entity))
                .size(inventoryData.getRow(), inventoryData.getColumn())
                .title(inventoryData.getTitle())
                .build();
    }

    static class Provider implements InventoryProvider {

        private final List<ItemData> items;
        private final ShopManager shopManager;
        private final Entity entity;

        public Provider(List<ItemData> items, ShopManager shopManager, Entity entity) {
            this.items = items;
            this.shopManager = shopManager;
            this.entity = entity;
        }

        @Override
        public void init(Player player, InventoryContents contents) {

            for (ItemData itemData : items) {

                EntityType entityType = entity.getType();
                ActionData actionData = itemData.getAction();
                ItemStack itemStack = itemData.getItem(entityType);

                if (actionData == ActionData.SELL_BUTCHER) {
                    double priceButcher = shopManager.generatePriceButcher(entityType);
                    ItemConverter.replacePriceTag(itemStack, priceButcher);

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> {
                                shopManager.sellMobButcher(entity, priceButcher, player);
                                player.closeInventory();
                            }));

                } else if (actionData == ActionData.SELL_SHOP) {
                    double priceShop = shopManager.generatePriceShop(entityType);
                    ItemConverter.replacePriceTag(itemStack, priceShop);

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> {
                                shopManager.sellMobShop(entity, priceShop, player);
                                player.closeInventory();
                            }));

                } else if (actionData == ActionData.CLOSE) {

                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> player.closeInventory()));

                } else {
                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.empty(itemStack));
                }

            }

        }

        @Override
        public void update(Player player, InventoryContents contents) {

        }

    }

}
