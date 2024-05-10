package com.stepuro.customer.service.impl;

import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.api.dto.TransferableEntity;
import com.stepuro.customer.context.TransferValidationContext;
import com.stepuro.customer.context.init.TransferContextInitializer;
import com.stepuro.customer.service.TransferAmountService;
import com.stepuro.customer.service.TransferableEntityService;
import com.stepuro.customer.validation.TransferValidationProcessor;

public class TransferAmountServiceImpl<T extends TransferableEntity> implements TransferAmountService {
    private final TransferValidationProcessor transferValidationProcessor;
    private final TransferContextInitializer<T> transferContextInitializer;
    private final TransferableEntityService<T> accountService;

    public TransferAmountServiceImpl(
            TransferValidationProcessor transferValidationProcessor,
            TransferContextInitializer<T> transferContextInitializer,
            TransferableEntityService<T> transferableEntityService
    ) {
        this.transferValidationProcessor = transferValidationProcessor;
        this.transferContextInitializer = transferContextInitializer;
        this.accountService = transferableEntityService;
    }


    @Override
    public void transferAmount(TransferEntity transferEntity) {
        TransferValidationContext transferValidationContext = transferContextInitializer.initialize(transferEntity);

        transferValidationProcessor.process(transferValidationContext);

        accountService.transferAmount(transferEntity);
    }
}
