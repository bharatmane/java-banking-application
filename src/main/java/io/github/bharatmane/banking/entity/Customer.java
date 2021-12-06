package io.github.bharatmane.banking.entity;

import java.math.BigDecimal;

public class Customer {

    private final String password;
    private final String bankAccountNo;
    private BigDecimal accountBalance = BigDecimal.ZERO;

    public Customer(String bankAccountNo, String password,String accountBalance) {
        this.password = password;
        this.bankAccountNo = bankAccountNo;
        this.accountBalance = new BigDecimal(accountBalance);;
    }

    public String getAccountNo() {
        return this.bankAccountNo;
    }

    public boolean isValidCredentials(String password) {
        return this.password.equals(password);
    }

    public String getAccountBalance() {
        return accountBalance.toString();
    }


    public void withdraw(String withdrawAmount) {
        accountBalance = accountBalance.subtract(new BigDecimal(withdrawAmount));
    }


    public void deposit(String amount) {
        accountBalance = accountBalance.add(new BigDecimal(amount));
    }

    public void transfer() {
    }

    public void logout() {
    }
}
