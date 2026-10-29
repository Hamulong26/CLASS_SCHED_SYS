package ui;

import dao.UserDao;
import model.User;
import java.util.Scanner;

public class Authentication {
    public static void start(UserDao userDao, Scanner sc) {
        boolean running = true;

        while (running) {
            System.out.println("\n=========================================");
            System.out.println("   CLASS SCHEDULE MANAGEMENT SYSTEM    ");
            System.out.println("=========================================");
            System.out.println("[1] Login");
            System.out.println("[2] Register Account");
            System.out.println("[3] Exit");
            System.out.print("Select choice: ");

            String option = sc.nextLine();

            switch (option) {
                case "1" -> handleLogin(userDao, sc);
                case "2" -> handleRegister(userDao, sc);
                case "3" -> {
                    running = false;
                    System.out.println("Exiting System. Goodbye!");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void handleLogin(UserDao userDao, Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        User currentUser = userDao.loginUser(username, password);

        if (currentUser == null) {
            return;
        }

        switch (currentUser.role().toUpperCase()) {
            case "ADMIN" -> AdminDashBoard.display(currentUser, sc, userDao);
            case "TEACHER" -> TeacherDashBoard.display(currentUser, sc);
            case "STUDENT" -> StudentDashBoard.display(currentUser, sc);
            default -> System.out.println("Unknown role found.");
        }
    }

    private static void handleRegister(UserDao userDao, Scanner sc) {
        System.out.println("\n--- REGISTRATION ---");
        System.out.print("Choose a username: ");
        String username = sc.nextLine();

        System.out.print("Choose a password: ");
        String password = sc.nextLine();

        System.out.print("Enter Role (TEACHER / STUDENT): ");
        String role = sc.nextLine().toUpperCase();

        if (userDao.registerUser(username, password, role)) {
            System.out.println("Registration submitted successfully! Your account status is PENDING admin approval.");
        }
    }
}