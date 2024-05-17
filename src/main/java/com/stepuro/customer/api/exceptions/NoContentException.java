package com.stepuro.customer.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends TransferValidationException {
    public NoContentException(String message) {super(message);}
}
