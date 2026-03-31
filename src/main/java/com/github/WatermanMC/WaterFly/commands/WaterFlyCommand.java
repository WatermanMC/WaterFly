package com.github.WatermanMC.WaterFly.commands;

import com.github.WatermanMC.WaterFly.WaterFly;
import com.github.WatermanMC.WaterFly.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class WaterFlyCommand implements CommandExecutor {
    private final WaterFly plugin;
    private ConfigManager configManager;
    private MiniMessage minimessage;

    public WaterFlyCommand(WaterFly plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.minimessage = MiniMessage.miniMessage();
        plugin.getCommand("waterfly").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (!(sender.hasPermission("waterfly.admin"))) {
            sender.sendMessage(minimessage.deserialize(configManager.getMessage("nopermission")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(minimessage.deserialize("<red>Usage: /waterfly <info/reload>"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                boolean success = configManager.reloadConfigs();
                if (success) {
                    sender.sendMessage(minimessage.deserialize(configManager.getMessage("pluginReloaded")));
                } else  {
                    sender.sendMessage(minimessage.deserialize("Plugin reload failed. Please check your console for error."));
                }
                return true;
            }
            case "info" -> {
                sender.sendMessage(minimessage.deserialize("<aqua><b>WaterFly <reset><white>v" + plugin.getPluginMeta().getVersion()));
                sender.sendMessage(minimessage.deserialize("<gray>Feature rich fly plugin for your server!"));
                sender.sendMessage(minimessage.deserialize("<gray>Author:" + plugin.getPluginMeta().getAuthors()));
                sender.sendMessage(minimessage.deserialize("<gold>Commands<reset>: /waterfly"));
                return true;
            }
            default -> {
                sender.sendMessage(minimessage.deserialize("<red>Usage: /waterfly <info/reload>"));
                return true;
            }
        }
    }
}
