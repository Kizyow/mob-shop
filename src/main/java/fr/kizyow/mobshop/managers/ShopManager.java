package fr.kizyow.mobshop.managers;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.MobData;
import fr.kizyow.mobshop.inventories.ConfirmInventory;
import fr.kizyow.mobshop.inventories.ShopInventory;
import fr.kizyow.mobshop.utils.JsonData;
import fr.kizyow.mobshop.utils.MessageConverter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShopManager {

    private final Plugin plugin;
    private final Map<Integer, MobData> mobDataMap;
    private Integer mobId = 0;

    public ShopManager(Plugin plugin){
        this.plugin = plugin;
        this.mobDataMap = JsonData.loadData();

        if(!mobDataMap.isEmpty()){
            mobId = Collections.max(mobDataMap.keySet()) + 1;
        }

    }

    public boolean checkTimeLeft(Integer id){
        MobData mobData = mobDataMap.get(id);
        boolean timeOut = (mobData.getExpireAt() - System.currentTimeMillis()) < 0;

        if(timeOut){
            OfflinePlayer author = Bukkit.getOfflinePlayer(mobData.getUUID());
            plugin.getEconomy().depositPlayer(author, mobData.getPrice() / 2);

            if(author.isOnline()){
                Player authorPlayer = author.getPlayer();
                String message = plugin.getMessageConfig().getTimeOut();
                message = MessageConverter.convert(message, mobData.getEntityType(), mobData.getPrice(), author);
                authorPlayer.sendMessage(message);
                authorPlayer.playSound(authorPlayer.getLocation(), "entity." + mobData.getEntityType().getKey().getKey() + ".hurt", 1.0F, 1.0F);
            }

            mobDataMap.remove(id);

        }

        return timeOut;

    }

    public Double generatePriceButcher(EntityType type){
        boolean isPresent = plugin.getMobShopConfig().getButcherEntities().stream().anyMatch(t -> t == type);
        if(!isPresent){
            return 0.0;
        }

        Random random = new Random();
        double price = plugin.getMobShopConfig().getButcherEntitiesPrices().get(type);
        double variation = random.nextInt(plugin.getMobShopConfig().getButcherVariation());
        variation = (price * variation) / 100;

        return random.nextBoolean() ? price + variation : price - variation;
    }

    public Double generatePriceShop(EntityType type){
        boolean isPresent = plugin.getMobShopConfig().getShopEntities().stream().anyMatch(t -> t == type);
        if(!isPresent){
            return 0.0;
        }

        Random random = new Random();
        double price = plugin.getMobShopConfig().getShopEntitiesPrices().get(type);
        double variation = random.nextInt(plugin.getMobShopConfig().getShopVariation());
        variation = (price * variation) / 100;

        return random.nextBoolean() ? price + variation : price - variation;
    }

    public void sellMobButcher(Entity entity, double price, Player player){
        plugin.getEconomy().depositPlayer(player, price);
        String message = plugin.getMessageConfig().getMobSoldButcher();
        message = MessageConverter.convert(message, entity.getType(), price, player);
        player.sendMessage(message);
        entity.remove();
    }

    public void sellMobShop(Entity entity, double price, Player player){
        MobData mobData = new MobData(entity.getType(), player.getUniqueId(), price);
        mobDataMap.put(mobId++, mobData);
        String message = plugin.getMessageConfig().getMobSoldShop();
        message = MessageConverter.convert(message, entity.getType(), price, player);
        player.sendMessage(message);
        entity.remove();
    }

    public void confirmItem(ItemStack itemStack, Player player, EntityType entityType){

        List<String> lore = itemStack.getItemMeta().getLore();
        String idRaw = lore.get(lore.size() - 1);
        Integer id = Integer.valueOf(idRaw.split(" ")[1]);

        if(alreadyBuy(player, id, entityType)) return;
        if(!player.getWorld().getName().equalsIgnoreCase("world")){
            String message = plugin.getMessageConfig().getErrorWrongWorld();
            message = MessageConverter.convert(message, entityType, 0, player);
            player.sendMessage(message);
            return;
        }

        ConfirmInventory confirmInventory = new ConfirmInventory(plugin, itemStack, id);
        confirmInventory.getInventory().open(player);

    }

    public void buyMob(Player player, Integer id, EntityType entityType){

        if(alreadyBuy(player, id, entityType)) return;

        MobData mobData = mobDataMap.get(id);
        double economy = plugin.getEconomy().getBalance(player);

        OfflinePlayer author = Bukkit.getOfflinePlayer(mobData.getUUID());
        double price = mobData.getPrice();

        if(economy < price){
            String message = plugin.getMessageConfig().getErrorInsufficientFounds();
            message = MessageConverter.convert(message, entityType, price, player);
            player.sendMessage(message);

            ShopInventory shopInventory = new ShopInventory(plugin, entityType);
            shopInventory.getInventory().open(player);
            return;
        }

        plugin.getEconomy().depositPlayer(author, price);
        plugin.getEconomy().withdrawPlayer(player, price);

        String message = plugin.getMessageConfig().getBuyerBuyMob();
        message = MessageConverter.convert(message, entityType, price, author);
        player.sendMessage(message);

        if(author.isOnline()){
            Player authorPlayer = author.getPlayer();
            message = plugin.getMessageConfig().getSellerBuyMob();
            message = MessageConverter.convert(message, entityType, price, player);
            authorPlayer.sendMessage(message);
            authorPlayer.playSound(authorPlayer.getLocation(), "entity." + entityType.getKey().getKey() + ".ambient", 1.0F, 1.0F);
        }

        player.getWorld().spawnEntity(player.getLocation(), entityType);
        mobDataMap.remove(id);

        ShopInventory shopInventory = new ShopInventory(plugin, entityType);
        shopInventory.getInventory().open(player);

    }

    private boolean alreadyBuy(Player player, Integer id, EntityType entityType){

        if(!mobDataMap.containsKey(id)) {
            ShopInventory shopInventory = new ShopInventory(plugin, entityType);
            shopInventory.getInventory().open(player);
            String message = plugin.getMessageConfig().getErrorMobAlreadyBought();
            message = MessageConverter.convert(message, entityType, 0, player);
            player.sendMessage(message);
            return true;

        }

        return false;

    }

    public Map<Integer, MobData> getMobDataMap(){
        return mobDataMap;
    }

}
