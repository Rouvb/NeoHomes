package io.github.rouvb.neoHomes.home;

import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.rouvb.neoHomes.NeoHomes;
import io.github.rouvb.neoHomes.config.Config;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.config.Lang;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HomeService {

    private final ConfigManager configManager;
    private final NeoHomes plugin;

    public HomeService(ConfigManager configManager, NeoHomes plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
    }

    public void teleportToHome(Player player, Home home) {

        new BukkitRunnable() {
            int countdown = configManager.getConfig().getInt("home.countdown");
            Location cachedLocation = player.getLocation().clone();

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                if (configManager.getConfig().getBoolean(Config.HOME_CANCEL_ON_MOVE.getPath()) &&
                        isPlayerMoved(player, cachedLocation)) {
                    player.sendMessage(ColorParser.of(configManager.getLang()
                                    .getString(Lang.TELEPORT_CANCELLED_MOVED.getPath()))
                            .legacy().build());
                    cancel();
                    return;
                }

                if (countdown <= 0) {
                    player.teleport(home.getLocation());
                    player.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.TELEPORT_SUCCESS.getPath()))
                            .with("home", home.getHomeName()).legacy().build());
                    cancel();
                    return;
                }

                player.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.TELEPORT_STARTING.getPath()))
                        .with("time", String.valueOf(countdown)).legacy().build());
                countdown--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private boolean isPlayerMoved(Player player, Location cachedLocation) {
        Location currentLocation = player.getLocation();
        return cachedLocation.getX() != currentLocation.getX() ||
                cachedLocation.getZ() != currentLocation.getZ();
    }
}
