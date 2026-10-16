package ui;

import model.user;
import java.util.Scanner;

public class StudentDashBoard {
    public static void display(user student, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== STUDENT DASHBOARD ===");
            System.out.println("Welcome, " + student.username());
            System.out.println("[1] View Class Schedule");
            System.out.println("[2] View Enrolled Subjects");
            System.out.println("[3] Logout");
            System.out.print("Choice: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> System.out.println("Displaying class schedule...");
                case "2" -> System.out.println("Displaying enrolled subjects...");
                case "3" -> back = true;
                default -> System.out.println("Invalid selection.");
            }
        }
    }
}