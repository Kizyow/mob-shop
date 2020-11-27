package fr.kizyow.mobshop.commands;

import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.inventories.CategoryInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MobShopCommand implements CommandExecutor, TabExecutor {

    private final Plugin plugin;

    public MobShopCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                CategoryInventory categoryInventory = new CategoryInventory(plugin);
                categoryInventory.getInventory().open(player);

            }

        }

        if (args.length >= 1 && sender.isOp()) {

            String subCommand = args[0];

            if (subCommand.equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                plugin.getSellConfig().reloadConfig();
                plugin.getShopConfig().reloadConfig();
                plugin.getCategoryConfig().reloadConfig();
                plugin.getConfirmConfig().reloadConfig();

                sender.sendMessage(ChatColor.GREEN + "All configurations were reloaded!");

            }

        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length >= 1 && sender.isOp()) {
            return Collections.singletonList("reload");
        }

        return Collections.emptyList();

    }

}
