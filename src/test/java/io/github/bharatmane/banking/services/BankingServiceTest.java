package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.Prompter;
import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
import io.github.bharatmane.banking.exception.InvalidMenuChoiceException;
import io.github.bharatmane.banking.exception.InvalidOtpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BankingServiceTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    private Prompter prompter;
    private CustomerService customerService;
    @Mock
    OtpService mockOtpService;

    @BeforeEach
    public void init() {

        mockOtpService = mock(OtpService.class);

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
        BankingService bankingService = new BankingService(customerService, prompter,mockOtpService);

        //When

        //Then
        assertThat(bankingService.getCustomerCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should be logged in with correct credentials")
    void  shouldBeLoggedInWithCorrectCredentials() throws InvalidMenuChoiceException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n1\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();

        //Then
        assertThat(customer.isLoggedIn()).isTrue();
    }

    @Test
    @DisplayName("Should exit with message when chosen option 2")
    void  shouldExitWithMessageWhenChosenOptionAs2() throws InvalidMenuChoiceException {
        Scanner scanner = new Scanner("2\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();

        //Then
        assertThat(outputStream.toString()).contains("Thank you for banking with Indian Bank");
    }

    @Test
    @DisplayName("Should show invalid login attempt when logged in with incorrect credentials")
    void  shouldShowInvalidLoginAttemptWhenLoggedInWithIncorrectCredentials() throws InvalidMenuChoiceException {
        Scanner scanner = new Scanner("1\n1234\njack@125\n1");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        bankingService.login();

        //Then
        assertThat(outputStream.toString()).contains("Invalid user name or password");
    }

    @Test
    @DisplayName("Should be logged out when log out option chosen")
    void  shouldBeLoggedOutWhenLogoutOptionChosen() throws InsufficientFundsException, InvalidMenuChoiceException, InvalidOtpException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n4\n");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);

        //Then
        assertThat(customer.isLoggedIn()).isFalse();
    }

    @Test
    @DisplayName("Should decrease balance when withdrawn")
    void  shouldDecreaseBalanceBalanceWhenWithdrawn() throws InsufficientFundsException, InvalidMenuChoiceException, InvalidOtpException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n2\n50\n4");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);

        //Then
        assertThat(customer.getAccountBalance()).isEqualTo("50.1");

    }


    @Test
    @DisplayName("Should decrease balance of customer 1 when transferred to customer 2")
    void  shouldDecreaseBalanceOfCustomer1WhenTransferredToCustomer2() throws InsufficientFundsException, InvalidMenuChoiceException, InvalidOtpException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n1\n100\n3\n1235\n100\n12345\n4");
        prompter = new Prompter(printStream,scanner);
        mockOtpService();

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);

        //Then
        assertThat(customer.getAccountBalance()).isEqualTo("100.1");

    }

    private void mockOtpService() throws InvalidOtpException {
        when(mockOtpService.generateOTP()).thenReturn(null);
        when(mockOtpService.sendOTP(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(null);
        when(mockOtpService.validateOtp(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(true);
    }

    @Test
    @DisplayName("Should throw exception when transferred more than balance")
    void  shouldThrowInsufficientFundsExceptionWhenTransferredMoreThanBalance() throws InvalidMenuChoiceException, InvalidOtpException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n3\n1235\n200\n12345\n4");
        prompter = new Prompter(printStream,scanner);

        mockOtpService();

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();

        //Then
        assertThrows(InsufficientFundsException.class, () -> bankingService.operate(customer));
    }

    @Test
    @DisplayName("Should increase balance of customer 2 when transferred to customer 2")
    void  shouldIncreaseBalanceOfCustomer2WhenTransferredToCustomer2() throws InsufficientFundsException, InvalidMenuChoiceException, InvalidOtpException {
        Scanner scanner = new Scanner("1\n1234\njack@123\n1\n100\n3\n1235\n100\n12345\n4");
        prompter = new Prompter(printStream,scanner);
        mockOtpService();
        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);
        Customer customer2 = bankingService.getCustomer("1235");

        //Then
        assertThat(customer2.getAccountBalance()).isEqualTo("300.2");

    }

    @Test
    @DisplayName("Should throw invalid option exception when entered incorrect login option")
    void  shouldThrowInvalidOptionExceptionWhenIncorrectOptionGiven() {
        Scanner scanner = new Scanner("4\n1234\njack@123\n3\n1235\n200\n4");
        prompter = new Prompter(printStream,scanner);

        //Given
        BankingService bankingService = new BankingService(customerService,prompter,mockOtpService);

        //When
        bankingService.greet();
        //Then
        assertThrows(InvalidMenuChoiceException.class, () -> {
            Customer customer = bankingService.login();
        });
    }
}
