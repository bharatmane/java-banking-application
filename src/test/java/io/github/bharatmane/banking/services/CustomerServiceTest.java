package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
import io.github.bharatmane.banking.exception.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerServiceTest {

    private Customer customer;
    private List<Customer> customers;
    private CustomerService customerService;

    @BeforeEach
    public void init() {
        customers = new ArrayList<Customer>(Arrays.asList(
                new Customer("1234","jack@123","+919632104315","100.1"),
                new Customer("1235","jill@123","+919632104324","200.2")
        ));
        customer = new Customer("1234","jack@123","+919632104315","100.1");
        customerService = new CustomerService(customers);
    }
    @Test
    @DisplayName("Should login with valid credentials")
    void  shouldLoginWhenCorrectCredentialsGiven() throws InvalidCredentialsException {
        String accountNo = "1234";
        String password = "jack@123";
        String expectedBalance = "235.5";
        //Given

        //When
        customer = customerService.login(accountNo,password);

        //Then
        assertThat(customer.isLoggedIn()).isTrue();
    }
    @Test
    @DisplayName("Should throw Invalid Credentials Exception when invalid credentials given")
    void  shouldThrowExceptionWhenInvalidCredentialsGiven() {
        String accountNo = "1234";
        String password = "jack@125";

        //Given

        //When
        assertThrows(InvalidCredentialsException.class, () -> {
            customerService.login(accountNo,password);
        });

        //Then

    }

    @Test
    @DisplayName("Should increase balance when deposited")
    void  shouldIncreaseBalanceWhenDeposited() {

        String expectedBalance = "235.5";
        //Given

        //When
        customerService.deposit(customer,"135.4");

        //Then
        assertThat(customer.getAccountBalance()).isEqualTo(expectedBalance);
    }

    @Test
    @DisplayName("Should decrease balance when withdrawal")
    void  shouldDecreaseBalanceWhenWithdrawal() throws InsufficientFundsException {
        String expectedBalance = "23.5";
        //Given

        //When
        customerService.withdraw(customer,"76.6");

        //Then
        assertThat(customer.getAccountBalance()).isEqualTo(expectedBalance);

    }

    @Test
    @DisplayName("Should show insufficient funds when withdrawal is more than balance")
    void  shouldShowInsufficientFundsWhenWithdrawalIsMoreThanBalance() {

        //Given

        //When
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            customerService.withdraw(customer,"200");
        });

        //Then

    }

}
