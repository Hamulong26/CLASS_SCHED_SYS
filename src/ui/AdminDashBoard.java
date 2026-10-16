package ui;

import dao.LogDao;
import dao.UserDao;
import model.user;

import java.util.List;
import java.util.Scanner;

public class AdminDashBoard {
    private static final UserDao userDao = new UserDao();

    public static void display(user adminUser, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("Welcome, " + adminUser.username());
            System.out.println("[1] View & Approve Pending Users");
            System.out.println("[2] View Activity Logs");
            System.out.println("[3] Logout");
            System.out.print("Choice: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> approveUsers(sc);
                case "2" -> LogDao.viewAllLogs();
                case "3" -> back = true;
                default -> System.out.println("Invalid selection.");
            }
        }
    }

    private static void approveUsers(Scanner sc) {
        List<user> pending = userDao.getPendingUsers();
        if (pending.isEmpty()) {
            System.out.println("No pending accounts.");
            return;
        }

        System.out.println("\n--- PENDING ACCOUNTS ---");
        for (user u : pending) {
            System.out.println(u);
        }

        System.out.print("Enter User ID to approve/reject (0 to cancel): ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (id == 0) return;

            System.out.print("Type 'ACTIVE' to approve or 'REJECTED' to reject: ");
            String status = sc.nextLine();

            if (userDao.updateUserStatus(id, status)) {
                System.out.println("User status updated successfully!");
                LogDao.logAction("ADMIN", "Updated status of User ID " + id + " to " + status);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }
}