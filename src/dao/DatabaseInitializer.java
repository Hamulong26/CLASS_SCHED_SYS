package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "roles TEXT NOT NULL, " +
                "status TEXT NOT NULL)";

        String createLogsTable = "CREATE TABLE IF NOT EXISTS activity_logs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "action TEXT NOT NULL, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createLogsTable);
            System.out.println("-> Database tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database tables: " + e.getMessage());
        }
    }
}