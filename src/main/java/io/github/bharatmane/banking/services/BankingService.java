package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.entity.AccountMenu;
import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.entity.MainMenu;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
import io.github.bharatmane.banking.exception.InvalidCredentialsException;
import io.github.bharatmane.banking.exception.InvalidMenuChoiceException;
import io.github.bharatmane.banking.exception.InvalidOtpException;

public class BankingService {

    private final Prompter prompter;
    private final CustomerService customerService;
    private final OtpService otpService;

    public BankingService(CustomerService customerService, Prompter prompter,OtpService otpService) {
        this.customerService = customerService;
        this.prompter = prompter;
        this.otpService = otpService;

    }

    public int getCustomerCount() {
        return customerService.getCustomerCount();
    }

    public void greet() {
        prompter.greetUser();
    }

    public Customer login() throws InvalidMenuChoiceException {

        Customer customer =  null;
        MainMenu mainMenu;
        try {
            mainMenu = MainMenu.values()[prompter.promptLogin() - 1];
        }
        catch (ArrayIndexOutOfBoundsException exception){
            throw new InvalidMenuChoiceException("Invalid choice, please enter correct option");
        }

        if(mainMenu == MainMenu.LOGIN) {
            customer = processLogin();
        }
        else{
                prompter.promptGoodBye();
        }
        return customer;
    }

    private Customer processLogin() {
        Customer customer = null;
        String accountNo = prompter.promptAccountNo();
        String password = prompter.promptPassword();
        try {
            customer = customerService.login(accountNo,password);
        }
        catch (InvalidCredentialsException exception){
            prompter.printInvalidCredentials();
        }
        return customer;
    }

    public void operate(Customer customer) throws InsufficientFundsException, InvalidOtpException {
        AccountMenu accountMenu;
        do
        {
            accountMenu = AccountMenu.values()[prompter.promptAccountMenu() - 1];
            processAccountMenu(customer,accountMenu);
        }   while(accountMenu != AccountMenu.LOGOUT);

    }
    private void processAccountMenu(Customer customer, AccountMenu accountMenu) throws InsufficientFundsException, InvalidOtpException {

        switch(accountMenu){
            case DEPOSIT:
                String depositAmount = prompter.promptDeposit();
                customerService.deposit(customer,depositAmount);
                break;
            case WITHDRAW:
                String withdrawAmount = prompter.promptWithdraw();
                customerService.withdraw(customer,withdrawAmount);
                break;
            case TRANSFER:
                processTransfer(customer);
                break;
            case LOGOUT:
                customerService.logout(customer);
                break;
        }
    }

    private void processTransfer(Customer customer) throws InvalidOtpException, InsufficientFundsException {
        String transferAccountNo = prompter.promptTransferAccountNo();
        String transferAmount = prompter.promptTransfer();
        String generatedOtp = otpService.generateOTP();
        String otpRequestId = otpService.sendOTP(generatedOtp,customer.getPhoneNumber());
        String enteredOtp = prompter.promptOtp();

        otpService.validateOtp(enteredOtp,otpRequestId);
        customerService.transfer(customer,transferAccountNo,transferAmount);
    }

    public Customer getCustomer(String accountNo) {
        return customerService.getCustomer(accountNo);
    }
}
