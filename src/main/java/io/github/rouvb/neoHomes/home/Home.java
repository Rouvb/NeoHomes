package io.github.rouvb.neoHomes.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class Home {
    private String homeName;
    private Location location;
}
