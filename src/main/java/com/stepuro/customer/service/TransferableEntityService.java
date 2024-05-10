package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.api.dto.TransferableEntity;

public interface TransferableEntityService<T extends TransferableEntity> {
    void transferAmount(TransferEntity transferEntity);
    T findByNumber(String number);
}
