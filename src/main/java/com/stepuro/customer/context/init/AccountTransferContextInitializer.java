package com.stepuro.customer.context.init;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.context.TransferValidationContext;
import com.stepuro.customer.service.AccountService;

public class AccountTransferContextInitializer {
    private final AccountService accountService;

    public AccountTransferContextInitializer(final AccountService accountService) {
        this.accountService = accountService;
    }

    public TransferValidationContext initialize(TransferEntity transferEntity) {
        TransferValidationContext validationContext = new TransferValidationContext();

        AccountDto sourceAccount = accountService.findByNumber(transferEntity.getSourceNumber());
        AccountDto targetAccount = accountService.findByNumber(transferEntity.getDestinationNumber());

        validationContext.setTransferEntity(transferEntity);
        validationContext.setSourceBalance(sourceAccount.getBalance());
        validationContext.setSourceStatus(sourceAccount.getStatus().toString());
        validationContext.setTargetStatus(targetAccount.getStatus().toString());

        return validationContext;
    }
}
