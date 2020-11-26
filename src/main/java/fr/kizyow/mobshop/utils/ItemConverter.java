package fr.kizyow.mobshop.utils;

import fr.kizyow.mobshop.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ItemConverter {

    public static ItemStack getPredefinedItem(ItemStack itemStack, String title, List<String> lore, EntityType entityType) {
        customMeta(itemStack, title, lore, entityType);
        return itemStack;

    }

    public static ItemStack getItem(String strMaterial, String title, List<String> lore, EntityType entityType, boolean random) {

        ItemStack itemStack = customHead(entityType, strMaterial, random);
        if (itemStack == null) {
            Material material = Material.valueOf(strMaterial);
            itemStack = new ItemStack(material);
        }

        customMeta(itemStack, title, lore, entityType);

        return itemStack;

    }

    public static void replacePriceTag(ItemStack itemStack, double price) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> loreMeta = itemMeta.getLore().stream()
                .map(l -> l.replace("<entity_price_butcher>", String.valueOf(price)))
                .map(l -> l.replace("<entity_price_shop>", String.valueOf(price)))
                .collect(Collectors.toList());
        itemMeta.setLore(loreMeta);

        itemStack.setItemMeta(itemMeta);

    }

    public static void replaceShopTag(ItemStack itemStack, String author, double price, String timeLeft) {

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasLore()) return;

        List<String> loreMeta = itemMeta.getLore().stream()
                .map(l -> l.replace("<author>", author))
                .map(l -> l.replace("<entity_price_shop>", String.valueOf(price)))
                .map(l -> l.replace("<expire_at>", timeLeft))
                .collect(Collectors.toList());
        itemMeta.setLore(loreMeta);

        itemStack.setItemMeta(itemMeta);

    }

    public static void replaceOfferTag(ItemStack itemStack, Integer offer) {

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasLore()) return;

        List<String> loreMeta = itemMeta.getLore().stream()
                .map(l -> l.replace("<offres>", String.valueOf(offer)))
                .collect(Collectors.toList());
        itemMeta.setLore(loreMeta);

        itemStack.setItemMeta(itemMeta);

    }

    private static void customMeta(ItemStack itemStack, String title, List<String> lore, EntityType entityType) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        String titleMeta = ChatColor.translateAlternateColorCodes('&', title);
        titleMeta = replaceTag(titleMeta, entityType);
        itemMeta.setDisplayName(titleMeta);

        List<String> loreMeta = lore.stream()
                .map(l -> ChatColor.translateAlternateColorCodes('&', l))
                .map(l -> replaceTag(l, entityType))
                .collect(Collectors.toList());
        itemMeta.setLore(loreMeta);

        itemStack.setItemMeta(itemMeta);

    }

    private static String replaceTag(String string, EntityType entityType) {

        if (entityType == null) {
            return string;
        }

        String entityNameShop = Plugin.getInstance().getMobShopConfig().getShopEntitiesName().get(entityType);
        List<String> entityHeadShop = Plugin.getInstance().getMobShopConfig().getShopEntitiesHead().get(entityType);
        String entityNameButcher = Plugin.getInstance().getMobShopConfig().getButcherEntitiesName().get(entityType);
        String entityHeadButcher = Plugin.getInstance().getMobShopConfig().getButcherEntitiesHead().get(entityType);

        if (entityNameButcher != null && entityHeadButcher != null) {
            string = string.replace("<entity_name_butcher>", entityNameButcher);
            string = string.replace("<entity_head_butcher>", entityHeadButcher);
        }

        string = string.replace("<entity_name_shop>", entityNameShop);
        string = string.replace("<entity_head_shop>", entityHeadShop.get(0));
        string = string.replace("<entity_type>", entityType.name());

        return string;

    }

    private static ItemStack customHead(EntityType entityType, String material, boolean random) {

        String data = "";

        if (material.equalsIgnoreCase("<entity_head_shop>")) {

            List<String> dataHead = Plugin.getInstance().getMobShopConfig().getShopEntitiesHead().get(entityType);


            if (random) {
                Random random1 = new Random();
                int select = random1.nextInt(dataHead.size());
                data = dataHead.get(select);
            } else {
                data = dataHead.get(0);

            }

        } else if (material.equalsIgnoreCase("<entity_head_butcher>")) {
            data = Plugin.getInstance().getMobShopConfig().getButcherEntitiesHead().get(entityType);
        } else if (material.startsWith("hdb-")) {
            data = material;
        } else {
            return null;
        }

        String id = data.substring(4);
        ItemStack itemStack = Plugin.getInstance().getHeadDatabaseAPI().getItemHead(id);

        if (itemStack == null) {
            itemStack = new ItemStack(Material.ZOMBIE_HEAD);
        }

        return itemStack;
    }

}
