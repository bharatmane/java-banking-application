package io.github.bharatmane.banking.entity;

import io.github.bharatmane.banking.exception.InSufficientFundsException;

import java.math.BigDecimal;

public class Customer {

    private final String password;
    private final String bankAccountNo;
    private BigDecimal accountBalance = BigDecimal.ZERO;
    private boolean isLoggedIn;

    public Customer(String bankAccountNo, String password,String accountBalance) {
        isLoggedIn = false;
        this.password = password;
        this.bankAccountNo = bankAccountNo;
        this.accountBalance = new BigDecimal(accountBalance);;
    }

    public String getAccountNo() {
        return this.bankAccountNo;
    }

    public boolean isValidCredentials(String password) {
        isLoggedIn  = this.password.equals(password);
        return isLoggedIn;
    }

    public String getAccountBalance() {
        return accountBalance.toString();
    }


    public void withdraw(String withdrawAmount) throws InSufficientFundsException {
        BigDecimal amount = new BigDecimal(withdrawAmount);
        if(checkAccountForAvailableFunds(withdrawAmount) == true) {
            accountBalance = accountBalance.subtract(new BigDecimal(withdrawAmount));
        }
        else{
            throw new InSufficientFundsException("Insufficient funds, please try with lower amount");
        }
    }

    private boolean checkAccountForAvailableFunds(String withdrawAmount)
    {
        boolean isAvailable;
        BigDecimal bigDecimalWithdrawAmount = new BigDecimal(withdrawAmount);

        if (bigDecimalWithdrawAmount.compareTo(accountBalance) == 1)
        {
            isAvailable = false;
        }
        else
        {
            isAvailable = true;
        }
        return isAvailable;
    }


    public void deposit(String amount) {
        accountBalance = accountBalance.add(new BigDecimal(amount));
    }

    public void transfer() {
    }

    public void logout() {
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
