package com.stepuro.customer.validation.validator;

import com.stepuro.customer.api.exceptions.NotEnoughMoneyException;
import com.stepuro.customer.context.TransferValidationContext;

import java.math.BigDecimal;

public class BalanceValidator implements TransferValidator {
    @Override
    public void validateTransfer(TransferValidationContext transferValidationContext) {
        BigDecimal sourceCardBalance = transferValidationContext.getSourceBalance();
        BigDecimal transferAmount = transferValidationContext.getTransferEntity().getAmount();

        if(sourceCardBalance.compareTo(transferAmount) < 0) {
            throw new NotEnoughMoneyException("Not enough money on number " +
                    transferValidationContext.getTransferEntity().getSourceNumber() +
                    " to transfer " + transferAmount);
        }
    }
}
