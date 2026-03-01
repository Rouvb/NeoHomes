package io.github.rouvb.neoHomes.profile.repository;

import io.github.rouvb.neoHomes.profile.data.ProfileData;

import java.util.UUID;

public interface ProfileRepository {

    ProfileData getProfileData(UUID uuid);

    void saveProfileData(ProfileData profileData);
}
