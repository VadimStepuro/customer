package com.stepuro.customer.api.exceptions.handler;

import com.stepuro.customer.api.dto.ApiError;
import com.stepuro.customer.api.dto.ApiSubError;
import com.stepuro.customer.api.dto.ApiValidationError;
import com.stepuro.customer.api.exceptions.EqualNumberException;
import com.stepuro.customer.api.exceptions.NotEnoughMoneyException;
import com.stepuro.customer.api.exceptions.StatusException;
import com.stepuro.customer.api.exceptions.UserIdDoesntMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class ValidationHandlers {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ApiSubError> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            Object rejectedValue = error.getRejectedValue();
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            String objectName = error.getObjectName();

            errors.add(new ApiValidationError(objectName, fieldName, rejectedValue, errorMessage));
        });

        StringBuilder returnMessage = new StringBuilder();
        ex.getAllErrors().forEach((error) -> {
            returnMessage.append(error.getDefaultMessage());
            returnMessage.append(" (");
            returnMessage.append(error.getObjectName());
            returnMessage.append(") ");
        });

        return new ApiError(HttpStatus.BAD_REQUEST, returnMessage.toString(), ex, errors);
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
}