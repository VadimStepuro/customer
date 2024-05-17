package com.stepuro.customer.validation.validator;

import com.stepuro.customer.context.TransferValidationContext;

public interface TransferValidator {

    void validateTransfer(TransferValidationContext transferValidationContext);
}
