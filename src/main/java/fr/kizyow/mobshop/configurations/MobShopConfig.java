package fr.kizyow.mobshop.configurations;

import fr.kizyow.mobshop.Plugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MobShopConfig {

    private final Plugin plugin;

    public MobShopConfig(Plugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();

    }

    public List<EntityType> getShopEntities() {
        List<String> entityString = plugin.getConfig().getStringList("entity-sell-shop");
        return entityString.stream().map(EntityType::valueOf).collect(Collectors.toList());
    }

    public Map<EntityType, String> getShopEntitiesName() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("entity-name-shop");
        Map<EntityType, String> outMap = new HashMap<>();
        Map<String, Object> values = section.getValues(true);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            EntityType entityType = EntityType.valueOf(entry.getKey().toUpperCase());
            String name = (String) entry.getValue();
            outMap.put(entityType, name);

        }

        return outMap;
    }

    public Map<EntityType, List<String>> getShopEntitiesHead() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("entity-head-shop");
        Map<EntityType, List<String>> outMap = new HashMap<>();
        Map<String, Object> values = section.getValues(true);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            EntityType entityType = EntityType.valueOf(entry.getKey().toUpperCase());
            List<String> headString = (List<String>) entry.getValue();
            outMap.put(entityType, headString);

        }

        return outMap;
    }

    public Map<EntityType, Double> getShopEntitiesPrices() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("entity-price-shop");
        Map<EntityType, Double> outMap = new HashMap<>();
        Map<String, Object> values = section.getValues(true);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            EntityType entityType = EntityType.valueOf(entry.getKey().toUpperCase());
            String stringPrice = String.valueOf(entry.getValue());
            Double price = Double.parseDouble(stringPrice);
            outMap.put(entityType, price);

        }

        return outMap;
    }

    public Integer getShopVariation() {
        return plugin.getConfig().getInt("price-variation-shop");
    }

    public List<EntityType> getButcherEntities() {
        List<String> entityString = plugin.getConfig().getStringList("entity-sell-butcher");
        return entityString.stream().map(EntityType::valueOf).collect(Collectors.toList());
    }

    public Map<EntityType, String> getButcherEntitiesName() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("entity-name-butcher");
        Map<EntityType, String> outMap = new HashMap<>();
        Map<String, Object> values = section.getValues(true);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            EntityType entityType = EntityType.valueOf(entry.getKey().toUpperCase());
            String name = (String) entry.getValue();
            outMap.put(entityType, name);

        }

        return outMap;
    }

    public Map<EntityType, String> getButcherEntitiesHead() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("entity-head-butcher");
        Map<EntityType, String> outMap = new HashMap<>();
        Map<String, Object> values = section.getValues(true);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            EntityType entityType = EntityType.valueOf(entry.getKey().toUpperCase());
            String headString = (String) entry.getValue();
            outMap.put(entityType, headString);

        }

        return outMap;
    }

    public Map<EntityType, Double> getButcherEntitiesPrices() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("entity-price-butcher");
        Map<EntityType, Double> outMap = new HashMap<>();
        Map<String, Object> values = section.getValues(true);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            EntityType entityType = EntityType.valueOf(entry.getKey().toUpperCase());
            String stringPrice = String.valueOf(entry.getValue());
            Double price = Double.parseDouble(stringPrice);
            outMap.put(entityType, price);

        }

        return outMap;
    }

    public Integer getButcherVariation() {
        return plugin.getConfig().getInt("price-variation-butcher");
    }

}
