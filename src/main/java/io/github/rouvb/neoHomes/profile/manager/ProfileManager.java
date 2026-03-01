package io.github.rouvb.neoHomes.profile.manager;

import io.github.rouvb.neoHomes.NeoHomes;
import io.github.rouvb.neoHomes.profile.Profile;
import io.github.rouvb.neoHomes.profile.data.ProfileData;
import io.github.rouvb.neoHomes.profile.repository.ProfileRepository;
import io.github.rouvb.neoHomes.utils.AsyncExecutor;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager {

    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();
    private final ProfileRepository profileRepository;
    private final AsyncExecutor async;

    public ProfileManager(NeoHomes plugin, ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        this.async = new AsyncExecutor();
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, this::saveAll, 0L, 20L * 5);
    }

    public CompletableFuture<Profile> loadProfile(UUID uuid) {
        return async.supplyAsync(() -> {
            ProfileData profileData = profileRepository.getProfileData(uuid);
            Profile profile = new Profile(uuid, profileData);
            profiles.put(uuid, profile);

            return profile;
        });
    }

    public void unloadProfile(UUID uuid) {
        Profile profile = profiles.remove(uuid);
        if (profile != null) {
            saveProfile(profile);
        }
    }

    public CompletableFuture<Void> saveProfile(Profile profile) {
        return async.runAsync(() -> {
            try {
                profileRepository.saveProfileData(profile.getProfileData());
            } catch (Exception e) {
                Bukkit.getLogger().info("[NeoHomes] Failed to save profile: " + e.getMessage());
            }
        });
    }

    public Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public void saveAll() {
        for (Profile profile : profiles.values()) {
            saveProfile(profile);
        }
    }

    public void shutdown() {
        saveAll();
        async.shutdown();
        profiles.clear();
    }
}
