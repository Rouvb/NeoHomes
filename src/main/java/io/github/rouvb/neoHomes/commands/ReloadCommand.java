package io.github.rouvb.neoHomes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.rouvb.neoHomes.config.ConfigManager;
import org.bukkit.command.CommandSender;

@CommandAlias("neohomes")
@CommandPermission("neohomes.admin")
public class ReloadCommand extends BaseCommand {

    private final ConfigManager configManager;

    public ReloadCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @CommandAlias("reload")
    public void onCommand(CommandSender sender) {
        configManager.reload();
        sender.sendMessage(ColorParser.of("&aConfiguration reloaded.").legacy().build());
    }
}
