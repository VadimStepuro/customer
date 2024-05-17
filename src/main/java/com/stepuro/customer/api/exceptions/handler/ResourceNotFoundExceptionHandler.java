package com.stepuro.customer.api.exceptions.handler;

import com.stepuro.customer.api.dto.errors.ApiError;
import com.stepuro.customer.api.exceptions.NoContentException;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ResourceNotFoundExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiError handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    public ApiError handleNoContentException(NoContentException ex) {
        return new ApiError(HttpStatus.NO_CONTENT, ex.getMessage(), ex);
    }
}
