package com.stepuro.customer.context.init;

import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.api.dto.TransferableEntity;
import com.stepuro.customer.context.TransferValidationContext;
import com.stepuro.customer.service.TransferableEntityService;

public class TransferContextInitializer<T extends TransferableEntity> {
    private final TransferableEntityService<T> transferableEntityService;

    public TransferContextInitializer(TransferableEntityService<T> transferableEntityService) {
        this.transferableEntityService = transferableEntityService;
    }

    public TransferValidationContext initialize(TransferEntity transferEntity) {

        T source = transferableEntityService.findByNumber(transferEntity.getSourceNumber());
        T target = transferableEntityService.findByNumber(transferEntity.getDestinationNumber());

        return TransferValidationContext.builder()
                .transferEntity(transferEntity)
                .sourceBalance(source.getAmount())
                .sourceUserId(source.getUserId())
                .sourceStatus(source.getEntityStatus())
                .targetStatus(target.getEntityStatus())
                .build();
    }
}
