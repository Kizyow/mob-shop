package fr.kizyow.mobshop.listeners;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.managers.ShopManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerJoinListener implements Listener {

    private final Plugin plugin;

    public PlayerJoinListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        ShopManager shopManager = plugin.getShopManager();

        if (shopManager.getDataOfflinePlayer().containsKey(player.getUniqueId())) {

            String title = ChatColor.translateAlternateColorCodes('&', plugin.getMessageConfig().getPaperTitle());
            List<String> lore = shopManager.getDataOfflinePlayer().get(player.getUniqueId());

            ItemStack itemStack = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(title);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            shopManager.getDataOfflinePlayer().remove(player.getUniqueId());

            player.getInventory().addItem(itemStack);

        }

    }

}

