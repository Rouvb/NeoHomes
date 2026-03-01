package io.github.rouvb.neoHomes.profile.handler;

import io.github.rouvb.neoHomes.profile.manager.ProfileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileHandler implements Listener {

    private final ProfileManager profileManager;

    public ProfileHandler(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            profileManager.loadProfile(event.getUniqueId());
        } else {
            profileManager.unloadProfile(event.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        profileManager.unloadProfile(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        profileManager.unloadProfile(event.getPlayer().getUniqueId());
    }
}
