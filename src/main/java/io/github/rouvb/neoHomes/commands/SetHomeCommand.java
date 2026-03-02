package io.github.rouvb.neoHomes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.rouvb.neoHomes.config.Config;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.config.Lang;
import io.github.rouvb.neoHomes.home.Home;
import io.github.rouvb.neoHomes.profile.Profile;
import io.github.rouvb.neoHomes.profile.manager.ProfileManager;
import org.bukkit.entity.Player;

@CommandAlias("sethome")
@CommandPermission("neohomes.sethome")
public class SetHomeCommand extends BaseCommand {

    private final ConfigManager configManager;
    private final ProfileManager profileManager;

    public SetHomeCommand(ConfigManager configManager, ProfileManager profileManager) {
        this.configManager = configManager;
        this.profileManager = profileManager;
    }

    @Default
    @Syntax("<home>")
    public void onCommand(Player sender, String[] homeName) {
        if (homeName.length == 0) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.COMMAND_SETHOME_USAGE.getPath()))
                    .legacy().build());
            return;
        }

        Profile profile = profileManager.getProfile(sender.getUniqueId());
        String homeNameStr = String.join(" ", homeName);
        Home home = profile.getHome(homeNameStr);

        // Home Exists
        if (home != null) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.HOME_NOT_FOUND.getPath()))
                    .legacy().build());
            return;
        }

        // Maximum Limit
        if (profile.getHomes().size() >= profile.getHomeLimit(sender.getUniqueId())) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.ERROR_MAX_LIMIT.getPath()))
                    .legacy().build());
            return;
        }

        // Blacklisted World
        if (configManager.getConfig().getStringList(Config.BLACKLISTED_WORLDS.getPath()).contains(sender.getWorld().getName())) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.ERROR_BLACKLISTED_WORLD.getPath()))
                    .legacy().build());
            return;
        }

        // Blacklisted Word
        if (configManager.getConfig().getStringList(Config.BLACKLISTED_WORDS.getPath()).contains(homeNameStr)) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.ERROR_BLACKLISTED_WORD.getPath()))
                    .legacy().build());
            return;
        }

        profile.addHome(homeNameStr, sender.getLocation());
        sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.HOME_SET.getPath()))
                .with("home", homeNameStr).legacy().build());
    }
}
