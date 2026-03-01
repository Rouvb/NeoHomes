package io.github.rouvb.neoHomes;

import co.aikar.commands.PaperCommandManager;
import io.github.rouvb.neoHomes.commands.DelHomeCommand;
import io.github.rouvb.neoHomes.commands.HomeCommand;
import io.github.rouvb.neoHomes.commands.ReloadCommand;
import io.github.rouvb.neoHomes.commands.SetHomeCommand;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.database.SQLiteManager;
import io.github.rouvb.neoHomes.gui.HomesGUI;
import io.github.rouvb.neoHomes.home.HomeService;
import io.github.rouvb.neoHomes.profile.Profile;
import io.github.rouvb.neoHomes.profile.handler.ProfileHandler;
import io.github.rouvb.neoHomes.profile.manager.ProfileManager;
import io.github.rouvb.neoHomes.profile.repository.ProfileRepository;
import io.github.rouvb.neoHomes.profile.repository.SQLiteProfileRepository;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NeoHomes extends JavaPlugin {

    private ConfigManager configManager;
    private SQLiteManager sqLiteManager;
    private ProfileRepository profileRepository;
    private PaperCommandManager commandManager;
    private ProfileManager profileManager;
    private HomeService homeService;
    private HomesGUI homesGUI;

    @Override
    public void onEnable() {
        loadConfigs();
        loadDatabase();
        loadManagers();
        homeService = new HomeService(configManager, this);
        homesGUI = new HomesGUI(configManager, profileManager, homeService);
        loadHandlers();
        registerCommands();
        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        sqLiteManager.close();
        profileManager.shutdown();
        this.getLogger().info("Plugin disabled.");
    }

    private void loadConfigs() {
        configManager = new ConfigManager(this);
        configManager.loadAll();
        getLogger().info("Config loaded.");
    }

    private void loadDatabase() {
        sqLiteManager = new SQLiteManager(getDataFolder());
        sqLiteManager.connect();
        profileRepository = new SQLiteProfileRepository(sqLiteManager.getConnection());
        getLogger().info("Database loaded.");
    }

    private void loadManagers() {
        profileManager = new ProfileManager(this, profileRepository);
        getLogger().info("Managers loaded.");
    }

    private void loadHandlers() {
        getServer().getPluginManager().registerEvents(new ProfileHandler(profileManager), this);
        getLogger().info("Handlers loaded.");
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new HomeCommand(configManager, profileManager, homeService, homesGUI));
        commandManager.registerCommand(new SetHomeCommand(configManager, profileManager));
        commandManager.registerCommand(new DelHomeCommand(configManager, profileManager));
        commandManager.registerCommand(new ReloadCommand(configManager));
        commandManager.getCommandCompletions().registerCompletion("homes", c -> {
            Player player = (Player) c.getSender();
            Profile profile = profileManager.getProfile(player.getUniqueId());
            if (profile == null) return List.of();
            return new ArrayList<>(profile.getHomeNames());
        });
        getLogger().info("Commands registered.");
    }
}
