package com.grandstrand.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Manages SQLite database connections and schema initialization.
 * Centralizes JDBC URL, connection lifecycle, and migration script execution.
 */
public class DatabaseManager {
    private final String jdbcUrl;

    public DatabaseManager(String databasePath) {
        this.jdbcUrl = "jdbc:sqlite:" + databasePath;
    }

    /**
     * Opens a new database connection.
     * Caller is responsible for closing it (use try-with-resources).
     */
    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(jdbcUrl);
        // Enable foreign key enforcement (off by default in SQLite)
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    /**
     * Initializes the database schema from schema.sql on the classpath.
     * Safe to call repeatedly — all statements use IF NOT EXISTS.
     */
    public void initializeSchema() throws DataAccessException {
        String schema = loadSchemaScript();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);
            try {
                for (String sql : schema.split(";")) {
                    String trimmed = sql.trim();
                    if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                        stmt.execute(trimmed);
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new DataAccessException("Schema initialization failed", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not connect to database", e);
        }
    }

    private String loadSchemaScript() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("schema.sql");
        if (in == null) {
            throw new DataAccessException("schema.sql not found on classpath", null);
        }
        return new BufferedReader(new InputStreamReader(in))
            .lines()
            .collect(Collectors.joining("\n"));
    }
}
