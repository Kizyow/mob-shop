package fr.kizyow.mobshop;

import fr.kizyow.mobshop.commands.MobShopCommand;
import fr.kizyow.mobshop.configurations.*;
import fr.kizyow.mobshop.listeners.MobInteractListener;
import fr.kizyow.mobshop.managers.ShopManager;
import fr.minuskube.inv.InventoryManager;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private static Plugin instance;

    private final MobShopConfig mobShopConfig = new MobShopConfig(this);
    private final SellConfig sellConfig = new SellConfig(this);
    private final ShopConfig shopConfig = new ShopConfig(this);
    private final ConfirmConfig confirmConfig = new ConfirmConfig(this);
    private final CategoryConfig categoryConfig = new CategoryConfig(this);
    private final MessageConfig messageConfig = new MessageConfig(this);

    private ShopManager shopManager;
    private InventoryManager inventoryManager;
    private HeadDatabaseAPI headDatabaseAPI;
    private Economy economy;

    @Override
    public void onEnable(){
        instance = this;

        this.shopManager = new ShopManager(this);

        this.inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        this.headDatabaseAPI = new HeadDatabaseAPI();

        this.setupEconomy();

        MobInteractListener mobInteractListener = new MobInteractListener(this);
        Bukkit.getPluginManager().registerEvents(mobInteractListener, this);

        MobShopCommand mobShopCommand = new MobShopCommand(this);
        getCommand("mobshop").setExecutor(mobShopCommand);

    }

    @Override
    public void onDisable(){}

    private void setupEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault") == null){
            return;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            return;
        }

        economy = rsp.getProvider();
    }

    // Getter

    public static Plugin getInstance(){
        return instance;
    }

    public MobShopConfig getMobShopConfig(){
        return mobShopConfig;
    }

    public SellConfig getSellConfig(){
        return sellConfig;
    }

    public ShopConfig getShopConfig(){
        return shopConfig;
    }

    public ConfirmConfig getConfirmConfig(){
        return confirmConfig;
    }

    public CategoryConfig getCategoryConfig(){
        return categoryConfig;
    }

    public MessageConfig getMessageConfig(){
        return messageConfig;
    }

    public ShopManager getShopManager(){
        return shopManager;
    }

    public InventoryManager getInventoryManager(){
        return inventoryManager;
    }

    public HeadDatabaseAPI getHeadDatabaseAPI(){
        return headDatabaseAPI;
    }

    public Economy getEconomy(){
        return economy;
    }

}
