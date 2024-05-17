package com.stepuro.customer.api.exceptions;


public class TransferValidationException extends RuntimeException {
    public TransferValidationException(String message) {
        super(message);
    }
}
