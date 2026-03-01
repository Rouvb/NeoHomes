package io.github.rouvb.neoHomes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.config.Lang;
import io.github.rouvb.neoHomes.home.Home;
import io.github.rouvb.neoHomes.profile.Profile;
import io.github.rouvb.neoHomes.profile.manager.ProfileManager;
import org.bukkit.entity.Player;

@CommandAlias("delhome")
@CommandPermission("neohomes.delhome")
public class DelHomeCommand extends BaseCommand {

    private final ConfigManager configManager;
    private final ProfileManager profileManager;

    public DelHomeCommand(ConfigManager configManager, ProfileManager profileManager) {
        this.configManager = configManager;
        this.profileManager = profileManager;
    }

    @Default
    @Syntax("<home>")
    public void onCommand(Player sender, String[] homeName) {
        if (homeName.length == 0) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.COMMAND_DELHOME_USAGE.getPath()))
                    .legacy().build());
            return;
        }

        Profile profile = profileManager.getProfile(sender.getUniqueId());
        String homeNameStr = String.join(" ", homeName);
        Home home = profile.getHome(homeNameStr);

        // Home does not exist
        if (home == null) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.HOME_NOT_FOUND.getPath()))
                    .legacy().build());
            return;
        }

        profile.deleteHome(homeNameStr);
        sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.HOME_DELETED.getPath()))
                .with("home", homeNameStr).legacy().build());
    }
}
