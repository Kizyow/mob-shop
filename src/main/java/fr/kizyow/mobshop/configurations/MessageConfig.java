package fr.kizyow.mobshop.configurations;

import fr.kizyow.mobshop.Plugin;

public class MessageConfig extends AbstractConfig {

    public MessageConfig(Plugin plugin){
        super(plugin, "messages.yml");
    }

    public String getMobSoldShop(){
        return getConfig().getString("mob-sold-shop");
    }

    public String getMobSoldButcher(){
        return getConfig().getString("mob-sold-butcher");
    }

    public String getSellerBuyMob(){
        return getConfig().getString("seller-buy-mob");
    }

    public String getBuyerBuyMob(){
        return getConfig().getString("buyer-buy-mob");
    }

    public String getErrorWrongWorld(){
        return getConfig().getString("error-wrong-world");
    }

    public String getErrorInsufficientFounds(){
        return getConfig().getString("error-insufficient-funds");
    }

    public String getErrorMobAlreadyBought(){
        return getConfig().getString("error-mob-already-bought");
    }

    public String getTimeOut(){
        return getConfig().getString("time-out");
    }

}