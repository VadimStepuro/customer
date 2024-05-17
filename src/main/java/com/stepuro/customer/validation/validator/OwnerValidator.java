package com.stepuro.customer.validation.validator;

import com.stepuro.customer.api.exceptions.UserIdDoesntMatchException;
import com.stepuro.customer.context.TransferValidationContext;

public class OwnerValidator implements TransferValidator {
    @Override
    public void validateTransfer(TransferValidationContext transferValidationContext) {
        Integer sourceCardUserId = transferValidationContext.getSourceUserId();
        Integer transferUserId = transferValidationContext.getTransferEntity().getUserId();

        if(!sourceCardUserId.equals(transferUserId)){
            throw new UserIdDoesntMatchException("User isn't owner of the entity " +
                    "(individual id: " + transferUserId +
                    ", number: " + transferValidationContext.getTransferEntity().getSourceNumber() + ")");
        }
    }
}
