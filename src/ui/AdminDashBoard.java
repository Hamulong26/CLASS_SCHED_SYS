package ui;

import dao.LogDao;
import dao.UserDao;
import model.User;

import java.util.List;
import java.util.Scanner;

public class AdminDashBoard {

    public static void display(User adminUser, Scanner sc, UserDao userDao) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=========================================");
            System.out.println("          ADMINISTRATOR MENU             ");
            System.out.println("=========================================");
            System.out.println("1. View All System Users");
            System.out.println("2. View Pending Registrations");
            System.out.println("3. Approve User Account");
            System.out.println("4. View Activity Logs");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> viewAllUsers(userDao);
                case "2" -> viewPendingRegistrations(userDao);
                case "3" -> approveUser(sc, userDao);
                case "4" -> LogDao.viewAllLogs();
                case "5" -> back = true;
                default -> System.out.println("Invalid selection.");
            }
        }
    }

    private static void viewAllUsers(UserDao userDao) {
        List<User> users = userDao.getAllUsers();
        System.out.println("\n--- ALL SYSTEM USERS ---");
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-4s %-12s %-16s %-10s%n", "ID", "USERNAME", "ROLE", "STATUS");
        System.out.println("---------------------------------------------------------");
        for (User u : users) {
            System.out.println(u);
        }
        System.out.println("---------------------------------------------------------");
    }

    private static void viewPendingRegistrations(UserDao userDao) {
        List<User> pending = userDao.getPendingUsers();
        if (pending.isEmpty()) {
            System.out.println("No pending accounts.");
            return;
        }

        System.out.println("\n--- PENDING REGISTRATIONS ---");
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-4s %-12s %-16s %-10s%n", "ID", "USERNAME", "ROLE", "STATUS");
        System.out.println("---------------------------------------------------------");
        for (User u : pending) {
            System.out.println(u);
        }
        System.out.println("---------------------------------------------------------");
    }

    private static void approveUser(Scanner sc, UserDao userDao) {
        viewPendingRegistrations(userDao);
        List<User> pending = userDao.getPendingUsers();
        if (pending.isEmpty()) return;

        System.out.print("Enter User ID to approve (0 to cancel): ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (id == 0) return;

            if (userDao.updateUserStatus(id, "ACTIVE")) {
                System.out.println("SUCCESS: User ID " + id + " has been approved!");
                LogDao.logAction("ADMIN", "Approved User ID " + id);
            } else {
                System.out.println("Failed to update status. User ID may not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }
}