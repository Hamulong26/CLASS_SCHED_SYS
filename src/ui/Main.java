package ui;

import dao.DatabaseInitializer;
import dao.UserDao;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();

        UserDao userDao = new UserDao();
        Scanner sc = new Scanner(System.in);

        Authentication.start(userDao, sc);

        sc.close();
    }
}