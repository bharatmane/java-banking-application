package io.github.bharatmane.banking.entity;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.exception.InSufficientFundsException;
import io.github.bharatmane.banking.services.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {

    private Customer customer;


    @BeforeEach
    public void init() {
        customer = new Customer("1234","jack@123","100.1");
    }

    @Test
    @DisplayName("Should increase balance when deposited")
    void  shouldIncreaseBalanceWhenDeposited() throws InSufficientFundsException {

        String expectedBalance = "235.5";
        //Given

        //When
        customer.deposit("135.4");

        //Then
        assertThat(customer.getAccountBalance()).isEqualTo(expectedBalance);
    }

    @Test
    @DisplayName("Should decrease balance when withdrawal")
    void  shouldDecreaseBalanceWhenWithdrawal() throws InSufficientFundsException {
        String expectedBalance = "23.5";
        //Given

        //When
        customer.withdraw("76.6");

        //Then
        assertThat(customer.getAccountBalance()).isEqualTo(expectedBalance);

    }

    @Test
    @DisplayName("Should show insufficient funds when withdrawal is more than balance")
    void  shouldShowInsufficientFundsWhenWithdrawalIsMoreThanBalance() {

        //Given

        //When
        Exception exception = assertThrows(InSufficientFundsException.class, () -> {
            customer.withdraw("200");
        });

        //Then

    }

}
