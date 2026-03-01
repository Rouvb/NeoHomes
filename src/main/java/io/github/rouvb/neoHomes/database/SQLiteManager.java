package io.github.rouvb.neoHomes.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {

    private final File dbFile;
    private Connection connection;

    public SQLiteManager(File dataFolder) {
        this.dbFile = new File(dataFolder, "data.db");
    }

    public void connect() {
        try {
            if (!dbFile.exists()) {
                dbFile.getParentFile().mkdirs();
            }

            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());

            enableWAL();
            createTables();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect SQLite", e);
        }
    }

    private void enableWAL() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL;");
        }
    }

    private void createTables() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS homes (
                    UUID TEXT NOT NULL,
                    name TEXT NOT NULL,
                    world TEXT NOT NULL,
                    x REAL NOT NULL,
                    y REAL NOT NULL,
                    z REAL NOT NULL,
                    yaw REAL NOT NULL,
                    pitch REAL NOT NULL,
                    PRIMARY KEY (UUID, name)
                );
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ignored) {}
    }
}
