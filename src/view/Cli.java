package view;

import controller.Controller;
import model.user.Student;

import java.util.Scanner;

public class Cli {
    private Scanner scanner;
    private Controller controller;

    public Cli() {
        this.scanner = new Scanner(System.in);
        controller = new Controller();
        init();
    }

    public void init() {
        System.out.println("Welcome \nchoose your option: \n1-sign up\n2-login");
        String firstCommand = scanner.nextLine();
        if (firstCommand.equals("1")) {
            signUp();
        } else if (firstCommand.equals("2")) {
            login();
        } else {
            System.out.println("invalid Entry!\n Enter correct one:");
            init();
        }
    }

    private void signUp() {
        System.out.println("0-back");
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        if (username.equals("0")) {
            init();
        } else {
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            if (controller.processSignup(username, password)) {
                System.out.println("the user successfully created");
                init();
            } else {
                System.out.println("*this username already exists*");
                signUp();
            }
        }
    }

    public void login() {
        System.out.println("0- back");
        System.out.println("Enter your username:");
        String myUsername = scanner.nextLine();
        if (myUsername.equals("0")) {
            init();
        } else {
            System.out.println("Enter your password:");
            String myPassword = scanner.nextLine();
            if (myUsername.equals("Admin") && myPassword.equals("Admin")) {
                new AdminCli(this, controller);
            } else {
                Student student = controller.getUserByUsername(myUsername);
                if (student == null) {
                    System.out.println("*invalid username*");
                    login();
                } else if (!controller.checkPassword(student, myPassword)) {
                    System.out.println("*incorrect password*");
                    login();
                } else {
                    System.out.println("Successfully entered:)");
                    new StudentCli(this, controller, myUsername);
                }
            }
        }
    }
}
