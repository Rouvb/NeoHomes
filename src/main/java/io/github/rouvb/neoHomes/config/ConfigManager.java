package io.github.rouvb.neoHomes.config;

import io.github.rouvb.neoHomes.NeoHomes;
import io.github.rouvb.neoHomes.utils.CommentedConfiguration;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigManager {

    private final NeoHomes plugin;

    @Getter private CommentedConfiguration config;
    @Getter private CommentedConfiguration lang;

    public ConfigManager(NeoHomes plugin) {
        this.plugin = plugin;
    }

    public void loadAll() {
        config = load("config.yml");
        lang = load("lang.yml");
    }

    public void reload() {
        loadAll();
        plugin.getLogger().info("Configuration reloaded.");
    }

    public CommentedConfiguration load(String name) {
        File file = new File(plugin.getDataFolder(), name);

        if (!file.exists()) {
            plugin.saveResource(name, false);
        }

        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(file);

        InputStream resource = plugin.getResource(name);
        if (resource != null) {
            try {
                cfg.syncWithConfig(file, resource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return cfg;
    }
}
