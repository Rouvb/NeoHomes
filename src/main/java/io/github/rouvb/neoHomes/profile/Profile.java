package io.github.rouvb.neoHomes.profile;

import io.github.rouvb.neoHomes.home.Home;
import io.github.rouvb.neoHomes.profile.data.ProfileData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Getter
public class Profile {

    private final UUID uuid;
    private ProfileData profileData;

    public Profile(UUID uuid, ProfileData profileData) {
        this.uuid = uuid;
        this.profileData = profileData;
    }

    public void addHome(String homeName, Location location) {
        Home home = new Home(homeName, location);
        profileData.getHomes().put(homeName, home);
    }

    public void deleteHome(String homeName) {
        profileData.getHomes().remove(homeName);
    }

    public Home getHome(String homeName) {
        return profileData.getHomes().get(homeName);
    }

    public Set<String> getHomeNames() {
        return profileData.getHomes().keySet();
    }

    public Collection<Home> getHomes() {
        return profileData.getHomes().values();
    }

    public int getHomeLimit(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.hasPermission("neohomes.limit.*")) {
            return Integer.MAX_VALUE;
        }

        int highest = -1;

        for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
            if (!perm.getValue()) continue;
            String name = perm.getPermission();
            if (!name.startsWith("neohomes.limit.")) continue;
            String number = name.substring("neohomes.limit.".length());
            try {
                int value = Integer.parseInt(number);
                if (value > highest) highest = value;
            } catch (NumberFormatException ignored) {}
        }

        if (highest == -1) {
            return 1;
        }

        return highest;
    }
}
