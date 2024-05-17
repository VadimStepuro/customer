package com.stepuro.customer.validation.validator;

import com.stepuro.customer.api.exceptions.StatusException;
import com.stepuro.customer.context.TransferValidationContext;
import com.stepuro.customer.model.enums.TransferEntityStatus;

public class StatusValidator implements TransferValidator {
    @Override
    public void validateTransfer(TransferValidationContext transferValidationContext) {
        TransferEntityStatus sourceStatus = transferValidationContext.getSourceStatus();
        TransferEntityStatus targetStatus = transferValidationContext.getTargetStatus();

        if (!sourceStatus.isActive() || !targetStatus.isActive()){
            throw new StatusException("Source entity is not active");
        }
    }
}
