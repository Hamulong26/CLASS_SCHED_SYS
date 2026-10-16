package dao;

import model.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // Constructor to automatically ensure default admin is created on startup
    public UserDao() {
        createDefaultAdmin();
    }

    private void createDefaultAdmin() {
        String checkSql = "SELECT COUNT(*) FROM users WHERE role = 'ADMIN'";
        String insertSql = "INSERT INTO users(username, password, role, status) VALUES('admin', 'admin123', 'ADMIN', 'ACTIVE')";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate(insertSql);
                System.out.println("Default admin account created: admin / admin123");
            }
        } catch (SQLException e) {
            System.err.println("Error seeding default admin: " + e.getMessage());
        }
    }

    public user loginUser(String username, String password) {
        String sql = "SELECT id, username, password, role, status FROM users WHERE username = ? AND password = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");

                    if ("PENDING".equalsIgnoreCase(status)) {
                        System.out.println("Account is pending, waiting for approval.");
                        return null;
                    }
                    if ("REJECTED".equalsIgnoreCase(status)) {
                        System.out.println("Account registration was rejected.");
                        return null;
                    }

                    return new user(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("status")
                    );
                } else {
                    System.out.println("Invalid username or password.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return null;
    }

    public boolean registerUser(String username, String password, String role) {
        String sql = "INSERT INTO users(username, password, role, status) VALUES(?, ?, ?, 'PENDING')";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role.toUpperCase());

            pstmt.executeUpdate();
            LogDao.logAction(username, "Registered account (Pending Approval)");
            return true;
        } catch (SQLException e) {
            System.err.println("Registration Failed (Username might already exist): " + e.getMessage());
            return false;
        }
    }

    public List<user> getPendingUsers() {
        List<user> pendingList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE status = 'PENDING'";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pendingList.add(new user(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching pending users: " + e.getMessage());
        }
        return pendingList;
    }

    public boolean updateUserStatus(int id, String status) {
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, status.toUpperCase());
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating status: " + e.getMessage());
            return false;
        }
    }
}