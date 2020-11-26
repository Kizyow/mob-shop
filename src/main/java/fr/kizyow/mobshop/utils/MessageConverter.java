package fr.kizyow.mobshop.utils;

import fr.kizyow.mobshop.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;

public class MessageConverter {

    public static String convert(String message, EntityType type, double price, OfflinePlayer buyer){



        if(type != null){
            String entityNameShop = Plugin.getInstance().getMobShopConfig().getShopEntitiesName().get(type);
            message = message.replace("<entity_name>", entityNameShop);
            message = message.replace("<entity_type>", type.name());
        }

        if(buyer != null) {
            message = message.replace("<buyer>", buyer.getName());
        }

        message = message.replace("<price>", String.valueOf(price));

        return ChatColor.translateAlternateColorCodes('&', message);

    }

}
