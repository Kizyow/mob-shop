package fr.kizyow.mobshop.managers;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.MobData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShopManager {

    private final Plugin plugin;
    private final Map<Integer, MobData> mobDataMap;
    private Integer mobId = 0;

    public ShopManager(Plugin plugin){
        this.plugin = plugin;
        this.mobDataMap = new HashMap<>();
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
        player.sendMessage(ChatColor.WHITE + "Tu as vendu ton animal à la boucherie, tu as reçu " + ChatColor.GREEN + price + "$");
        entity.remove();
    }

    public void sellMobShop(Entity entity, double price, Player player){
        MobData mobData = new MobData(entity.getType(), player.getUniqueId(), price);
        mobDataMap.put(mobId++, mobData);
        player.sendMessage(ChatColor.WHITE + "Ton animal a été mis en vente à la boutique, tu recevras " +
                ChatColor.GREEN + price + "$" + ChatColor.RESET + " quand un joueur aura acheté ton animal.");
        entity.remove();
    }

}
