package fr.kizyow.mobshop.inventories;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.*;
import fr.kizyow.mobshop.managers.ShopManager;
import fr.kizyow.mobshop.utils.ItemConverter;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CategoryInventory {

    private final Plugin plugin;
    private final InventoryData inventoryData;

    public CategoryInventory(Plugin plugin) {
        this.plugin = plugin;
        this.inventoryData = plugin.getCategoryConfig().getInventoryCategory();

    }

    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .manager(plugin.getInventoryManager())
                .provider(new Provider(inventoryData.getSettingData(), inventoryData.getItems(), plugin))
                .size(inventoryData.getRow(), inventoryData.getColumn())
                .title(inventoryData.getTitle())
                .build();
    }

    static class Provider implements InventoryProvider {

        private final SettingData settingData;
        private final List<ItemData> itemDataList;
        private final Plugin plugin;

        public Provider(SettingData settingData, List<ItemData> itemDataList, Plugin plugin) {
            this.settingData = settingData;
            this.itemDataList = itemDataList;
            this.plugin = plugin;
        }

        @Override
        public void init(Player player, InventoryContents contents) {

            for (ItemData itemData : itemDataList) {

                ItemStack itemStack = itemData.getItem();
                ActionData actionData = itemData.getAction();

                if (actionData == ActionData.CLOSE) {
                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.of(itemStack,
                            event -> player.closeInventory()));

                } else {
                    contents.set(itemData.getRow(), itemData.getColumn(), ClickableItem.empty(itemStack));

                }

            }

            for (EntityType entityType : plugin.getMobShopConfig().getShopEntities()) {

                List<String> loreClone = new ArrayList<>(settingData.getLore());
                ItemStack itemStack = ItemConverter.getItem(settingData.getMaterial(), settingData.getTitle(), loreClone, entityType, false);
                ItemConverter.replaceOfferTag(itemStack, countMob(entityType));

                ClickableItem item = ClickableItem.of(itemStack, event -> {
                    ShopInventory shopInventory = new ShopInventory(plugin, entityType);
                    shopInventory.getInventory().open(player);
                });

                contents.add(item);

            }

        }

        @Override
        public void update(Player player, InventoryContents contents) {

        }

        private Integer countMob(EntityType entityType) {

            ShopManager shopManager = plugin.getShopManager();
            return Math.toIntExact(shopManager.getMobDataMap()
                    .values()
                    .stream()
                    .filter(mobData -> mobData.getEntityType() == entityType)
                    .count());

        }

    }

}
