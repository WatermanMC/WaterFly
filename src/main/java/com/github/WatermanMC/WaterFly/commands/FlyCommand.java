package com.github.WatermanMC.WaterFly.commands;

import com.github.WatermanMC.WaterFly.WaterFly;
import com.github.WatermanMC.WaterFly.managers.ConfigManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlyCommand implements CommandExecutor, Listener {
    private final WaterFly plugin;
    private final NamespacedKey flyKey;
    private ConfigManager configManager;
    private MiniMessage miniMessage;

    public FlyCommand(WaterFly plugin,
                      ConfigManager configManager,
                      NamespacedKey flyKey) {
        this.plugin = plugin;
        this.flyKey = flyKey;
        this.configManager = configManager;
        miniMessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }

        if (!(sender.hasPermission("waterfly.command.fly"))) {
            sender.sendMessage(miniMessage.deserialize(configManager.getMessage("nopermission")));
            return true;
        }

        if (configManager.getDisabledWorlds().contains(player.getWorld().getName())) {
            player.sendMessage(miniMessage.deserialize(configManager.getConfig().getString("disabledworld")));
            return true;
        }

        if (args.length < 1) {
            boolean affectCreative = configManager.getConfig().getBoolean("affect-creative-spectators");

            if (!affectCreative && (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)) {
                player.sendMessage(miniMessage.deserialize(configManager.getMessage("invalidgamemode-for-fly")
                        .replace("{gamemode}", player.getGameMode().toString())));
                return true;
            }

            String currentWorld = player.getWorld().getName();
            List<String> disabledWorlds = configManager.getDisabledWorlds();

            if (disabledWorlds.contains(currentWorld) &&
                    !player.hasPermission("waterfly.bypass.world-restriction")) {
                player.sendMessage(miniMessage.deserialize(configManager.getConfig().getString("disabledworld")));
                return true;
            }

            PersistentDataContainer pdc = player.getPersistentDataContainer();
            boolean isFlying = pdc.has(flyKey, PersistentDataType.BOOLEAN);

            if (isFlying) {
                pdc.remove(flyKey);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(miniMessage.deserialize(configManager.getConfig().getString("fly.disabled")));
            } else {
                pdc.set(flyKey, PersistentDataType.BOOLEAN, true);
                player.setAllowFlight(true);
                player.sendMessage(miniMessage.deserialize(configManager.getConfig().getString("fly.enabled")));
            }
        }

        player.sendMessage(miniMessage.deserialize("<red>Usage: /flyspeed <int: 1-10>"));
        return true;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        GameMode gameMode = player.getGameMode();

        boolean affectCreative = configManager.getConfig().getBoolean("affect-creative-spectators");

        if (!affectCreative && (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)) {
            return;
        }

        List<String> disabledWorlds = configManager.getDisabledWorlds();
        boolean removeOnWorldChange = configManager.getConfig().getBoolean("remove-fly.world-change");

        if (disabledWorlds.contains(player.getWorld().getName()) || removeOnWorldChange) {
            player.getPersistentDataContainer().remove(flyKey);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setFlySpeed((float) configManager.getConfig().getDouble("default-fly-speeds.normal", 0.1));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        GameMode gameMode = player.getGameMode();

        boolean affectCreative = configManager.getConfig().getBoolean("affect-creative-spectators");

        if (!affectCreative && (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)) {
            return;
        }

        List<String> disabledWorlds = configManager.getDisabledWorlds();
        boolean removeOnWorldChange = configManager.getConfig().getBoolean("remove-fly.world-change");

        if (disabledWorlds.contains(player.getWorld().getName()) || removeOnWorldChange) {
            player.getPersistentDataContainer().remove(flyKey);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setFlySpeed((float) configManager.getConfig().getDouble("default-fly-speeds.normal", 0.1));

        }
    }
}