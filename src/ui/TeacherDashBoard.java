package ui;

import dao.LogDao;
import model.user;
import java.util.Scanner;

public class TeacherDashBoard {
    public static void display(user teacher, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== TEACHER DASHBOARD ===");
            System.out.println("Welcome, Prof. " + teacher.username());
            System.out.println("[1] View Assigned Teaching Schedule");
            System.out.println("[2] Post Class Updates");
            System.out.println("[3] Logout");
            System.out.print("Choice: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> System.out.println("Displaying teaching schedule...");
                case "2" -> {
                    System.out.print("Enter class update message: ");
                    String msg = sc.nextLine();
                    LogDao.logAction(teacher.username(), "Posted announcement: " + msg);
                    System.out.println("Announcement posted!");
                }
                case "3" -> back = true;
                default -> System.out.println("Invalid selection.");
            }
        }
    }
}