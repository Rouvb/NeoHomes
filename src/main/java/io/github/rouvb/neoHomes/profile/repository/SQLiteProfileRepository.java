package io.github.rouvb.neoHomes.profile.repository;

import io.github.rouvb.neoHomes.home.Home;
import io.github.rouvb.neoHomes.profile.data.ProfileData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SQLiteProfileRepository implements ProfileRepository {

    private final Connection connection;

    public SQLiteProfileRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ProfileData getProfileData(UUID uuid) {
        Map<String, Home> homes = new HashMap<>();

        String sql = "SELECT * FROM homes where uuid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String world = rs.getString("world");

                World bukkitWorld = Bukkit.getWorld(world);
                if (bukkitWorld == null) continue;

                Location loc = new Location(
                        bukkitWorld,
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("z"),
                        rs.getFloat("yaw"),
                        rs.getFloat("pitch")
                );

                homes.put(name, new Home(name, loc));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new ProfileData(uuid, homes);
    }

    @Override
    public void saveProfileData(ProfileData profileData) {

        UUID uuid = profileData.getUuid();

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement delete = connection.prepareStatement("DELETE FROM homes WHERE uuid = ?")) {
                delete.setString(1, uuid.toString());
                delete.executeUpdate();
            }

            String insertSql = """
                    INSERT INTO homes (uuid, name, world, x, y, z, yaw, pitch)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                for (Home home : profileData.getHomes().values()) {
                    Location loc = home.getLocation();
                    if (loc.getWorld() == null) continue;

                    insert.setString(1, uuid.toString());
                    insert.setString(2, home.getHomeName());
                    insert.setString(3, loc.getWorld().getName());
                    insert.setDouble(4, loc.getX());
                    insert.setDouble(5, loc.getY());
                    insert.setDouble(6, loc.getZ());
                    insert.setFloat(7, loc.getYaw());
                    insert.setFloat(8, loc.getPitch());

                    insert.addBatch();
                }

                insert.executeBatch();
            }

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {}
            throw new RuntimeException(e);
        }
    }
}
