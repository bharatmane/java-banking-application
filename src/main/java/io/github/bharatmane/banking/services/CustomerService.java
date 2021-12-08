package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
import io.github.bharatmane.banking.exception.InvalidCredentialsException;

import java.math.BigDecimal;
import java.util.List;

public class CustomerService {
    private final List<Customer> customers;

    public CustomerService(List<Customer> customers) {
        this.customers = customers;
    }
    private Customer getValidCustomer(String accountNo, String password ) throws InvalidCredentialsException {

        Customer customerTobeChecked = getCustomer(accountNo);
        if(customerTobeChecked == null){
            throw new InvalidCredentialsException("Invalid credentials, please try with valid account number and password");
        }

        customerTobeChecked.setIsLoggedIn(customerTobeChecked.comparePassword(password));

        return customerTobeChecked;
    }
    public void withdraw(Customer customer,String withdrawAmount) throws InsufficientFundsException {

        if(checkAccountForAvailableFunds(customer, withdrawAmount)) {
            BigDecimal accountBalance = customer.getAccountBalance();
            customer.setAccountBalance(accountBalance.subtract(new BigDecimal(withdrawAmount)));
        }
        else{
            throw new InsufficientFundsException("Insufficient funds, please try with lower amount");
        }
    }

    private boolean checkAccountForAvailableFunds(Customer customer,String withdrawAmount)
    {
        boolean isAvailable;
        BigDecimal bigDecimalWithdrawAmount = new BigDecimal(withdrawAmount);

        isAvailable = bigDecimalWithdrawAmount.compareTo(customer.getAccountBalance()) != 1;
        return isAvailable;
    }


    public void deposit(Customer customer ,String amount) {
        BigDecimal accountBalance = customer.getAccountBalance();
        customer.setAccountBalance(accountBalance.add(new BigDecimal(amount)));
    }

    public void transfer(Customer customer, String transferToAccountNo, String amount) throws InsufficientFundsException {
        Customer transferToCustomer = getCustomer(transferToAccountNo);
        BigDecimal transferAmount = new BigDecimal(amount);

        if(checkAccountForAvailableFunds(customer,amount) ) {
            deductBalance(customer,transferAmount);
            increaseBalance(transferToCustomer,transferAmount);
        }
        else{
            throw new InsufficientFundsException("Insufficient funds, please try with lower amount");
        }
    }

    private void increaseBalance(Customer transferToCustomer, BigDecimal transferAmount) {
        BigDecimal accountBalance = transferToCustomer.getAccountBalance();
        transferToCustomer.setAccountBalance(accountBalance.add(transferAmount));
    }

    private void deductBalance(Customer customer, BigDecimal transferAmount) {
        BigDecimal accountBalance = customer.getAccountBalance();
        customer.setAccountBalance(accountBalance.subtract(transferAmount));
    }

    public void logout(Customer customer) {
        customer.setIsLoggedIn(false);
    }

    public Customer login(String accountNo, String password) throws InvalidCredentialsException {
        Customer customer = getValidCustomer(accountNo,password);
        if(customer.isLoggedIn()) {
            return customer;
        }
        else{
            throw new InvalidCredentialsException("Invalid credentials, please try with valid account number and password");
        }
    }

    public int getCustomerCount() {
        return customers.size();
    }

    public Customer getCustomer(String accountNo) {
        return  this.customers.stream().filter(c-> c.getAccountNo().equals(accountNo)).findFirst()
                .orElse(null);
    }
}
