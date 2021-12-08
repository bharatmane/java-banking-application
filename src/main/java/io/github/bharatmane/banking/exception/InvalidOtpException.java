package io.github.bharatmane.banking.exception;

public class InvalidOtpException extends Exception{
    public InvalidOtpException(String errorMessage) {
        super(errorMessage);
    }
}
