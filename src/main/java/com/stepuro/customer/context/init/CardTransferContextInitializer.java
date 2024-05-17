package com.stepuro.customer.context.init;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.context.TransferValidationContext;
import com.stepuro.customer.service.CardService;

public class CardTransferContextInitializer {
    private final CardService cardService;

    public CardTransferContextInitializer(final CardService cardService) {
        this.cardService = cardService;
    }

    public TransferValidationContext initialize(TransferEntity transferEntity) {
        TransferValidationContext validationContext = new TransferValidationContext();

        CardDto sourceCard = cardService.findByNumber(transferEntity.getSourceNumber());
        CardDto targetCard = cardService.findByNumber(transferEntity.getDestinationNumber());

        validationContext.setTransferEntity(transferEntity);
        validationContext.setSourceStatus(sourceCard.getStatus());
        validationContext.setSourceBalance(sourceCard.getBalance());
        validationContext.setTargetStatus(targetCard.getStatus());
        validationContext.setSourceUserId(sourceCard.getIndividualDto().getIndividualId());

        return validationContext;
    }
}
