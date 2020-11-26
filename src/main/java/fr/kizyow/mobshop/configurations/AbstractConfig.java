package fr.kizyow.mobshop.configurations;

import fr.kizyow.mobshop.Plugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class AbstractConfig {

    private final Plugin plugin;
    private final String configName;
    private File file;
    private FileConfiguration config;

    public AbstractConfig(Plugin plugin, String configName) {
        this.plugin = plugin;
        this.configName = configName;
        this.createConfig();
    }

    private void createConfig() {
        file = new File(plugin.getDataFolder(), configName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(configName, false);
        }

        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void loadConfig() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

}