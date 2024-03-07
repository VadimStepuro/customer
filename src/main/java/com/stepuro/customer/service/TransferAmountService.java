package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.PaymentOrderEntityDto;

public interface TransferAmountService {
    void transferAmount(PaymentOrderEntityDto paymentOrderEntityDto);
}
