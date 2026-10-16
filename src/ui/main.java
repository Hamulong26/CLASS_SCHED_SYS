package ui;

import dao.UserDao;
import model.user;
import java.util.Scanner;

public class main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDao userDao = new UserDao();

    public static void main(String[] args) {
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
                case "1" -> handleLogin();
                case "2" -> handleRegister();
                case "3" -> {
                    running = false;
                    System.out.println("Exiting System. Goodbye!");
                }
                default -> System.out.println("Invalid option.");
            }
        }
        sc.close();
    }

    private static void handleLogin() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        user currentUser = userDao.loginUser(username, password);

        if (currentUser == null) {
            return;
        }

        switch (currentUser.role().toUpperCase()) {
            case "ADMIN" -> AdminDashBoard.display(currentUser, sc);
            case "TEACHER" -> TeacherDashBoard.display(currentUser, sc);
            case "STUDENT" -> StudentDashBoard.display(currentUser, sc);
            default -> System.out.println("Unknown role found.");
        }
    }

    private static void handleRegister() {
        System.out.print("Enter new Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        System.out.println("Select Role:");
        System.out.println("[1] Student");
        System.out.println("[2] Teacher");
        System.out.print("Choice: ");

        String roleChoice = sc.nextLine();
        String role = roleChoice.equals("2") ? "TEACHER" : "STUDENT";

        if (userDao.registerUser(username, password, role)) {
            System.out.println("Registration submitted! Waiting for Admin approval.");
        }
    }
}