package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogDao {

    public static void logAction(String username, String action) {
        String sql = "INSERT INTO logs(username, action, timestamp) VALUES(?, ?, CURRENT_TIMESTAMP)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, action);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Log Error: " + e.getMessage());
        }
    }

    public static void viewAllLogs() {
        String sql = "SELECT * FROM logs ORDER BY id DESC";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- SYSTEM LOGS ---");
            while (rs.next()) {
                System.out.printf("[%s] User: %s | Action: %s\n",
                        rs.getString("timestamp"),
                        rs.getString("username"),
                        rs.getString("action"));
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
        }
    }
}