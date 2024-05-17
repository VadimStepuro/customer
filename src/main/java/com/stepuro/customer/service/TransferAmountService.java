package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.TransferEntity;

public interface TransferAmountService{
    void transferCardAmount(TransferEntity transferEntity);

    void transferAccountAmount(TransferEntity transferEntity);
}
