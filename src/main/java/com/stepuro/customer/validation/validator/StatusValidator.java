package com.stepuro.customer.validation.validator;

import com.stepuro.customer.api.exceptions.StatusException;
import com.stepuro.customer.context.TransferValidationContext;
import com.stepuro.customer.model.enums.AccountStatus;
import com.stepuro.customer.model.enums.CardStatus;

public class StatusValidator implements TransferValidator {
    @Override
    public void validateTransfer(TransferValidationContext transferValidationContext) {
        String sourceStatus = transferValidationContext.getSourceStatus();
        String targetStatus = transferValidationContext.getTargetStatus();

        String cardPositiveStatus = CardStatus.ACTIVE.toString();
        String accountPositiveStatus = AccountStatus.ACTIVE.toString();

        if (sourceStatus.equals(cardPositiveStatus) || sourceStatus.equals(accountPositiveStatus)){
            throw new StatusException("Source entity is not active");
        }

        if (targetStatus.equals(cardPositiveStatus) || targetStatus.equals(accountPositiveStatus)){
            throw new StatusException("Target entity is not active");
        }
    }
}
