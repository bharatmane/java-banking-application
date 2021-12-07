package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
import io.github.bharatmane.banking.exception.InvalidCredentialsException;

import java.math.BigDecimal;
import java.util.List;

public class CustomerService {
    private final List<Customer> customers;
    private boolean isLoggedIn;

    public CustomerService(List<Customer> customers) {
        this.customers = customers;
    }
    private boolean isValidCredentials(Customer customer) {
        String accountNo = customer.getAccountNo();
        String password = customer.getPassword();
        Customer customerTobeChecked = getCustomer(accountNo);

        if(customerTobeChecked != null && customerTobeChecked.comparePassword(password)){
            customer.setIsLoggedIn(true);
        }
        else{
            customer.setIsLoggedIn(false);
        }
        return customer.isLoggedIn();
    }
    public void withdraw(Customer customer,String withdrawAmount) throws InsufficientFundsException {
        BigDecimal amount = new BigDecimal(withdrawAmount);
        if(checkAccountForAvailableFunds(customer,withdrawAmount) == true) {
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

        if (bigDecimalWithdrawAmount.compareTo(customer.getAccountBalance()) == 1)
        {
            isAvailable = false;
        }
        else
        {
            isAvailable = true;
        }
        return isAvailable;
    }


    public void deposit(Customer customer ,String amount) {
        BigDecimal accountBalance = customer.getAccountBalance();
        customer.setAccountBalance(accountBalance.add(new BigDecimal(amount)));
    }

    public void transfer() {
    }

    public void logout(Customer customer) {
        customer.setIsLoggedIn(false);
    }

    public Customer login(Customer customer) throws InvalidCredentialsException {
        if(isValidCredentials(customer)) {
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
