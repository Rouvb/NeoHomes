package io.github.rouvb.neoHomes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.config.Lang;
import io.github.rouvb.neoHomes.gui.HomesGUI;
import io.github.rouvb.neoHomes.home.Home;
import io.github.rouvb.neoHomes.home.HomeService;
import io.github.rouvb.neoHomes.profile.Profile;
import io.github.rouvb.neoHomes.profile.manager.ProfileManager;
import org.bukkit.entity.Player;

@CommandAlias("home")
@CommandPermission("neohomes.home")
public class HomeCommand extends BaseCommand {

    private final ConfigManager configManager;
    private final ProfileManager profileManager;
    private final HomeService homeService;
    private final HomesGUI homesGUI;

    public HomeCommand(ConfigManager configManager, ProfileManager profileManager, HomeService homeService, HomesGUI homesGUI) {
        this.configManager = configManager;
        this.profileManager = profileManager;
        this.homeService = homeService;
        this.homesGUI = homesGUI;
    }

    @Default
    @Syntax("<home>")
    @CommandCompletion("@homes")
    public void onCommand(Player sender, @Optional @Values("@homes") String homeName) {
        if (homeName == null) {
            homesGUI.open(sender);
            return;
        }

        Profile profile = profileManager.getProfile(sender.getUniqueId());
        Home home = profile.getHome(homeName);
        String homeNameStr = String.join(" ", homeName);

        if (home == null) {
            sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.HOME_NOT_FOUND.getPath()))
                    .legacy().build());
            return;
        }

        homeService.teleportToHome(sender, home);
        sender.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.TELEPORT_SUCCESS.getPath()))
                .with("home", homeNameStr).legacy().build());
    }
}
