package com.stepuro.customer.service.impl;

import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.context.init.AccountTransferContextInitializer;
import com.stepuro.customer.context.init.CardTransferContextInitializer;
import com.stepuro.customer.service.AccountService;
import com.stepuro.customer.service.CardService;
import com.stepuro.customer.service.TransferAmountService;
import com.stepuro.customer.validation.TransferValidationProcessor;

public class TransferAmountServiceImpl implements TransferAmountService {
    private final TransferValidationProcessor transferValidationProcessor;
    private final CardTransferContextInitializer cardTransferContextInitializer;
    private final AccountTransferContextInitializer accountTransferContextInitializer;
    private final CardService cardService;
    private final AccountService accountService;

    public TransferAmountServiceImpl(
            TransferValidationProcessor transferValidationProcessor,
            CardTransferContextInitializer cardTransferContextInitializer,
            AccountTransferContextInitializer accountTransferContextInitializer,
            CardService cardService,
            AccountService accountService
    ) {
        this.transferValidationProcessor = transferValidationProcessor;
        this.cardTransferContextInitializer = cardTransferContextInitializer;
        this.accountTransferContextInitializer = accountTransferContextInitializer;
        this.cardService = cardService;
        this.accountService = accountService;
    }


    @Override
    public void transferCardAmount(TransferEntity transferEntity) {
        var transferValidationContext = cardTransferContextInitializer.initialize(transferEntity);

        transferValidationProcessor.process(transferValidationContext);

        cardService.transferAmount(transferEntity);
    }

    @Override
    public void transferAccountAmount(TransferEntity transferEntity) {
        var transferValidationContext = accountTransferContextInitializer.initialize(transferEntity);

        transferValidationProcessor.process(transferValidationContext);

        accountService.transferAmount(transferEntity);
    }
}
