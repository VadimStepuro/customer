package com.stepuro.customer.api.exceptions.handler;

import com.stepuro.customer.api.dto.errors.ApiError;
import com.stepuro.customer.api.dto.errors.ApiTransferError;
import com.stepuro.customer.api.dto.errors.ApiValidationError;
import com.stepuro.customer.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


@RestControllerAdvice
public class ValidationHandlers {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            Object rejectedValue = error.getRejectedValue();
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            String objectName = error.getObjectName();

            apiError.addSubError(new ApiValidationError(objectName, fieldName, rejectedValue, errorMessage));
        });

        return apiError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public ApiError handleSqlExceptions(SQLException ex){
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotEnoughMoneyException.class)
    public ApiError handleNotEnoughMoneyException(NotEnoughMoneyException ex){
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EqualNumberException.class)
    public ApiError handleEqualNumberException(EqualNumberException ex){
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserIdDoesntMatchException.class)
    public ApiError handleUserIdDoesntMatchException(UserIdDoesntMatchException ex){
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusException.class)
    public ApiError handleStatusException(StatusException ex){
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransferValidationException.class)
    public ApiError handleTransferValidationException(TransferValidationException ex){
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ComplexTransferValidationException.class)
    public ApiError handleComplexTransferValidationException(ComplexTransferValidationException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);

        if(!ex.getSubExceptions().isEmpty()){
            ex.getSubExceptions()
                    .forEach(exception -> {
                                ApiTransferError subError = new ApiTransferError(
                                        exception.getMessage(),
                                        exception.getClass().getName()
                                );
                                apiError.addSubError(subError);
                            }
                    );
        }

        return apiError;
    }
}