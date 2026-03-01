package io.github.rouvb.neoHomes.profile.data;

import io.github.rouvb.neoHomes.home.Home;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProfileData {
    private UUID uuid;
    private Map<String, Home> homes;
}
