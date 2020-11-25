package fr.kizyow.mobshop.commands;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.inventories.ShopInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobShopCommand implements CommandExecutor {

    private final Plugin plugin;

    public MobShopCommand(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(sender instanceof Player){

            Player player = (Player) sender;

            ShopInventory shopInventory = new ShopInventory(plugin);
            shopInventory.getInventory().open(player);

        }

        if(args.length >= 1){

            String subCommand = args[0];

            if(subCommand.equalsIgnoreCase("reload")){
                plugin.reloadConfig();
                plugin.getSellConfig().reloadConfig();
                plugin.getShopConfig().reloadConfig();

                sender.sendMessage(ChatColor.GREEN + "All configurations were reloaded!");

            }

        }

        return true;

    }

}
