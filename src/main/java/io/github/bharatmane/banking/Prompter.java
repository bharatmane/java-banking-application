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
        print("Welcome to Indian Banking Services!");
        print("-------------------------------------------------");
    }

    private void print(String message) {
        stdOut.println(message);
    }

    private void prompt() {
        stdOut.println("> ");
        lastInput = scanner.nextLine();
    }

    public int promptLogin() {
        print("Please choose one of the option shown below");
        print("---------------------------------------------------");
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

    public int promptAccountMenu() {
        print("Welcome to your account services, please choose one of the option shown below");
        print("------------------------------------------------------------------------------------");
        print("1 Deposit");
        print("2 Withdraw");
        print("3 Transfer");
        print("4 Logout");
        print("Enter your choice");
        prompt();
        return Integer.parseInt(lastInput);
    }

    public String promptDeposit() {
        print("Please enter the amount to deposit");
        prompt();
        return lastInput;
    }

    public String promptWithdraw() {
        print("Please enter the amount to withdraw");
        prompt();
        return lastInput;
    }

    public void printInvalidCredentials() {
        print("Invalid user name or password, please try again");
    }
}
