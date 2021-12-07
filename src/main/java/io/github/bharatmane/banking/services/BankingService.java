package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
import io.github.bharatmane.banking.exception.InvalidCredentialsException;

import java.util.List;

public class BankingService {

    private final Prompter prompter;
    private final CustomerService customerService;


    public BankingService(CustomerService customerService, Prompter prompter) {
        this.customerService = customerService;
        this.prompter = prompter;
    }

    public int getCustomerCount() {
        return customerService.getCustomerCount();
    }

    public void greet() {
        prompter.greetUser();
    }

    public Customer login(){
        Customer customer =  null;
        int chosenOption = prompter.promptLogin();
        if (chosenOption == 1) {
            String accountNo = prompter.promptAccountNo();
            String password = prompter.promptPassword();
            try {
                customer = customerService.login(accountNo,password);
            }
            catch (InvalidCredentialsException exception){
                prompter.printInvalidCredentials();
            }
        }
        return customer;
    }

    public void operate(Customer customer) throws InsufficientFundsException {
        int chosenOption = prompter.promptAccountMenu();
        processAccountMenu(customer,chosenOption);
    }
    private void processAccountMenu(Customer customer, int chosenOption) throws InsufficientFundsException {

        switch(chosenOption){
            case 1:
                String depositAmount = prompter.promptDeposit();
                customerService.deposit(customer,depositAmount);
                break;
            case 2:
                String withdrawAmount = prompter.promptWithdraw();
                customerService.withdraw(customer,withdrawAmount);
                break;
            case 3:
                customerService.transfer();
                break;
            case 4:
                customerService.logout(customer);
                break;
        }
    }
}
