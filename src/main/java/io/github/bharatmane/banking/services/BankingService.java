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
            if(customer !=null && customer.isValidCredentials(password)){
               chosenOption = prompter.promptAccountMenu();
               processAccountMenu(chosenOption);
            }
            else{
                prompter.promptInvalidCredentials();
            }

        }
    }

    private void processAccountMenu(int chosenOption) {
        switch(chosenOption){
            case 1:
                deposit();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                transfer();
                break;
            case 4:
                logout();
                break;
        }
    }

    private void withdraw() {
    }

    private void deposit() {
    }

    private void transfer() {
    }

    private void logout() {
    }
}
