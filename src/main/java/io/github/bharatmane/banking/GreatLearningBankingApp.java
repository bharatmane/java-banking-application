package io.github.bharatmane.banking;

import io.github.bharatmane.banking.entity.Customer;
import io.github.bharatmane.banking.exception.InsufficientFundsException;
import io.github.bharatmane.banking.exception.InvalidMenuChoiceException;
import io.github.bharatmane.banking.exception.InvalidOtpException;
import io.github.bharatmane.banking.services.BankingService;
import io.github.bharatmane.banking.services.CustomerService;
import io.github.bharatmane.banking.services.OtpService;
import io.github.bharatmane.banking.wrapper.UnirestWrapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class GreatLearningBankingApp {
    public static void main(String[] args) throws InvalidMenuChoiceException, InsufficientFundsException, InvalidOtpException {
        List<Customer> customers = new ArrayList<>(Arrays.asList(
                new Customer("1234", "jack@123", "+919632104315", "100.1"),
                new Customer("1235", "jill@123", "+919632104324", "200.2")
        ));
        CustomerService customerService = new CustomerService(customers);
        Scanner scanner = new Scanner(System.in);
        Prompter prompter = new Prompter(System.out,scanner);
        OtpService otpService = new OtpService(new UnirestWrapper());

        BankingService bankingService = new BankingService(customerService,prompter,otpService);
        bankingService.greet();
        Customer customer = bankingService.login();
        bankingService.operate(customer);

    }
}
