package com.github.WatermanMC.WaterFly.commands;

import com.github.WatermanMC.WaterFly.WaterFly;
import com.github.WatermanMC.WaterFly.managers.ConfigManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class FlyspeedCommand implements CommandExecutor {
    private final WaterFly plugin;
    private final NamespacedKey flySpeedKey;
    private ConfigManager configManager;
    private MiniMessage miniMessage;

    public FlyspeedCommand(WaterFly plugin, ConfigManager configManager, NamespacedKey flySpeedKey) {
        this.plugin = plugin;
        this.flySpeedKey = flySpeedKey;
        this.configManager = configManager;
        miniMessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        if (!(sender.hasPermission("waterfly.command.flyspeed"))) {
            sender.sendMessage(miniMessage.deserialize(configManager.getMessage("nopermission")));
            return true;
        }

        if (args.length == 1) {
            try {
                float input = Float.parseFloat(args[0]);
                float finalSpeed = input / 10.0f;

                if (finalSpeed < -1.0f || finalSpeed > 1.0f) {
                    player.sendMessage(miniMessage.deserialize(configManager.getConfig().getString("flyspeed.invalidinput")));
                    return true;
                }

                player.setFlySpeed(finalSpeed);
                player.getPersistentDataContainer().set(flySpeedKey, PersistentDataType.FLOAT, finalSpeed);
                
                player.sendMessage(miniMessage.deserialize(configManager.getConfig().getString("flyspeed.set")
                        .replace("{speed}", String.valueOf(input))));
            } catch (NumberFormatException e) {
                player.sendMessage(miniMessage.deserialize(configManager.getConfig().getString("flyspeed.invalidnumber")
                        .replace("{number}", args[0])));
            }
            return true;
        }

        player.sendMessage(miniMessage.deserialize("<red>Usage: /flyspeed <1-10>"));
        return true;
    }
}