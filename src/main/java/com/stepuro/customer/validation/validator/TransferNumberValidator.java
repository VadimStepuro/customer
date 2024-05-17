package com.stepuro.customer.validation.validator;

import com.stepuro.customer.api.exceptions.EqualNumberException;
import com.stepuro.customer.context.TransferValidationContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransferNumberValidator implements TransferValidator {

    @Override
    public void validateTransfer(TransferValidationContext transferValidationContext) {
        String sourceNumber = transferValidationContext.getTransferEntity().getSourceNumber();
        String destinationNumber = transferValidationContext.getTransferEntity().getDestinationNumber();

        if(sourceNumber.equals(destinationNumber))
            throw new EqualNumberException("Source number and destination number are the same");
    }
}
