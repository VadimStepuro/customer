package com.stepuro.customer.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatusException extends TransferValidationException{
    public StatusException(String message) {super(message);}
}
