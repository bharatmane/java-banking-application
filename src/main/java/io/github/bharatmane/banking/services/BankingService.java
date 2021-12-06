package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InSufficientFundsException;

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

    public Customer login() {
        Customer customer =  null;
        int chosenOption = prompter.promptLogin();
        if (chosenOption == 1) {
            String userName = prompter.promptUserName();
            String password = prompter.promptPassword();

            customer = customers.stream().filter(c-> c.getAccountNo().equals(userName)).findFirst()
                    .orElse(null);
            if(customer == null || customer.isValidCredentials(password) == false){
                prompter.printInvalidCredentials();
                customer = null;
            }
        }
        return customer;
    }

    public void operate(Customer customer) throws InSufficientFundsException {
        int chosenOption = prompter.promptAccountMenu();
        processAccountMenu(customer,chosenOption);
    }
    private void processAccountMenu(Customer customer, int chosenOption) throws InSufficientFundsException {
        switch(chosenOption){
            case 1:
                String depositAmount = prompter.promptDeposit();
                customer.deposit(depositAmount);
                break;
            case 2:
                String withdrawAmount = prompter.promptWithdraw();
                customer.withdraw(withdrawAmount);
                break;
            case 3:
                customer.transfer();
                break;
            case 4:
                customer.logout();
                break;
        }
    }
}
