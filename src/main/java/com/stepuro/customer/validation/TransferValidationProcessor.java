package com.stepuro.customer.validation;

import com.stepuro.customer.api.exceptions.ComplexTransferValidationException;
import com.stepuro.customer.api.exceptions.TransferValidationException;
import com.stepuro.customer.validation.validator.TransferValidator;
import com.stepuro.customer.context.TransferValidationContext;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class TransferValidationProcessor {

    private final List<TransferValidator> validators;

    public void process(TransferValidationContext validationContext) {
        var complexTransferValidationException = new ComplexTransferValidationException();

        validators.forEach(validator -> {
            try {
                validator.validateTransfer(validationContext);
            }
            catch (TransferValidationException e){
                complexTransferValidationException.addSubException(e);
            }
        });

        if(!complexTransferValidationException.getSubExceptions().isEmpty()){
            throw complexTransferValidationException;
        }
    }
}
