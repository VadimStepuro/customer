package com.stepuro.customer.api.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class ComplexTransferValidationException extends RuntimeException{
    private final List<TransferValidationException> subExceptions;

    public ComplexTransferValidationException(){
        subExceptions = new ArrayList<>();
    }

    public ComplexTransferValidationException(Collection<TransferValidationException> subExceptions) {
        super("There are exceptions in transfer validation");

        this.subExceptions = new ArrayList<>(subExceptions);
    }

    public void addSubException(TransferValidationException subException) {
        subExceptions.add(subException);
    }
}
