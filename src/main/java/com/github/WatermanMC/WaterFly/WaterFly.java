package com.github.WatermanMC.WaterFly;

import com.github.WatermanMC.WaterFly.commands.*;
import com.github.WatermanMC.WaterFly.managers.*;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class WaterFly extends JavaPlugin {
    private ConfigManager configManager;
    private FlyCommand flyCommand;
    private NamespacedKey flyKey;
    private NamespacedKey flySpeedKey;

    @Override
    public void onEnable() {
        this.flyKey = new NamespacedKey(this, "is_flying");
        this.flySpeedKey = new NamespacedKey(this, "custom_fly_speed");
        this.configManager = new ConfigManager(this);
        registerCommands();
        getServer().getPluginManager().registerEvents(flyCommand, this);
        getLogger().info("WaterFly v" + getPluginMeta().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaterFly v" + getPluginMeta().getVersion() + " disabled!");
    }

    private void registerCommands() {
        new WaterFlyCommand(this, configManager);
        this.flyCommand = new FlyCommand(this, configManager, flyKey);
        new FlyspeedCommand(this, configManager, flySpeedKey);
    }

    public String getDiscordHelp() {
        return "Cant fix it? Join on our fast discord support: https://discord.gg/Scgqfm5EU4";
    }
}
