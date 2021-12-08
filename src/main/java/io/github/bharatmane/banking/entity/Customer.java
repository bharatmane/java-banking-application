package io.github.bharatmane.banking.entity;

import java.math.BigDecimal;

public class Customer {

    private final String password;
    private final String bankAccountNo;
    private final String phoneNumber;
    private BigDecimal accountBalance = BigDecimal.ZERO;
    private boolean isLoggedIn;

    public Customer(String bankAccountNo, String password,String phoneNumber, String accountBalance) {
        isLoggedIn = false;
        this.bankAccountNo = bankAccountNo;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountBalance = new BigDecimal(accountBalance);
    }

    public String getAccountNo() {
        return this.bankAccountNo;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPassword() {
        return this.password;
    }
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean comparePassword(String password) {
        return this.password.equals(password);
    }
}
