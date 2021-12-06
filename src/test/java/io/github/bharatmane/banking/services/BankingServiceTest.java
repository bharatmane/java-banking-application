package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InSufficientFundsException;
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

public class BankingServiceTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private Prompter prompter;
    private List<Customer> customers;


    @BeforeEach
    public void init() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        customers = new ArrayList<Customer>(Arrays.asList(
                new Customer("1234","jack@123","100.1"),
                new Customer("1235","jill@123","200.2")
        ));

    }


    @Test
    @DisplayName("Should have valid list of customers when initialized")
    void  shouldHaveValidListOfCustomersWhenInitialized(){
        Scanner scanner = new Scanner("1\n1234\njack@123\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customers, prompter);

        //When

        //Then
        assertThat(bankingService.getCustomerCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should show accounts menu when logged in with correct credentials")
    void  shouldShowAccountsMenuWhenLoggedInWithCorrectCredentials() {
        Scanner scanner = new Scanner("1\n1234\njack@123\n1\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customers,prompter);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();

        //Then
        assertThat(customer.isLoggedIn());
    }

    @Test
    @DisplayName("Should show invalid login attempt when logged in with incorrect credentials")
    void  shouldShowInvalidLoginAttemptWhenLoggedInWithIncorrectCredentials(){
        Scanner scanner = new Scanner("1\njack\njack@125\n1");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customers,prompter);

        //When
        bankingService.greet();
        bankingService.login();

        //Then
        assertThat(outputStream.toString()).contains("Invalid user name or password");
    }

}
