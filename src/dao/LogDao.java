package dao;

import java.sql.*;

public class LogDao {
    public static void logAction(String username, String action) {
        String sql = "INSERT INTO activity_logs(username, action) VALUES(?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, action);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error logging action: " + e.getMessage());
        }
    }

    public static void viewAllLogs() {
        String sql = "SELECT * FROM activity_logs";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- ACTIVITY LOGS ---");
            while (rs.next()) {
                System.out.printf("[ID: %d] User: %s | Action: %s | Time: %s%n",
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("action"),
                        rs.getString("timestamp"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching logs: " + e.getMessage());
        }
    }
}