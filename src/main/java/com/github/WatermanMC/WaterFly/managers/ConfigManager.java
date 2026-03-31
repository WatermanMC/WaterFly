package com.github.WatermanMC.WaterFly.managers;

import com.github.WatermanMC.WaterFly.WaterFly;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;

public class ConfigManager {
    private WaterFly plugin;
    private FileConfiguration config;
    private FileConfiguration messages;
    private File configFile;
    private File messagesFile;

    public ConfigManager(WaterFly plugin) {
        this.plugin = plugin;
        createFiles();
        reloadConfigs();
    }

    private void createFiles() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

    public boolean reloadConfigs() {
        String currentFile = "config.yml";

        try {
            currentFile = "config.yml";
            config = new YamlConfiguration();
            config.load(configFile);

            currentFile = "messages.yml";
            messages = new YamlConfiguration();
            messages.load(messagesFile);

            config.setDefaults(YamlConfiguration.loadConfiguration(
                    new InputStreamReader(plugin.getResource("config.yml"), StandardCharsets.UTF_8)));
            messages.setDefaults(YamlConfiguration.loadConfiguration(
                    new InputStreamReader(plugin.getResource("messages.yml"), StandardCharsets.UTF_8)));

            return true;

        } catch (InvalidConfigurationException e) {
            String location = extractLocation(e.getMessage());
            plugin.getLogger().warning("Possible fix: The error is " + location + " on file " + currentFile);
            plugin.getLogger().warning(plugin.getDiscordHelp());
            return false;
        } catch (java.io.IOException e) {
            plugin.getLogger().warning("FILE ERROR: Could not read " + currentFile + "!");
            plugin.getLogger().warning("Make sure the file exists and the server has permission to read it.");
            plugin.getLogger().warning(plugin.getDiscordHelp());
            return false;
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Critical error during config reload!", e);
            plugin.getLogger().severe("This is an internal plugin error!");
            plugin.getLogger().severe(plugin.getDiscordHelp());
            return false;
        }
    }

    private String extractLocation(@Nullable String msg) {
        if (msg == null) return "unknown location";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("line \\d+, column \\d+").matcher(msg);
        if (matcher.find()) {
            return "in " + matcher.group();
        }
        return "a syntax error";
    }

    public String getMessage(@NotNull String path) {
        String prefix = getConfig().getString("prefix");
        String message = messages.getString(path, "<red>Message not found: " + path);
        return prefix + message;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public List<String> getDisabledWorlds() {
        return getConfig().getStringList("disabled-worlds");
    }
}