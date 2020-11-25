package fr.kizyow.mobshop;

import fr.kizyow.mobshop.configurations.MobShopConfig;
import fr.kizyow.mobshop.configurations.SellConfig;
import fr.kizyow.mobshop.listeners.MobInteractListener;
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

    private InventoryManager inventoryManager;
    private HeadDatabaseAPI headDatabaseAPI;
    private Economy economy;

    @Override
    public void onEnable(){
        instance = this;

        this.inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        this.headDatabaseAPI = new HeadDatabaseAPI();

        this.setupEconomy();

        MobInteractListener mobInteractListener = new MobInteractListener(this);
        Bukkit.getPluginManager().registerEvents(mobInteractListener, this);

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
