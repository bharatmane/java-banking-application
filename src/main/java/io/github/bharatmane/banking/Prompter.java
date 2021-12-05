package io.github.bharatmane.banking;

import io.github.bharatmane.banking.entity.Customer;

import java.io.PrintStream;
import java.util.Scanner;

public class Prompter {
    private final PrintStream stdOut;
    private Scanner scanner;
    private String lastInput;
    private Customer currentCustomer;
    public Prompter(PrintStream stdOut, Scanner scanner) {
        this.stdOut = stdOut;
        this.scanner = scanner;
    }

    public void greetUser() {
        print("Welcome to Great Learning Banking Services!");
    }

    private void print(String message) {
        stdOut.println(message);
    }

    private void prompt() {
        stdOut.println("> ");
        lastInput = scanner.nextLine();
    }

    public int promptLogin() {
        print("Choose Option");
        print("1. Login");
        print("2. Exit");
        prompt();
        return Integer.parseInt(lastInput);
    }

    public String currentOption() {
        return lastInput;
    }

    public String promptUserName() {
        print("Please Enter User Name");
        prompt();
        return lastInput;
    }

    public String promptPassword() {
        print("Please Enter Password");
        prompt();
        return lastInput;
    }
}
