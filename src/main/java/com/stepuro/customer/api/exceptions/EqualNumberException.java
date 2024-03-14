package com.stepuro.customer.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EqualNumberException extends RuntimeException {
    public EqualNumberException(String message) {super(message);}
}
