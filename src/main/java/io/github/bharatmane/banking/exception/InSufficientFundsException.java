package io.github.bharatmane.banking.exception;

public class InSufficientFundsException extends Exception {
    public InSufficientFundsException(String errorMessage) {
        super(errorMessage);
    }
}
