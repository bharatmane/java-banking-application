package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.entity.Customer;

import java.util.List;

public class BankingService {
    private final List<Customer> customers;
    private final Prompter prompter;


    public BankingService(List<Customer> customers, Prompter prompter) {
        this.customers = customers;
        this.prompter = prompter;
    }

    public int getCustomerCount() {
        return customers.size();
    }

    public void greet() {
        prompter.greetUser();
    }

    public void login(){
        int chosenOption = prompter.promptLogin();
        if (chosenOption == 1) {
            String userName = prompter.promptUserName();
            String password = prompter.promptPassword();

            var customer = customers.stream().filter(c-> c.getName().equals(userName)).findFirst()
                    .orElse(null);
        }
    }
}
