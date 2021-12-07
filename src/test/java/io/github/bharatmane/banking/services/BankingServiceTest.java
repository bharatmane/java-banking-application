package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
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

class BankingServiceTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    private Prompter prompter;
    private CustomerService customerService;


    @BeforeEach
    public void init() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        List<Customer> customers = new ArrayList<>(Arrays.asList(
                new Customer("1234", "jack@123", "+919632104315", "100.1"),
                new Customer("1235", "jill@123", "+919632104324", "200.2")
        ));
        customerService = new CustomerService(customers);
    }

    @Test
    @DisplayName("Should have valid list of customers when initialized")
    void  shouldHaveValidListOfCustomersWhenInitialized(){
        Scanner scanner = new Scanner("1\n1234\njack@123\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService, prompter);

        //When

        //Then
        assertThat(bankingService.getCustomerCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should be logged in with correct credentials")
    void  shouldBeLoggedInWithCorrectCredentials() {
        Scanner scanner = new Scanner("1\n1234\njack@123\n1\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();

        //Then
        assertThat(customer.isLoggedIn()).isTrue();
    }

    @Test
    @DisplayName("Should show invalid login attempt when logged in with incorrect credentials")
    void  shouldShowInvalidLoginAttemptWhenLoggedInWithIncorrectCredentials()  {
        Scanner scanner = new Scanner("1\n1234\njack@125\n1");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter);

        //When
        bankingService.greet();
        bankingService.login();

        //Then
        assertThat(outputStream.toString()).contains("Invalid user name or password");
    }

    @Test
    @DisplayName("Should be logged out when log out option chosen")
    void  shouldBeLoggedOutWhenLogoutOptionChosen() throws InsufficientFundsException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n4\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);

        //Then
        assertThat(customer.isLoggedIn()).isFalse();
    }

    @Test
    @DisplayName("Should decrease balance of customer 1 when transferred to customer 2")
    void  shouldDecreaseBalanceOfCustomer1WhenTransferredToCustomer2() throws InsufficientFundsException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n1\n100\n3\n1235\n100\n4");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);

        //Then
        assertThat(customer.getAccountBalance()).isEqualTo("100.1");

    }

    @Test
    @DisplayName("Should increase balance of customer 2 when transferred to customer 2")
    void  shouldIncreaseBalanceOfCustomer2WhenTransferredToCustomer2() throws InsufficientFundsException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n1\n100\n3\n1235\n100\n4");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);
        Customer customer2 = bankingService.getCustomer("1235");

        //Then
        assertThat(customer2.getAccountBalance()).isEqualTo("300.2");

    }
}
