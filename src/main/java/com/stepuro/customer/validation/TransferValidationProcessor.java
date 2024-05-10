package com.stepuro.customer.validation;

import com.stepuro.customer.api.exceptions.TransferValidationException;
import com.stepuro.customer.validation.validator.TransferValidator;
import com.stepuro.customer.context.TransferValidationContext;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TransferValidationProcessor {

    private final List<TransferValidator> validators;

    public void process(TransferValidationContext validationContext) {
        Set<Exception> exceptions = new HashSet<>();

        validators.forEach(validator -> {
            try {
                validator.validateTransfer(validationContext);
            }
            catch (TransferValidationException e){
                exceptions.add(e);
            }
        });

        if(!exceptions.isEmpty()){
            String message = extractErrorsText(exceptions);
            throw new TransferValidationException(message);
        }
    }

    private String extractErrorsText(final Collection<Exception> errors) {
        return errors
                .stream()
                .map(Throwable::getMessage)
                .collect(Collectors.joining(", "));
    }
}
